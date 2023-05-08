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
import com.chengxusheji.domain.Job;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.DeliveryState;
import com.chengxusheji.domain.Delivery;

@Service @Transactional
public class DeliveryDAO {

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
    public void AddDelivery(Delivery delivery) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(delivery);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Delivery> QueryDeliveryInfo(Job jobObj,UserInfo userObj,String deliveryTime,DeliveryState stateObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Delivery delivery where 1=1";
    	if(null != jobObj && jobObj.getJobId()!=0) hql += " and delivery.jobObj.jobId=" + jobObj.getJobId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and delivery.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!deliveryTime.equals("")) hql = hql + " and delivery.deliveryTime like '%" + deliveryTime + "%'";
    	if(null != stateObj && stateObj.getStateId()!=0) hql += " and delivery.stateObj.stateId=" + stateObj.getStateId();
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List deliveryList = q.list();
    	return (ArrayList<Delivery>) deliveryList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Delivery> QueryDeliveryInfo(Job jobObj,UserInfo userObj,String deliveryTime,DeliveryState stateObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Delivery delivery where 1=1";
    	if(null != jobObj && jobObj.getJobId()!=0) hql += " and delivery.jobObj.jobId=" + jobObj.getJobId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and delivery.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!deliveryTime.equals("")) hql = hql + " and delivery.deliveryTime like '%" + deliveryTime + "%'";
    	if(null != stateObj && stateObj.getStateId()!=0) hql += " and delivery.stateObj.stateId=" + stateObj.getStateId();
    	Query q = s.createQuery(hql);
    	List deliveryList = q.list();
    	return (ArrayList<Delivery>) deliveryList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Delivery> QueryAllDeliveryInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Delivery";
        Query q = s.createQuery(hql);
        List deliveryList = q.list();
        return (ArrayList<Delivery>) deliveryList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Job jobObj,UserInfo userObj,String deliveryTime,DeliveryState stateObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Delivery delivery where 1=1";
        if(null != jobObj && jobObj.getJobId()!=0) hql += " and delivery.jobObj.jobId=" + jobObj.getJobId();
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and delivery.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!deliveryTime.equals("")) hql = hql + " and delivery.deliveryTime like '%" + deliveryTime + "%'";
        if(null != stateObj && stateObj.getStateId()!=0) hql += " and delivery.stateObj.stateId=" + stateObj.getStateId();
        Query q = s.createQuery(hql);
        List deliveryList = q.list();
        recordNumber = deliveryList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Delivery GetDeliveryByDeliveryId(int deliveryId) {
        Session s = factory.getCurrentSession();
        Delivery delivery = (Delivery)s.get(Delivery.class, deliveryId);
        return delivery;
    }

    /*����Delivery��Ϣ*/
    public void UpdateDelivery(Delivery delivery) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(delivery);
    }

    /*ɾ��Delivery��Ϣ*/
    public void DeleteDelivery (int deliveryId) throws Exception {
        Session s = factory.getCurrentSession();
        Object delivery = s.load(Delivery.class, deliveryId);
        s.delete(delivery);
    }

}
