package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Qiye;
import com.mobileserver.util.DB;

public class QiyeDAO {

	public List<Qiye> QueryQiye(String qiyeUserName,String qiyeName,int qiyePropertyObj,int qiyeProfessionObj,String connectPerson,String telephone) {
		List<Qiye> qiyeList = new ArrayList<Qiye>();
		DB db = new DB();
		String sql = "select * from Qiye where 1=1";
		if (!qiyeUserName.equals(""))
			sql += " and qiyeUserName like '%" + qiyeUserName + "%'";
		if (!qiyeName.equals(""))
			sql += " and qiyeName like '%" + qiyeName + "%'";
		if (qiyePropertyObj != 0)
			sql += " and qiyePropertyObj=" + qiyePropertyObj;
		if (qiyeProfessionObj != 0)
			sql += " and qiyeProfessionObj=" + qiyeProfessionObj;
		if (!connectPerson.equals(""))
			sql += " and connectPerson like '%" + connectPerson + "%'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Qiye qiye = new Qiye();
				qiye.setQiyeUserName(rs.getString("qiyeUserName"));
				qiye.setPassword(rs.getString("password"));
				qiye.setQiyeName(rs.getString("qiyeName"));
				qiye.setQiyeQualify(rs.getString("qiyeQualify"));
				qiye.setQiyePropertyObj(rs.getInt("qiyePropertyObj"));
				qiye.setQiyeProfessionObj(rs.getInt("qiyeProfessionObj"));
				qiye.setQiyeScale(rs.getString("qiyeScale"));
				qiye.setConnectPerson(rs.getString("connectPerson"));
				qiye.setTelephone(rs.getString("telephone"));
				qiye.setEmail(rs.getString("email"));
				qiye.setAddress(rs.getString("address"));
				qiyeList.add(qiye);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return qiyeList;
	}
	/* ������ҵ���󣬽�����ҵ�����ҵ�� */
	public String AddQiye(Qiye qiye) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������ҵ */
			String sqlString = "insert into Qiye(qiyeUserName,password,qiyeName,qiyeQualify,qiyePropertyObj,qiyeProfessionObj,qiyeScale,connectPerson,telephone,email,address) values (";
			sqlString += "'" + qiye.getQiyeUserName() + "',";
			sqlString += "'" + qiye.getPassword() + "',";
			sqlString += "'" + qiye.getQiyeName() + "',";
			sqlString += "'" + qiye.getQiyeQualify() + "',";
			sqlString += qiye.getQiyePropertyObj() + ",";
			sqlString += qiye.getQiyeProfessionObj() + ",";
			sqlString += "'" + qiye.getQiyeScale() + "',";
			sqlString += "'" + qiye.getConnectPerson() + "',";
			sqlString += "'" + qiye.getTelephone() + "',";
			sqlString += "'" + qiye.getEmail() + "',";
			sqlString += "'" + qiye.getAddress() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��ҵ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ҵ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����ҵ */
	public String DeleteQiye(String qiyeUserName) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Qiye where qiyeUserName='" + qiyeUserName + "'";
			db.executeUpdate(sqlString);
			result = "��ҵɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ҵɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ������ҵ�˺Ż�ȡ����ҵ */
	public Qiye GetQiye(String qiyeUserName) {
		Qiye qiye = null;
		DB db = new DB();
		String sql = "select * from Qiye where qiyeUserName='" + qiyeUserName + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				qiye = new Qiye();
				qiye.setQiyeUserName(rs.getString("qiyeUserName"));
				qiye.setPassword(rs.getString("password"));
				qiye.setQiyeName(rs.getString("qiyeName"));
				qiye.setQiyeQualify(rs.getString("qiyeQualify"));
				qiye.setQiyePropertyObj(rs.getInt("qiyePropertyObj"));
				qiye.setQiyeProfessionObj(rs.getInt("qiyeProfessionObj"));
				qiye.setQiyeScale(rs.getString("qiyeScale"));
				qiye.setConnectPerson(rs.getString("connectPerson"));
				qiye.setTelephone(rs.getString("telephone"));
				qiye.setEmail(rs.getString("email"));
				qiye.setAddress(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return qiye;
	}
	/* ������ҵ */
	public String UpdateQiye(Qiye qiye) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Qiye set ";
			sql += "password='" + qiye.getPassword() + "',";
			sql += "qiyeName='" + qiye.getQiyeName() + "',";
			sql += "qiyeQualify='" + qiye.getQiyeQualify() + "',";
			sql += "qiyePropertyObj=" + qiye.getQiyePropertyObj() + ",";
			sql += "qiyeProfessionObj=" + qiye.getQiyeProfessionObj() + ",";
			sql += "qiyeScale='" + qiye.getQiyeScale() + "',";
			sql += "connectPerson='" + qiye.getConnectPerson() + "',";
			sql += "telephone='" + qiye.getTelephone() + "',";
			sql += "email='" + qiye.getEmail() + "',";
			sql += "address='" + qiye.getAddress() + "'";
			sql += " where qiyeUserName='" + qiye.getQiyeUserName() + "'";
			db.executeUpdate(sql);
			result = "��ҵ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ҵ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
