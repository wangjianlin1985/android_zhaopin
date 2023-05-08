package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.NewsDAO;
import com.mobileserver.domain.News;

import org.json.JSONStringer;

public class NewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造新闻公告业务层对象*/
	private NewsDAO newsDAO = new NewsDAO();

	/*默认构造函数*/
	public NewsServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询新闻公告的参数信息*/
			int newsClassObj = 0;
			if (request.getParameter("newsClassObj") != null)
				newsClassObj = Integer.parseInt(request.getParameter("newsClassObj"));
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			String publishDate = request.getParameter("publishDate");
			publishDate = publishDate == null ? "" : new String(request.getParameter(
					"publishDate").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行新闻公告查询*/
			List<News> newsList = newsDAO.QueryNews(newsClassObj,title,publishDate);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Newss>").append("\r\n");
			for (int i = 0; i < newsList.size(); i++) {
				sb.append("	<News>").append("\r\n")
				.append("		<newsId>")
				.append(newsList.get(i).getNewsId())
				.append("</newsId>").append("\r\n")
				.append("		<newsClassObj>")
				.append(newsList.get(i).getNewsClassObj())
				.append("</newsClassObj>").append("\r\n")
				.append("		<title>")
				.append(newsList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<content>")
				.append(newsList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<publishDate>")
				.append(newsList.get(i).getPublishDate())
				.append("</publishDate>").append("\r\n")
				.append("	</News>").append("\r\n");
			}
			sb.append("</Newss>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(News news: newsList) {
				  stringer.object();
			  stringer.key("newsId").value(news.getNewsId());
			  stringer.key("newsClassObj").value(news.getNewsClassObj());
			  stringer.key("title").value(news.getTitle());
			  stringer.key("content").value(news.getContent());
			  stringer.key("publishDate").value(news.getPublishDate());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加新闻公告：获取新闻公告参数，参数保存到新建的新闻公告对象 */ 
			News news = new News();
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			news.setNewsId(newsId);
			int newsClassObj = Integer.parseInt(request.getParameter("newsClassObj"));
			news.setNewsClassObj(newsClassObj);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			news.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			news.setContent(content);
			String publishDate = new String(request.getParameter("publishDate").getBytes("iso-8859-1"), "UTF-8");
			news.setPublishDate(publishDate);

			/* 调用业务层执行添加操作 */
			String result = newsDAO.AddNews(news);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除新闻公告：获取新闻公告的新闻id*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			/*调用业务逻辑层执行删除操作*/
			String result = newsDAO.DeleteNews(newsId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新新闻公告之前先根据newsId查询某个新闻公告*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			News news = newsDAO.GetNews(newsId);

			// 客户端查询的新闻公告对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("newsId").value(news.getNewsId());
			  stringer.key("newsClassObj").value(news.getNewsClassObj());
			  stringer.key("title").value(news.getTitle());
			  stringer.key("content").value(news.getContent());
			  stringer.key("publishDate").value(news.getPublishDate());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新新闻公告：获取新闻公告参数，参数保存到新建的新闻公告对象 */ 
			News news = new News();
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			news.setNewsId(newsId);
			int newsClassObj = Integer.parseInt(request.getParameter("newsClassObj"));
			news.setNewsClassObj(newsClassObj);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			news.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			news.setContent(content);
			String publishDate = new String(request.getParameter("publishDate").getBytes("iso-8859-1"), "UTF-8");
			news.setPublishDate(publishDate);

			/* 调用业务层执行更新操作 */
			String result = newsDAO.UpdateNews(news);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
