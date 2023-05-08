package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.DeliveryDAO;
import com.mobileserver.domain.Delivery;

import org.json.JSONStringer;

public class DeliveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ְλͶ��ҵ������*/
	private DeliveryDAO deliveryDAO = new DeliveryDAO();

	/*Ĭ�Ϲ��캯��*/
	public DeliveryServlet() {
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
			/*��ȡ��ѯְλͶ�ݵĲ�����Ϣ*/
			int jobObj = 0;
			if (request.getParameter("jobObj") != null)
				jobObj = Integer.parseInt(request.getParameter("jobObj"));
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String deliveryTime = request.getParameter("deliveryTime");
			deliveryTime = deliveryTime == null ? "" : new String(request.getParameter(
					"deliveryTime").getBytes("iso-8859-1"), "UTF-8");
			int stateObj = 0;
			if (request.getParameter("stateObj") != null)
				stateObj = Integer.parseInt(request.getParameter("stateObj"));

			/*����ҵ���߼���ִ��ְλͶ�ݲ�ѯ*/
			List<Delivery> deliveryList = deliveryDAO.QueryDelivery(jobObj,userObj,deliveryTime,stateObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Deliverys>").append("\r\n");
			for (int i = 0; i < deliveryList.size(); i++) {
				sb.append("	<Delivery>").append("\r\n")
				.append("		<deliveryId>")
				.append(deliveryList.get(i).getDeliveryId())
				.append("</deliveryId>").append("\r\n")
				.append("		<jobObj>")
				.append(deliveryList.get(i).getJobObj())
				.append("</jobObj>").append("\r\n")
				.append("		<userObj>")
				.append(deliveryList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<deliveryTime>")
				.append(deliveryList.get(i).getDeliveryTime())
				.append("</deliveryTime>").append("\r\n")
				.append("		<stateObj>")
				.append(deliveryList.get(i).getStateObj())
				.append("</stateObj>").append("\r\n")
				.append("		<deliveryDemo>")
				.append(deliveryList.get(i).getDeliveryDemo())
				.append("</deliveryDemo>").append("\r\n")
				.append("	</Delivery>").append("\r\n");
			}
			sb.append("</Deliverys>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Delivery delivery: deliveryList) {
				  stringer.object();
			  stringer.key("deliveryId").value(delivery.getDeliveryId());
			  stringer.key("jobObj").value(delivery.getJobObj());
			  stringer.key("userObj").value(delivery.getUserObj());
			  stringer.key("deliveryTime").value(delivery.getDeliveryTime());
			  stringer.key("stateObj").value(delivery.getStateObj());
			  stringer.key("deliveryDemo").value(delivery.getDeliveryDemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ְλͶ�ݣ���ȡְλͶ�ݲ������������浽�½���ְλͶ�ݶ��� */ 
			Delivery delivery = new Delivery();
			int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
			delivery.setDeliveryId(deliveryId);
			int jobObj = Integer.parseInt(request.getParameter("jobObj"));
			delivery.setJobObj(jobObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			delivery.setUserObj(userObj);
			String deliveryTime = new String(request.getParameter("deliveryTime").getBytes("iso-8859-1"), "UTF-8");
			delivery.setDeliveryTime(deliveryTime);
			int stateObj = Integer.parseInt(request.getParameter("stateObj"));
			delivery.setStateObj(stateObj);
			String deliveryDemo = new String(request.getParameter("deliveryDemo").getBytes("iso-8859-1"), "UTF-8");
			delivery.setDeliveryDemo(deliveryDemo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = deliveryDAO.AddDelivery(delivery);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ְλͶ�ݣ���ȡְλͶ�ݵ�Ͷ��id*/
			int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = deliveryDAO.DeleteDelivery(deliveryId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ְλͶ��֮ǰ�ȸ���deliveryId��ѯĳ��ְλͶ��*/
			int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
			Delivery delivery = deliveryDAO.GetDelivery(deliveryId);

			// �ͻ��˲�ѯ��ְλͶ�ݶ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("deliveryId").value(delivery.getDeliveryId());
			  stringer.key("jobObj").value(delivery.getJobObj());
			  stringer.key("userObj").value(delivery.getUserObj());
			  stringer.key("deliveryTime").value(delivery.getDeliveryTime());
			  stringer.key("stateObj").value(delivery.getStateObj());
			  stringer.key("deliveryDemo").value(delivery.getDeliveryDemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ְλͶ�ݣ���ȡְλͶ�ݲ������������浽�½���ְλͶ�ݶ��� */ 
			Delivery delivery = new Delivery();
			int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
			delivery.setDeliveryId(deliveryId);
			int jobObj = Integer.parseInt(request.getParameter("jobObj"));
			delivery.setJobObj(jobObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			delivery.setUserObj(userObj);
			String deliveryTime = new String(request.getParameter("deliveryTime").getBytes("iso-8859-1"), "UTF-8");
			delivery.setDeliveryTime(deliveryTime);
			int stateObj = Integer.parseInt(request.getParameter("stateObj"));
			delivery.setStateObj(stateObj);
			String deliveryDemo = new String(request.getParameter("deliveryDemo").getBytes("iso-8859-1"), "UTF-8");
			delivery.setDeliveryDemo(deliveryDemo);

			/* ����ҵ���ִ�и��²��� */
			String result = deliveryDAO.UpdateDelivery(delivery);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
