package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.DeliveryState;
import com.mobileclient.util.HttpUtil;

/*投递状态管理业务逻辑层*/
public class DeliveryStateService {
	/* 添加投递状态 */
	public String AddDeliveryState(DeliveryState deliveryState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", deliveryState.getStateId() + "");
		params.put("stateName", deliveryState.getStateName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DeliveryStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询投递状态 */
	public List<DeliveryState> QueryDeliveryState(DeliveryState queryConditionDeliveryState) throws Exception {
		String urlString = HttpUtil.BASE_URL + "DeliveryStateServlet?action=query";
		if(queryConditionDeliveryState != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		DeliveryStateListHandler deliveryStateListHander = new DeliveryStateListHandler();
		xr.setContentHandler(deliveryStateListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<DeliveryState> deliveryStateList = deliveryStateListHander.getDeliveryStateList();
		return deliveryStateList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<DeliveryState> deliveryStateList = new ArrayList<DeliveryState>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				DeliveryState deliveryState = new DeliveryState();
				deliveryState.setStateId(object.getInt("stateId"));
				deliveryState.setStateName(object.getString("stateName"));
				deliveryStateList.add(deliveryState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deliveryStateList;
	}

	/* 更新投递状态 */
	public String UpdateDeliveryState(DeliveryState deliveryState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", deliveryState.getStateId() + "");
		params.put("stateName", deliveryState.getStateName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DeliveryStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除投递状态 */
	public String DeleteDeliveryState(int stateId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DeliveryStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "投递状态信息删除失败!";
		}
	}

	/* 根据投递状态id获取投递状态对象 */
	public DeliveryState GetDeliveryState(int stateId)  {
		List<DeliveryState> deliveryStateList = new ArrayList<DeliveryState>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DeliveryStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				DeliveryState deliveryState = new DeliveryState();
				deliveryState.setStateId(object.getInt("stateId"));
				deliveryState.setStateName(object.getString("stateName"));
				deliveryStateList.add(deliveryState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = deliveryStateList.size();
		if(size>0) return deliveryStateList.get(0); 
		else return null; 
	}
}
