package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.DeliveryStateDAO;
import com.mobileserver.domain.DeliveryState;

import org.json.JSONStringer;

public class DeliveryStateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����Ͷ��״̬ҵ������*/
	private DeliveryStateDAO deliveryStateDAO = new DeliveryStateDAO();

	/*Ĭ�Ϲ��캯��*/
	public DeliveryStateServlet() {
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
			/*��ȡ��ѯͶ��״̬�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ��Ͷ��״̬��ѯ*/
			List<DeliveryState> deliveryStateList = deliveryStateDAO.QueryDeliveryState();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<DeliveryStates>").append("\r\n");
			for (int i = 0; i < deliveryStateList.size(); i++) {
				sb.append("	<DeliveryState>").append("\r\n")
				.append("		<stateId>")
				.append(deliveryStateList.get(i).getStateId())
				.append("</stateId>").append("\r\n")
				.append("		<stateName>")
				.append(deliveryStateList.get(i).getStateName())
				.append("</stateName>").append("\r\n")
				.append("	</DeliveryState>").append("\r\n");
			}
			sb.append("</DeliveryStates>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(DeliveryState deliveryState: deliveryStateList) {
				  stringer.object();
			  stringer.key("stateId").value(deliveryState.getStateId());
			  stringer.key("stateName").value(deliveryState.getStateName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���Ͷ��״̬����ȡͶ��״̬�������������浽�½���Ͷ��״̬���� */ 
			DeliveryState deliveryState = new DeliveryState();
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			deliveryState.setStateId(stateId);
			String stateName = new String(request.getParameter("stateName").getBytes("iso-8859-1"), "UTF-8");
			deliveryState.setStateName(stateName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = deliveryStateDAO.AddDeliveryState(deliveryState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��Ͷ��״̬����ȡͶ��״̬��Ͷ��״̬id*/
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = deliveryStateDAO.DeleteDeliveryState(stateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����Ͷ��״̬֮ǰ�ȸ���stateId��ѯĳ��Ͷ��״̬*/
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			DeliveryState deliveryState = deliveryStateDAO.GetDeliveryState(stateId);

			// �ͻ��˲�ѯ��Ͷ��״̬���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("stateId").value(deliveryState.getStateId());
			  stringer.key("stateName").value(deliveryState.getStateName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����Ͷ��״̬����ȡͶ��״̬�������������浽�½���Ͷ��״̬���� */ 
			DeliveryState deliveryState = new DeliveryState();
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			deliveryState.setStateId(stateId);
			String stateName = new String(request.getParameter("stateName").getBytes("iso-8859-1"), "UTF-8");
			deliveryState.setStateName(stateName);

			/* ����ҵ���ִ�и��²��� */
			String result = deliveryStateDAO.UpdateDeliveryState(deliveryState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
