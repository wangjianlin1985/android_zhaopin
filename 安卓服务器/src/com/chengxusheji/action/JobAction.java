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

    /*界面层需要查询的属性: 招聘企业*/
    private Qiye qiyeObj;
    public void setQiyeObj(Qiye qiyeObj) {
        this.qiyeObj = qiyeObj;
    }
    public Qiye getQiyeObj() {
        return this.qiyeObj;
    }

    /*界面层需要查询的属性: 招聘职位*/
    private String positionName;
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    public String getPositionName() {
        return this.positionName;
    }

    /*界面层需要查询的属性: 职位分类*/
    private JobType jobTypeObj;
    public void setJobTypeObj(JobType jobTypeObj) {
        this.jobTypeObj = jobTypeObj;
    }
    public JobType getJobTypeObj() {
        return this.jobTypeObj;
    }

    /*界面层需要查询的属性: 所属专业*/
    private SpecialInfo specialObj;
    public void setSpecialObj(SpecialInfo specialObj) {
        this.specialObj = specialObj;
    }
    public SpecialInfo getSpecialObj() {
        return this.specialObj;
    }

    /*界面层需要查询的属性: 所在城市*/
    private String city;
    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return this.city;
    }

    /*界面层需要查询的属性: 学历要求*/
    private SchoolRecord schoolRecordObj;
    public void setSchoolRecordObj(SchoolRecord schoolRecordObj) {
        this.schoolRecordObj = schoolRecordObj;
    }
    public SchoolRecord getSchoolRecordObj() {
        return this.schoolRecordObj;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource QiyeDAO qiyeDAO;
    @Resource JobTypeDAO jobTypeDAO;
    @Resource SpecialInfoDAO specialInfoDAO;
    @Resource SchoolRecordDAO schoolRecordDAO;
    @Resource JobDAO jobDAO;

    /*待操作的Job对象*/
    private Job job;
    public void setJob(Job job) {
        this.job = job;
    }
    public Job getJob() {
        return this.job;
    }

    /*跳转到添加Job视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Qiye信息*/
        List<Qiye> qiyeList = qiyeDAO.QueryAllQiyeInfo();
        ctx.put("qiyeList", qiyeList);
        /*查询所有的JobType信息*/
        List<JobType> jobTypeList = jobTypeDAO.QueryAllJobTypeInfo();
        ctx.put("jobTypeList", jobTypeList);
        /*查询所有的SpecialInfo信息*/
        List<SpecialInfo> specialInfoList = specialInfoDAO.QueryAllSpecialInfoInfo();
        ctx.put("specialInfoList", specialInfoList);
        /*查询所有的SchoolRecord信息*/
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QueryAllSchoolRecordInfo();
        ctx.put("schoolRecordList", schoolRecordList);
        return "add_view";
    }

    /*添加Job信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("Job添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Job添加失败!"));
            return "error";
        }
    }

    /*查询Job信息*/
    public String QueryJob() {
        if(currentPage == 0) currentPage = 1;
        if(positionName == null) positionName = "";
        if(city == null) city = "";
        List<Job> jobList = jobDAO.QueryJobInfo(qiyeObj, positionName, jobTypeObj, specialObj, city, schoolRecordObj, currentPage);
        /*计算总的页数和总的记录数*/
        jobDAO.CalculateTotalPageAndRecordNumber(qiyeObj, positionName, jobTypeObj, specialObj, city, schoolRecordObj);
        /*获取到总的页码数目*/
        totalPage = jobDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryJobOutputToExcel() { 
        if(positionName == null) positionName = "";
        if(city == null) city = "";
        List<Job> jobList = jobDAO.QueryJobInfo(qiyeObj,positionName,jobTypeObj,specialObj,city,schoolRecordObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Job信息记录"; 
        String[] headers = { "职位id","招聘企业","招聘职位","职位分类","所属专业","招聘人数","所在城市","薪资待遇","学历要求","工作年限","工作地址"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Job.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询Job信息*/
    public String FrontQueryJob() {
        if(currentPage == 0) currentPage = 1;
        if(positionName == null) positionName = "";
        if(city == null) city = "";
        List<Job> jobList = jobDAO.QueryJobInfo(qiyeObj, positionName, jobTypeObj, specialObj, city, schoolRecordObj, currentPage);
        /*计算总的页数和总的记录数*/
        jobDAO.CalculateTotalPageAndRecordNumber(qiyeObj, positionName, jobTypeObj, specialObj, city, schoolRecordObj);
        /*获取到总的页码数目*/
        totalPage = jobDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Job信息*/
    public String ModifyJobQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键jobId获取Job对象*/
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

    /*查询要修改的Job信息*/
    public String FrontShowJobQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键jobId获取Job对象*/
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

    /*更新修改Job信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("Job信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Job信息更新失败!"));
            return "error";
       }
   }

    /*删除Job信息*/
    public String DeleteJob() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            jobDAO.DeleteJob(jobId);
            ctx.put("message",  java.net.URLEncoder.encode("Job删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Job删除失败!"));
            return "error";
        }
    }

}
