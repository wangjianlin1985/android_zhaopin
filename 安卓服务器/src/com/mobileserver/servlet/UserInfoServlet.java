package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.UserInfoDAO;
import com.mobileserver.domain.UserInfo;

import org.json.JSONStringer;

public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������ְ��ҵ������*/
	private UserInfoDAO userInfoDAO = new UserInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public UserInfoServlet() {
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
			/*��ȡ��ѯ��ְ�ߵĲ�����Ϣ*/
			String user_name = request.getParameter("user_name");
			user_name = user_name == null ? "" : new String(request.getParameter(
					"user_name").getBytes("iso-8859-1"), "UTF-8");
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			Timestamp birthDate = null;
			if (request.getParameter("birthDate") != null)
				birthDate = Timestamp.valueOf(request.getParameter("birthDate"));
			int myShoolRecord = 0;
			if (request.getParameter("myShoolRecord") != null)
				myShoolRecord = Integer.parseInt(request.getParameter("myShoolRecord"));
			String schoolName = request.getParameter("schoolName");
			schoolName = schoolName == null ? "" : new String(request.getParameter(
					"schoolName").getBytes("iso-8859-1"), "UTF-8");
			String workYears = request.getParameter("workYears");
			workYears = workYears == null ? "" : new String(request.getParameter(
					"workYears").getBytes("iso-8859-1"), "UTF-8");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ����ְ�߲�ѯ*/
			List<UserInfo> userInfoList = userInfoDAO.QueryUserInfo(user_name,name,birthDate,myShoolRecord,schoolName,workYears,telephone);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<UserInfos>").append("\r\n");
			for (int i = 0; i < userInfoList.size(); i++) {
				sb.append("	<UserInfo>").append("\r\n")
				.append("		<user_name>")
				.append(userInfoList.get(i).getUser_name())
				.append("</user_name>").append("\r\n")
				.append("		<password>")
				.append(userInfoList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<name>")
				.append(userInfoList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("		<gender>")
				.append(userInfoList.get(i).getGender())
				.append("</gender>").append("\r\n")
				.append("		<birthDate>")
				.append(userInfoList.get(i).getBirthDate())
				.append("</birthDate>").append("\r\n")
				.append("		<userPhoto>")
				.append(userInfoList.get(i).getUserPhoto())
				.append("</userPhoto>").append("\r\n")
				.append("		<myShoolRecord>")
				.append(userInfoList.get(i).getMyShoolRecord())
				.append("</myShoolRecord>").append("\r\n")
				.append("		<schoolName>")
				.append(userInfoList.get(i).getSchoolName())
				.append("</schoolName>").append("\r\n")
				.append("		<workYears>")
				.append(userInfoList.get(i).getWorkYears())
				.append("</workYears>").append("\r\n")
				.append("		<telephone>")
				.append(userInfoList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<email>")
				.append(userInfoList.get(i).getEmail())
				.append("</email>").append("\r\n")
				.append("		<address>")
				.append(userInfoList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("		<qzyx>")
				.append(userInfoList.get(i).getQzyx())
				.append("</qzyx>").append("\r\n")
				.append("		<gzjl>")
				.append(userInfoList.get(i).getGzjl())
				.append("</gzjl>").append("\r\n")
				.append("		<jyjl>")
				.append(userInfoList.get(i).getJyjl())
				.append("</jyjl>").append("\r\n")
				.append("		<zwpj>")
				.append(userInfoList.get(i).getZwpj())
				.append("</zwpj>").append("\r\n")
				.append("		<regTime>")
				.append(userInfoList.get(i).getRegTime())
				.append("</regTime>").append("\r\n")
				.append("	</UserInfo>").append("\r\n");
			}
			sb.append("</UserInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(UserInfo userInfo: userInfoList) {
				  stringer.object();
			  stringer.key("user_name").value(userInfo.getUser_name());
			  stringer.key("password").value(userInfo.getPassword());
			  stringer.key("name").value(userInfo.getName());
			  stringer.key("gender").value(userInfo.getGender());
			  stringer.key("birthDate").value(userInfo.getBirthDate());
			  stringer.key("userPhoto").value(userInfo.getUserPhoto());
			  stringer.key("myShoolRecord").value(userInfo.getMyShoolRecord());
			  stringer.key("schoolName").value(userInfo.getSchoolName());
			  stringer.key("workYears").value(userInfo.getWorkYears());
			  stringer.key("telephone").value(userInfo.getTelephone());
			  stringer.key("email").value(userInfo.getEmail());
			  stringer.key("address").value(userInfo.getAddress());
			  stringer.key("qzyx").value(userInfo.getQzyx());
			  stringer.key("gzjl").value(userInfo.getGzjl());
			  stringer.key("jyjl").value(userInfo.getJyjl());
			  stringer.key("zwpj").value(userInfo.getZwpj());
			  stringer.key("regTime").value(userInfo.getRegTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����ְ�ߣ���ȡ��ְ�߲������������浽�½�����ְ�߶��� */ 
			UserInfo userInfo = new UserInfo();
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUser_name(user_name);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setPassword(password);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setName(name);
			String gender = new String(request.getParameter("gender").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setGender(gender);
			Timestamp birthDate = Timestamp.valueOf(request.getParameter("birthDate"));
			userInfo.setBirthDate(birthDate);
			String userPhoto = new String(request.getParameter("userPhoto").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUserPhoto(userPhoto);
			int myShoolRecord = Integer.parseInt(request.getParameter("myShoolRecord"));
			userInfo.setMyShoolRecord(myShoolRecord);
			String schoolName = new String(request.getParameter("schoolName").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setSchoolName(schoolName);
			String workYears = new String(request.getParameter("workYears").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setWorkYears(workYears);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setTelephone(telephone);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setEmail(email);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setAddress(address);
			String qzyx = new String(request.getParameter("qzyx").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setQzyx(qzyx);
			String gzjl = new String(request.getParameter("gzjl").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setGzjl(gzjl);
			String jyjl = new String(request.getParameter("jyjl").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setJyjl(jyjl);
			String zwpj = new String(request.getParameter("zwpj").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setZwpj(zwpj);
			String regTime = new String(request.getParameter("regTime").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setRegTime(regTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = userInfoDAO.AddUserInfo(userInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ְ�ߣ���ȡ��ְ�ߵ��û���*/
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = userInfoDAO.DeleteUserInfo(user_name);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������ְ��֮ǰ�ȸ���user_name��ѯĳ����ְ��*/
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			UserInfo userInfo = userInfoDAO.GetUserInfo(user_name);

			// �ͻ��˲�ѯ����ְ�߶��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("user_name").value(userInfo.getUser_name());
			  stringer.key("password").value(userInfo.getPassword());
			  stringer.key("name").value(userInfo.getName());
			  stringer.key("gender").value(userInfo.getGender());
			  stringer.key("birthDate").value(userInfo.getBirthDate());
			  stringer.key("userPhoto").value(userInfo.getUserPhoto());
			  stringer.key("myShoolRecord").value(userInfo.getMyShoolRecord());
			  stringer.key("schoolName").value(userInfo.getSchoolName());
			  stringer.key("workYears").value(userInfo.getWorkYears());
			  stringer.key("telephone").value(userInfo.getTelephone());
			  stringer.key("email").value(userInfo.getEmail());
			  stringer.key("address").value(userInfo.getAddress());
			  stringer.key("qzyx").value(userInfo.getQzyx());
			  stringer.key("gzjl").value(userInfo.getGzjl());
			  stringer.key("jyjl").value(userInfo.getJyjl());
			  stringer.key("zwpj").value(userInfo.getZwpj());
			  stringer.key("regTime").value(userInfo.getRegTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������ְ�ߣ���ȡ��ְ�߲������������浽�½�����ְ�߶��� */ 
			UserInfo userInfo = new UserInfo();
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUser_name(user_name);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setPassword(password);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setName(name);
			String gender = new String(request.getParameter("gender").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setGender(gender);
			Timestamp birthDate = Timestamp.valueOf(request.getParameter("birthDate"));
			userInfo.setBirthDate(birthDate);
			String userPhoto = new String(request.getParameter("userPhoto").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUserPhoto(userPhoto);
			int myShoolRecord = Integer.parseInt(request.getParameter("myShoolRecord"));
			userInfo.setMyShoolRecord(myShoolRecord);
			String schoolName = new String(request.getParameter("schoolName").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setSchoolName(schoolName);
			String workYears = new String(request.getParameter("workYears").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setWorkYears(workYears);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setTelephone(telephone);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setEmail(email);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setAddress(address);
			String qzyx = new String(request.getParameter("qzyx").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setQzyx(qzyx);
			String gzjl = new String(request.getParameter("gzjl").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setGzjl(gzjl);
			String jyjl = new String(request.getParameter("jyjl").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setJyjl(jyjl);
			String zwpj = new String(request.getParameter("zwpj").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setZwpj(zwpj);
			String regTime = new String(request.getParameter("regTime").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setRegTime(regTime);

			/* ����ҵ���ִ�и��²��� */
			String result = userInfoDAO.UpdateUserInfo(userInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
