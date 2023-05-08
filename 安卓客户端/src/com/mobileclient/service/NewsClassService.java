package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.NewsClass;
import com.mobileclient.util.HttpUtil;

/*新闻分类管理业务逻辑层*/
public class NewsClassService {
	/* 添加新闻分类 */
	public String AddNewsClass(NewsClass newsClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsClassId", newsClass.getNewsClassId() + "");
		params.put("newsClassName", newsClass.getNewsClassName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询新闻分类 */
	public List<NewsClass> QueryNewsClass(NewsClass queryConditionNewsClass) throws Exception {
		String urlString = HttpUtil.BASE_URL + "NewsClassServlet?action=query";
		if(queryConditionNewsClass != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		NewsClassListHandler newsClassListHander = new NewsClassListHandler();
		xr.setContentHandler(newsClassListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<NewsClass> newsClassList = newsClassListHander.getNewsClassList();
		return newsClassList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<NewsClass> newsClassList = new ArrayList<NewsClass>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				NewsClass newsClass = new NewsClass();
				newsClass.setNewsClassId(object.getInt("newsClassId"));
				newsClass.setNewsClassName(object.getString("newsClassName"));
				newsClassList.add(newsClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsClassList;
	}

	/* 更新新闻分类 */
	public String UpdateNewsClass(NewsClass newsClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsClassId", newsClass.getNewsClassId() + "");
		params.put("newsClassName", newsClass.getNewsClassName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除新闻分类 */
	public String DeleteNewsClass(int newsClassId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsClassId", newsClassId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "新闻分类信息删除失败!";
		}
	}

	/* 根据新闻分类id获取新闻分类对象 */
	public NewsClass GetNewsClass(int newsClassId)  {
		List<NewsClass> newsClassList = new ArrayList<NewsClass>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsClassId", newsClassId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				NewsClass newsClass = new NewsClass();
				newsClass.setNewsClassId(object.getInt("newsClassId"));
				newsClass.setNewsClassName(object.getString("newsClassName"));
				newsClassList.add(newsClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = newsClassList.size();
		if(size>0) return newsClassList.get(0); 
		else return null; 
	}
}
