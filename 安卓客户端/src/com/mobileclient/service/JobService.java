package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Job;
import com.mobileclient.util.HttpUtil;

/*职位管理业务逻辑层*/
public class JobService {
	/* 添加职位 */
	public String AddJob(Job job) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jobId", job.getJobId() + "");
		params.put("qiyeObj", job.getQiyeObj());
		params.put("positionName", job.getPositionName());
		params.put("jobTypeObj", job.getJobTypeObj() + "");
		params.put("specialObj", job.getSpecialObj() + "");
		params.put("personNum", job.getPersonNum());
		params.put("city", job.getCity());
		params.put("salary", job.getSalary());
		params.put("schoolRecordObj", job.getSchoolRecordObj() + "");
		params.put("workYears", job.getWorkYears());
		params.put("workAddress", job.getWorkAddress());
		params.put("welfare", job.getWelfare());
		params.put("positionDesc", job.getPositionDesc());
		params.put("connectPerson", job.getConnectPerson());
		params.put("telephone", job.getTelephone());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JobServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询职位 */
	public List<Job> QueryJob(Job queryConditionJob) throws Exception {
		String urlString = HttpUtil.BASE_URL + "JobServlet?action=query";
		if(queryConditionJob != null) {
			urlString += "&qiyeObj=" + URLEncoder.encode(queryConditionJob.getQiyeObj(), "UTF-8") + "";
			urlString += "&positionName=" + URLEncoder.encode(queryConditionJob.getPositionName(), "UTF-8") + "";
			urlString += "&jobTypeObj=" + queryConditionJob.getJobTypeObj();
			urlString += "&specialObj=" + queryConditionJob.getSpecialObj();
			urlString += "&city=" + URLEncoder.encode(queryConditionJob.getCity(), "UTF-8") + "";
			urlString += "&schoolRecordObj=" + queryConditionJob.getSchoolRecordObj();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		JobListHandler jobListHander = new JobListHandler();
		xr.setContentHandler(jobListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Job> jobList = jobListHander.getJobList();
		return jobList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Job> jobList = new ArrayList<Job>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Job job = new Job();
				job.setJobId(object.getInt("jobId"));
				job.setQiyeObj(object.getString("qiyeObj"));
				job.setPositionName(object.getString("positionName"));
				job.setJobTypeObj(object.getInt("jobTypeObj"));
				job.setSpecialObj(object.getInt("specialObj"));
				job.setPersonNum(object.getString("personNum"));
				job.setCity(object.getString("city"));
				job.setSalary(object.getString("salary"));
				job.setSchoolRecordObj(object.getInt("schoolRecordObj"));
				job.setWorkYears(object.getString("workYears"));
				job.setWorkAddress(object.getString("workAddress"));
				job.setWelfare(object.getString("welfare"));
				job.setPositionDesc(object.getString("positionDesc"));
				job.setConnectPerson(object.getString("connectPerson"));
				job.setTelephone(object.getString("telephone"));
				jobList.add(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobList;
	}

	/* 更新职位 */
	public String UpdateJob(Job job) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jobId", job.getJobId() + "");
		params.put("qiyeObj", job.getQiyeObj());
		params.put("positionName", job.getPositionName());
		params.put("jobTypeObj", job.getJobTypeObj() + "");
		params.put("specialObj", job.getSpecialObj() + "");
		params.put("personNum", job.getPersonNum());
		params.put("city", job.getCity());
		params.put("salary", job.getSalary());
		params.put("schoolRecordObj", job.getSchoolRecordObj() + "");
		params.put("workYears", job.getWorkYears());
		params.put("workAddress", job.getWorkAddress());
		params.put("welfare", job.getWelfare());
		params.put("positionDesc", job.getPositionDesc());
		params.put("connectPerson", job.getConnectPerson());
		params.put("telephone", job.getTelephone());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JobServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除职位 */
	public String DeleteJob(int jobId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jobId", jobId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JobServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "职位信息删除失败!";
		}
	}

	/* 根据职位id获取职位对象 */
	public Job GetJob(int jobId)  {
		List<Job> jobList = new ArrayList<Job>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jobId", jobId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JobServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Job job = new Job();
				job.setJobId(object.getInt("jobId"));
				job.setQiyeObj(object.getString("qiyeObj"));
				job.setPositionName(object.getString("positionName"));
				job.setJobTypeObj(object.getInt("jobTypeObj"));
				job.setSpecialObj(object.getInt("specialObj"));
				job.setPersonNum(object.getString("personNum"));
				job.setCity(object.getString("city"));
				job.setSalary(object.getString("salary"));
				job.setSchoolRecordObj(object.getInt("schoolRecordObj"));
				job.setWorkYears(object.getString("workYears"));
				job.setWorkAddress(object.getString("workAddress"));
				job.setWelfare(object.getString("welfare"));
				job.setPositionDesc(object.getString("positionDesc"));
				job.setConnectPerson(object.getString("connectPerson"));
				job.setTelephone(object.getString("telephone"));
				jobList.add(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = jobList.size();
		if(size>0) return jobList.get(0); 
		else return null; 
	}
}
