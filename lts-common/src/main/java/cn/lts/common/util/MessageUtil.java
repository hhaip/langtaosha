package cn.lts.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

import cn.lts.common.constant.WeiXinConstants;
import cn.lts.common.pojo.Article;
import cn.lts.common.pojo.ImageMessage;
import cn.lts.common.pojo.MusicMessage;
import cn.lts.common.pojo.NewsMessage;
import cn.lts.common.pojo.TextMessage;

/**
 * 消息工具类
 *
 */
public class MessageUtil {
	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	
	/**
	 * 请求消息类型：视频
	 */
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	/**
	 * 事件类型：ShakearoundLotteryBind(摇一摇红包绑定)
	 */
	public static final String EVENT_TYPE_SHAKEAROUNDLOTTERYBIND = "ShakearoundLotteryBind";
	
	/**
	 * 扫描事件
	 */
	public static final String EVENT_TYPE_SCAN = "SCAN";
	
	/**
	 * 群发任务事件推送
	 */
	public static final String EVENT_TYPE_JOB_FINISH = "MASSSENDJOBFINISH";
	
	/**
	 * 扫描推事件
	 */
	public static final String EVENT_TYPE_SCAN_PUSH = "scancode_push";
	
	/**
	 * 扫描推事件且提示
	 */
	public static final String EVENT_TYPE_SCAN_WAIT = "scancode_waitmsg";
	
	/**
	 * 客服消息
	 */
	public static final String TRANSFER_CUSTOMER_SERVICE = "transfer_customer_service";
	
	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(String msg)
			throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = new ByteArrayInputStream(msg.getBytes("UTF-8"));
		
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	/**
	 * map转xml
	 * @param map
	 * @return
	 */
	public static String mapToXml(Map<String, String> map){
		StringBuilder sb = new StringBuilder("<xml>\n");
		if(map != null && !map.isEmpty()){
			for(String key : map.keySet()){
				sb.append("<").append(key).append(">").append("<![CDATA[");
				sb.append(map.get(key)).append("]]></").append(key).append(">\n");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	
	/**
	 * 对象转换成xml
	 * @param <T>
	 * 
	 * @param message
	 *            消息对象
	 * @return xml
	 */
	public static <T> String messageToXml(T message) {
		xstream.alias("xml", message.getClass());
		return xstream.toXML(message);
	}
	
	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage
	 *            文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 图片消息对象转换成xml
	 * 
	 * @param textMessage
	 *            文本消息对象
	 * @return xml
	 */
	public static String imageMessageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage
	 *            音乐消息对象
	 * @return xml
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage
	 *            图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out, new XmlFriendlyReplacer("_-", "_")) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@Override
				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				@Override
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
	
   /**
     * 摇一摇红包商户订单号
    */
   public static String generateMchbillno() {
       LocalDateTime date = LocalDateTime.now();
       StringBuffer randomNo = new StringBuffer("");
       Random random = new Random();
       for(int i=0;i<10;i++){
       	randomNo.append(random.nextInt(10));
       }
       String mchbillno = WeiXinConstants.MERCHANT_KEY+date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+randomNo;
       return mchbillno;
   }
}