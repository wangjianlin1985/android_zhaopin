package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.QiyePropertyDAO;
import com.mobileserver.domain.QiyeProperty;

import org.json.JSONStringer;

public class QiyePropertyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造企业性质业务层对象*/
	private QiyePropertyDAO qiyePropertyDAO = new QiyePropertyDAO();

	/*默认构造函数*/
	public QiyePropertyServlet() {
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
			/*获取查询企业性质的参数信息*/

			/*调用业务逻辑层执行企业性质查询*/
			List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryQiyeProperty();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<QiyePropertys>").append("\r\n");
			for (int i = 0; i < qiyePropertyList.size(); i++) {
				sb.append("	<QiyeProperty>").append("\r\n")
				.append("		<id>")
				.append(qiyePropertyList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<propertyName>")
				.append(qiyePropertyList.get(i).getPropertyName())
				.append("</propertyName>").append("\r\n")
				.append("	</QiyeProperty>").append("\r\n");
			}
			sb.append("</QiyePropertys>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(QiyeProperty qiyeProperty: qiyePropertyList) {
				  stringer.object();
			  stringer.key("id").value(qiyeProperty.getId());
			  stringer.key("propertyName").value(qiyeProperty.getPropertyName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加企业性质：获取企业性质参数，参数保存到新建的企业性质对象 */ 
			QiyeProperty qiyeProperty = new QiyeProperty();
			int id = Integer.parseInt(request.getParameter("id"));
			qiyeProperty.setId(id);
			String propertyName = new String(request.getParameter("propertyName").getBytes("iso-8859-1"), "UTF-8");
			qiyeProperty.setPropertyName(propertyName);

			/* 调用业务层执行添加操作 */
			String result = qiyePropertyDAO.AddQiyeProperty(qiyeProperty);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除企业性质：获取企业性质的记录编号*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*调用业务逻辑层执行删除操作*/
			String result = qiyePropertyDAO.DeleteQiyeProperty(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新企业性质之前先根据id查询某个企业性质*/
			int id = Integer.parseInt(request.getParameter("id"));
			QiyeProperty qiyeProperty = qiyePropertyDAO.GetQiyeProperty(id);

			// 客户端查询的企业性质对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(qiyeProperty.getId());
			  stringer.key("propertyName").value(qiyeProperty.getPropertyName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新企业性质：获取企业性质参数，参数保存到新建的企业性质对象 */ 
			QiyeProperty qiyeProperty = new QiyeProperty();
			int id = Integer.parseInt(request.getParameter("id"));
			qiyeProperty.setId(id);
			String propertyName = new String(request.getParameter("propertyName").getBytes("iso-8859-1"), "UTF-8");
			qiyeProperty.setPropertyName(propertyName);

			/* 调用业务层执行更新操作 */
			String result = qiyePropertyDAO.UpdateQiyeProperty(qiyeProperty);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
