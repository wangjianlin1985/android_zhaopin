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
import com.chengxusheji.domain.QiyeProfession;
import com.chengxusheji.domain.Qiye;

@Service @Transactional
public class QiyeDAO {

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
    public void AddQiye(Qiye qiye) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(qiye);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Qiye> QueryQiyeInfo(String qiyeUserName,String qiyeName,QiyeProperty qiyePropertyObj,QiyeProfession qiyeProfessionObj,String connectPerson,String telephone,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Qiye qiye where 1=1";
    	if(!qiyeUserName.equals("")) hql = hql + " and qiye.qiyeUserName like '%" + qiyeUserName + "%'";
    	if(!qiyeName.equals("")) hql = hql + " and qiye.qiyeName like '%" + qiyeName + "%'";
    	if(null != qiyePropertyObj && qiyePropertyObj.getId()!=0) hql += " and qiye.qiyePropertyObj.id=" + qiyePropertyObj.getId();
    	if(null != qiyeProfessionObj && qiyeProfessionObj.getId()!=0) hql += " and qiye.qiyeProfessionObj.id=" + qiyeProfessionObj.getId();
    	if(!connectPerson.equals("")) hql = hql + " and qiye.connectPerson like '%" + connectPerson + "%'";
    	if(!telephone.equals("")) hql = hql + " and qiye.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List qiyeList = q.list();
    	return (ArrayList<Qiye>) qiyeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Qiye> QueryQiyeInfo(String qiyeUserName,String qiyeName,QiyeProperty qiyePropertyObj,QiyeProfession qiyeProfessionObj,String connectPerson,String telephone) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Qiye qiye where 1=1";
    	if(!qiyeUserName.equals("")) hql = hql + " and qiye.qiyeUserName like '%" + qiyeUserName + "%'";
    	if(!qiyeName.equals("")) hql = hql + " and qiye.qiyeName like '%" + qiyeName + "%'";
    	if(null != qiyePropertyObj && qiyePropertyObj.getId()!=0) hql += " and qiye.qiyePropertyObj.id=" + qiyePropertyObj.getId();
    	if(null != qiyeProfessionObj && qiyeProfessionObj.getId()!=0) hql += " and qiye.qiyeProfessionObj.id=" + qiyeProfessionObj.getId();
    	if(!connectPerson.equals("")) hql = hql + " and qiye.connectPerson like '%" + connectPerson + "%'";
    	if(!telephone.equals("")) hql = hql + " and qiye.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	List qiyeList = q.list();
    	return (ArrayList<Qiye>) qiyeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Qiye> QueryAllQiyeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Qiye";
        Query q = s.createQuery(hql);
        List qiyeList = q.list();
        return (ArrayList<Qiye>) qiyeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String qiyeUserName,String qiyeName,QiyeProperty qiyePropertyObj,QiyeProfession qiyeProfessionObj,String connectPerson,String telephone) {
        Session s = factory.getCurrentSession();
        String hql = "From Qiye qiye where 1=1";
        if(!qiyeUserName.equals("")) hql = hql + " and qiye.qiyeUserName like '%" + qiyeUserName + "%'";
        if(!qiyeName.equals("")) hql = hql + " and qiye.qiyeName like '%" + qiyeName + "%'";
        if(null != qiyePropertyObj && qiyePropertyObj.getId()!=0) hql += " and qiye.qiyePropertyObj.id=" + qiyePropertyObj.getId();
        if(null != qiyeProfessionObj && qiyeProfessionObj.getId()!=0) hql += " and qiye.qiyeProfessionObj.id=" + qiyeProfessionObj.getId();
        if(!connectPerson.equals("")) hql = hql + " and qiye.connectPerson like '%" + connectPerson + "%'";
        if(!telephone.equals("")) hql = hql + " and qiye.telephone like '%" + telephone + "%'";
        Query q = s.createQuery(hql);
        List qiyeList = q.list();
        recordNumber = qiyeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Qiye GetQiyeByQiyeUserName(String qiyeUserName) {
        Session s = factory.getCurrentSession();
        Qiye qiye = (Qiye)s.get(Qiye.class, qiyeUserName);
        return qiye;
    }

    /*更新Qiye信息*/
    public void UpdateQiye(Qiye qiye) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(qiye);
    }

    /*删除Qiye信息*/
    public void DeleteQiye (String qiyeUserName) throws Exception {
        Session s = factory.getCurrentSession();
        Object qiye = s.load(Qiye.class, qiyeUserName);
        s.delete(qiye);
    }

}
