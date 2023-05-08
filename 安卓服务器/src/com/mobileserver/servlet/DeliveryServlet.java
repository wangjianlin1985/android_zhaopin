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

	/*构造职位投递业务层对象*/
	private DeliveryDAO deliveryDAO = new DeliveryDAO();

	/*默认构造函数*/
	public DeliveryServlet() {
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
			/*获取查询职位投递的参数信息*/
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

			/*调用业务逻辑层执行职位投递查询*/
			List<Delivery> deliveryList = deliveryDAO.QueryDelivery(jobObj,userObj,deliveryTime,stateObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加职位投递：获取职位投递参数，参数保存到新建的职位投递对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = deliveryDAO.AddDelivery(delivery);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除职位投递：获取职位投递的投递id*/
			int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
			/*调用业务逻辑层执行删除操作*/
			String result = deliveryDAO.DeleteDelivery(deliveryId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新职位投递之前先根据deliveryId查询某个职位投递*/
			int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
			Delivery delivery = deliveryDAO.GetDelivery(deliveryId);

			// 客户端查询的职位投递对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新职位投递：获取职位投递参数，参数保存到新建的职位投递对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = deliveryDAO.UpdateDelivery(delivery);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
