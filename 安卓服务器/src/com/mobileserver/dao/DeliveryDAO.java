package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Delivery;
import com.mobileserver.util.DB;

public class DeliveryDAO {

	public List<Delivery> QueryDelivery(int jobObj,String userObj,String deliveryTime,int stateObj) {
		List<Delivery> deliveryList = new ArrayList<Delivery>();
		DB db = new DB();
		String sql = "select * from Delivery where 1=1";
		if (jobObj != 0)
			sql += " and jobObj=" + jobObj;
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!deliveryTime.equals(""))
			sql += " and deliveryTime like '%" + deliveryTime + "%'";
		if (stateObj != 0)
			sql += " and stateObj=" + stateObj;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Delivery delivery = new Delivery();
				delivery.setDeliveryId(rs.getInt("deliveryId"));
				delivery.setJobObj(rs.getInt("jobObj"));
				delivery.setUserObj(rs.getString("userObj"));
				delivery.setDeliveryTime(rs.getString("deliveryTime"));
				delivery.setStateObj(rs.getInt("stateObj"));
				delivery.setDeliveryDemo(rs.getString("deliveryDemo"));
				deliveryList.add(delivery);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return deliveryList;
	}
	/* 传入职位投递对象，进行职位投递的添加业务 */
	public String AddDelivery(Delivery delivery) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新职位投递 */
			String sqlString = "insert into Delivery(jobObj,userObj,deliveryTime,stateObj,deliveryDemo) values (";
			sqlString += delivery.getJobObj() + ",";
			sqlString += "'" + delivery.getUserObj() + "',";
			sqlString += "'" + delivery.getDeliveryTime() + "',";
			sqlString += delivery.getStateObj() + ",";
			sqlString += "'" + delivery.getDeliveryDemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "职位投递添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "职位投递添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除职位投递 */
	public String DeleteDelivery(int deliveryId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Delivery where deliveryId=" + deliveryId;
			db.executeUpdate(sqlString);
			result = "职位投递删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "职位投递删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据投递id获取到职位投递 */
	public Delivery GetDelivery(int deliveryId) {
		Delivery delivery = null;
		DB db = new DB();
		String sql = "select * from Delivery where deliveryId=" + deliveryId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				delivery = new Delivery();
				delivery.setDeliveryId(rs.getInt("deliveryId"));
				delivery.setJobObj(rs.getInt("jobObj"));
				delivery.setUserObj(rs.getString("userObj"));
				delivery.setDeliveryTime(rs.getString("deliveryTime"));
				delivery.setStateObj(rs.getInt("stateObj"));
				delivery.setDeliveryDemo(rs.getString("deliveryDemo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return delivery;
	}
	/* 更新职位投递 */
	public String UpdateDelivery(Delivery delivery) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Delivery set ";
			sql += "jobObj=" + delivery.getJobObj() + ",";
			sql += "userObj='" + delivery.getUserObj() + "',";
			sql += "deliveryTime='" + delivery.getDeliveryTime() + "',";
			sql += "stateObj=" + delivery.getStateObj() + ",";
			sql += "deliveryDemo='" + delivery.getDeliveryDemo() + "'";
			sql += " where deliveryId=" + delivery.getDeliveryId();
			db.executeUpdate(sql);
			result = "职位投递更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "职位投递更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
