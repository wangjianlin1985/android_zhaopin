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
	/* 传入学历对象，进行学历的添加业务 */
	public String AddSchoolRecord(SchoolRecord schoolRecord) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新学历 */
			String sqlString = "insert into SchoolRecord(schooRecordName) values (";
			sqlString += "'" + schoolRecord.getSchooRecordName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "学历添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "学历添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除学历 */
	public String DeleteSchoolRecord(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SchoolRecord where id=" + id;
			db.executeUpdate(sqlString);
			result = "学历删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "学历删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到学历 */
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
	/* 更新学历 */
	public String UpdateSchoolRecord(SchoolRecord schoolRecord) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SchoolRecord set ";
			sql += "schooRecordName='" + schoolRecord.getSchooRecordName() + "'";
			sql += " where id=" + schoolRecord.getId();
			db.executeUpdate(sql);
			result = "学历更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "学历更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
