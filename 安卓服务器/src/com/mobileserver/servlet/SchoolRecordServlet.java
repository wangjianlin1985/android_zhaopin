package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SchoolRecordDAO;
import com.mobileserver.domain.SchoolRecord;

import org.json.JSONStringer;

public class SchoolRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造学历业务层对象*/
	private SchoolRecordDAO schoolRecordDAO = new SchoolRecordDAO();

	/*默认构造函数*/
	public SchoolRecordServlet() {
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
			/*获取查询学历的参数信息*/

			/*调用业务逻辑层执行学历查询*/
			List<SchoolRecord> schoolRecordList = schoolRecordDAO.QuerySchoolRecord();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SchoolRecords>").append("\r\n");
			for (int i = 0; i < schoolRecordList.size(); i++) {
				sb.append("	<SchoolRecord>").append("\r\n")
				.append("		<id>")
				.append(schoolRecordList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<schooRecordName>")
				.append(schoolRecordList.get(i).getSchooRecordName())
				.append("</schooRecordName>").append("\r\n")
				.append("	</SchoolRecord>").append("\r\n");
			}
			sb.append("</SchoolRecords>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SchoolRecord schoolRecord: schoolRecordList) {
				  stringer.object();
			  stringer.key("id").value(schoolRecord.getId());
			  stringer.key("schooRecordName").value(schoolRecord.getSchooRecordName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加学历：获取学历参数，参数保存到新建的学历对象 */ 
			SchoolRecord schoolRecord = new SchoolRecord();
			int id = Integer.parseInt(request.getParameter("id"));
			schoolRecord.setId(id);
			String schooRecordName = new String(request.getParameter("schooRecordName").getBytes("iso-8859-1"), "UTF-8");
			schoolRecord.setSchooRecordName(schooRecordName);

			/* 调用业务层执行添加操作 */
			String result = schoolRecordDAO.AddSchoolRecord(schoolRecord);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除学历：获取学历的记录编号*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*调用业务逻辑层执行删除操作*/
			String result = schoolRecordDAO.DeleteSchoolRecord(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新学历之前先根据id查询某个学历*/
			int id = Integer.parseInt(request.getParameter("id"));
			SchoolRecord schoolRecord = schoolRecordDAO.GetSchoolRecord(id);

			// 客户端查询的学历对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(schoolRecord.getId());
			  stringer.key("schooRecordName").value(schoolRecord.getSchooRecordName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新学历：获取学历参数，参数保存到新建的学历对象 */ 
			SchoolRecord schoolRecord = new SchoolRecord();
			int id = Integer.parseInt(request.getParameter("id"));
			schoolRecord.setId(id);
			String schooRecordName = new String(request.getParameter("schooRecordName").getBytes("iso-8859-1"), "UTF-8");
			schoolRecord.setSchooRecordName(schooRecordName);

			/* 调用业务层执行更新操作 */
			String result = schoolRecordDAO.UpdateSchoolRecord(schoolRecord);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
