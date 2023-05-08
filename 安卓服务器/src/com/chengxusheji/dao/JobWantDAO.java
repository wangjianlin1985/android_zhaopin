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
import com.chengxusheji.domain.SpecialInfo;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.JobWant;

@Service @Transactional
public class JobWantDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddJobWant(JobWant jobWant) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(jobWant);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<JobWant> QueryJobWantInfo(JobType jobTypeObj,SpecialInfo specialObj,String positionName,String workCity,UserInfo userObj,String addTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From JobWant jobWant where 1=1";
    	if(null != jobTypeObj && jobTypeObj.getJobTypeId()!=0) hql += " and jobWant.jobTypeObj.jobTypeId=" + jobTypeObj.getJobTypeId();
    	if(null != specialObj && specialObj.getSpecialId()!=0) hql += " and jobWant.specialObj.specialId=" + specialObj.getSpecialId();
    	if(!positionName.equals("")) hql = hql + " and jobWant.positionName like '%" + positionName + "%'";
    	if(!workCity.equals("")) hql = hql + " and jobWant.workCity like '%" + workCity + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and jobWant.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) hql = hql + " and jobWant.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List jobWantList = q.list();
    	return (ArrayList<JobWant>) jobWantList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<JobWant> QueryJobWantInfo(JobType jobTypeObj,SpecialInfo specialObj,String positionName,String workCity,UserInfo userObj,String addTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From JobWant jobWant where 1=1";
    	if(null != jobTypeObj && jobTypeObj.getJobTypeId()!=0) hql += " and jobWant.jobTypeObj.jobTypeId=" + jobTypeObj.getJobTypeId();
    	if(null != specialObj && specialObj.getSpecialId()!=0) hql += " and jobWant.specialObj.specialId=" + specialObj.getSpecialId();
    	if(!positionName.equals("")) hql = hql + " and jobWant.positionName like '%" + positionName + "%'";
    	if(!workCity.equals("")) hql = hql + " and jobWant.workCity like '%" + workCity + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and jobWant.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) hql = hql + " and jobWant.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	List jobWantList = q.list();
    	return (ArrayList<JobWant>) jobWantList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<JobWant> QueryAllJobWantInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From JobWant";
        Query q = s.createQuery(hql);
        List jobWantList = q.list();
        return (ArrayList<JobWant>) jobWantList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(JobType jobTypeObj,SpecialInfo specialObj,String positionName,String workCity,UserInfo userObj,String addTime) {
        Session s = factory.getCurrentSession();
        String hql = "From JobWant jobWant where 1=1";
        if(null != jobTypeObj && jobTypeObj.getJobTypeId()!=0) hql += " and jobWant.jobTypeObj.jobTypeId=" + jobTypeObj.getJobTypeId();
        if(null != specialObj && specialObj.getSpecialId()!=0) hql += " and jobWant.specialObj.specialId=" + specialObj.getSpecialId();
        if(!positionName.equals("")) hql = hql + " and jobWant.positionName like '%" + positionName + "%'";
        if(!workCity.equals("")) hql = hql + " and jobWant.workCity like '%" + workCity + "%'";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and jobWant.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!addTime.equals("")) hql = hql + " and jobWant.addTime like '%" + addTime + "%'";
        Query q = s.createQuery(hql);
        List jobWantList = q.list();
        recordNumber = jobWantList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public JobWant GetJobWantByWantId(int wantId) {
        Session s = factory.getCurrentSession();
        JobWant jobWant = (JobWant)s.get(JobWant.class, wantId);
        return jobWant;
    }

    /*����JobWant��Ϣ*/
    public void UpdateJobWant(JobWant jobWant) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(jobWant);
    }

    /*ɾ��JobWant��Ϣ*/
    public void DeleteJobWant (int wantId) throws Exception {
        Session s = factory.getCurrentSession();
        Object jobWant = s.load(JobWant.class, wantId);
        s.delete(jobWant);
    }

}
