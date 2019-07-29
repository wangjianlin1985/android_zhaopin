package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.QiyeProperty;
import com.mobileclient.util.HttpUtil;

/*企业性质管理业务逻辑层*/
public class QiyePropertyService {
	/* 添加企业性质 */
	public String AddQiyeProperty(QiyeProperty qiyeProperty) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", qiyeProperty.getId() + "");
		params.put("propertyName", qiyeProperty.getPropertyName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QiyePropertyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询企业性质 */
	public List<QiyeProperty> QueryQiyeProperty(QiyeProperty queryConditionQiyeProperty) throws Exception {
		String urlString = HttpUtil.BASE_URL + "QiyePropertyServlet?action=query";
		if(queryConditionQiyeProperty != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		QiyePropertyListHandler qiyePropertyListHander = new QiyePropertyListHandler();
		xr.setContentHandler(qiyePropertyListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<QiyeProperty> qiyePropertyList = qiyePropertyListHander.getQiyePropertyList();
		return qiyePropertyList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<QiyeProperty> qiyePropertyList = new ArrayList<QiyeProperty>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				QiyeProperty qiyeProperty = new QiyeProperty();
				qiyeProperty.setId(object.getInt("id"));
				qiyeProperty.setPropertyName(object.getString("propertyName"));
				qiyePropertyList.add(qiyeProperty);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qiyePropertyList;
	}

	/* 更新企业性质 */
	public String UpdateQiyeProperty(QiyeProperty qiyeProperty) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", qiyeProperty.getId() + "");
		params.put("propertyName", qiyeProperty.getPropertyName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QiyePropertyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除企业性质 */
	public String DeleteQiyeProperty(int id) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QiyePropertyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "企业性质信息删除失败!";
		}
	}

	/* 根据记录编号获取企业性质对象 */
	public QiyeProperty GetQiyeProperty(int id)  {
		List<QiyeProperty> qiyePropertyList = new ArrayList<QiyeProperty>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QiyePropertyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				QiyeProperty qiyeProperty = new QiyeProperty();
				qiyeProperty.setId(object.getInt("id"));
				qiyeProperty.setPropertyName(object.getString("propertyName"));
				qiyePropertyList.add(qiyeProperty);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = qiyePropertyList.size();
		if(size>0) return qiyePropertyList.get(0); 
		else return null; 
	}
}
