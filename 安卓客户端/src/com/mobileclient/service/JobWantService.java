package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.JobWant;
import com.mobileclient.util.HttpUtil;

/*求职管理业务逻辑层*/
public class JobWantService {
	/* 添加求职 */
	public String AddJobWant(JobWant jobWant) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("wantId", jobWant.getWantId() + "");
		params.put("jobTypeObj", jobWant.getJobTypeObj() + "");
		params.put("specialObj", jobWant.getSpecialObj() + "");
		params.put("positionName", jobWant.getPositionName());
		params.put("salary", jobWant.getSalary());
		params.put("workCity", jobWant.getWorkCity());
		params.put("wantMemo", jobWant.getWantMemo());
		params.put("userObj", jobWant.getUserObj());
		params.put("addTime", jobWant.getAddTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JobWantServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询求职 */
	public List<JobWant> QueryJobWant(JobWant queryConditionJobWant) throws Exception {
		String urlString = HttpUtil.BASE_URL + "JobWantServlet?action=query";
		if(queryConditionJobWant != null) {
			urlString += "&jobTypeObj=" + queryConditionJobWant.getJobTypeObj();
			urlString += "&specialObj=" + queryConditionJobWant.getSpecialObj();
			urlString += "&positionName=" + URLEncoder.encode(queryConditionJobWant.getPositionName(), "UTF-8") + "";
			urlString += "&workCity=" + URLEncoder.encode(queryConditionJobWant.getWorkCity(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionJobWant.getUserObj(), "UTF-8") + "";
			urlString += "&addTime=" + URLEncoder.encode(queryConditionJobWant.getAddTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		JobWantListHandler jobWantListHander = new JobWantListHandler();
		xr.setContentHandler(jobWantListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<JobWant> jobWantList = jobWantListHander.getJobWantList();
		return jobWantList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<JobWant> jobWantList = new ArrayList<JobWant>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				JobWant jobWant = new JobWant();
				jobWant.setWantId(object.getInt("wantId"));
				jobWant.setJobTypeObj(object.getInt("jobTypeObj"));
				jobWant.setSpecialObj(object.getInt("specialObj"));
				jobWant.setPositionName(object.getString("positionName"));
				jobWant.setSalary(object.getString("salary"));
				jobWant.setWorkCity(object.getString("workCity"));
				jobWant.setWantMemo(object.getString("wantMemo"));
				jobWant.setUserObj(object.getString("userObj"));
				jobWant.setAddTime(object.getString("addTime"));
				jobWantList.add(jobWant);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobWantList;
	}

	/* 更新求职 */
	public String UpdateJobWant(JobWant jobWant) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("wantId", jobWant.getWantId() + "");
		params.put("jobTypeObj", jobWant.getJobTypeObj() + "");
		params.put("specialObj", jobWant.getSpecialObj() + "");
		params.put("positionName", jobWant.getPositionName());
		params.put("salary", jobWant.getSalary());
		params.put("workCity", jobWant.getWorkCity());
		params.put("wantMemo", jobWant.getWantMemo());
		params.put("userObj", jobWant.getUserObj());
		params.put("addTime", jobWant.getAddTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JobWantServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除求职 */
	public String DeleteJobWant(int wantId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("wantId", wantId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JobWantServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "求职信息删除失败!";
		}
	}

	/* 根据记录id获取求职对象 */
	public JobWant GetJobWant(int wantId)  {
		List<JobWant> jobWantList = new ArrayList<JobWant>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("wantId", wantId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JobWantServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				JobWant jobWant = new JobWant();
				jobWant.setWantId(object.getInt("wantId"));
				jobWant.setJobTypeObj(object.getInt("jobTypeObj"));
				jobWant.setSpecialObj(object.getInt("specialObj"));
				jobWant.setPositionName(object.getString("positionName"));
				jobWant.setSalary(object.getString("salary"));
				jobWant.setWorkCity(object.getString("workCity"));
				jobWant.setWantMemo(object.getString("wantMemo"));
				jobWant.setUserObj(object.getString("userObj"));
				jobWant.setAddTime(object.getString("addTime"));
				jobWantList.add(jobWant);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = jobWantList.size();
		if(size>0) return jobWantList.get(0); 
		else return null; 
	}
}
