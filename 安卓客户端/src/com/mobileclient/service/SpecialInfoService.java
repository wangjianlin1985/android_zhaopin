package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.SpecialInfo;
import com.mobileclient.util.HttpUtil;

/*专业管理业务逻辑层*/
public class SpecialInfoService {
	/* 添加专业 */
	public String AddSpecialInfo(SpecialInfo specialInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("specialId", specialInfo.getSpecialId() + "");
		params.put("specialName", specialInfo.getSpecialName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SpecialInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询专业 */
	public List<SpecialInfo> QuerySpecialInfo(SpecialInfo queryConditionSpecialInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SpecialInfoServlet?action=query";
		if(queryConditionSpecialInfo != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SpecialInfoListHandler specialInfoListHander = new SpecialInfoListHandler();
		xr.setContentHandler(specialInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SpecialInfo> specialInfoList = specialInfoListHander.getSpecialInfoList();
		return specialInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<SpecialInfo> specialInfoList = new ArrayList<SpecialInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SpecialInfo specialInfo = new SpecialInfo();
				specialInfo.setSpecialId(object.getInt("specialId"));
				specialInfo.setSpecialName(object.getString("specialName"));
				specialInfoList.add(specialInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return specialInfoList;
	}

	/* 更新专业 */
	public String UpdateSpecialInfo(SpecialInfo specialInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("specialId", specialInfo.getSpecialId() + "");
		params.put("specialName", specialInfo.getSpecialName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SpecialInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除专业 */
	public String DeleteSpecialInfo(int specialId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("specialId", specialId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SpecialInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "专业信息删除失败!";
		}
	}

	/* 根据专业id获取专业对象 */
	public SpecialInfo GetSpecialInfo(int specialId)  {
		List<SpecialInfo> specialInfoList = new ArrayList<SpecialInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("specialId", specialId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SpecialInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SpecialInfo specialInfo = new SpecialInfo();
				specialInfo.setSpecialId(object.getInt("specialId"));
				specialInfo.setSpecialName(object.getString("specialName"));
				specialInfoList.add(specialInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = specialInfoList.size();
		if(size>0) return specialInfoList.get(0); 
		else return null; 
	}
}
