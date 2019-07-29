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
	/* 传入企业行业对象，进行企业行业的添加业务 */
	public String AddQiyeProfession(QiyeProfession qiyeProfession) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新企业行业 */
			String sqlString = "insert into QiyeProfession(professionName) values (";
			sqlString += "'" + qiyeProfession.getProfessionName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "企业行业添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "企业行业添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除企业行业 */
	public String DeleteQiyeProfession(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from QiyeProfession where id=" + id;
			db.executeUpdate(sqlString);
			result = "企业行业删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "企业行业删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到企业行业 */
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
	/* 更新企业行业 */
	public String UpdateQiyeProfession(QiyeProfession qiyeProfession) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update QiyeProfession set ";
			sql += "professionName='" + qiyeProfession.getProfessionName() + "'";
			sql += " where id=" + qiyeProfession.getId();
			db.executeUpdate(sql);
			result = "企业行业更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "企业行业更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
