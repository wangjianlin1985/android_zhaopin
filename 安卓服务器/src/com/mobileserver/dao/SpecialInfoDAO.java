package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.SpecialInfo;
import com.mobileserver.util.DB;

public class SpecialInfoDAO {

	public List<SpecialInfo> QuerySpecialInfo() {
		List<SpecialInfo> specialInfoList = new ArrayList<SpecialInfo>();
		DB db = new DB();
		String sql = "select * from SpecialInfo where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				SpecialInfo specialInfo = new SpecialInfo();
				specialInfo.setSpecialId(rs.getInt("specialId"));
				specialInfo.setSpecialName(rs.getString("specialName"));
				specialInfoList.add(specialInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return specialInfoList;
	}
	/* ����רҵ���󣬽���רҵ�����ҵ�� */
	public String AddSpecialInfo(SpecialInfo specialInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����רҵ */
			String sqlString = "insert into SpecialInfo(specialName) values (";
			sqlString += "'" + specialInfo.getSpecialName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "רҵ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "רҵ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��רҵ */
	public String DeleteSpecialInfo(int specialId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SpecialInfo where specialId=" + specialId;
			db.executeUpdate(sqlString);
			result = "רҵɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "רҵɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����רҵid��ȡ��רҵ */
	public SpecialInfo GetSpecialInfo(int specialId) {
		SpecialInfo specialInfo = null;
		DB db = new DB();
		String sql = "select * from SpecialInfo where specialId=" + specialId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				specialInfo = new SpecialInfo();
				specialInfo.setSpecialId(rs.getInt("specialId"));
				specialInfo.setSpecialName(rs.getString("specialName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return specialInfo;
	}
	/* ����רҵ */
	public String UpdateSpecialInfo(SpecialInfo specialInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SpecialInfo set ";
			sql += "specialName='" + specialInfo.getSpecialName() + "'";
			sql += " where specialId=" + specialInfo.getSpecialId();
			db.executeUpdate(sql);
			result = "רҵ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "רҵ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
