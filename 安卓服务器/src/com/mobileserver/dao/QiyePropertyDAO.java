package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.QiyeProperty;
import com.mobileserver.util.DB;

public class QiyePropertyDAO {

	public List<QiyeProperty> QueryQiyeProperty() {
		List<QiyeProperty> qiyePropertyList = new ArrayList<QiyeProperty>();
		DB db = new DB();
		String sql = "select * from QiyeProperty where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				QiyeProperty qiyeProperty = new QiyeProperty();
				qiyeProperty.setId(rs.getInt("id"));
				qiyeProperty.setPropertyName(rs.getString("propertyName"));
				qiyePropertyList.add(qiyeProperty);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return qiyePropertyList;
	}
	/* ������ҵ���ʶ��󣬽�����ҵ���ʵ����ҵ�� */
	public String AddQiyeProperty(QiyeProperty qiyeProperty) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������ҵ���� */
			String sqlString = "insert into QiyeProperty(propertyName) values (";
			sqlString += "'" + qiyeProperty.getPropertyName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��ҵ������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ҵ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����ҵ���� */
	public String DeleteQiyeProperty(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from QiyeProperty where id=" + id;
			db.executeUpdate(sqlString);
			result = "��ҵ����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ҵ����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ����ҵ���� */
	public QiyeProperty GetQiyeProperty(int id) {
		QiyeProperty qiyeProperty = null;
		DB db = new DB();
		String sql = "select * from QiyeProperty where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				qiyeProperty = new QiyeProperty();
				qiyeProperty.setId(rs.getInt("id"));
				qiyeProperty.setPropertyName(rs.getString("propertyName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return qiyeProperty;
	}
	/* ������ҵ���� */
	public String UpdateQiyeProperty(QiyeProperty qiyeProperty) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update QiyeProperty set ";
			sql += "propertyName='" + qiyeProperty.getPropertyName() + "'";
			sql += " where id=" + qiyeProperty.getId();
			db.executeUpdate(sql);
			result = "��ҵ���ʸ��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ҵ���ʸ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
