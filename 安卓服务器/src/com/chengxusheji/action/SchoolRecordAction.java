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
import com.chengxusheji.dao.SchoolRecordDAO;
import com.chengxusheji.domain.SchoolRecord;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SchoolRecordAction extends BaseAction {

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

    private int id;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
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
    @Resource SchoolRecordDAO schoolRecordDAO;

    /*待操作的SchoolRecord对象*/
    private SchoolRecord schoolRecord;
    public void setSchoolRecord(SchoolRecord schoolRecord) {
        this.schoolRecord = schoolRecord;
    }
    public SchoolRecord getSchoolRecord() {
        return this.schoolRecord;
    }

    /*跳转到添加SchoolRecord视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加SchoolRecord信息*/
    @SuppressWarnings("deprecation")
    public String AddSchoolRecord() {
        ActionContext ctx = ActionContext.getContext();
        try {
            schoolRecordDAO.AddSchoolRecord(schoolRecord);
            ctx.put("message",  java.net.URLEncoder.encode("SchoolRecord添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SchoolRecord添加失败!"));
            return "error";
        }
    }

    /*查询SchoolRecord信息*/
    public String QuerySchoolRecord() {
        if(currentPage == 0) currentPage = 1;
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QuerySchoolRecordInfo(currentPage);
        /*计算总的页数和总的记录数*/
        schoolRecordDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = schoolRecordDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = schoolRecordDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("schoolRecordList",  schoolRecordList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QuerySchoolRecordOutputToExcel() { 
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QuerySchoolRecordInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SchoolRecord信息记录"; 
        String[] headers = { "记录编号","学历名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<schoolRecordList.size();i++) {
        	SchoolRecord schoolRecord = schoolRecordList.get(i); 
        	dataset.add(new String[]{schoolRecord.getId() + "",schoolRecord.getSchooRecordName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SchoolRecord.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询SchoolRecord信息*/
    public String FrontQuerySchoolRecord() {
        if(currentPage == 0) currentPage = 1;
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QuerySchoolRecordInfo(currentPage);
        /*计算总的页数和总的记录数*/
        schoolRecordDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = schoolRecordDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = schoolRecordDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("schoolRecordList",  schoolRecordList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的SchoolRecord信息*/
    public String ModifySchoolRecordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取SchoolRecord对象*/
        SchoolRecord schoolRecord = schoolRecordDAO.GetSchoolRecordById(id);

        ctx.put("schoolRecord",  schoolRecord);
        return "modify_view";
    }

    /*查询要修改的SchoolRecord信息*/
    public String FrontShowSchoolRecordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取SchoolRecord对象*/
        SchoolRecord schoolRecord = schoolRecordDAO.GetSchoolRecordById(id);

        ctx.put("schoolRecord",  schoolRecord);
        return "front_show_view";
    }

    /*更新修改SchoolRecord信息*/
    public String ModifySchoolRecord() {
        ActionContext ctx = ActionContext.getContext();
        try {
            schoolRecordDAO.UpdateSchoolRecord(schoolRecord);
            ctx.put("message",  java.net.URLEncoder.encode("SchoolRecord信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SchoolRecord信息更新失败!"));
            return "error";
       }
   }

    /*删除SchoolRecord信息*/
    public String DeleteSchoolRecord() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            schoolRecordDAO.DeleteSchoolRecord(id);
            ctx.put("message",  java.net.URLEncoder.encode("SchoolRecord删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SchoolRecord删除失败!"));
            return "error";
        }
    }

}
