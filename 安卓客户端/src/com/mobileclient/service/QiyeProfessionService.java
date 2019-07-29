package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.QiyeProfession;
import com.mobileclient.util.HttpUtil;

/*企业行业管理业务逻辑层*/
public class QiyeProfessionService {
	/* 添加企业行业 */
	public String AddQiyeProfession(QiyeProfession qiyeProfession) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", qiyeProfession.getId() + "");
		params.put("professionName", qiyeProfession.getProfessionName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QiyeProfessionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询企业行业 */
	public List<QiyeProfession> QueryQiyeProfession(QiyeProfession queryConditionQiyeProfession) throws Exception {
		String urlString = HttpUtil.BASE_URL + "QiyeProfessionServlet?action=query";
		if(queryConditionQiyeProfession != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		QiyeProfessionListHandler qiyeProfessionListHander = new QiyeProfessionListHandler();
		xr.setContentHandler(qiyeProfessionListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<QiyeProfession> qiyeProfessionList = qiyeProfessionListHander.getQiyeProfessionList();
		return qiyeProfessionList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<QiyeProfession> qiyeProfessionList = new ArrayList<QiyeProfession>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				QiyeProfession qiyeProfession = new QiyeProfession();
				qiyeProfession.setId(object.getInt("id"));
				qiyeProfession.setProfessionName(object.getString("professionName"));
				qiyeProfessionList.add(qiyeProfession);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qiyeProfessionList;
	}

	/* 更新企业行业 */
	public String UpdateQiyeProfession(QiyeProfession qiyeProfession) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", qiyeProfession.getId() + "");
		params.put("professionName", qiyeProfession.getProfessionName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QiyeProfessionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除企业行业 */
	public String DeleteQiyeProfession(int id) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QiyeProfessionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "企业行业信息删除失败!";
		}
	}

	/* 根据记录编号获取企业行业对象 */
	public QiyeProfession GetQiyeProfession(int id)  {
		List<QiyeProfession> qiyeProfessionList = new ArrayList<QiyeProfession>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QiyeProfessionServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				QiyeProfession qiyeProfession = new QiyeProfession();
				qiyeProfession.setId(object.getInt("id"));
				qiyeProfession.setProfessionName(object.getString("professionName"));
				qiyeProfessionList.add(qiyeProfession);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = qiyeProfessionList.size();
		if(size>0) return qiyeProfessionList.get(0); 
		else return null; 
	}
}
