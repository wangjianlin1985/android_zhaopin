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
    public void AddQiyeProperty(QiyeProperty qiyeProperty) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(qiyeProperty);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<QiyeProperty> QueryQiyePropertyInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From QiyeProperty qiyeProperty where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public QiyeProperty GetQiyePropertyById(int id) {
        Session s = factory.getCurrentSession();
        QiyeProperty qiyeProperty = (QiyeProperty)s.get(QiyeProperty.class, id);
        return qiyeProperty;
    }

    /*����QiyeProperty��Ϣ*/
    public void UpdateQiyeProperty(QiyeProperty qiyeProperty) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(qiyeProperty);
    }

    /*ɾ��QiyeProperty��Ϣ*/
    public void DeleteQiyeProperty (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object qiyeProperty = s.load(QiyeProperty.class, id);
        s.delete(qiyeProperty);
    }

}
