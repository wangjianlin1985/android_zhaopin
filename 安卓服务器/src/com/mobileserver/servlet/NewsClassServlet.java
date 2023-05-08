package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.NewsClassDAO;
import com.mobileserver.domain.NewsClass;

import org.json.JSONStringer;

public class NewsClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�������ŷ���ҵ������*/
	private NewsClassDAO newsClassDAO = new NewsClassDAO();

	/*Ĭ�Ϲ��캯��*/
	public NewsClassServlet() {
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
			/*��ȡ��ѯ���ŷ���Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ�����ŷ����ѯ*/
			List<NewsClass> newsClassList = newsClassDAO.QueryNewsClass();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<NewsClasss>").append("\r\n");
			for (int i = 0; i < newsClassList.size(); i++) {
				sb.append("	<NewsClass>").append("\r\n")
				.append("		<newsClassId>")
				.append(newsClassList.get(i).getNewsClassId())
				.append("</newsClassId>").append("\r\n")
				.append("		<newsClassName>")
				.append(newsClassList.get(i).getNewsClassName())
				.append("</newsClassName>").append("\r\n")
				.append("	</NewsClass>").append("\r\n");
			}
			sb.append("</NewsClasss>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(NewsClass newsClass: newsClassList) {
				  stringer.object();
			  stringer.key("newsClassId").value(newsClass.getNewsClassId());
			  stringer.key("newsClassName").value(newsClass.getNewsClassName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ������ŷ��ࣺ��ȡ���ŷ���������������浽�½������ŷ������ */ 
			NewsClass newsClass = new NewsClass();
			int newsClassId = Integer.parseInt(request.getParameter("newsClassId"));
			newsClass.setNewsClassId(newsClassId);
			String newsClassName = new String(request.getParameter("newsClassName").getBytes("iso-8859-1"), "UTF-8");
			newsClass.setNewsClassName(newsClassName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = newsClassDAO.AddNewsClass(newsClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����ŷ��ࣺ��ȡ���ŷ�������ŷ���id*/
			int newsClassId = Integer.parseInt(request.getParameter("newsClassId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = newsClassDAO.DeleteNewsClass(newsClassId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�������ŷ���֮ǰ�ȸ���newsClassId��ѯĳ�����ŷ���*/
			int newsClassId = Integer.parseInt(request.getParameter("newsClassId"));
			NewsClass newsClass = newsClassDAO.GetNewsClass(newsClassId);

			// �ͻ��˲�ѯ�����ŷ�����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("newsClassId").value(newsClass.getNewsClassId());
			  stringer.key("newsClassName").value(newsClass.getNewsClassName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �������ŷ��ࣺ��ȡ���ŷ���������������浽�½������ŷ������ */ 
			NewsClass newsClass = new NewsClass();
			int newsClassId = Integer.parseInt(request.getParameter("newsClassId"));
			newsClass.setNewsClassId(newsClassId);
			String newsClassName = new String(request.getParameter("newsClassName").getBytes("iso-8859-1"), "UTF-8");
			newsClass.setNewsClassName(newsClassName);

			/* ����ҵ���ִ�и��²��� */
			String result = newsClassDAO.UpdateNewsClass(newsClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
