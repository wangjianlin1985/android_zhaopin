package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Delivery;
import com.mobileclient.util.HttpUtil;

/*职位投递管理业务逻辑层*/
public class DeliveryService {
	/* 添加职位投递 */
	public String AddDelivery(Delivery delivery) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("deliveryId", delivery.getDeliveryId() + "");
		params.put("jobObj", delivery.getJobObj() + "");
		params.put("userObj", delivery.getUserObj());
		params.put("deliveryTime", delivery.getDeliveryTime());
		params.put("stateObj", delivery.getStateObj() + "");
		params.put("deliveryDemo", delivery.getDeliveryDemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DeliveryServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询职位投递 */
	public List<Delivery> QueryDelivery(Delivery queryConditionDelivery) throws Exception {
		String urlString = HttpUtil.BASE_URL + "DeliveryServlet?action=query";
		if(queryConditionDelivery != null) {
			urlString += "&jobObj=" + queryConditionDelivery.getJobObj();
			urlString += "&userObj=" + URLEncoder.encode(queryConditionDelivery.getUserObj(), "UTF-8") + "";
			urlString += "&deliveryTime=" + URLEncoder.encode(queryConditionDelivery.getDeliveryTime(), "UTF-8") + "";
			urlString += "&stateObj=" + queryConditionDelivery.getStateObj();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		DeliveryListHandler deliveryListHander = new DeliveryListHandler();
		xr.setContentHandler(deliveryListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Delivery> deliveryList = deliveryListHander.getDeliveryList();
		return deliveryList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Delivery> deliveryList = new ArrayList<Delivery>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Delivery delivery = new Delivery();
				delivery.setDeliveryId(object.getInt("deliveryId"));
				delivery.setJobObj(object.getInt("jobObj"));
				delivery.setUserObj(object.getString("userObj"));
				delivery.setDeliveryTime(object.getString("deliveryTime"));
				delivery.setStateObj(object.getInt("stateObj"));
				delivery.setDeliveryDemo(object.getString("deliveryDemo"));
				deliveryList.add(delivery);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deliveryList;
	}

	/* 更新职位投递 */
	public String UpdateDelivery(Delivery delivery) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("deliveryId", delivery.getDeliveryId() + "");
		params.put("jobObj", delivery.getJobObj() + "");
		params.put("userObj", delivery.getUserObj());
		params.put("deliveryTime", delivery.getDeliveryTime());
		params.put("stateObj", delivery.getStateObj() + "");
		params.put("deliveryDemo", delivery.getDeliveryDemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DeliveryServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除职位投递 */
	public String DeleteDelivery(int deliveryId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("deliveryId", deliveryId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DeliveryServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "职位投递信息删除失败!";
		}
	}

	/* 根据投递id获取职位投递对象 */
	public Delivery GetDelivery(int deliveryId)  {
		List<Delivery> deliveryList = new ArrayList<Delivery>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("deliveryId", deliveryId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DeliveryServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Delivery delivery = new Delivery();
				delivery.setDeliveryId(object.getInt("deliveryId"));
				delivery.setJobObj(object.getInt("jobObj"));
				delivery.setUserObj(object.getString("userObj"));
				delivery.setDeliveryTime(object.getString("deliveryTime"));
				delivery.setStateObj(object.getInt("stateObj"));
				delivery.setDeliveryDemo(object.getString("deliveryDemo"));
				deliveryList.add(delivery);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = deliveryList.size();
		if(size>0) return deliveryList.get(0); 
		else return null; 
	}
}
