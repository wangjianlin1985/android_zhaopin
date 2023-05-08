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
import com.chengxusheji.domain.Qiye;
import com.chengxusheji.domain.JobType;
import com.chengxusheji.domain.SpecialInfo;
import com.chengxusheji.domain.SchoolRecord;
import com.chengxusheji.domain.Job;

@Service @Transactional
public class JobDAO {

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
    public void AddJob(Job job) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(job);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Job> QueryJobInfo(Qiye qiyeObj,String positionName,JobType jobTypeObj,SpecialInfo specialObj,String city,SchoolRecord schoolRecordObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Job job where 1=1";
    	if(null != qiyeObj && !qiyeObj.getQiyeUserName().equals("")) hql += " and job.qiyeObj.qiyeUserName='" + qiyeObj.getQiyeUserName() + "'";
    	if(!positionName.equals("")) hql = hql + " and job.positionName like '%" + positionName + "%'";
    	if(null != jobTypeObj && jobTypeObj.getJobTypeId()!=0) hql += " and job.jobTypeObj.jobTypeId=" + jobTypeObj.getJobTypeId();
    	if(null != specialObj && specialObj.getSpecialId()!=0) hql += " and job.specialObj.specialId=" + specialObj.getSpecialId();
    	if(!city.equals("")) hql = hql + " and job.city like '%" + city + "%'";
    	if(null != schoolRecordObj && schoolRecordObj.getId()!=0) hql += " and job.schoolRecordObj.id=" + schoolRecordObj.getId();
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List jobList = q.list();
    	return (ArrayList<Job>) jobList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Job> QueryJobInfo(Qiye qiyeObj,String positionName,JobType jobTypeObj,SpecialInfo specialObj,String city,SchoolRecord schoolRecordObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Job job where 1=1";
    	if(null != qiyeObj && !qiyeObj.getQiyeUserName().equals("")) hql += " and job.qiyeObj.qiyeUserName='" + qiyeObj.getQiyeUserName() + "'";
    	if(!positionName.equals("")) hql = hql + " and job.positionName like '%" + positionName + "%'";
    	if(null != jobTypeObj && jobTypeObj.getJobTypeId()!=0) hql += " and job.jobTypeObj.jobTypeId=" + jobTypeObj.getJobTypeId();
    	if(null != specialObj && specialObj.getSpecialId()!=0) hql += " and job.specialObj.specialId=" + specialObj.getSpecialId();
    	if(!city.equals("")) hql = hql + " and job.city like '%" + city + "%'";
    	if(null != schoolRecordObj && schoolRecordObj.getId()!=0) hql += " and job.schoolRecordObj.id=" + schoolRecordObj.getId();
    	Query q = s.createQuery(hql);
    	List jobList = q.list();
    	return (ArrayList<Job>) jobList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Job> QueryAllJobInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Job";
        Query q = s.createQuery(hql);
        List jobList = q.list();
        return (ArrayList<Job>) jobList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Qiye qiyeObj,String positionName,JobType jobTypeObj,SpecialInfo specialObj,String city,SchoolRecord schoolRecordObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Job job where 1=1";
        if(null != qiyeObj && !qiyeObj.getQiyeUserName().equals("")) hql += " and job.qiyeObj.qiyeUserName='" + qiyeObj.getQiyeUserName() + "'";
        if(!positionName.equals("")) hql = hql + " and job.positionName like '%" + positionName + "%'";
        if(null != jobTypeObj && jobTypeObj.getJobTypeId()!=0) hql += " and job.jobTypeObj.jobTypeId=" + jobTypeObj.getJobTypeId();
        if(null != specialObj && specialObj.getSpecialId()!=0) hql += " and job.specialObj.specialId=" + specialObj.getSpecialId();
        if(!city.equals("")) hql = hql + " and job.city like '%" + city + "%'";
        if(null != schoolRecordObj && schoolRecordObj.getId()!=0) hql += " and job.schoolRecordObj.id=" + schoolRecordObj.getId();
        Query q = s.createQuery(hql);
        List jobList = q.list();
        recordNumber = jobList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Job GetJobByJobId(int jobId) {
        Session s = factory.getCurrentSession();
        Job job = (Job)s.get(Job.class, jobId);
        return job;
    }

    /*更新Job信息*/
    public void UpdateJob(Job job) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(job);
    }

    /*删除Job信息*/
    public void DeleteJob (int jobId) throws Exception {
        Session s = factory.getCurrentSession();
        Object job = s.load(Job.class, jobId);
        s.delete(job);
    }

}
