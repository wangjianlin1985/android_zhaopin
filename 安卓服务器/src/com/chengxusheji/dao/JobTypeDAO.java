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
import com.chengxusheji.domain.JobType;

@Service @Transactional
public class JobTypeDAO {

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
    public void AddJobType(JobType jobType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(jobType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<JobType> QueryJobTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From JobType jobType where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List jobTypeList = q.list();
    	return (ArrayList<JobType>) jobTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<JobType> QueryJobTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From JobType jobType where 1=1";
    	Query q = s.createQuery(hql);
    	List jobTypeList = q.list();
    	return (ArrayList<JobType>) jobTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<JobType> QueryAllJobTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From JobType";
        Query q = s.createQuery(hql);
        List jobTypeList = q.list();
        return (ArrayList<JobType>) jobTypeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From JobType jobType where 1=1";
        Query q = s.createQuery(hql);
        List jobTypeList = q.list();
        recordNumber = jobTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public JobType GetJobTypeByJobTypeId(int jobTypeId) {
        Session s = factory.getCurrentSession();
        JobType jobType = (JobType)s.get(JobType.class, jobTypeId);
        return jobType;
    }

    /*更新JobType信息*/
    public void UpdateJobType(JobType jobType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(jobType);
    }

    /*删除JobType信息*/
    public void DeleteJobType (int jobTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object jobType = s.load(JobType.class, jobTypeId);
        s.delete(jobType);
    }

}
