package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.JobType;
import com.mobileclient.util.HttpUtil;

/*职位分类管理业务逻辑层*/
public class JobTypeService {
	/* 添加职位分类 */
	public String AddJobType(JobType jobType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jobTypeId", jobType.getJobTypeId() + "");
		params.put("typeName", jobType.getTypeName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JobTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询职位分类 */
	public List<JobType> QueryJobType(JobType queryConditionJobType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "JobTypeServlet?action=query";
		if(queryConditionJobType != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		JobTypeListHandler jobTypeListHander = new JobTypeListHandler();
		xr.setContentHandler(jobTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<JobType> jobTypeList = jobTypeListHander.getJobTypeList();
		return jobTypeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<JobType> jobTypeList = new ArrayList<JobType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				JobType jobType = new JobType();
				jobType.setJobTypeId(object.getInt("jobTypeId"));
				jobType.setTypeName(object.getString("typeName"));
				jobTypeList.add(jobType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobTypeList;
	}

	/* 更新职位分类 */
	public String UpdateJobType(JobType jobType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jobTypeId", jobType.getJobTypeId() + "");
		params.put("typeName", jobType.getTypeName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JobTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除职位分类 */
	public String DeleteJobType(int jobTypeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jobTypeId", jobTypeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JobTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "职位分类信息删除失败!";
		}
	}

	/* 根据职位分类id获取职位分类对象 */
	public JobType GetJobType(int jobTypeId)  {
		List<JobType> jobTypeList = new ArrayList<JobType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jobTypeId", jobTypeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JobTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				JobType jobType = new JobType();
				jobType.setJobTypeId(object.getInt("jobTypeId"));
				jobType.setTypeName(object.getString("typeName"));
				jobTypeList.add(jobType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = jobTypeList.size();
		if(size>0) return jobTypeList.get(0); 
		else return null; 
	}
}
