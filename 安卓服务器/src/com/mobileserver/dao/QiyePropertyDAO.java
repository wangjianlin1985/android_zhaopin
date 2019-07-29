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
	/* 传入企业性质对象，进行企业性质的添加业务 */
	public String AddQiyeProperty(QiyeProperty qiyeProperty) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新企业性质 */
			String sqlString = "insert into QiyeProperty(propertyName) values (";
			sqlString += "'" + qiyeProperty.getPropertyName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "企业性质添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "企业性质添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除企业性质 */
	public String DeleteQiyeProperty(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from QiyeProperty where id=" + id;
			db.executeUpdate(sqlString);
			result = "企业性质删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "企业性质删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到企业性质 */
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
	/* 更新企业性质 */
	public String UpdateQiyeProperty(QiyeProperty qiyeProperty) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update QiyeProperty set ";
			sql += "propertyName='" + qiyeProperty.getPropertyName() + "'";
			sql += " where id=" + qiyeProperty.getId();
			db.executeUpdate(sql);
			result = "企业性质更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "企业性质更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
