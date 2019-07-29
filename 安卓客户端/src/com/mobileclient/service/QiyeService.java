package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Qiye;
import com.mobileclient.util.HttpUtil;

/*企业管理业务逻辑层*/
public class QiyeService {
	/* 添加企业 */
	public String AddQiye(Qiye qiye) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("qiyeUserName", qiye.getQiyeUserName());
		params.put("password", qiye.getPassword());
		params.put("qiyeName", qiye.getQiyeName());
		params.put("qiyeQualify", qiye.getQiyeQualify());
		params.put("qiyePropertyObj", qiye.getQiyePropertyObj() + "");
		params.put("qiyeProfessionObj", qiye.getQiyeProfessionObj() + "");
		params.put("qiyeScale", qiye.getQiyeScale());
		params.put("connectPerson", qiye.getConnectPerson());
		params.put("telephone", qiye.getTelephone());
		params.put("email", qiye.getEmail());
		params.put("address", qiye.getAddress());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QiyeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询企业 */
	public List<Qiye> QueryQiye(Qiye queryConditionQiye) throws Exception {
		String urlString = HttpUtil.BASE_URL + "QiyeServlet?action=query";
		if(queryConditionQiye != null) {
			urlString += "&qiyeUserName=" + URLEncoder.encode(queryConditionQiye.getQiyeUserName(), "UTF-8") + "";
			urlString += "&qiyeName=" + URLEncoder.encode(queryConditionQiye.getQiyeName(), "UTF-8") + "";
			urlString += "&qiyePropertyObj=" + queryConditionQiye.getQiyePropertyObj();
			urlString += "&qiyeProfessionObj=" + queryConditionQiye.getQiyeProfessionObj();
			urlString += "&connectPerson=" + URLEncoder.encode(queryConditionQiye.getConnectPerson(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionQiye.getTelephone(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		QiyeListHandler qiyeListHander = new QiyeListHandler();
		xr.setContentHandler(qiyeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Qiye> qiyeList = qiyeListHander.getQiyeList();
		return qiyeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Qiye> qiyeList = new ArrayList<Qiye>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Qiye qiye = new Qiye();
				qiye.setQiyeUserName(object.getString("qiyeUserName"));
				qiye.setPassword(object.getString("password"));
				qiye.setQiyeName(object.getString("qiyeName"));
				qiye.setQiyeQualify(object.getString("qiyeQualify"));
				qiye.setQiyePropertyObj(object.getInt("qiyePropertyObj"));
				qiye.setQiyeProfessionObj(object.getInt("qiyeProfessionObj"));
				qiye.setQiyeScale(object.getString("qiyeScale"));
				qiye.setConnectPerson(object.getString("connectPerson"));
				qiye.setTelephone(object.getString("telephone"));
				qiye.setEmail(object.getString("email"));
				qiye.setAddress(object.getString("address"));
				qiyeList.add(qiye);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qiyeList;
	}

	/* 更新企业 */
	public String UpdateQiye(Qiye qiye) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("qiyeUserName", qiye.getQiyeUserName());
		params.put("password", qiye.getPassword());
		params.put("qiyeName", qiye.getQiyeName());
		params.put("qiyeQualify", qiye.getQiyeQualify());
		params.put("qiyePropertyObj", qiye.getQiyePropertyObj() + "");
		params.put("qiyeProfessionObj", qiye.getQiyeProfessionObj() + "");
		params.put("qiyeScale", qiye.getQiyeScale());
		params.put("connectPerson", qiye.getConnectPerson());
		params.put("telephone", qiye.getTelephone());
		params.put("email", qiye.getEmail());
		params.put("address", qiye.getAddress());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QiyeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除企业 */
	public String DeleteQiye(String qiyeUserName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("qiyeUserName", qiyeUserName);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QiyeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "企业信息删除失败!";
		}
	}

	/* 根据企业账号获取企业对象 */
	public Qiye GetQiye(String qiyeUserName)  {
		List<Qiye> qiyeList = new ArrayList<Qiye>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("qiyeUserName", qiyeUserName);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "QiyeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Qiye qiye = new Qiye();
				qiye.setQiyeUserName(object.getString("qiyeUserName"));
				qiye.setPassword(object.getString("password"));
				qiye.setQiyeName(object.getString("qiyeName"));
				qiye.setQiyeQualify(object.getString("qiyeQualify"));
				qiye.setQiyePropertyObj(object.getInt("qiyePropertyObj"));
				qiye.setQiyeProfessionObj(object.getInt("qiyeProfessionObj"));
				qiye.setQiyeScale(object.getString("qiyeScale"));
				qiye.setConnectPerson(object.getString("connectPerson"));
				qiye.setTelephone(object.getString("telephone"));
				qiye.setEmail(object.getString("email"));
				qiye.setAddress(object.getString("address"));
				qiyeList.add(qiye);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = qiyeList.size();
		if(size>0) return qiyeList.get(0); 
		else return null; 
	}
}
