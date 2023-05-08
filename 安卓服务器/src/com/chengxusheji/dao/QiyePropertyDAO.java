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
import com.chengxusheji.domain.QiyeProperty;

@Service @Transactional
public class QiyePropertyDAO {

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
    public void AddQiyeProperty(QiyeProperty qiyeProperty) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(qiyeProperty);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<QiyeProperty> QueryQiyePropertyInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From QiyeProperty qiyeProperty where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List qiyePropertyList = q.list();
    	return (ArrayList<QiyeProperty>) qiyePropertyList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<QiyeProperty> QueryQiyePropertyInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From QiyeProperty qiyeProperty where 1=1";
    	Query q = s.createQuery(hql);
    	List qiyePropertyList = q.list();
    	return (ArrayList<QiyeProperty>) qiyePropertyList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<QiyeProperty> QueryAllQiyePropertyInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From QiyeProperty";
        Query q = s.createQuery(hql);
        List qiyePropertyList = q.list();
        return (ArrayList<QiyeProperty>) qiyePropertyList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From QiyeProperty qiyeProperty where 1=1";
        Query q = s.createQuery(hql);
        List qiyePropertyList = q.list();
        recordNumber = qiyePropertyList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public QiyeProperty GetQiyePropertyById(int id) {
        Session s = factory.getCurrentSession();
        QiyeProperty qiyeProperty = (QiyeProperty)s.get(QiyeProperty.class, id);
        return qiyeProperty;
    }

    /*更新QiyeProperty信息*/
    public void UpdateQiyeProperty(QiyeProperty qiyeProperty) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(qiyeProperty);
    }

    /*删除QiyeProperty信息*/
    public void DeleteQiyeProperty (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object qiyeProperty = s.load(QiyeProperty.class, id);
        s.delete(qiyeProperty);
    }

}
