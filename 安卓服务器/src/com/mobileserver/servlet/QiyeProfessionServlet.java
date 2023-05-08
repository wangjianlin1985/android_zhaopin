package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.QiyeProfessionDAO;
import com.mobileserver.domain.QiyeProfession;

import org.json.JSONStringer;

public class QiyeProfessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������ҵ��ҵҵ������*/
	private QiyeProfessionDAO qiyeProfessionDAO = new QiyeProfessionDAO();

	/*Ĭ�Ϲ��캯��*/
	public QiyeProfessionServlet() {
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
			/*��ȡ��ѯ��ҵ��ҵ�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ����ҵ��ҵ��ѯ*/
			List<QiyeProfession> qiyeProfessionList = qiyeProfessionDAO.QueryQiyeProfession();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<QiyeProfessions>").append("\r\n");
			for (int i = 0; i < qiyeProfessionList.size(); i++) {
				sb.append("	<QiyeProfession>").append("\r\n")
				.append("		<id>")
				.append(qiyeProfessionList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<professionName>")
				.append(qiyeProfessionList.get(i).getProfessionName())
				.append("</professionName>").append("\r\n")
				.append("	</QiyeProfession>").append("\r\n");
			}
			sb.append("</QiyeProfessions>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(QiyeProfession qiyeProfession: qiyeProfessionList) {
				  stringer.object();
			  stringer.key("id").value(qiyeProfession.getId());
			  stringer.key("professionName").value(qiyeProfession.getProfessionName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����ҵ��ҵ����ȡ��ҵ��ҵ�������������浽�½�����ҵ��ҵ���� */ 
			QiyeProfession qiyeProfession = new QiyeProfession();
			int id = Integer.parseInt(request.getParameter("id"));
			qiyeProfession.setId(id);
			String professionName = new String(request.getParameter("professionName").getBytes("iso-8859-1"), "UTF-8");
			qiyeProfession.setProfessionName(professionName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = qiyeProfessionDAO.AddQiyeProfession(qiyeProfession);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ҵ��ҵ����ȡ��ҵ��ҵ�ļ�¼���*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = qiyeProfessionDAO.DeleteQiyeProfession(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������ҵ��ҵ֮ǰ�ȸ���id��ѯĳ����ҵ��ҵ*/
			int id = Integer.parseInt(request.getParameter("id"));
			QiyeProfession qiyeProfession = qiyeProfessionDAO.GetQiyeProfession(id);

			// �ͻ��˲�ѯ����ҵ��ҵ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(qiyeProfession.getId());
			  stringer.key("professionName").value(qiyeProfession.getProfessionName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������ҵ��ҵ����ȡ��ҵ��ҵ�������������浽�½�����ҵ��ҵ���� */ 
			QiyeProfession qiyeProfession = new QiyeProfession();
			int id = Integer.parseInt(request.getParameter("id"));
			qiyeProfession.setId(id);
			String professionName = new String(request.getParameter("professionName").getBytes("iso-8859-1"), "UTF-8");
			qiyeProfession.setProfessionName(professionName);

			/* ����ҵ���ִ�и��²��� */
			String result = qiyeProfessionDAO.UpdateQiyeProfession(qiyeProfession);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
