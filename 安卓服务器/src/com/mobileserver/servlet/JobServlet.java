package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.JobDAO;
import com.mobileserver.domain.Job;

import org.json.JSONStringer;

public class JobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ְλҵ������*/
	private JobDAO jobDAO = new JobDAO();

	/*Ĭ�Ϲ��캯��*/
	public JobServlet() {
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
			/*��ȡ��ѯְλ�Ĳ�����Ϣ*/
			String qiyeObj = "";
			if (request.getParameter("qiyeObj") != null)
				qiyeObj = request.getParameter("qiyeObj");
			String positionName = request.getParameter("positionName");
			positionName = positionName == null ? "" : new String(request.getParameter(
					"positionName").getBytes("iso-8859-1"), "UTF-8");
			int jobTypeObj = 0;
			if (request.getParameter("jobTypeObj") != null)
				jobTypeObj = Integer.parseInt(request.getParameter("jobTypeObj"));
			int specialObj = 0;
			if (request.getParameter("specialObj") != null)
				specialObj = Integer.parseInt(request.getParameter("specialObj"));
			String city = request.getParameter("city");
			city = city == null ? "" : new String(request.getParameter(
					"city").getBytes("iso-8859-1"), "UTF-8");
			int schoolRecordObj = 0;
			if (request.getParameter("schoolRecordObj") != null)
				schoolRecordObj = Integer.parseInt(request.getParameter("schoolRecordObj"));

			/*����ҵ���߼���ִ��ְλ��ѯ*/
			List<Job> jobList = jobDAO.QueryJob(qiyeObj,positionName,jobTypeObj,specialObj,city,schoolRecordObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Jobs>").append("\r\n");
			for (int i = 0; i < jobList.size(); i++) {
				sb.append("	<Job>").append("\r\n")
				.append("		<jobId>")
				.append(jobList.get(i).getJobId())
				.append("</jobId>").append("\r\n")
				.append("		<qiyeObj>")
				.append(jobList.get(i).getQiyeObj())
				.append("</qiyeObj>").append("\r\n")
				.append("		<positionName>")
				.append(jobList.get(i).getPositionName())
				.append("</positionName>").append("\r\n")
				.append("		<jobTypeObj>")
				.append(jobList.get(i).getJobTypeObj())
				.append("</jobTypeObj>").append("\r\n")
				.append("		<specialObj>")
				.append(jobList.get(i).getSpecialObj())
				.append("</specialObj>").append("\r\n")
				.append("		<personNum>")
				.append(jobList.get(i).getPersonNum())
				.append("</personNum>").append("\r\n")
				.append("		<city>")
				.append(jobList.get(i).getCity())
				.append("</city>").append("\r\n")
				.append("		<salary>")
				.append(jobList.get(i).getSalary())
				.append("</salary>").append("\r\n")
				.append("		<schoolRecordObj>")
				.append(jobList.get(i).getSchoolRecordObj())
				.append("</schoolRecordObj>").append("\r\n")
				.append("		<workYears>")
				.append(jobList.get(i).getWorkYears())
				.append("</workYears>").append("\r\n")
				.append("		<workAddress>")
				.append(jobList.get(i).getWorkAddress())
				.append("</workAddress>").append("\r\n")
				.append("		<welfare>")
				.append(jobList.get(i).getWelfare())
				.append("</welfare>").append("\r\n")
				.append("		<positionDesc>")
				.append(jobList.get(i).getPositionDesc())
				.append("</positionDesc>").append("\r\n")
				.append("		<connectPerson>")
				.append(jobList.get(i).getConnectPerson())
				.append("</connectPerson>").append("\r\n")
				.append("		<telephone>")
				.append(jobList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("	</Job>").append("\r\n");
			}
			sb.append("</Jobs>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Job job: jobList) {
				  stringer.object();
			  stringer.key("jobId").value(job.getJobId());
			  stringer.key("qiyeObj").value(job.getQiyeObj());
			  stringer.key("positionName").value(job.getPositionName());
			  stringer.key("jobTypeObj").value(job.getJobTypeObj());
			  stringer.key("specialObj").value(job.getSpecialObj());
			  stringer.key("personNum").value(job.getPersonNum());
			  stringer.key("city").value(job.getCity());
			  stringer.key("salary").value(job.getSalary());
			  stringer.key("schoolRecordObj").value(job.getSchoolRecordObj());
			  stringer.key("workYears").value(job.getWorkYears());
			  stringer.key("workAddress").value(job.getWorkAddress());
			  stringer.key("welfare").value(job.getWelfare());
			  stringer.key("positionDesc").value(job.getPositionDesc());
			  stringer.key("connectPerson").value(job.getConnectPerson());
			  stringer.key("telephone").value(job.getTelephone());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ְλ����ȡְλ�������������浽�½���ְλ���� */ 
			Job job = new Job();
			int jobId = Integer.parseInt(request.getParameter("jobId"));
			job.setJobId(jobId);
			String qiyeObj = new String(request.getParameter("qiyeObj").getBytes("iso-8859-1"), "UTF-8");
			job.setQiyeObj(qiyeObj);
			String positionName = new String(request.getParameter("positionName").getBytes("iso-8859-1"), "UTF-8");
			job.setPositionName(positionName);
			int jobTypeObj = Integer.parseInt(request.getParameter("jobTypeObj"));
			job.setJobTypeObj(jobTypeObj);
			int specialObj = Integer.parseInt(request.getParameter("specialObj"));
			job.setSpecialObj(specialObj);
			String personNum = new String(request.getParameter("personNum").getBytes("iso-8859-1"), "UTF-8");
			job.setPersonNum(personNum);
			String city = new String(request.getParameter("city").getBytes("iso-8859-1"), "UTF-8");
			job.setCity(city);
			String salary = new String(request.getParameter("salary").getBytes("iso-8859-1"), "UTF-8");
			job.setSalary(salary);
			int schoolRecordObj = Integer.parseInt(request.getParameter("schoolRecordObj"));
			job.setSchoolRecordObj(schoolRecordObj);
			String workYears = new String(request.getParameter("workYears").getBytes("iso-8859-1"), "UTF-8");
			job.setWorkYears(workYears);
			String workAddress = new String(request.getParameter("workAddress").getBytes("iso-8859-1"), "UTF-8");
			job.setWorkAddress(workAddress);
			String welfare = new String(request.getParameter("welfare").getBytes("iso-8859-1"), "UTF-8");
			job.setWelfare(welfare);
			String positionDesc = new String(request.getParameter("positionDesc").getBytes("iso-8859-1"), "UTF-8");
			job.setPositionDesc(positionDesc);
			String connectPerson = new String(request.getParameter("connectPerson").getBytes("iso-8859-1"), "UTF-8");
			job.setConnectPerson(connectPerson);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			job.setTelephone(telephone);

			/* ����ҵ���ִ����Ӳ��� */
			String result = jobDAO.AddJob(job);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ְλ����ȡְλ��ְλid*/
			int jobId = Integer.parseInt(request.getParameter("jobId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = jobDAO.DeleteJob(jobId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ְλ֮ǰ�ȸ���jobId��ѯĳ��ְλ*/
			int jobId = Integer.parseInt(request.getParameter("jobId"));
			Job job = jobDAO.GetJob(jobId);

			// �ͻ��˲�ѯ��ְλ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("jobId").value(job.getJobId());
			  stringer.key("qiyeObj").value(job.getQiyeObj());
			  stringer.key("positionName").value(job.getPositionName());
			  stringer.key("jobTypeObj").value(job.getJobTypeObj());
			  stringer.key("specialObj").value(job.getSpecialObj());
			  stringer.key("personNum").value(job.getPersonNum());
			  stringer.key("city").value(job.getCity());
			  stringer.key("salary").value(job.getSalary());
			  stringer.key("schoolRecordObj").value(job.getSchoolRecordObj());
			  stringer.key("workYears").value(job.getWorkYears());
			  stringer.key("workAddress").value(job.getWorkAddress());
			  stringer.key("welfare").value(job.getWelfare());
			  stringer.key("positionDesc").value(job.getPositionDesc());
			  stringer.key("connectPerson").value(job.getConnectPerson());
			  stringer.key("telephone").value(job.getTelephone());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ְλ����ȡְλ�������������浽�½���ְλ���� */ 
			Job job = new Job();
			int jobId = Integer.parseInt(request.getParameter("jobId"));
			job.setJobId(jobId);
			String qiyeObj = new String(request.getParameter("qiyeObj").getBytes("iso-8859-1"), "UTF-8");
			job.setQiyeObj(qiyeObj);
			String positionName = new String(request.getParameter("positionName").getBytes("iso-8859-1"), "UTF-8");
			job.setPositionName(positionName);
			int jobTypeObj = Integer.parseInt(request.getParameter("jobTypeObj"));
			job.setJobTypeObj(jobTypeObj);
			int specialObj = Integer.parseInt(request.getParameter("specialObj"));
			job.setSpecialObj(specialObj);
			String personNum = new String(request.getParameter("personNum").getBytes("iso-8859-1"), "UTF-8");
			job.setPersonNum(personNum);
			String city = new String(request.getParameter("city").getBytes("iso-8859-1"), "UTF-8");
			job.setCity(city);
			String salary = new String(request.getParameter("salary").getBytes("iso-8859-1"), "UTF-8");
			job.setSalary(salary);
			int schoolRecordObj = Integer.parseInt(request.getParameter("schoolRecordObj"));
			job.setSchoolRecordObj(schoolRecordObj);
			String workYears = new String(request.getParameter("workYears").getBytes("iso-8859-1"), "UTF-8");
			job.setWorkYears(workYears);
			String workAddress = new String(request.getParameter("workAddress").getBytes("iso-8859-1"), "UTF-8");
			job.setWorkAddress(workAddress);
			String welfare = new String(request.getParameter("welfare").getBytes("iso-8859-1"), "UTF-8");
			job.setWelfare(welfare);
			String positionDesc = new String(request.getParameter("positionDesc").getBytes("iso-8859-1"), "UTF-8");
			job.setPositionDesc(positionDesc);
			String connectPerson = new String(request.getParameter("connectPerson").getBytes("iso-8859-1"), "UTF-8");
			job.setConnectPerson(connectPerson);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			job.setTelephone(telephone);

			/* ����ҵ���ִ�и��²��� */
			String result = jobDAO.UpdateJob(job);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
