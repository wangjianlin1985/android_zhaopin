package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.News;
import com.mobileserver.util.DB;

public class NewsDAO {

	public List<News> QueryNews(int newsClassObj,String title,String publishDate) {
		List<News> newsList = new ArrayList<News>();
		DB db = new DB();
		String sql = "select * from News where 1=1";
		if (newsClassObj != 0)
			sql += " and newsClassObj=" + newsClassObj;
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (!publishDate.equals(""))
			sql += " and publishDate like '%" + publishDate + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				News news = new News();
				news.setNewsId(rs.getInt("newsId"));
				news.setNewsClassObj(rs.getInt("newsClassObj"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setPublishDate(rs.getString("publishDate"));
				newsList.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return newsList;
	}
	/* �������Ź�����󣬽������Ź�������ҵ�� */
	public String AddNews(News news) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в��������Ź��� */
			String sqlString = "insert into News(newsClassObj,title,content,publishDate) values (";
			sqlString += news.getNewsClassObj() + ",";
			sqlString += "'" + news.getTitle() + "',";
			sqlString += "'" + news.getContent() + "',";
			sqlString += "'" + news.getPublishDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "���Ź�����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���Ź������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�����Ź��� */
	public String DeleteNews(int newsId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from News where newsId=" + newsId;
			db.executeUpdate(sqlString);
			result = "���Ź���ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���Ź���ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ��������id��ȡ�����Ź��� */
	public News GetNews(int newsId) {
		News news = null;
		DB db = new DB();
		String sql = "select * from News where newsId=" + newsId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				news = new News();
				news.setNewsId(rs.getInt("newsId"));
				news.setNewsClassObj(rs.getInt("newsClassObj"));
				news.setTitle(rs.getString("title"));
				news.setContent(rs.getString("content"));
				news.setPublishDate(rs.getString("publishDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return news;
	}
	/* �������Ź��� */
	public String UpdateNews(News news) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update News set ";
			sql += "newsClassObj=" + news.getNewsClassObj() + ",";
			sql += "title='" + news.getTitle() + "',";
			sql += "content='" + news.getContent() + "',";
			sql += "publishDate='" + news.getPublishDate() + "'";
			sql += " where newsId=" + news.getNewsId();
			db.executeUpdate(sql);
			result = "���Ź�����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���Ź������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
