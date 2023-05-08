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
import com.chengxusheji.domain.SchoolRecord;

@Service @Transactional
public class SchoolRecordDAO {

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
    public void AddSchoolRecord(SchoolRecord schoolRecord) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(schoolRecord);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SchoolRecord> QuerySchoolRecordInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SchoolRecord schoolRecord where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List schoolRecordList = q.list();
    	return (ArrayList<SchoolRecord>) schoolRecordList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SchoolRecord> QuerySchoolRecordInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SchoolRecord schoolRecord where 1=1";
    	Query q = s.createQuery(hql);
    	List schoolRecordList = q.list();
    	return (ArrayList<SchoolRecord>) schoolRecordList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SchoolRecord> QueryAllSchoolRecordInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From SchoolRecord";
        Query q = s.createQuery(hql);
        List schoolRecordList = q.list();
        return (ArrayList<SchoolRecord>) schoolRecordList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From SchoolRecord schoolRecord where 1=1";
        Query q = s.createQuery(hql);
        List schoolRecordList = q.list();
        recordNumber = schoolRecordList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SchoolRecord GetSchoolRecordById(int id) {
        Session s = factory.getCurrentSession();
        SchoolRecord schoolRecord = (SchoolRecord)s.get(SchoolRecord.class, id);
        return schoolRecord;
    }

    /*����SchoolRecord��Ϣ*/
    public void UpdateSchoolRecord(SchoolRecord schoolRecord) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(schoolRecord);
    }

    /*ɾ��SchoolRecord��Ϣ*/
    public void DeleteSchoolRecord (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object schoolRecord = s.load(SchoolRecord.class, id);
        s.delete(schoolRecord);
    }

}
