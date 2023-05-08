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

    private int jobTypeId;
    public void setJobTypeId(int jobTypeId) {
        this.jobTypeId = jobTypeId;
    }
    public int getJobTypeId() {
        return jobTypeId;
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

    /*待操作的JobType对象*/
    private JobType jobType;
    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }
    public JobType getJobType() {
        return this.jobType;
    }

    /*跳转到添加JobType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加JobType信息*/
    @SuppressWarnings("deprecation")
    public String AddJobType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            jobTypeDAO.AddJobType(jobType);
            ctx.put("message",  java.net.URLEncoder.encode("JobType添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JobType添加失败!"));
            return "error";
        }
    }

    /*查询JobType信息*/
    public String QueryJobType() {
        if(currentPage == 0) currentPage = 1;
        List<JobType> jobTypeList = jobTypeDAO.QueryJobTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        jobTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = jobTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = jobTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jobTypeList",  jobTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryJobTypeOutputToExcel() { 
        List<JobType> jobTypeList = jobTypeDAO.QueryJobTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "JobType信息记录"; 
        String[] headers = { "职位分类id","分类名称"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"JobType.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询JobType信息*/
    public String FrontQueryJobType() {
        if(currentPage == 0) currentPage = 1;
        List<JobType> jobTypeList = jobTypeDAO.QueryJobTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        jobTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = jobTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = jobTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jobTypeList",  jobTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的JobType信息*/
    public String ModifyJobTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键jobTypeId获取JobType对象*/
        JobType jobType = jobTypeDAO.GetJobTypeByJobTypeId(jobTypeId);

        ctx.put("jobType",  jobType);
        return "modify_view";
    }

    /*查询要修改的JobType信息*/
    public String FrontShowJobTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键jobTypeId获取JobType对象*/
        JobType jobType = jobTypeDAO.GetJobTypeByJobTypeId(jobTypeId);

        ctx.put("jobType",  jobType);
        return "front_show_view";
    }

    /*更新修改JobType信息*/
    public String ModifyJobType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            jobTypeDAO.UpdateJobType(jobType);
            ctx.put("message",  java.net.URLEncoder.encode("JobType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JobType信息更新失败!"));
            return "error";
       }
   }

    /*删除JobType信息*/
    public String DeleteJobType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            jobTypeDAO.DeleteJobType(jobTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("JobType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JobType删除失败!"));
            return "error";
        }
    }

}
