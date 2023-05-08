package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.SchoolRecord;
import com.mobileserver.util.DB;

public class SchoolRecordDAO {

	public List<SchoolRecord> QuerySchoolRecord() {
		List<SchoolRecord> schoolRecordList = new ArrayList<SchoolRecord>();
		DB db = new DB();
		String sql = "select * from SchoolRecord where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				SchoolRecord schoolRecord = new SchoolRecord();
				schoolRecord.setId(rs.getInt("id"));
				schoolRecord.setSchooRecordName(rs.getString("schooRecordName"));
				schoolRecordList.add(schoolRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return schoolRecordList;
	}
	/* ����ѧ�����󣬽���ѧ�������ҵ�� */
	public String AddSchoolRecord(SchoolRecord schoolRecord) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ѧ�� */
			String sqlString = "insert into SchoolRecord(schooRecordName) values (";
			sqlString += "'" + schoolRecord.getSchooRecordName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ѧ����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ѧ�� */
	public String DeleteSchoolRecord(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SchoolRecord where id=" + id;
			db.executeUpdate(sqlString);
			result = "ѧ��ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ��ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��ѧ�� */
	public SchoolRecord GetSchoolRecord(int id) {
		SchoolRecord schoolRecord = null;
		DB db = new DB();
		String sql = "select * from SchoolRecord where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				schoolRecord = new SchoolRecord();
				schoolRecord.setId(rs.getInt("id"));
				schoolRecord.setSchooRecordName(rs.getString("schooRecordName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return schoolRecord;
	}
	/* ����ѧ�� */
	public String UpdateSchoolRecord(SchoolRecord schoolRecord) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SchoolRecord set ";
			sql += "schooRecordName='" + schoolRecord.getSchooRecordName() + "'";
			sql += " where id=" + schoolRecord.getId();
			db.executeUpdate(sql);
			result = "ѧ�����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
