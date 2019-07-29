package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SpecialInfoDAO;
import com.mobileserver.domain.SpecialInfo;

import org.json.JSONStringer;

public class SpecialInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造专业业务层对象*/
	private SpecialInfoDAO specialInfoDAO = new SpecialInfoDAO();

	/*默认构造函数*/
	public SpecialInfoServlet() {
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
			/*获取查询专业的参数信息*/

			/*调用业务逻辑层执行专业查询*/
			List<SpecialInfo> specialInfoList = specialInfoDAO.QuerySpecialInfo();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SpecialInfos>").append("\r\n");
			for (int i = 0; i < specialInfoList.size(); i++) {
				sb.append("	<SpecialInfo>").append("\r\n")
				.append("		<specialId>")
				.append(specialInfoList.get(i).getSpecialId())
				.append("</specialId>").append("\r\n")
				.append("		<specialName>")
				.append(specialInfoList.get(i).getSpecialName())
				.append("</specialName>").append("\r\n")
				.append("	</SpecialInfo>").append("\r\n");
			}
			sb.append("</SpecialInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SpecialInfo specialInfo: specialInfoList) {
				  stringer.object();
			  stringer.key("specialId").value(specialInfo.getSpecialId());
			  stringer.key("specialName").value(specialInfo.getSpecialName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加专业：获取专业参数，参数保存到新建的专业对象 */ 
			SpecialInfo specialInfo = new SpecialInfo();
			int specialId = Integer.parseInt(request.getParameter("specialId"));
			specialInfo.setSpecialId(specialId);
			String specialName = new String(request.getParameter("specialName").getBytes("iso-8859-1"), "UTF-8");
			specialInfo.setSpecialName(specialName);

			/* 调用业务层执行添加操作 */
			String result = specialInfoDAO.AddSpecialInfo(specialInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除专业：获取专业的专业id*/
			int specialId = Integer.parseInt(request.getParameter("specialId"));
			/*调用业务逻辑层执行删除操作*/
			String result = specialInfoDAO.DeleteSpecialInfo(specialId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新专业之前先根据specialId查询某个专业*/
			int specialId = Integer.parseInt(request.getParameter("specialId"));
			SpecialInfo specialInfo = specialInfoDAO.GetSpecialInfo(specialId);

			// 客户端查询的专业对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("specialId").value(specialInfo.getSpecialId());
			  stringer.key("specialName").value(specialInfo.getSpecialName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新专业：获取专业参数，参数保存到新建的专业对象 */ 
			SpecialInfo specialInfo = new SpecialInfo();
			int specialId = Integer.parseInt(request.getParameter("specialId"));
			specialInfo.setSpecialId(specialId);
			String specialName = new String(request.getParameter("specialName").getBytes("iso-8859-1"), "UTF-8");
			specialInfo.setSpecialName(specialName);

			/* 调用业务层执行更新操作 */
			String result = specialInfoDAO.UpdateSpecialInfo(specialInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
