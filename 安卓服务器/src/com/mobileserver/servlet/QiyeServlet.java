package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.QiyeDAO;
import com.mobileserver.domain.Qiye;

import org.json.JSONStringer;

public class QiyeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������ҵҵ������*/
	private QiyeDAO qiyeDAO = new QiyeDAO();

	/*Ĭ�Ϲ��캯��*/
	public QiyeServlet() {
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
			/*��ȡ��ѯ��ҵ�Ĳ�����Ϣ*/
			String qiyeUserName = request.getParameter("qiyeUserName");
			qiyeUserName = qiyeUserName == null ? "" : new String(request.getParameter(
					"qiyeUserName").getBytes("iso-8859-1"), "UTF-8");
			String qiyeName = request.getParameter("qiyeName");
			qiyeName = qiyeName == null ? "" : new String(request.getParameter(
					"qiyeName").getBytes("iso-8859-1"), "UTF-8");
			int qiyePropertyObj = 0;
			if (request.getParameter("qiyePropertyObj") != null)
				qiyePropertyObj = Integer.parseInt(request.getParameter("qiyePropertyObj"));
			int qiyeProfessionObj = 0;
			if (request.getParameter("qiyeProfessionObj") != null)
				qiyeProfessionObj = Integer.parseInt(request.getParameter("qiyeProfessionObj"));
			String connectPerson = request.getParameter("connectPerson");
			connectPerson = connectPerson == null ? "" : new String(request.getParameter(
					"connectPerson").getBytes("iso-8859-1"), "UTF-8");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ����ҵ��ѯ*/
			List<Qiye> qiyeList = qiyeDAO.QueryQiye(qiyeUserName,qiyeName,qiyePropertyObj,qiyeProfessionObj,connectPerson,telephone);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Qiyes>").append("\r\n");
			for (int i = 0; i < qiyeList.size(); i++) {
				sb.append("	<Qiye>").append("\r\n")
				.append("		<qiyeUserName>")
				.append(qiyeList.get(i).getQiyeUserName())
				.append("</qiyeUserName>").append("\r\n")
				.append("		<password>")
				.append(qiyeList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<qiyeName>")
				.append(qiyeList.get(i).getQiyeName())
				.append("</qiyeName>").append("\r\n")
				.append("		<qiyeQualify>")
				.append(qiyeList.get(i).getQiyeQualify())
				.append("</qiyeQualify>").append("\r\n")
				.append("		<qiyePropertyObj>")
				.append(qiyeList.get(i).getQiyePropertyObj())
				.append("</qiyePropertyObj>").append("\r\n")
				.append("		<qiyeProfessionObj>")
				.append(qiyeList.get(i).getQiyeProfessionObj())
				.append("</qiyeProfessionObj>").append("\r\n")
				.append("		<qiyeScale>")
				.append(qiyeList.get(i).getQiyeScale())
				.append("</qiyeScale>").append("\r\n")
				.append("		<connectPerson>")
				.append(qiyeList.get(i).getConnectPerson())
				.append("</connectPerson>").append("\r\n")
				.append("		<telephone>")
				.append(qiyeList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<email>")
				.append(qiyeList.get(i).getEmail())
				.append("</email>").append("\r\n")
				.append("		<address>")
				.append(qiyeList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("	</Qiye>").append("\r\n");
			}
			sb.append("</Qiyes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Qiye qiye: qiyeList) {
				  stringer.object();
			  stringer.key("qiyeUserName").value(qiye.getQiyeUserName());
			  stringer.key("password").value(qiye.getPassword());
			  stringer.key("qiyeName").value(qiye.getQiyeName());
			  stringer.key("qiyeQualify").value(qiye.getQiyeQualify());
			  stringer.key("qiyePropertyObj").value(qiye.getQiyePropertyObj());
			  stringer.key("qiyeProfessionObj").value(qiye.getQiyeProfessionObj());
			  stringer.key("qiyeScale").value(qiye.getQiyeScale());
			  stringer.key("connectPerson").value(qiye.getConnectPerson());
			  stringer.key("telephone").value(qiye.getTelephone());
			  stringer.key("email").value(qiye.getEmail());
			  stringer.key("address").value(qiye.getAddress());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����ҵ����ȡ��ҵ�������������浽�½�����ҵ���� */ 
			Qiye qiye = new Qiye();
			String qiyeUserName = new String(request.getParameter("qiyeUserName").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeUserName(qiyeUserName);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			qiye.setPassword(password);
			String qiyeName = new String(request.getParameter("qiyeName").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeName(qiyeName);
			String qiyeQualify = new String(request.getParameter("qiyeQualify").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeQualify(qiyeQualify);
			int qiyePropertyObj = Integer.parseInt(request.getParameter("qiyePropertyObj"));
			qiye.setQiyePropertyObj(qiyePropertyObj);
			int qiyeProfessionObj = Integer.parseInt(request.getParameter("qiyeProfessionObj"));
			qiye.setQiyeProfessionObj(qiyeProfessionObj);
			String qiyeScale = new String(request.getParameter("qiyeScale").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeScale(qiyeScale);
			String connectPerson = new String(request.getParameter("connectPerson").getBytes("iso-8859-1"), "UTF-8");
			qiye.setConnectPerson(connectPerson);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			qiye.setTelephone(telephone);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			qiye.setEmail(email);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			qiye.setAddress(address);

			/* ����ҵ���ִ����Ӳ��� */
			String result = qiyeDAO.AddQiye(qiye);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ҵ����ȡ��ҵ����ҵ�˺�*/
			String qiyeUserName = new String(request.getParameter("qiyeUserName").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = qiyeDAO.DeleteQiye(qiyeUserName);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������ҵ֮ǰ�ȸ���qiyeUserName��ѯĳ����ҵ*/
			String qiyeUserName = new String(request.getParameter("qiyeUserName").getBytes("iso-8859-1"), "UTF-8");
			Qiye qiye = qiyeDAO.GetQiye(qiyeUserName);

			// �ͻ��˲�ѯ����ҵ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("qiyeUserName").value(qiye.getQiyeUserName());
			  stringer.key("password").value(qiye.getPassword());
			  stringer.key("qiyeName").value(qiye.getQiyeName());
			  stringer.key("qiyeQualify").value(qiye.getQiyeQualify());
			  stringer.key("qiyePropertyObj").value(qiye.getQiyePropertyObj());
			  stringer.key("qiyeProfessionObj").value(qiye.getQiyeProfessionObj());
			  stringer.key("qiyeScale").value(qiye.getQiyeScale());
			  stringer.key("connectPerson").value(qiye.getConnectPerson());
			  stringer.key("telephone").value(qiye.getTelephone());
			  stringer.key("email").value(qiye.getEmail());
			  stringer.key("address").value(qiye.getAddress());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������ҵ����ȡ��ҵ�������������浽�½�����ҵ���� */ 
			Qiye qiye = new Qiye();
			String qiyeUserName = new String(request.getParameter("qiyeUserName").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeUserName(qiyeUserName);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			qiye.setPassword(password);
			String qiyeName = new String(request.getParameter("qiyeName").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeName(qiyeName);
			String qiyeQualify = new String(request.getParameter("qiyeQualify").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeQualify(qiyeQualify);
			int qiyePropertyObj = Integer.parseInt(request.getParameter("qiyePropertyObj"));
			qiye.setQiyePropertyObj(qiyePropertyObj);
			int qiyeProfessionObj = Integer.parseInt(request.getParameter("qiyeProfessionObj"));
			qiye.setQiyeProfessionObj(qiyeProfessionObj);
			String qiyeScale = new String(request.getParameter("qiyeScale").getBytes("iso-8859-1"), "UTF-8");
			qiye.setQiyeScale(qiyeScale);
			String connectPerson = new String(request.getParameter("connectPerson").getBytes("iso-8859-1"), "UTF-8");
			qiye.setConnectPerson(connectPerson);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			qiye.setTelephone(telephone);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			qiye.setEmail(email);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			qiye.setAddress(address);

			/* ����ҵ���ִ�и��²��� */
			String result = qiyeDAO.UpdateQiye(qiye);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
