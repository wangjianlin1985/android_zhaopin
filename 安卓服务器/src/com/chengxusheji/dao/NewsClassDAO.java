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

@Service @Transactional
public class NewsClassDAO {

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
    public void AddNewsClass(NewsClass newsClass) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(newsClass);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NewsClass> QueryNewsClassInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From NewsClass newsClass where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List newsClassList = q.list();
    	return (ArrayList<NewsClass>) newsClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NewsClass> QueryNewsClassInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From NewsClass newsClass where 1=1";
    	Query q = s.createQuery(hql);
    	List newsClassList = q.list();
    	return (ArrayList<NewsClass>) newsClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<NewsClass> QueryAllNewsClassInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From NewsClass";
        Query q = s.createQuery(hql);
        List newsClassList = q.list();
        return (ArrayList<NewsClass>) newsClassList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From NewsClass newsClass where 1=1";
        Query q = s.createQuery(hql);
        List newsClassList = q.list();
        recordNumber = newsClassList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public NewsClass GetNewsClassByNewsClassId(int newsClassId) {
        Session s = factory.getCurrentSession();
        NewsClass newsClass = (NewsClass)s.get(NewsClass.class, newsClassId);
        return newsClass;
    }

    /*更新NewsClass信息*/
    public void UpdateNewsClass(NewsClass newsClass) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(newsClass);
    }

    /*删除NewsClass信息*/
    public void DeleteNewsClass (int newsClassId) throws Exception {
        Session s = factory.getCurrentSession();
        Object newsClass = s.load(NewsClass.class, newsClassId);
        s.delete(newsClass);
    }

}
