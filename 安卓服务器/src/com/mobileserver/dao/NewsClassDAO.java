package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.NewsClass;
import com.mobileserver.util.DB;

public class NewsClassDAO {

	public List<NewsClass> QueryNewsClass() {
		List<NewsClass> newsClassList = new ArrayList<NewsClass>();
		DB db = new DB();
		String sql = "select * from NewsClass where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				NewsClass newsClass = new NewsClass();
				newsClass.setNewsClassId(rs.getInt("newsClassId"));
				newsClass.setNewsClassName(rs.getString("newsClassName"));
				newsClassList.add(newsClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return newsClassList;
	}
	/* 传入新闻分类对象，进行新闻分类的添加业务 */
	public String AddNewsClass(NewsClass newsClass) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新新闻分类 */
			String sqlString = "insert into NewsClass(newsClassName) values (";
			sqlString += "'" + newsClass.getNewsClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "新闻分类添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "新闻分类添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除新闻分类 */
	public String DeleteNewsClass(int newsClassId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from NewsClass where newsClassId=" + newsClassId;
			db.executeUpdate(sqlString);
			result = "新闻分类删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "新闻分类删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据新闻分类id获取到新闻分类 */
	public NewsClass GetNewsClass(int newsClassId) {
		NewsClass newsClass = null;
		DB db = new DB();
		String sql = "select * from NewsClass where newsClassId=" + newsClassId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				newsClass = new NewsClass();
				newsClass.setNewsClassId(rs.getInt("newsClassId"));
				newsClass.setNewsClassName(rs.getString("newsClassName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return newsClass;
	}
	/* 更新新闻分类 */
	public String UpdateNewsClass(NewsClass newsClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update NewsClass set ";
			sql += "newsClassName='" + newsClass.getNewsClassName() + "'";
			sql += " where newsClassId=" + newsClass.getNewsClassId();
			db.executeUpdate(sql);
			result = "新闻分类更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "新闻分类更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
