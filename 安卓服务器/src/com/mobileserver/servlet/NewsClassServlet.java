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

	/*构造新闻分类业务层对象*/
	private NewsClassDAO newsClassDAO = new NewsClassDAO();

	/*默认构造函数*/
	public NewsClassServlet() {
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
			/*获取查询新闻分类的参数信息*/

			/*调用业务逻辑层执行新闻分类查询*/
			List<NewsClass> newsClassList = newsClassDAO.QueryNewsClass();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加新闻分类：获取新闻分类参数，参数保存到新建的新闻分类对象 */ 
			NewsClass newsClass = new NewsClass();
			int newsClassId = Integer.parseInt(request.getParameter("newsClassId"));
			newsClass.setNewsClassId(newsClassId);
			String newsClassName = new String(request.getParameter("newsClassName").getBytes("iso-8859-1"), "UTF-8");
			newsClass.setNewsClassName(newsClassName);

			/* 调用业务层执行添加操作 */
			String result = newsClassDAO.AddNewsClass(newsClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除新闻分类：获取新闻分类的新闻分类id*/
			int newsClassId = Integer.parseInt(request.getParameter("newsClassId"));
			/*调用业务逻辑层执行删除操作*/
			String result = newsClassDAO.DeleteNewsClass(newsClassId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新新闻分类之前先根据newsClassId查询某个新闻分类*/
			int newsClassId = Integer.parseInt(request.getParameter("newsClassId"));
			NewsClass newsClass = newsClassDAO.GetNewsClass(newsClassId);

			// 客户端查询的新闻分类对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新新闻分类：获取新闻分类参数，参数保存到新建的新闻分类对象 */ 
			NewsClass newsClass = new NewsClass();
			int newsClassId = Integer.parseInt(request.getParameter("newsClassId"));
			newsClass.setNewsClassId(newsClassId);
			String newsClassName = new String(request.getParameter("newsClassName").getBytes("iso-8859-1"), "UTF-8");
			newsClass.setNewsClassName(newsClassName);

			/* 调用业务层执行更新操作 */
			String result = newsClassDAO.UpdateNewsClass(newsClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
