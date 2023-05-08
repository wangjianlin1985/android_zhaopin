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

	/*�������Ź���ҵ������*/
	private NewsDAO newsDAO = new NewsDAO();

	/*Ĭ�Ϲ��캯��*/
	public NewsServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ���Ź���Ĳ�����Ϣ*/
			int newsClassObj = 0;
			if (request.getParameter("newsClassObj") != null)
				newsClassObj = Integer.parseInt(request.getParameter("newsClassObj"));
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			String publishDate = request.getParameter("publishDate");
			publishDate = publishDate == null ? "" : new String(request.getParameter(
					"publishDate").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�����Ź����ѯ*/
			List<News> newsList = newsDAO.QueryNews(newsClassObj,title,publishDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ������Ź��棺��ȡ���Ź���������������浽�½������Ź������ */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = newsDAO.AddNews(news);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����Ź��棺��ȡ���Ź��������id*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = newsDAO.DeleteNews(newsId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�������Ź���֮ǰ�ȸ���newsId��ѯĳ�����Ź���*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			News news = newsDAO.GetNews(newsId);

			// �ͻ��˲�ѯ�����Ź�����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �������Ź��棺��ȡ���Ź���������������浽�½������Ź������ */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = newsDAO.UpdateNews(news);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
