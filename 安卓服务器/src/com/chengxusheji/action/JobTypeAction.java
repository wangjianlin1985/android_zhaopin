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
import com.chengxusheji.dao.JobTypeDAO;
import com.chengxusheji.domain.JobType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class JobTypeAction extends BaseAction {

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

    private int jobTypeId;
    public void setJobTypeId(int jobTypeId) {
        this.jobTypeId = jobTypeId;
    }
    public int getJobTypeId() {
        return jobTypeId;
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

    /*��������JobType����*/
    private JobType jobType;
    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }
    public JobType getJobType() {
        return this.jobType;
    }

    /*��ת�����JobType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���JobType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddJobType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            jobTypeDAO.AddJobType(jobType);
            ctx.put("message",  java.net.URLEncoder.encode("JobType��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JobType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯJobType��Ϣ*/
    public String QueryJobType() {
        if(currentPage == 0) currentPage = 1;
        List<JobType> jobTypeList = jobTypeDAO.QueryJobTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        jobTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = jobTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = jobTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jobTypeList",  jobTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryJobTypeOutputToExcel() { 
        List<JobType> jobTypeList = jobTypeDAO.QueryJobTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "JobType��Ϣ��¼"; 
        String[] headers = { "ְλ����id","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<jobTypeList.size();i++) {
        	JobType jobType = jobTypeList.get(i); 
        	dataset.add(new String[]{jobType.getJobTypeId() + "",jobType.getTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"JobType.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯJobType��Ϣ*/
    public String FrontQueryJobType() {
        if(currentPage == 0) currentPage = 1;
        List<JobType> jobTypeList = jobTypeDAO.QueryJobTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        jobTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = jobTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = jobTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jobTypeList",  jobTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�JobType��Ϣ*/
    public String ModifyJobTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������jobTypeId��ȡJobType����*/
        JobType jobType = jobTypeDAO.GetJobTypeByJobTypeId(jobTypeId);

        ctx.put("jobType",  jobType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�JobType��Ϣ*/
    public String FrontShowJobTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������jobTypeId��ȡJobType����*/
        JobType jobType = jobTypeDAO.GetJobTypeByJobTypeId(jobTypeId);

        ctx.put("jobType",  jobType);
        return "front_show_view";
    }

    /*�����޸�JobType��Ϣ*/
    public String ModifyJobType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            jobTypeDAO.UpdateJobType(jobType);
            ctx.put("message",  java.net.URLEncoder.encode("JobType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JobType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��JobType��Ϣ*/
    public String DeleteJobType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            jobTypeDAO.DeleteJobType(jobTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("JobTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JobTypeɾ��ʧ��!"));
            return "error";
        }
    }

}
