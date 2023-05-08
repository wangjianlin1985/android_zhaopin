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

	/*����ְλ����ҵ������*/
	private JobTypeDAO jobTypeDAO = new JobTypeDAO();

	/*Ĭ�Ϲ��캯��*/
	public JobTypeServlet() {
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
			/*��ȡ��ѯְλ����Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ��ְλ�����ѯ*/
			List<JobType> jobTypeList = jobTypeDAO.QueryJobType();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ְλ���ࣺ��ȡְλ����������������浽�½���ְλ������� */ 
			JobType jobType = new JobType();
			int jobTypeId = Integer.parseInt(request.getParameter("jobTypeId"));
			jobType.setJobTypeId(jobTypeId);
			String typeName = new String(request.getParameter("typeName").getBytes("iso-8859-1"), "UTF-8");
			jobType.setTypeName(typeName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = jobTypeDAO.AddJobType(jobType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ְλ���ࣺ��ȡְλ�����ְλ����id*/
			int jobTypeId = Integer.parseInt(request.getParameter("jobTypeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = jobTypeDAO.DeleteJobType(jobTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ְλ����֮ǰ�ȸ���jobTypeId��ѯĳ��ְλ����*/
			int jobTypeId = Integer.parseInt(request.getParameter("jobTypeId"));
			JobType jobType = jobTypeDAO.GetJobType(jobTypeId);

			// �ͻ��˲�ѯ��ְλ������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ְλ���ࣺ��ȡְλ����������������浽�½���ְλ������� */ 
			JobType jobType = new JobType();
			int jobTypeId = Integer.parseInt(request.getParameter("jobTypeId"));
			jobType.setJobTypeId(jobTypeId);
			String typeName = new String(request.getParameter("typeName").getBytes("iso-8859-1"), "UTF-8");
			jobType.setTypeName(typeName);

			/* ����ҵ���ִ�и��²��� */
			String result = jobTypeDAO.UpdateJobType(jobType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
