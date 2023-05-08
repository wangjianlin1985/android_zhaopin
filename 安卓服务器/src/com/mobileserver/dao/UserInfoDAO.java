package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.UserInfo;
import com.mobileserver.util.DB;

public class UserInfoDAO {

	public List<UserInfo> QueryUserInfo(String user_name,String name,Timestamp birthDate,int myShoolRecord,String schoolName,String workYears,String telephone) {
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		DB db = new DB();
		String sql = "select * from UserInfo where 1=1";
		if (!user_name.equals(""))
			sql += " and user_name like '%" + user_name + "%'";
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if(birthDate!=null)
			sql += " and birthDate='" + birthDate + "'";
		if (myShoolRecord != 0)
			sql += " and myShoolRecord=" + myShoolRecord;
		if (!schoolName.equals(""))
			sql += " and schoolName like '%" + schoolName + "%'";
		if (!workYears.equals(""))
			sql += " and workYears like '%" + workYears + "%'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUser_name(rs.getString("user_name"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setName(rs.getString("name"));
				userInfo.setGender(rs.getString("gender"));
				userInfo.setBirthDate(rs.getTimestamp("birthDate"));
				userInfo.setUserPhoto(rs.getString("userPhoto"));
				userInfo.setMyShoolRecord(rs.getInt("myShoolRecord"));
				userInfo.setSchoolName(rs.getString("schoolName"));
				userInfo.setWorkYears(rs.getString("workYears"));
				userInfo.setTelephone(rs.getString("telephone"));
				userInfo.setEmail(rs.getString("email"));
				userInfo.setAddress(rs.getString("address"));
				userInfo.setQzyx(rs.getString("qzyx"));
				userInfo.setGzjl(rs.getString("gzjl"));
				userInfo.setJyjl(rs.getString("jyjl"));
				userInfo.setZwpj(rs.getString("zwpj"));
				userInfo.setRegTime(rs.getString("regTime"));
				userInfoList.add(userInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return userInfoList;
	}
	/* 传入求职者对象，进行求职者的添加业务 */
	public String AddUserInfo(UserInfo userInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新求职者 */
			String sqlString = "insert into UserInfo(user_name,password,name,gender,birthDate,userPhoto,myShoolRecord,schoolName,workYears,telephone,email,address,qzyx,gzjl,jyjl,zwpj,regTime) values (";
			sqlString += "'" + userInfo.getUser_name() + "',";
			sqlString += "'" + userInfo.getPassword() + "',";
			sqlString += "'" + userInfo.getName() + "',";
			sqlString += "'" + userInfo.getGender() + "',";
			sqlString += "'" + userInfo.getBirthDate() + "',";
			sqlString += "'" + userInfo.getUserPhoto() + "',";
			sqlString += userInfo.getMyShoolRecord() + ",";
			sqlString += "'" + userInfo.getSchoolName() + "',";
			sqlString += "'" + userInfo.getWorkYears() + "',";
			sqlString += "'" + userInfo.getTelephone() + "',";
			sqlString += "'" + userInfo.getEmail() + "',";
			sqlString += "'" + userInfo.getAddress() + "',";
			sqlString += "'" + userInfo.getQzyx() + "',";
			sqlString += "'" + userInfo.getGzjl() + "',";
			sqlString += "'" + userInfo.getJyjl() + "',";
			sqlString += "'" + userInfo.getZwpj() + "',";
			sqlString += "'" + userInfo.getRegTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "求职者添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "求职者添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除求职者 */
	public String DeleteUserInfo(String user_name) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from UserInfo where user_name='" + user_name + "'";
			db.executeUpdate(sqlString);
			result = "求职者删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "求职者删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据用户名获取到求职者 */
	public UserInfo GetUserInfo(String user_name) {
		UserInfo userInfo = null;
		DB db = new DB();
		String sql = "select * from UserInfo where user_name='" + user_name + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				userInfo = new UserInfo();
				userInfo.setUser_name(rs.getString("user_name"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setName(rs.getString("name"));
				userInfo.setGender(rs.getString("gender"));
				userInfo.setBirthDate(rs.getTimestamp("birthDate"));
				userInfo.setUserPhoto(rs.getString("userPhoto"));
				userInfo.setMyShoolRecord(rs.getInt("myShoolRecord"));
				userInfo.setSchoolName(rs.getString("schoolName"));
				userInfo.setWorkYears(rs.getString("workYears"));
				userInfo.setTelephone(rs.getString("telephone"));
				userInfo.setEmail(rs.getString("email"));
				userInfo.setAddress(rs.getString("address"));
				userInfo.setQzyx(rs.getString("qzyx"));
				userInfo.setGzjl(rs.getString("gzjl"));
				userInfo.setJyjl(rs.getString("jyjl"));
				userInfo.setZwpj(rs.getString("zwpj"));
				userInfo.setRegTime(rs.getString("regTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return userInfo;
	}
	/* 更新求职者 */
	public String UpdateUserInfo(UserInfo userInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update UserInfo set ";
			sql += "password='" + userInfo.getPassword() + "',";
			sql += "name='" + userInfo.getName() + "',";
			sql += "gender='" + userInfo.getGender() + "',";
			sql += "birthDate='" + userInfo.getBirthDate() + "',";
			sql += "userPhoto='" + userInfo.getUserPhoto() + "',";
			sql += "myShoolRecord=" + userInfo.getMyShoolRecord() + ",";
			sql += "schoolName='" + userInfo.getSchoolName() + "',";
			sql += "workYears='" + userInfo.getWorkYears() + "',";
			sql += "telephone='" + userInfo.getTelephone() + "',";
			sql += "email='" + userInfo.getEmail() + "',";
			sql += "address='" + userInfo.getAddress() + "',";
			sql += "qzyx='" + userInfo.getQzyx() + "',";
			sql += "gzjl='" + userInfo.getGzjl() + "',";
			sql += "jyjl='" + userInfo.getJyjl() + "',";
			sql += "zwpj='" + userInfo.getZwpj() + "',";
			sql += "regTime='" + userInfo.getRegTime() + "'";
			sql += " where user_name='" + userInfo.getUser_name() + "'";
			db.executeUpdate(sql);
			result = "求职者更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "求职者更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
