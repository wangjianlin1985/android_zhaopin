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
	/* 传入专业对象，进行专业的添加业务 */
	public String AddSpecialInfo(SpecialInfo specialInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新专业 */
			String sqlString = "insert into SpecialInfo(specialName) values (";
			sqlString += "'" + specialInfo.getSpecialName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "专业添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "专业添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除专业 */
	public String DeleteSpecialInfo(int specialId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SpecialInfo where specialId=" + specialId;
			db.executeUpdate(sqlString);
			result = "专业删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "专业删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据专业id获取到专业 */
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
	/* 更新专业 */
	public String UpdateSpecialInfo(SpecialInfo specialInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SpecialInfo set ";
			sql += "specialName='" + specialInfo.getSpecialName() + "'";
			sql += " where specialId=" + specialInfo.getSpecialId();
			db.executeUpdate(sql);
			result = "专业更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "专业更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
