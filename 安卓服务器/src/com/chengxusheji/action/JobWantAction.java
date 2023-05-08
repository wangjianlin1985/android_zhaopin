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

    /*�������Ҫ��ѯ������: ְλ����*/
    private JobType jobTypeObj;
    public void setJobTypeObj(JobType jobTypeObj) {
        this.jobTypeObj = jobTypeObj;
    }
    public JobType getJobTypeObj() {
        return this.jobTypeObj;
    }

    /*�������Ҫ��ѯ������: ��ְרҵ*/
    private SpecialInfo specialObj;
    public void setSpecialObj(SpecialInfo specialObj) {
        this.specialObj = specialObj;
    }
    public SpecialInfo getSpecialObj() {
        return this.specialObj;
    }

    /*�������Ҫ��ѯ������: ְλ����*/
    private String positionName;
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    public String getPositionName() {
        return this.positionName;
    }

    /*�������Ҫ��ѯ������: �����ص�*/
    private String workCity;
    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }
    public String getWorkCity() {
        return this.workCity;
    }

    /*�������Ҫ��ѯ������: ��ְ��*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
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

    private int wantId;
    public void setWantId(int wantId) {
        this.wantId = wantId;
    }
    public int getWantId() {
        return wantId;
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
    @Resource JobTypeDAO jobTypeDAO;
    @Resource SpecialInfoDAO specialInfoDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource JobWantDAO jobWantDAO;

    /*��������JobWant����*/
    private JobWant jobWant;
    public void setJobWant(JobWant jobWant) {
        this.jobWant = jobWant;
    }
    public JobWant getJobWant() {
        return this.jobWant;
    }

    /*��ת�����JobWant��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�JobType��Ϣ*/
        List<JobType> jobTypeList = jobTypeDAO.QueryAllJobTypeInfo();
        ctx.put("jobTypeList", jobTypeList);
        /*��ѯ���е�SpecialInfo��Ϣ*/
        List<SpecialInfo> specialInfoList = specialInfoDAO.QueryAllSpecialInfoInfo();
        ctx.put("specialInfoList", specialInfoList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���JobWant��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("JobWant��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JobWant���ʧ��!"));
            return "error";
        }
    }

    /*��ѯJobWant��Ϣ*/
    public String QueryJobWant() {
        if(currentPage == 0) currentPage = 1;
        if(positionName == null) positionName = "";
        if(workCity == null) workCity = "";
        if(addTime == null) addTime = "";
        List<JobWant> jobWantList = jobWantDAO.QueryJobWantInfo(jobTypeObj, specialObj, positionName, workCity, userObj, addTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        jobWantDAO.CalculateTotalPageAndRecordNumber(jobTypeObj, specialObj, positionName, workCity, userObj, addTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = jobWantDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryJobWantOutputToExcel() { 
        if(positionName == null) positionName = "";
        if(workCity == null) workCity = "";
        if(addTime == null) addTime = "";
        List<JobWant> jobWantList = jobWantDAO.QueryJobWantInfo(jobTypeObj,specialObj,positionName,workCity,userObj,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "JobWant��Ϣ��¼"; 
        String[] headers = { "��¼id","ְλ����","��ְרҵ","ְλ����","����н��","�����ص�","��ְ��","����ʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"JobWant.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯJobWant��Ϣ*/
    public String FrontQueryJobWant() {
        if(currentPage == 0) currentPage = 1;
        if(positionName == null) positionName = "";
        if(workCity == null) workCity = "";
        if(addTime == null) addTime = "";
        List<JobWant> jobWantList = jobWantDAO.QueryJobWantInfo(jobTypeObj, specialObj, positionName, workCity, userObj, addTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        jobWantDAO.CalculateTotalPageAndRecordNumber(jobTypeObj, specialObj, positionName, workCity, userObj, addTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = jobWantDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�JobWant��Ϣ*/
    public String ModifyJobWantQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������wantId��ȡJobWant����*/
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

    /*��ѯҪ�޸ĵ�JobWant��Ϣ*/
    public String FrontShowJobWantQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������wantId��ȡJobWant����*/
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

    /*�����޸�JobWant��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("JobWant��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JobWant��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��JobWant��Ϣ*/
    public String DeleteJobWant() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            jobWantDAO.DeleteJobWant(wantId);
            ctx.put("message",  java.net.URLEncoder.encode("JobWantɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JobWantɾ��ʧ��!"));
            return "error";
        }
    }

}
