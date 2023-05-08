package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.SchoolRecord;
import com.mobileclient.util.HttpUtil;

/*学历管理业务逻辑层*/
public class SchoolRecordService {
	/* 添加学历 */
	public String AddSchoolRecord(SchoolRecord schoolRecord) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", schoolRecord.getId() + "");
		params.put("schooRecordName", schoolRecord.getSchooRecordName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SchoolRecordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询学历 */
	public List<SchoolRecord> QuerySchoolRecord(SchoolRecord queryConditionSchoolRecord) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SchoolRecordServlet?action=query";
		if(queryConditionSchoolRecord != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SchoolRecordListHandler schoolRecordListHander = new SchoolRecordListHandler();
		xr.setContentHandler(schoolRecordListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SchoolRecord> schoolRecordList = schoolRecordListHander.getSchoolRecordList();
		return schoolRecordList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<SchoolRecord> schoolRecordList = new ArrayList<SchoolRecord>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SchoolRecord schoolRecord = new SchoolRecord();
				schoolRecord.setId(object.getInt("id"));
				schoolRecord.setSchooRecordName(object.getString("schooRecordName"));
				schoolRecordList.add(schoolRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schoolRecordList;
	}

	/* 更新学历 */
	public String UpdateSchoolRecord(SchoolRecord schoolRecord) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", schoolRecord.getId() + "");
		params.put("schooRecordName", schoolRecord.getSchooRecordName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SchoolRecordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除学历 */
	public String DeleteSchoolRecord(int id) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SchoolRecordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "学历信息删除失败!";
		}
	}

	/* 根据记录编号获取学历对象 */
	public SchoolRecord GetSchoolRecord(int id)  {
		List<SchoolRecord> schoolRecordList = new ArrayList<SchoolRecord>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SchoolRecordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SchoolRecord schoolRecord = new SchoolRecord();
				schoolRecord.setId(object.getInt("id"));
				schoolRecord.setSchooRecordName(object.getString("schooRecordName"));
				schoolRecordList.add(schoolRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = schoolRecordList.size();
		if(size>0) return schoolRecordList.get(0); 
		else return null; 
	}
}
