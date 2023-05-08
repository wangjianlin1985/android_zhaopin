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

	/*����ѧ��ҵ������*/
	private SchoolRecordDAO schoolRecordDAO = new SchoolRecordDAO();

	/*Ĭ�Ϲ��캯��*/
	public SchoolRecordServlet() {
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
			/*��ȡ��ѯѧ���Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ��ѧ����ѯ*/
			List<SchoolRecord> schoolRecordList = schoolRecordDAO.QuerySchoolRecord();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ѧ������ȡѧ���������������浽�½���ѧ������ */ 
			SchoolRecord schoolRecord = new SchoolRecord();
			int id = Integer.parseInt(request.getParameter("id"));
			schoolRecord.setId(id);
			String schooRecordName = new String(request.getParameter("schooRecordName").getBytes("iso-8859-1"), "UTF-8");
			schoolRecord.setSchooRecordName(schooRecordName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = schoolRecordDAO.AddSchoolRecord(schoolRecord);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ѧ������ȡѧ���ļ�¼���*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = schoolRecordDAO.DeleteSchoolRecord(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ѧ��֮ǰ�ȸ���id��ѯĳ��ѧ��*/
			int id = Integer.parseInt(request.getParameter("id"));
			SchoolRecord schoolRecord = schoolRecordDAO.GetSchoolRecord(id);

			// �ͻ��˲�ѯ��ѧ�����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ѧ������ȡѧ���������������浽�½���ѧ������ */ 
			SchoolRecord schoolRecord = new SchoolRecord();
			int id = Integer.parseInt(request.getParameter("id"));
			schoolRecord.setId(id);
			String schooRecordName = new String(request.getParameter("schooRecordName").getBytes("iso-8859-1"), "UTF-8");
			schoolRecord.setSchooRecordName(schooRecordName);

			/* ����ҵ���ִ�и��²��� */
			String result = schoolRecordDAO.UpdateSchoolRecord(schoolRecord);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
