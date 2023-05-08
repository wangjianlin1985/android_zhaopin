package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.QiyeDAO;
import com.mobileserver.domain.Qiye;

import org.json.JSONStringer;

public class QiyeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造企业业务层对象*/
	private QiyeDAO qiyeDAO = new QiyeDAO();

	/*默认构造函数*/
	public QiyeServlet() {
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
			/*获取查询企业的参数信息*/
			String qiyeUserName = request.getParameter("qiyeUserName");
			qiyeUserName = qiyeUserName == null ? "" : new String(request.getParameter(
					"qiyeUserName").getBytes("iso-8859-1"), "UTF-8");
			String qiyeName = request.getParameter("qiyeName");
			qiyeName = qiyeName == null ? "" : new String(request.getParameter(
					"qiyeName").getBytes("iso-8859-1"), "UTF-8");
			int qiyePropertyObj = 0;
			if (request.getParameter("qiyePropertyObj") != null)
				qiyePropertyObj = Integer.parseInt(request.getParameter("qiyePropertyObj"));
			int qiyeProfessionObj = 0;
			if (request.getParameter("qiyeProfessionObj") != null)
				qiyeProfessionObj = Integer.parseInt(request.getParameter("qiyeProfessionObj"));
			String connectPerson = request.getParameter("connectPerson");
			connectPerson = connectPerson == null ? "" : new String(request.getParameter(
					"connectPerson").getBytes("iso-8859-1"), "UTF-8");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行企业查询*/
			List<Qiye> qiyeList = qiyeDAO.QueryQiye(qiyeUserName,qiyeName,qiyePropertyObj,qiyeProfessionObj,connectPerson,telephone);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Qiyes>").append("\r\n");
			for (int i = 0; i < qiyeList.size(); i++) {
				sb.append("	<Qiye>").append("\r\n")
				.append("		<qiyeUserName>")
				.append(qiyeList.get(i).getQiyeUserName())
				.append("</qiyeUserName>").append("\r\n")
				.append("		<password>")
				.append(qiyeList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<qiyeName>")
				.append(qiyeList.get(i).getQiyeName())
				.append("</qiyeName>").append("\r\n")
				.append("		<qiyeQualify>")
				.append(qiyeList.get(i).getQiyeQualify())
				.append("</qiyeQualify>").append("\r\n")
				.append("		<qiyePropertyObj>")
				.append(qiyeList.get(i).getQiyePropertyObj())
				.append("</qiyePropertyObj>").append("\r\n")
				.append("		<qiyeProfessionObj>")
				.append(qiyeList.get(i).getQiyeProfessionObj())
				.append("</qiyeProfessionObj>").append("\r\n")
				.append("		<qiyeScale>")
				.append(qiyeList.get(i).getQiyeScale())
				.append("</qiyeScale>").append("\r\n")
				.append("		<connectPerson>")
				.append(qiyeList.get(i).getConnectPerson())
				.append("</connectPerson>").append("\r\n")
				.append("		<telephone>")
				.append(qiyeList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<email>")
				.append(qiyeList.get(i).getEmail())
				.append("</email>").append("\r\n")
				.append("		<address>")
				.append(qiyeList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("	</Qiye>").append("\r\n");
			}
			sb.append("</Qiyes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Qiye qiye: qiyeList) {
				  stringer.object();
			  stringer.key("qiyeUserName").value(qiye.getQiyeUserName());
			  stringer.key("password").value(qiye.getPassword());
			  stringer.key("qiyeName").value(qiye.getQiyeName());
			  stringer.key("qiyeQualify").value(qiye.getQiyeQualify());
			  stringer.key("qiyePropertyObj").value(qiye.getQiyePropertyObj());
			  stringer.key("qiyeProfessionObj").value(qiye.getQiyeProfessionObj());
			  stringer.key("qiyeScale").value(qiye.getQiyeScale());
			  stringer.key("connectPerson").value(qiye.getConnectPerson());
			  stringer.key("telephone").value(qiye.getTelephone());
			  stringer.key("email").value(qiye.getEmail());
			  stringer.key("address").value(qiye.getAddress());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加企业：获取企业参数，参数保存到新建的企业对象 */ 
			Qiye qiye = new Qiye();
			String qiyeUserName = new String(request.getParameter("qiyeUserName").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeUserName(qiyeUserName);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			qiye.setPassword(password);
			String qiyeName = new String(request.getParameter("qiyeName").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeName(qiyeName);
			String qiyeQualify = new String(request.getParameter("qiyeQualify").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeQualify(qiyeQualify);
			int qiyePropertyObj = Integer.parseInt(request.getParameter("qiyePropertyObj"));
			qiye.setQiyePropertyObj(qiyePropertyObj);
			int qiyeProfessionObj = Integer.parseInt(request.getParameter("qiyeProfessionObj"));
			qiye.setQiyeProfessionObj(qiyeProfessionObj);
			String qiyeScale = new String(request.getParameter("qiyeScale").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeScale(qiyeScale);
			String connectPerson = new String(request.getParameter("connectPerson").getBytes("iso-8859-1"), "UTF-8");
			qiye.setConnectPerson(connectPerson);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			qiye.setTelephone(telephone);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			qiye.setEmail(email);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			qiye.setAddress(address);

			/* 调用业务层执行添加操作 */
			String result = qiyeDAO.AddQiye(qiye);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除企业：获取企业的企业账号*/
			String qiyeUserName = new String(request.getParameter("qiyeUserName").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = qiyeDAO.DeleteQiye(qiyeUserName);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新企业之前先根据qiyeUserName查询某个企业*/
			String qiyeUserName = new String(request.getParameter("qiyeUserName").getBytes("iso-8859-1"), "UTF-8");
			Qiye qiye = qiyeDAO.GetQiye(qiyeUserName);

			// 客户端查询的企业对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("qiyeUserName").value(qiye.getQiyeUserName());
			  stringer.key("password").value(qiye.getPassword());
			  stringer.key("qiyeName").value(qiye.getQiyeName());
			  stringer.key("qiyeQualify").value(qiye.getQiyeQualify());
			  stringer.key("qiyePropertyObj").value(qiye.getQiyePropertyObj());
			  stringer.key("qiyeProfessionObj").value(qiye.getQiyeProfessionObj());
			  stringer.key("qiyeScale").value(qiye.getQiyeScale());
			  stringer.key("connectPerson").value(qiye.getConnectPerson());
			  stringer.key("telephone").value(qiye.getTelephone());
			  stringer.key("email").value(qiye.getEmail());
			  stringer.key("address").value(qiye.getAddress());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新企业：获取企业参数，参数保存到新建的企业对象 */ 
			Qiye qiye = new Qiye();
			String qiyeUserName = new String(request.getParameter("qiyeUserName").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeUserName(qiyeUserName);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			qiye.setPassword(password);
			String qiyeName = new String(request.getParameter("qiyeName").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeName(qiyeName);
			String qiyeQualify = new String(request.getParameter("qiyeQualify").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeQualify(qiyeQualify);
			int qiyePropertyObj = Integer.parseInt(request.getParameter("qiyePropertyObj"));
			qiye.setQiyePropertyObj(qiyePropertyObj);
			int qiyeProfessionObj = Integer.parseInt(request.getParameter("qiyeProfessionObj"));
			qiye.setQiyeProfessionObj(qiyeProfessionObj);
			String qiyeScale = new String(request.getParameter("qiyeScale").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeScale(qiyeScale);
			String connectPerson = new String(request.getParameter("connectPerson").getBytes("iso-8859-1"), "UTF-8");
			qiye.setConnectPerson(connectPerson);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			qiye.setTelephone(telephone);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			qiye.setEmail(email);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			qiye.setAddress(address);

			/* 调用业务层执行更新操作 */
			String result = qiyeDAO.UpdateQiye(qiye);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
