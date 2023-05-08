package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.News;
import com.mobileclient.util.HttpUtil;

/*新闻公告管理业务逻辑层*/
public class NewsService {
	/* 添加新闻公告 */
	public String AddNews(News news) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsId", news.getNewsId() + "");
		params.put("newsClassObj", news.getNewsClassObj() + "");
		params.put("title", news.getTitle());
		params.put("content", news.getContent());
		params.put("publishDate", news.getPublishDate());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询新闻公告 */
	public List<News> QueryNews(News queryConditionNews) throws Exception {
		String urlString = HttpUtil.BASE_URL + "NewsServlet?action=query";
		if(queryConditionNews != null) {
			urlString += "&newsClassObj=" + queryConditionNews.getNewsClassObj();
			urlString += "&title=" + URLEncoder.encode(queryConditionNews.getTitle(), "UTF-8") + "";
			urlString += "&publishDate=" + URLEncoder.encode(queryConditionNews.getPublishDate(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		NewsListHandler newsListHander = new NewsListHandler();
		xr.setContentHandler(newsListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<News> newsList = newsListHander.getNewsList();
		return newsList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<News> newsList = new ArrayList<News>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				News news = new News();
				news.setNewsId(object.getInt("newsId"));
				news.setNewsClassObj(object.getInt("newsClassObj"));
				news.setTitle(object.getString("title"));
				news.setContent(object.getString("content"));
				news.setPublishDate(object.getString("publishDate"));
				newsList.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsList;
	}

	/* 更新新闻公告 */
	public String UpdateNews(News news) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsId", news.getNewsId() + "");
		params.put("newsClassObj", news.getNewsClassObj() + "");
		params.put("title", news.getTitle());
		params.put("content", news.getContent());
		params.put("publishDate", news.getPublishDate());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除新闻公告 */
	public String DeleteNews(int newsId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsId", newsId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "新闻公告信息删除失败!";
		}
	}

	/* 根据新闻id获取新闻公告对象 */
	public News GetNews(int newsId)  {
		List<News> newsList = new ArrayList<News>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsId", newsId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				News news = new News();
				news.setNewsId(object.getInt("newsId"));
				news.setNewsClassObj(object.getInt("newsClassObj"));
				news.setTitle(object.getString("title"));
				news.setContent(object.getString("content"));
				news.setPublishDate(object.getString("publishDate"));
				newsList.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = newsList.size();
		if(size>0) return newsList.get(0); 
		else return null; 
	}
}
