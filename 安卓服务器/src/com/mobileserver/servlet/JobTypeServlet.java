package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.JobTypeDAO;
import com.mobileserver.domain.JobType;

import org.json.JSONStringer;

public class JobTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造职位分类业务层对象*/
	private JobTypeDAO jobTypeDAO = new JobTypeDAO();

	/*默认构造函数*/
	public JobTypeServlet() {
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
			/*获取查询职位分类的参数信息*/

			/*调用业务逻辑层执行职位分类查询*/
			List<JobType> jobTypeList = jobTypeDAO.QueryJobType();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<JobTypes>").append("\r\n");
			for (int i = 0; i < jobTypeList.size(); i++) {
				sb.append("	<JobType>").append("\r\n")
				.append("		<jobTypeId>")
				.append(jobTypeList.get(i).getJobTypeId())
				.append("</jobTypeId>").append("\r\n")
				.append("		<typeName>")
				.append(jobTypeList.get(i).getTypeName())
				.append("</typeName>").append("\r\n")
				.append("	</JobType>").append("\r\n");
			}
			sb.append("</JobTypes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(JobType jobType: jobTypeList) {
				  stringer.object();
			  stringer.key("jobTypeId").value(jobType.getJobTypeId());
			  stringer.key("typeName").value(jobType.getTypeName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加职位分类：获取职位分类参数，参数保存到新建的职位分类对象 */ 
			JobType jobType = new JobType();
			int jobTypeId = Integer.parseInt(request.getParameter("jobTypeId"));
			jobType.setJobTypeId(jobTypeId);
			String typeName = new String(request.getParameter("typeName").getBytes("iso-8859-1"), "UTF-8");
			jobType.setTypeName(typeName);

			/* 调用业务层执行添加操作 */
			String result = jobTypeDAO.AddJobType(jobType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除职位分类：获取职位分类的职位分类id*/
			int jobTypeId = Integer.parseInt(request.getParameter("jobTypeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = jobTypeDAO.DeleteJobType(jobTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新职位分类之前先根据jobTypeId查询某个职位分类*/
			int jobTypeId = Integer.parseInt(request.getParameter("jobTypeId"));
			JobType jobType = jobTypeDAO.GetJobType(jobTypeId);

			// 客户端查询的职位分类对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("jobTypeId").value(jobType.getJobTypeId());
			  stringer.key("typeName").value(jobType.getTypeName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新职位分类：获取职位分类参数，参数保存到新建的职位分类对象 */ 
			JobType jobType = new JobType();
			int jobTypeId = Integer.parseInt(request.getParameter("jobTypeId"));
			jobType.setJobTypeId(jobTypeId);
			String typeName = new String(request.getParameter("typeName").getBytes("iso-8859-1"), "UTF-8");
			jobType.setTypeName(typeName);

			/* 调用业务层执行更新操作 */
			String result = jobTypeDAO.UpdateJobType(jobType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
