package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.NewsClass;
import com.chengxusheji.domain.News;

@Service @Transactional
public class NewsDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddNews(News news) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(news);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<News> QueryNewsInfo(NewsClass newsClassObj,String title,String publishDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From News news where 1=1";
    	if(null != newsClassObj && newsClassObj.getNewsClassId()!=0) hql += " and news.newsClassObj.newsClassId=" + newsClassObj.getNewsClassId();
    	if(!title.equals("")) hql = hql + " and news.title like '%" + title + "%'";
    	if(!publishDate.equals("")) hql = hql + " and news.publishDate like '%" + publishDate + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List newsList = q.list();
    	return (ArrayList<News>) newsList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<News> QueryNewsInfo(NewsClass newsClassObj,String title,String publishDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From News news where 1=1";
    	if(null != newsClassObj && newsClassObj.getNewsClassId()!=0) hql += " and news.newsClassObj.newsClassId=" + newsClassObj.getNewsClassId();
    	if(!title.equals("")) hql = hql + " and news.title like '%" + title + "%'";
    	if(!publishDate.equals("")) hql = hql + " and news.publishDate like '%" + publishDate + "%'";
    	Query q = s.createQuery(hql);
    	List newsList = q.list();
    	return (ArrayList<News>) newsList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<News> QueryAllNewsInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From News";
        Query q = s.createQuery(hql);
        List newsList = q.list();
        return (ArrayList<News>) newsList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(NewsClass newsClassObj,String title,String publishDate) {
        Session s = factory.getCurrentSession();
        String hql = "From News news where 1=1";
        if(null != newsClassObj && newsClassObj.getNewsClassId()!=0) hql += " and news.newsClassObj.newsClassId=" + newsClassObj.getNewsClassId();
        if(!title.equals("")) hql = hql + " and news.title like '%" + title + "%'";
        if(!publishDate.equals("")) hql = hql + " and news.publishDate like '%" + publishDate + "%'";
        Query q = s.createQuery(hql);
        List newsList = q.list();
        recordNumber = newsList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public News GetNewsByNewsId(int newsId) {
        Session s = factory.getCurrentSession();
        News news = (News)s.get(News.class, newsId);
        return news;
    }

    /*更新News信息*/
    public void UpdateNews(News news) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(news);
    }

    /*删除News信息*/
    public void DeleteNews (int newsId) throws Exception {
        Session s = factory.getCurrentSession();
        Object news = s.load(News.class, newsId);
        s.delete(news);
    }

}
