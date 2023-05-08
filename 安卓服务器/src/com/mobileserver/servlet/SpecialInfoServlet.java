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

	/*����רҵҵ������*/
	private SpecialInfoDAO specialInfoDAO = new SpecialInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public SpecialInfoServlet() {
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
			/*��ȡ��ѯרҵ�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ��רҵ��ѯ*/
			List<SpecialInfo> specialInfoList = specialInfoDAO.QuerySpecialInfo();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���רҵ����ȡרҵ�������������浽�½���רҵ���� */ 
			SpecialInfo specialInfo = new SpecialInfo();
			int specialId = Integer.parseInt(request.getParameter("specialId"));
			specialInfo.setSpecialId(specialId);
			String specialName = new String(request.getParameter("specialName").getBytes("iso-8859-1"), "UTF-8");
			specialInfo.setSpecialName(specialName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = specialInfoDAO.AddSpecialInfo(specialInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��רҵ����ȡרҵ��רҵid*/
			int specialId = Integer.parseInt(request.getParameter("specialId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = specialInfoDAO.DeleteSpecialInfo(specialId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����רҵ֮ǰ�ȸ���specialId��ѯĳ��רҵ*/
			int specialId = Integer.parseInt(request.getParameter("specialId"));
			SpecialInfo specialInfo = specialInfoDAO.GetSpecialInfo(specialId);

			// �ͻ��˲�ѯ��רҵ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����רҵ����ȡרҵ�������������浽�½���רҵ���� */ 
			SpecialInfo specialInfo = new SpecialInfo();
			int specialId = Integer.parseInt(request.getParameter("specialId"));
			specialInfo.setSpecialId(specialId);
			String specialName = new String(request.getParameter("specialName").getBytes("iso-8859-1"), "UTF-8");
			specialInfo.setSpecialName(specialName);

			/* ����ҵ���ִ�и��²��� */
			String result = specialInfoDAO.UpdateSpecialInfo(specialInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
