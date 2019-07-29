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
import com.chengxusheji.domain.DeliveryState;

@Service @Transactional
public class DeliveryStateDAO {

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
    public void AddDeliveryState(DeliveryState deliveryState) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(deliveryState);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<DeliveryState> QueryDeliveryStateInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From DeliveryState deliveryState where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List deliveryStateList = q.list();
    	return (ArrayList<DeliveryState>) deliveryStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<DeliveryState> QueryDeliveryStateInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From DeliveryState deliveryState where 1=1";
    	Query q = s.createQuery(hql);
    	List deliveryStateList = q.list();
    	return (ArrayList<DeliveryState>) deliveryStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<DeliveryState> QueryAllDeliveryStateInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From DeliveryState";
        Query q = s.createQuery(hql);
        List deliveryStateList = q.list();
        return (ArrayList<DeliveryState>) deliveryStateList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From DeliveryState deliveryState where 1=1";
        Query q = s.createQuery(hql);
        List deliveryStateList = q.list();
        recordNumber = deliveryStateList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public DeliveryState GetDeliveryStateByStateId(int stateId) {
        Session s = factory.getCurrentSession();
        DeliveryState deliveryState = (DeliveryState)s.get(DeliveryState.class, stateId);
        return deliveryState;
    }

    /*更新DeliveryState信息*/
    public void UpdateDeliveryState(DeliveryState deliveryState) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(deliveryState);
    }

    /*删除DeliveryState信息*/
    public void DeleteDeliveryState (int stateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object deliveryState = s.load(DeliveryState.class, stateId);
        s.delete(deliveryState);
    }

}
