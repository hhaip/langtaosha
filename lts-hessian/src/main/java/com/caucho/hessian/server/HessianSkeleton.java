/*
 * Copyright (c) 2001-2004 Caucho Technology, Inc.  All rights reserved.
 *
 * The Apache Software License, Version 1.1
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Caucho Technology (http://www.caucho.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Hessian", "Resin", and "Caucho" must not be used to
 *    endorse or promote products derived from this software without prior
 *    written permission. For written permission, please contact
 *    info@caucho.com.
 *
 * 5. Products derived from this software may not be called "Resin"
 *    nor may "Resin" appear in their names without prior written
 *    permission of Caucho Technology.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL CAUCHO TECHNOLOGY OR ITS CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author Scott Ferguson
 */

package com.caucho.hessian.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.services.server.AbstractSkeleton;
import com.caucho.services.server.ServiceContext;

/**
 * Proxy class for Hessian services.
 */
public class HessianSkeleton extends AbstractSkeleton {
  private static final Logger log
    = Logger.getLogger(HessianSkeleton.class.getName());
  
  private Object _service;
  
  /**
   * Create a new hessian skeleton.
   *
   * @param service the underlying service object.
   * @param apiClass the API interface
   */
  public HessianSkeleton(Object service, Class apiClass)
  {
    super(apiClass);

    if (service == null)
      service = this;

    _service = service;

    if (! apiClass.isAssignableFrom(service.getClass()))
      throw new IllegalArgumentException("Service " + service + " must be an instance of " + apiClass.getName());
  }
  
  /**
   * Create a new hessian skeleton.
   *
   * @param service the underlying service object.
   * @param apiClass the API interface
   */
  public HessianSkeleton(Class apiClass)
  {
    super(apiClass);
  }

  /**
   * Invoke the object with the request from the input stream.
   *
   * @param in the Hessian input stream
   * @param out the Hessian output stream
   */
  public void invoke(AbstractHessianInput in, AbstractHessianOutput out)
    throws Exception
  {
    invoke(_service, in, out);
  }

  /**
   * Invoke the object with the request from the input stream.
   *
   * @param in the Hessian input stream
   * @param out the Hessian output stream
   */
  public void invoke(Object service,
		     AbstractHessianInput in,
		     AbstractHessianOutput out)
    throws Exception
  {
    ServiceContext context = ServiceContext.getContext();

    // backward compatibility for some frameworks that don't read
    // the call type first
    in.skipOptionalCall();

    // Hessian 1.0 backward compatibility
    String header;
    while ((header = in.readHeader()) != null) {
      Object value = in.readObject();

      context.addHeader(header, value);
    }

    String methodName = in.readMethod();
    int argLength = in.readMethodArgLength();
    
    Method method;
    
    method = getMethod(methodName + "__" + argLength);

    if (method == null)
      method = getMethod(methodName);

    if (method == null) {
      out.writeFault("NoSuchMethodException",
		     "The service has no method named: " + in.getMethod(),
		     null);
      out.close();
      return;
    }
    else if ("_hessian_getAttribute".equals(methodName)) {
      String attrName = in.readString();
      in.completeCall();

      String value = null;

      if ("java.api.class".equals(attrName))
	value = getAPIClassName();
      else if ("java.home.class".equals(attrName))
	value = getHomeClassName();
      else if ("java.object.class".equals(attrName))
	value = getObjectClassName();

      out.writeReply(value);
      out.close();
      return;
    }

    Class []args = method.getParameterTypes();

    if (argLength != args.length && argLength >= 0) {
      out.writeFault("NoSuchMethod",
		     "method " + method + " argument length mismatch, received length=" + argLength,
		     null);
      out.close();
      return;
    }
    
    Object []values = new Object[args.length];

    for (int i = 0; i < args.length; i++) {
      values[i] = in.readObject(args[i]);
    }

		Object result = null;
		try {
			result = method.invoke(service, values);
			InvokeRecorder.afterInvoke(getAPIClassSimpleName(), method, true);
		} catch (Throwable e) {
			if (e instanceof InvocationTargetException)
				e = ((InvocationTargetException) e).getTargetException();
			log.log(Level.FINE, this + " " + e.toString(), e);
			out.writeFault("ServiceException", e.getMessage(), e);
			out.close();
			InvokeRecorder.afterInvoke(getAPIClassSimpleName(), method, false);
			return;
		}
    // The complete call needs to be after the invoke to handle a
    // trailing InputStream
    in.completeCall();
    out.writeReply(result);
    out.close();
  }
}
