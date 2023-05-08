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
    public void AddSpecialInfo(SpecialInfo specialInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(specialInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SpecialInfo> QuerySpecialInfoInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SpecialInfo specialInfo where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SpecialInfo GetSpecialInfoBySpecialId(int specialId) {
        Session s = factory.getCurrentSession();
        SpecialInfo specialInfo = (SpecialInfo)s.get(SpecialInfo.class, specialId);
        return specialInfo;
    }

    /*����SpecialInfo��Ϣ*/
    public void UpdateSpecialInfo(SpecialInfo specialInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(specialInfo);
    }

    /*ɾ��SpecialInfo��Ϣ*/
    public void DeleteSpecialInfo (int specialId) throws Exception {
        Session s = factory.getCurrentSession();
        Object specialInfo = s.load(SpecialInfo.class, specialId);
        s.delete(specialInfo);
    }

}
