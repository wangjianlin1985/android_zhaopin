package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.QiyeProfession;
import com.mobileserver.util.DB;

public class QiyeProfessionDAO {

	public List<QiyeProfession> QueryQiyeProfession() {
		List<QiyeProfession> qiyeProfessionList = new ArrayList<QiyeProfession>();
		DB db = new DB();
		String sql = "select * from QiyeProfession where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				QiyeProfession qiyeProfession = new QiyeProfession();
				qiyeProfession.setId(rs.getInt("id"));
				qiyeProfession.setProfessionName(rs.getString("professionName"));
				qiyeProfessionList.add(qiyeProfession);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return qiyeProfessionList;
	}
	/* ������ҵ��ҵ���󣬽�����ҵ��ҵ�����ҵ�� */
	public String AddQiyeProfession(QiyeProfession qiyeProfession) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������ҵ��ҵ */
			String sqlString = "insert into QiyeProfession(professionName) values (";
			sqlString += "'" + qiyeProfession.getProfessionName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��ҵ��ҵ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ҵ��ҵ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����ҵ��ҵ */
	public String DeleteQiyeProfession(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from QiyeProfession where id=" + id;
			db.executeUpdate(sqlString);
			result = "��ҵ��ҵɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ҵ��ҵɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ����ҵ��ҵ */
	public QiyeProfession GetQiyeProfession(int id) {
		QiyeProfession qiyeProfession = null;
		DB db = new DB();
		String sql = "select * from QiyeProfession where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				qiyeProfession = new QiyeProfession();
				qiyeProfession.setId(rs.getInt("id"));
				qiyeProfession.setProfessionName(rs.getString("professionName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return qiyeProfession;
	}
	/* ������ҵ��ҵ */
	public String UpdateQiyeProfession(QiyeProfession qiyeProfession) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update QiyeProfession set ";
			sql += "professionName='" + qiyeProfession.getProfessionName() + "'";
			sql += " where id=" + qiyeProfession.getId();
			db.executeUpdate(sql);
			result = "��ҵ��ҵ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ҵ��ҵ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
