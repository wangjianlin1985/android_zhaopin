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
import com.chengxusheji.dao.JobWantDAO;
import com.chengxusheji.domain.JobWant;
import com.chengxusheji.dao.JobTypeDAO;
import com.chengxusheji.domain.JobType;
import com.chengxusheji.dao.SpecialInfoDAO;
import com.chengxusheji.domain.SpecialInfo;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class JobWantAction extends BaseAction {

    /*界面层需要查询的属性: 职位分类*/
    private JobType jobTypeObj;
    public void setJobTypeObj(JobType jobTypeObj) {
        this.jobTypeObj = jobTypeObj;
    }
    public JobType getJobTypeObj() {
        return this.jobTypeObj;
    }

    /*界面层需要查询的属性: 求职专业*/
    private SpecialInfo specialObj;
    public void setSpecialObj(SpecialInfo specialObj) {
        this.specialObj = specialObj;
    }
    public SpecialInfo getSpecialObj() {
        return this.specialObj;
    }

    /*界面层需要查询的属性: 职位名称*/
    private String positionName;
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    public String getPositionName() {
        return this.positionName;
    }

    /*界面层需要查询的属性: 工作地点*/
    private String workCity;
    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }
    public String getWorkCity() {
        return this.workCity;
    }

    /*界面层需要查询的属性: 求职人*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 发布时间*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
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

    private int wantId;
    public void setWantId(int wantId) {
        this.wantId = wantId;
    }
    public int getWantId() {
        return wantId;
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
    @Resource JobTypeDAO jobTypeDAO;
    @Resource SpecialInfoDAO specialInfoDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource JobWantDAO jobWantDAO;

    /*待操作的JobWant对象*/
    private JobWant jobWant;
    public void setJobWant(JobWant jobWant) {
        this.jobWant = jobWant;
    }
    public JobWant getJobWant() {
        return this.jobWant;
    }

    /*跳转到添加JobWant视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的JobType信息*/
        List<JobType> jobTypeList = jobTypeDAO.QueryAllJobTypeInfo();
        ctx.put("jobTypeList", jobTypeList);
        /*查询所有的SpecialInfo信息*/
        List<SpecialInfo> specialInfoList = specialInfoDAO.QueryAllSpecialInfoInfo();
        ctx.put("specialInfoList", specialInfoList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加JobWant信息*/
    @SuppressWarnings("deprecation")
    public String AddJobWant() {
        ActionContext ctx = ActionContext.getContext();
        try {
            JobType jobTypeObj = jobTypeDAO.GetJobTypeByJobTypeId(jobWant.getJobTypeObj().getJobTypeId());
            jobWant.setJobTypeObj(jobTypeObj);
            SpecialInfo specialObj = specialInfoDAO.GetSpecialInfoBySpecialId(jobWant.getSpecialObj().getSpecialId());
            jobWant.setSpecialObj(specialObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(jobWant.getUserObj().getUser_name());
            jobWant.setUserObj(userObj);
            jobWantDAO.AddJobWant(jobWant);
            ctx.put("message",  java.net.URLEncoder.encode("JobWant添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JobWant添加失败!"));
            return "error";
        }
    }

    /*查询JobWant信息*/
    public String QueryJobWant() {
        if(currentPage == 0) currentPage = 1;
        if(positionName == null) positionName = "";
        if(workCity == null) workCity = "";
        if(addTime == null) addTime = "";
        List<JobWant> jobWantList = jobWantDAO.QueryJobWantInfo(jobTypeObj, specialObj, positionName, workCity, userObj, addTime, currentPage);
        /*计算总的页数和总的记录数*/
        jobWantDAO.CalculateTotalPageAndRecordNumber(jobTypeObj, specialObj, positionName, workCity, userObj, addTime);
        /*获取到总的页码数目*/
        totalPage = jobWantDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = jobWantDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jobWantList",  jobWantList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("jobTypeObj", jobTypeObj);
        List<JobType> jobTypeList = jobTypeDAO.QueryAllJobTypeInfo();
        ctx.put("jobTypeList", jobTypeList);
        ctx.put("specialObj", specialObj);
        List<SpecialInfo> specialInfoList = specialInfoDAO.QueryAllSpecialInfoInfo();
        ctx.put("specialInfoList", specialInfoList);
        ctx.put("positionName", positionName);
        ctx.put("workCity", workCity);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("addTime", addTime);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryJobWantOutputToExcel() { 
        if(positionName == null) positionName = "";
        if(workCity == null) workCity = "";
        if(addTime == null) addTime = "";
        List<JobWant> jobWantList = jobWantDAO.QueryJobWantInfo(jobTypeObj,specialObj,positionName,workCity,userObj,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "JobWant信息记录"; 
        String[] headers = { "记录id","职位分类","求职专业","职位名称","期望薪资","工作地点","求职人","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<jobWantList.size();i++) {
        	JobWant jobWant = jobWantList.get(i); 
        	dataset.add(new String[]{jobWant.getWantId() + "",jobWant.getJobTypeObj().getTypeName(),
jobWant.getSpecialObj().getSpecialName(),
jobWant.getPositionName(),jobWant.getSalary(),jobWant.getWorkCity(),jobWant.getUserObj().getName(),
jobWant.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"JobWant.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询JobWant信息*/
    public String FrontQueryJobWant() {
        if(currentPage == 0) currentPage = 1;
        if(positionName == null) positionName = "";
        if(workCity == null) workCity = "";
        if(addTime == null) addTime = "";
        List<JobWant> jobWantList = jobWantDAO.QueryJobWantInfo(jobTypeObj, specialObj, positionName, workCity, userObj, addTime, currentPage);
        /*计算总的页数和总的记录数*/
        jobWantDAO.CalculateTotalPageAndRecordNumber(jobTypeObj, specialObj, positionName, workCity, userObj, addTime);
        /*获取到总的页码数目*/
        totalPage = jobWantDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = jobWantDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jobWantList",  jobWantList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("jobTypeObj", jobTypeObj);
        List<JobType> jobTypeList = jobTypeDAO.QueryAllJobTypeInfo();
        ctx.put("jobTypeList", jobTypeList);
        ctx.put("specialObj", specialObj);
        List<SpecialInfo> specialInfoList = specialInfoDAO.QueryAllSpecialInfoInfo();
        ctx.put("specialInfoList", specialInfoList);
        ctx.put("positionName", positionName);
        ctx.put("workCity", workCity);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("addTime", addTime);
        return "front_query_view";
    }

    /*查询要修改的JobWant信息*/
    public String ModifyJobWantQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键wantId获取JobWant对象*/
        JobWant jobWant = jobWantDAO.GetJobWantByWantId(wantId);

        List<JobType> jobTypeList = jobTypeDAO.QueryAllJobTypeInfo();
        ctx.put("jobTypeList", jobTypeList);
        List<SpecialInfo> specialInfoList = specialInfoDAO.QueryAllSpecialInfoInfo();
        ctx.put("specialInfoList", specialInfoList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jobWant",  jobWant);
        return "modify_view";
    }

    /*查询要修改的JobWant信息*/
    public String FrontShowJobWantQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键wantId获取JobWant对象*/
        JobWant jobWant = jobWantDAO.GetJobWantByWantId(wantId);

        List<JobType> jobTypeList = jobTypeDAO.QueryAllJobTypeInfo();
        ctx.put("jobTypeList", jobTypeList);
        List<SpecialInfo> specialInfoList = specialInfoDAO.QueryAllSpecialInfoInfo();
        ctx.put("specialInfoList", specialInfoList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jobWant",  jobWant);
        return "front_show_view";
    }

    /*更新修改JobWant信息*/
    public String ModifyJobWant() {
        ActionContext ctx = ActionContext.getContext();
        try {
            JobType jobTypeObj = jobTypeDAO.GetJobTypeByJobTypeId(jobWant.getJobTypeObj().getJobTypeId());
            jobWant.setJobTypeObj(jobTypeObj);
            SpecialInfo specialObj = specialInfoDAO.GetSpecialInfoBySpecialId(jobWant.getSpecialObj().getSpecialId());
            jobWant.setSpecialObj(specialObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(jobWant.getUserObj().getUser_name());
            jobWant.setUserObj(userObj);
            jobWantDAO.UpdateJobWant(jobWant);
            ctx.put("message",  java.net.URLEncoder.encode("JobWant信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JobWant信息更新失败!"));
            return "error";
       }
   }

    /*删除JobWant信息*/
    public String DeleteJobWant() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            jobWantDAO.DeleteJobWant(wantId);
            ctx.put("message",  java.net.URLEncoder.encode("JobWant删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JobWant删除失败!"));
            return "error";
        }
    }

}
