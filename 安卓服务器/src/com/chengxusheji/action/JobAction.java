package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.JobDAO;
import com.chengxusheji.domain.Job;
import com.chengxusheji.dao.QiyeDAO;
import com.chengxusheji.domain.Qiye;
import com.chengxusheji.dao.JobTypeDAO;
import com.chengxusheji.domain.JobType;
import com.chengxusheji.dao.SpecialInfoDAO;
import com.chengxusheji.domain.SpecialInfo;
import com.chengxusheji.dao.SchoolRecordDAO;
import com.chengxusheji.domain.SchoolRecord;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class JobAction extends BaseAction {

    /*�������Ҫ��ѯ������: ��Ƹ��ҵ*/
    private Qiye qiyeObj;
    public void setQiyeObj(Qiye qiyeObj) {
        this.qiyeObj = qiyeObj;
    }
    public Qiye getQiyeObj() {
        return this.qiyeObj;
    }

    /*�������Ҫ��ѯ������: ��Ƹְλ*/
    private String positionName;
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    public String getPositionName() {
        return this.positionName;
    }

    /*�������Ҫ��ѯ������: ְλ����*/
    private JobType jobTypeObj;
    public void setJobTypeObj(JobType jobTypeObj) {
        this.jobTypeObj = jobTypeObj;
    }
    public JobType getJobTypeObj() {
        return this.jobTypeObj;
    }

    /*�������Ҫ��ѯ������: ����רҵ*/
    private SpecialInfo specialObj;
    public void setSpecialObj(SpecialInfo specialObj) {
        this.specialObj = specialObj;
    }
    public SpecialInfo getSpecialObj() {
        return this.specialObj;
    }

    /*�������Ҫ��ѯ������: ���ڳ���*/
    private String city;
    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return this.city;
    }

    /*�������Ҫ��ѯ������: ѧ��Ҫ��*/
    private SchoolRecord schoolRecordObj;
    public void setSchoolRecordObj(SchoolRecord schoolRecordObj) {
        this.schoolRecordObj = schoolRecordObj;
    }
    public SchoolRecord getSchoolRecordObj() {
        return this.schoolRecordObj;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int jobId;
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
    public int getJobId() {
        return jobId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource QiyeDAO qiyeDAO;
    @Resource JobTypeDAO jobTypeDAO;
    @Resource SpecialInfoDAO specialInfoDAO;
    @Resource SchoolRecordDAO schoolRecordDAO;
    @Resource JobDAO jobDAO;

    /*��������Job����*/
    private Job job;
    public void setJob(Job job) {
        this.job = job;
    }
    public Job getJob() {
        return this.job;
    }

    /*��ת�����Job��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Qiye��Ϣ*/
        List<Qiye> qiyeList = qiyeDAO.QueryAllQiyeInfo();
        ctx.put("qiyeList", qiyeList);
        /*��ѯ���е�JobType��Ϣ*/
        List<JobType> jobTypeList = jobTypeDAO.QueryAllJobTypeInfo();
        ctx.put("jobTypeList", jobTypeList);
        /*��ѯ���е�SpecialInfo��Ϣ*/
        List<SpecialInfo> specialInfoList = specialInfoDAO.QueryAllSpecialInfoInfo();
        ctx.put("specialInfoList", specialInfoList);
        /*��ѯ���е�SchoolRecord��Ϣ*/
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QueryAllSchoolRecordInfo();
        ctx.put("schoolRecordList", schoolRecordList);
        return "add_view";
    }

    /*���Job��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddJob() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Qiye qiyeObj = qiyeDAO.GetQiyeByQiyeUserName(job.getQiyeObj().getQiyeUserName());
            job.setQiyeObj(qiyeObj);
            JobType jobTypeObj = jobTypeDAO.GetJobTypeByJobTypeId(job.getJobTypeObj().getJobTypeId());
            job.setJobTypeObj(jobTypeObj);
            SpecialInfo specialObj = specialInfoDAO.GetSpecialInfoBySpecialId(job.getSpecialObj().getSpecialId());
            job.setSpecialObj(specialObj);
            SchoolRecord schoolRecordObj = schoolRecordDAO.GetSchoolRecordById(job.getSchoolRecordObj().getId());
            job.setSchoolRecordObj(schoolRecordObj);
            jobDAO.AddJob(job);
            ctx.put("message",  java.net.URLEncoder.encode("Job��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Job���ʧ��!"));
            return "error";
        }
    }

    /*��ѯJob��Ϣ*/
    public String QueryJob() {
        if(currentPage == 0) currentPage = 1;
        if(positionName == null) positionName = "";
        if(city == null) city = "";
        List<Job> jobList = jobDAO.QueryJobInfo(qiyeObj, positionName, jobTypeObj, specialObj, city, schoolRecordObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        jobDAO.CalculateTotalPageAndRecordNumber(qiyeObj, positionName, jobTypeObj, specialObj, city, schoolRecordObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = jobDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = jobDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jobList",  jobList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("qiyeObj", qiyeObj);
        List<Qiye> qiyeList = qiyeDAO.QueryAllQiyeInfo();
        ctx.put("qiyeList", qiyeList);
        ctx.put("positionName", positionName);
        ctx.put("jobTypeObj", jobTypeObj);
        List<JobType> jobTypeList = jobTypeDAO.QueryAllJobTypeInfo();
        ctx.put("jobTypeList", jobTypeList);
        ctx.put("specialObj", specialObj);
        List<SpecialInfo> specialInfoList = specialInfoDAO.QueryAllSpecialInfoInfo();
        ctx.put("specialInfoList", specialInfoList);
        ctx.put("city", city);
        ctx.put("schoolRecordObj", schoolRecordObj);
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QueryAllSchoolRecordInfo();
        ctx.put("schoolRecordList", schoolRecordList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryJobOutputToExcel() { 
        if(positionName == null) positionName = "";
        if(city == null) city = "";
        List<Job> jobList = jobDAO.QueryJobInfo(qiyeObj,positionName,jobTypeObj,specialObj,city,schoolRecordObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Job��Ϣ��¼"; 
        String[] headers = { "ְλid","��Ƹ��ҵ","��Ƹְλ","ְλ����","����רҵ","��Ƹ����","���ڳ���","н�ʴ���","ѧ��Ҫ��","��������","������ַ"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<jobList.size();i++) {
        	Job job = jobList.get(i); 
        	dataset.add(new String[]{job.getJobId() + "",job.getQiyeObj().getQiyeName(),
job.getPositionName(),job.getJobTypeObj().getTypeName(),
job.getSpecialObj().getSpecialName(),
job.getPersonNum(),job.getCity(),job.getSalary(),job.getSchoolRecordObj().getSchooRecordName(),
job.getWorkYears(),job.getWorkAddress()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Job.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯJob��Ϣ*/
    public String FrontQueryJob() {
        if(currentPage == 0) currentPage = 1;
        if(positionName == null) positionName = "";
        if(city == null) city = "";
        List<Job> jobList = jobDAO.QueryJobInfo(qiyeObj, positionName, jobTypeObj, specialObj, city, schoolRecordObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        jobDAO.CalculateTotalPageAndRecordNumber(qiyeObj, positionName, jobTypeObj, specialObj, city, schoolRecordObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = jobDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = jobDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jobList",  jobList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("qiyeObj", qiyeObj);
        List<Qiye> qiyeList = qiyeDAO.QueryAllQiyeInfo();
        ctx.put("qiyeList", qiyeList);
        ctx.put("positionName", positionName);
        ctx.put("jobTypeObj", jobTypeObj);
        List<JobType> jobTypeList = jobTypeDAO.QueryAllJobTypeInfo();
        ctx.put("jobTypeList", jobTypeList);
        ctx.put("specialObj", specialObj);
        List<SpecialInfo> specialInfoList = specialInfoDAO.QueryAllSpecialInfoInfo();
        ctx.put("specialInfoList", specialInfoList);
        ctx.put("city", city);
        ctx.put("schoolRecordObj", schoolRecordObj);
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QueryAllSchoolRecordInfo();
        ctx.put("schoolRecordList", schoolRecordList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Job��Ϣ*/
    public String ModifyJobQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������jobId��ȡJob����*/
        Job job = jobDAO.GetJobByJobId(jobId);

        List<Qiye> qiyeList = qiyeDAO.QueryAllQiyeInfo();
        ctx.put("qiyeList", qiyeList);
        List<JobType> jobTypeList = jobTypeDAO.QueryAllJobTypeInfo();
        ctx.put("jobTypeList", jobTypeList);
        List<SpecialInfo> specialInfoList = specialInfoDAO.QueryAllSpecialInfoInfo();
        ctx.put("specialInfoList", specialInfoList);
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QueryAllSchoolRecordInfo();
        ctx.put("schoolRecordList", schoolRecordList);
        ctx.put("job",  job);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Job��Ϣ*/
    public String FrontShowJobQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������jobId��ȡJob����*/
        Job job = jobDAO.GetJobByJobId(jobId);

        List<Qiye> qiyeList = qiyeDAO.QueryAllQiyeInfo();
        ctx.put("qiyeList", qiyeList);
        List<JobType> jobTypeList = jobTypeDAO.QueryAllJobTypeInfo();
        ctx.put("jobTypeList", jobTypeList);
        List<SpecialInfo> specialInfoList = specialInfoDAO.QueryAllSpecialInfoInfo();
        ctx.put("specialInfoList", specialInfoList);
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QueryAllSchoolRecordInfo();
        ctx.put("schoolRecordList", schoolRecordList);
        ctx.put("job",  job);
        return "front_show_view";
    }

    /*�����޸�Job��Ϣ*/
    public String ModifyJob() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Qiye qiyeObj = qiyeDAO.GetQiyeByQiyeUserName(job.getQiyeObj().getQiyeUserName());
            job.setQiyeObj(qiyeObj);
            JobType jobTypeObj = jobTypeDAO.GetJobTypeByJobTypeId(job.getJobTypeObj().getJobTypeId());
            job.setJobTypeObj(jobTypeObj);
            SpecialInfo specialObj = specialInfoDAO.GetSpecialInfoBySpecialId(job.getSpecialObj().getSpecialId());
            job.setSpecialObj(specialObj);
            SchoolRecord schoolRecordObj = schoolRecordDAO.GetSchoolRecordById(job.getSchoolRecordObj().getId());
            job.setSchoolRecordObj(schoolRecordObj);
            jobDAO.UpdateJob(job);
            ctx.put("message",  java.net.URLEncoder.encode("Job��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Job��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Job��Ϣ*/
    public String DeleteJob() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            jobDAO.DeleteJob(jobId);
            ctx.put("message",  java.net.URLEncoder.encode("Jobɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jobɾ��ʧ��!"));
            return "error";
        }
    }

}
