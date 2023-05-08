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
import com.chengxusheji.domain.QiyeProfession;

@Service @Transactional
public class QiyeProfessionDAO {

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
    public void AddQiyeProfession(QiyeProfession qiyeProfession) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(qiyeProfession);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<QiyeProfession> QueryQiyeProfessionInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From QiyeProfession qiyeProfession where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List qiyeProfessionList = q.list();
    	return (ArrayList<QiyeProfession>) qiyeProfessionList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<QiyeProfession> QueryQiyeProfessionInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From QiyeProfession qiyeProfession where 1=1";
    	Query q = s.createQuery(hql);
    	List qiyeProfessionList = q.list();
    	return (ArrayList<QiyeProfession>) qiyeProfessionList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<QiyeProfession> QueryAllQiyeProfessionInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From QiyeProfession";
        Query q = s.createQuery(hql);
        List qiyeProfessionList = q.list();
        return (ArrayList<QiyeProfession>) qiyeProfessionList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From QiyeProfession qiyeProfession where 1=1";
        Query q = s.createQuery(hql);
        List qiyeProfessionList = q.list();
        recordNumber = qiyeProfessionList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public QiyeProfession GetQiyeProfessionById(int id) {
        Session s = factory.getCurrentSession();
        QiyeProfession qiyeProfession = (QiyeProfession)s.get(QiyeProfession.class, id);
        return qiyeProfession;
    }

    /*更新QiyeProfession信息*/
    public void UpdateQiyeProfession(QiyeProfession qiyeProfession) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(qiyeProfession);
    }

    /*删除QiyeProfession信息*/
    public void DeleteQiyeProfession (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object qiyeProfession = s.load(QiyeProfession.class, id);
        s.delete(qiyeProfession);
    }

}
