package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.DeliveryState;
import com.mobileserver.util.DB;

public class DeliveryStateDAO {

	public List<DeliveryState> QueryDeliveryState() {
		List<DeliveryState> deliveryStateList = new ArrayList<DeliveryState>();
		DB db = new DB();
		String sql = "select * from DeliveryState where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				DeliveryState deliveryState = new DeliveryState();
				deliveryState.setStateId(rs.getInt("stateId"));
				deliveryState.setStateName(rs.getString("stateName"));
				deliveryStateList.add(deliveryState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return deliveryStateList;
	}
	/* 传入投递状态对象，进行投递状态的添加业务 */
	public String AddDeliveryState(DeliveryState deliveryState) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新投递状态 */
			String sqlString = "insert into DeliveryState(stateName) values (";
			sqlString += "'" + deliveryState.getStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "投递状态添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "投递状态添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除投递状态 */
	public String DeleteDeliveryState(int stateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from DeliveryState where stateId=" + stateId;
			db.executeUpdate(sqlString);
			result = "投递状态删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "投递状态删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据投递状态id获取到投递状态 */
	public DeliveryState GetDeliveryState(int stateId) {
		DeliveryState deliveryState = null;
		DB db = new DB();
		String sql = "select * from DeliveryState where stateId=" + stateId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				deliveryState = new DeliveryState();
				deliveryState.setStateId(rs.getInt("stateId"));
				deliveryState.setStateName(rs.getString("stateName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return deliveryState;
	}
	/* 更新投递状态 */
	public String UpdateDeliveryState(DeliveryState deliveryState) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update DeliveryState set ";
			sql += "stateName='" + deliveryState.getStateName() + "'";
			sql += " where stateId=" + deliveryState.getStateId();
			db.executeUpdate(sql);
			result = "投递状态更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "投递状态更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
