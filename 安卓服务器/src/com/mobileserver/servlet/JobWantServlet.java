package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.JobWantDAO;
import com.mobileserver.domain.JobWant;

import org.json.JSONStringer;

public class JobWantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造求职业务层对象*/
	private JobWantDAO jobWantDAO = new JobWantDAO();

	/*默认构造函数*/
	public JobWantServlet() {
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
			/*获取查询求职的参数信息*/
			int jobTypeObj = 0;
			if (request.getParameter("jobTypeObj") != null)
				jobTypeObj = Integer.parseInt(request.getParameter("jobTypeObj"));
			int specialObj = 0;
			if (request.getParameter("specialObj") != null)
				specialObj = Integer.parseInt(request.getParameter("specialObj"));
			String positionName = request.getParameter("positionName");
			positionName = positionName == null ? "" : new String(request.getParameter(
					"positionName").getBytes("iso-8859-1"), "UTF-8");
			String workCity = request.getParameter("workCity");
			workCity = workCity == null ? "" : new String(request.getParameter(
					"workCity").getBytes("iso-8859-1"), "UTF-8");
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String addTime = request.getParameter("addTime");
			addTime = addTime == null ? "" : new String(request.getParameter(
					"addTime").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行求职查询*/
			List<JobWant> jobWantList = jobWantDAO.QueryJobWant(jobTypeObj,specialObj,positionName,workCity,userObj,addTime);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<JobWants>").append("\r\n");
			for (int i = 0; i < jobWantList.size(); i++) {
				sb.append("	<JobWant>").append("\r\n")
				.append("		<wantId>")
				.append(jobWantList.get(i).getWantId())
				.append("</wantId>").append("\r\n")
				.append("		<jobTypeObj>")
				.append(jobWantList.get(i).getJobTypeObj())
				.append("</jobTypeObj>").append("\r\n")
				.append("		<specialObj>")
				.append(jobWantList.get(i).getSpecialObj())
				.append("</specialObj>").append("\r\n")
				.append("		<positionName>")
				.append(jobWantList.get(i).getPositionName())
				.append("</positionName>").append("\r\n")
				.append("		<salary>")
				.append(jobWantList.get(i).getSalary())
				.append("</salary>").append("\r\n")
				.append("		<workCity>")
				.append(jobWantList.get(i).getWorkCity())
				.append("</workCity>").append("\r\n")
				.append("		<wantMemo>")
				.append(jobWantList.get(i).getWantMemo())
				.append("</wantMemo>").append("\r\n")
				.append("		<userObj>")
				.append(jobWantList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<addTime>")
				.append(jobWantList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("	</JobWant>").append("\r\n");
			}
			sb.append("</JobWants>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(JobWant jobWant: jobWantList) {
				  stringer.object();
			  stringer.key("wantId").value(jobWant.getWantId());
			  stringer.key("jobTypeObj").value(jobWant.getJobTypeObj());
			  stringer.key("specialObj").value(jobWant.getSpecialObj());
			  stringer.key("positionName").value(jobWant.getPositionName());
			  stringer.key("salary").value(jobWant.getSalary());
			  stringer.key("workCity").value(jobWant.getWorkCity());
			  stringer.key("wantMemo").value(jobWant.getWantMemo());
			  stringer.key("userObj").value(jobWant.getUserObj());
			  stringer.key("addTime").value(jobWant.getAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加求职：获取求职参数，参数保存到新建的求职对象 */ 
			JobWant jobWant = new JobWant();
			int wantId = Integer.parseInt(request.getParameter("wantId"));
			jobWant.setWantId(wantId);
			int jobTypeObj = Integer.parseInt(request.getParameter("jobTypeObj"));
			jobWant.setJobTypeObj(jobTypeObj);
			int specialObj = Integer.parseInt(request.getParameter("specialObj"));
			jobWant.setSpecialObj(specialObj);
			String positionName = new String(request.getParameter("positionName").getBytes("iso-8859-1"), "UTF-8");
			jobWant.setPositionName(positionName);
			String salary = new String(request.getParameter("salary").getBytes("iso-8859-1"), "UTF-8");
			jobWant.setSalary(salary);
			String workCity = new String(request.getParameter("workCity").getBytes("iso-8859-1"), "UTF-8");
			jobWant.setWorkCity(workCity);
			String wantMemo = new String(request.getParameter("wantMemo").getBytes("iso-8859-1"), "UTF-8");
			jobWant.setWantMemo(wantMemo);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			jobWant.setUserObj(userObj);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			jobWant.setAddTime(addTime);

			/* 调用业务层执行添加操作 */
			String result = jobWantDAO.AddJobWant(jobWant);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除求职：获取求职的记录id*/
			int wantId = Integer.parseInt(request.getParameter("wantId"));
			/*调用业务逻辑层执行删除操作*/
			String result = jobWantDAO.DeleteJobWant(wantId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新求职之前先根据wantId查询某个求职*/
			int wantId = Integer.parseInt(request.getParameter("wantId"));
			JobWant jobWant = jobWantDAO.GetJobWant(wantId);

			// 客户端查询的求职对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("wantId").value(jobWant.getWantId());
			  stringer.key("jobTypeObj").value(jobWant.getJobTypeObj());
			  stringer.key("specialObj").value(jobWant.getSpecialObj());
			  stringer.key("positionName").value(jobWant.getPositionName());
			  stringer.key("salary").value(jobWant.getSalary());
			  stringer.key("workCity").value(jobWant.getWorkCity());
			  stringer.key("wantMemo").value(jobWant.getWantMemo());
			  stringer.key("userObj").value(jobWant.getUserObj());
			  stringer.key("addTime").value(jobWant.getAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新求职：获取求职参数，参数保存到新建的求职对象 */ 
			JobWant jobWant = new JobWant();
			int wantId = Integer.parseInt(request.getParameter("wantId"));
			jobWant.setWantId(wantId);
			int jobTypeObj = Integer.parseInt(request.getParameter("jobTypeObj"));
			jobWant.setJobTypeObj(jobTypeObj);
			int specialObj = Integer.parseInt(request.getParameter("specialObj"));
			jobWant.setSpecialObj(specialObj);
			String positionName = new String(request.getParameter("positionName").getBytes("iso-8859-1"), "UTF-8");
			jobWant.setPositionName(positionName);
			String salary = new String(request.getParameter("salary").getBytes("iso-8859-1"), "UTF-8");
			jobWant.setSalary(salary);
			String workCity = new String(request.getParameter("workCity").getBytes("iso-8859-1"), "UTF-8");
			jobWant.setWorkCity(workCity);
			String wantMemo = new String(request.getParameter("wantMemo").getBytes("iso-8859-1"), "UTF-8");
			jobWant.setWantMemo(wantMemo);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			jobWant.setUserObj(userObj);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			jobWant.setAddTime(addTime);

			/* 调用业务层执行更新操作 */
			String result = jobWantDAO.UpdateJobWant(jobWant);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
