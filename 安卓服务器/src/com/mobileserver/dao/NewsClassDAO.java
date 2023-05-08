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
	/* �������ŷ�����󣬽������ŷ�������ҵ�� */
	public String AddNewsClass(NewsClass newsClass) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в��������ŷ��� */
			String sqlString = "insert into NewsClass(newsClassName) values (";
			sqlString += "'" + newsClass.getNewsClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���ŷ�����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ŷ������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�����ŷ��� */
	public String DeleteNewsClass(int newsClassId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from NewsClass where newsClassId=" + newsClassId;
			db.executeUpdate(sqlString);
			result = "���ŷ���ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ŷ���ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* �������ŷ���id��ȡ�����ŷ��� */
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
	/* �������ŷ��� */
	public String UpdateNewsClass(NewsClass newsClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update NewsClass set ";
			sql += "newsClassName='" + newsClass.getNewsClassName() + "'";
			sql += " where newsClassId=" + newsClass.getNewsClassId();
			db.executeUpdate(sql);
			result = "���ŷ�����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ŷ������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
