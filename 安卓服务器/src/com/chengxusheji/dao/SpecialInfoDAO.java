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
import com.chengxusheji.domain.SpecialInfo;

@Service @Transactional
public class SpecialInfoDAO {

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
    public void AddSpecialInfo(SpecialInfo specialInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(specialInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SpecialInfo> QuerySpecialInfoInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SpecialInfo specialInfo where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List specialInfoList = q.list();
    	return (ArrayList<SpecialInfo>) specialInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SpecialInfo> QuerySpecialInfoInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SpecialInfo specialInfo where 1=1";
    	Query q = s.createQuery(hql);
    	List specialInfoList = q.list();
    	return (ArrayList<SpecialInfo>) specialInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SpecialInfo> QueryAllSpecialInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From SpecialInfo";
        Query q = s.createQuery(hql);
        List specialInfoList = q.list();
        return (ArrayList<SpecialInfo>) specialInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From SpecialInfo specialInfo where 1=1";
        Query q = s.createQuery(hql);
        List specialInfoList = q.list();
        recordNumber = specialInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SpecialInfo GetSpecialInfoBySpecialId(int specialId) {
        Session s = factory.getCurrentSession();
        SpecialInfo specialInfo = (SpecialInfo)s.get(SpecialInfo.class, specialId);
        return specialInfo;
    }

    /*更新SpecialInfo信息*/
    public void UpdateSpecialInfo(SpecialInfo specialInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(specialInfo);
    }

    /*删除SpecialInfo信息*/
    public void DeleteSpecialInfo (int specialId) throws Exception {
        Session s = factory.getCurrentSession();
        Object specialInfo = s.load(SpecialInfo.class, specialId);
        s.delete(specialInfo);
    }

}
