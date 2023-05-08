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

	/*构造投递状态业务层对象*/
	private DeliveryStateDAO deliveryStateDAO = new DeliveryStateDAO();

	/*默认构造函数*/
	public DeliveryStateServlet() {
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
			/*获取查询投递状态的参数信息*/

			/*调用业务逻辑层执行投递状态查询*/
			List<DeliveryState> deliveryStateList = deliveryStateDAO.QueryDeliveryState();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加投递状态：获取投递状态参数，参数保存到新建的投递状态对象 */ 
			DeliveryState deliveryState = new DeliveryState();
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			deliveryState.setStateId(stateId);
			String stateName = new String(request.getParameter("stateName").getBytes("iso-8859-1"), "UTF-8");
			deliveryState.setStateName(stateName);

			/* 调用业务层执行添加操作 */
			String result = deliveryStateDAO.AddDeliveryState(deliveryState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除投递状态：获取投递状态的投递状态id*/
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			/*调用业务逻辑层执行删除操作*/
			String result = deliveryStateDAO.DeleteDeliveryState(stateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新投递状态之前先根据stateId查询某个投递状态*/
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			DeliveryState deliveryState = deliveryStateDAO.GetDeliveryState(stateId);

			// 客户端查询的投递状态对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新投递状态：获取投递状态参数，参数保存到新建的投递状态对象 */ 
			DeliveryState deliveryState = new DeliveryState();
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			deliveryState.setStateId(stateId);
			String stateName = new String(request.getParameter("stateName").getBytes("iso-8859-1"), "UTF-8");
			deliveryState.setStateName(stateName);

			/* 调用业务层执行更新操作 */
			String result = deliveryStateDAO.UpdateDeliveryState(deliveryState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
