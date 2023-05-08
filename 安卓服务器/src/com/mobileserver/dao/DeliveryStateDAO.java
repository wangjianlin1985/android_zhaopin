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
	/* ����Ͷ��״̬���󣬽���Ͷ��״̬�����ҵ�� */
	public String AddDeliveryState(DeliveryState deliveryState) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����Ͷ��״̬ */
			String sqlString = "insert into DeliveryState(stateName) values (";
			sqlString += "'" + deliveryState.getStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "Ͷ��״̬��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Ͷ��״̬���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��Ͷ��״̬ */
	public String DeleteDeliveryState(int stateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from DeliveryState where stateId=" + stateId;
			db.executeUpdate(sqlString);
			result = "Ͷ��״̬ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Ͷ��״̬ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����Ͷ��״̬id��ȡ��Ͷ��״̬ */
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
	/* ����Ͷ��״̬ */
	public String UpdateDeliveryState(DeliveryState deliveryState) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update DeliveryState set ";
			sql += "stateName='" + deliveryState.getStateName() + "'";
			sql += " where stateId=" + deliveryState.getStateId();
			db.executeUpdate(sql);
			result = "Ͷ��״̬���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Ͷ��״̬����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
