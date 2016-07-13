package cn.lts.mobile.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lts.common.constant.WeiXinConstants;
import cn.lts.common.exception.AesException;
import cn.lts.common.util.MD5;
import cn.lts.common.util.MessageUtil;
import cn.lts.common.util.SignUtil;
import cn.lts.common.wx.WxConfig;
import cn.lts.mobile.aes.WXBizMsgCrypt;
import cn.lts.mobile.service.CoreServiceFacade;
import cn.lts.mobile.util.ConfigInfo;
import cn.lts.mobile.util.JSONMessage;
import cn.lts.mobile.web.core.BaseController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 核心处理业务类
 * 接收消息/回复消息/微信通知回调/微信NATIVE扫码回调
 *
 * @author zhoujianyong
 *         2015年4月24日-下午5:05:33
 */
@Controller
public class CoreController extends BaseController {
    private transient final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CoreServiceFacade coreServiceFacade;

    @Autowired
    private WxConfig wxConfig;

    /**
     * 配置验证有效性
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = {"/coreJoin.do"}, method = RequestMethod.GET)
    public void coreJoinGet(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        String result = null;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(wxConfig.getToken(), WeiXinConstants.EncodingAESKey, wxConfig.getAppId());
            result = wxcpt.VerifyURL(signature, timestamp, nonce, echostr);
        } catch (AesException e) {
            logger.error(e.getMessage(), e);
        }
        if (result == null) {
            result = wxConfig.getToken();
        }
        out.print(result);
        out.close();
        out = null;
    }

    /**
     * 接收消息/回复消息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = {"/coreJoin.do"}, method = RequestMethod.POST)
    public void coreJoinPost(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        //从请求中读取整个post数据
        InputStream inputStream = request.getInputStream();
        String postData = IOUtils.toString(inputStream, "UTF-8");

        String msg = "";
        WXBizMsgCrypt wxcpt = null;
        try {
            wxcpt = new WXBizMsgCrypt(wxConfig.getToken(), WeiXinConstants.EncodingAESKey, wxConfig.getAppId());
            //解密消息
            msg = wxcpt.DecryptMsg(signature, timestamp, nonce, postData);
        } catch (AesException e) {
            logger.error(e.getMessage(), e);
        }
        // 调用核心业务类接收消息、处理消息
        String respMessage = coreServiceFacade.processRequest(msg);
        if (StringUtils.isNotBlank(respMessage)) {
            String encryptMsg = "";
            try {
                encryptMsg = wxcpt.EncryptMsg(respMessage, timestamp, nonce);
            } catch (AesException e) {
                logger.error(e.getMessage(), e);
            }
            // 响应消息
            PrintWriter out = response.getWriter();
            out.print(encryptMsg);
            out.close();
        }
    }

    /**
     * 预支付初始化
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/prepayInit.do"}, method = RequestMethod.POST)
    @ResponseBody
    public String prepayInit(HttpServletRequest request) {
        JSONMessage jsonMessage = JSONMessage.newMessageOnSuccess();
         
        return jsonMessage.toJSONString();
    }

    /**
     * 微信Native支付扫码之后回调
     *
     * @param request
     */
    @RequestMapping(value = {"/notifyForNatvie"})
    public void notifyForNatvie(HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        InputStream inputStream = request.getInputStream();
        String postData = IOUtils.toString(inputStream, "UTF-8");
        logger.info("扫码支付回调参数：" + postData);
        Map<String, String> resXml = null;
        String respMessage = "";
        PrintWriter out = response.getWriter();
        
        
        
        logger.info(respMessage);
        out.print(respMessage);
        out.close();
    }

    /**
     * 构建扫码错误信息
     *
     * @param nonceStr
     * @param errCodeDes
     * @return
     */
    private String createFailRespMessage(String nonceStr, String errCodeDes) {
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("appid", wxConfig.getAppId());
        signMap.put("mch_id", WeiXinConstants.MERCHANT_KEY);
        signMap.put("nonce_str", nonceStr);
        signMap.put("return_code", "SUCCESS");
        signMap.put("result_code", "FAIL");
        signMap.put("err_code_des", errCodeDes);
        String sign = MD5.MD5Encode(SignUtil.dicSort(signMap) + "&key=" + WeiXinConstants.API_SECRET).toUpperCase();
        signMap.put("sign", sign);
        return MessageUtil.mapToXml(signMap);
    }

    /**
     * 微信回调
     *
     * @param request
     */
    @RequestMapping(value = {"/notifyForWeiXin"})
    public void notifyForWeiXin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        InputStream inputStream = request.getInputStream();
        String postData = IOUtils.toString(inputStream, "UTF-8");
        logger.debug(postData);
        PrintWriter out = response.getWriter();
        try {
            Map<String, String> resXml = MessageUtil.parseXml(postData);
            String respMessage = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[" + resXml.get("return_msg") + "]]></return_msg></xml>";
            if ("SUCCESS".equals(resXml.get("return_code"))) {
                //preHandle(resXml);
                respMessage = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><result_code><![CDATA[SUCCESS]]></result_code><is_subscribe><![CDATA[Y]]></is_subscribe></xml>";
                out.print(respMessage);
            } else {
                out.print(respMessage);
                logger.error(resXml.get("return_msg"));
            }
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
}