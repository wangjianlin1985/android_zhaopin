package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.QiyePropertyDAO;
import com.mobileserver.domain.QiyeProperty;

import org.json.JSONStringer;

public class QiyePropertyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������ҵ����ҵ������*/
	private QiyePropertyDAO qiyePropertyDAO = new QiyePropertyDAO();

	/*Ĭ�Ϲ��캯��*/
	public QiyePropertyServlet() {
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
			/*��ȡ��ѯ��ҵ���ʵĲ�����Ϣ*/

			/*����ҵ���߼���ִ����ҵ���ʲ�ѯ*/
			List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryQiyeProperty();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<QiyePropertys>").append("\r\n");
			for (int i = 0; i < qiyePropertyList.size(); i++) {
				sb.append("	<QiyeProperty>").append("\r\n")
				.append("		<id>")
				.append(qiyePropertyList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<propertyName>")
				.append(qiyePropertyList.get(i).getPropertyName())
				.append("</propertyName>").append("\r\n")
				.append("	</QiyeProperty>").append("\r\n");
			}
			sb.append("</QiyePropertys>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(QiyeProperty qiyeProperty: qiyePropertyList) {
				  stringer.object();
			  stringer.key("id").value(qiyeProperty.getId());
			  stringer.key("propertyName").value(qiyeProperty.getPropertyName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����ҵ���ʣ���ȡ��ҵ���ʲ������������浽�½�����ҵ���ʶ��� */ 
			QiyeProperty qiyeProperty = new QiyeProperty();
			int id = Integer.parseInt(request.getParameter("id"));
			qiyeProperty.setId(id);
			String propertyName = new String(request.getParameter("propertyName").getBytes("iso-8859-1"), "UTF-8");
			qiyeProperty.setPropertyName(propertyName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = qiyePropertyDAO.AddQiyeProperty(qiyeProperty);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ҵ���ʣ���ȡ��ҵ���ʵļ�¼���*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = qiyePropertyDAO.DeleteQiyeProperty(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������ҵ����֮ǰ�ȸ���id��ѯĳ����ҵ����*/
			int id = Integer.parseInt(request.getParameter("id"));
			QiyeProperty qiyeProperty = qiyePropertyDAO.GetQiyeProperty(id);

			// �ͻ��˲�ѯ����ҵ���ʶ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(qiyeProperty.getId());
			  stringer.key("propertyName").value(qiyeProperty.getPropertyName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������ҵ���ʣ���ȡ��ҵ���ʲ������������浽�½�����ҵ���ʶ��� */ 
			QiyeProperty qiyeProperty = new QiyeProperty();
			int id = Integer.parseInt(request.getParameter("id"));
			qiyeProperty.setId(id);
			String propertyName = new String(request.getParameter("propertyName").getBytes("iso-8859-1"), "UTF-8");
			qiyeProperty.setPropertyName(propertyName);

			/* ����ҵ���ִ�и��²��� */
			String result = qiyePropertyDAO.UpdateQiyeProperty(qiyeProperty);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
