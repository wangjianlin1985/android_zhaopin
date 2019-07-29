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
import com.chengxusheji.dao.SpecialInfoDAO;
import com.chengxusheji.domain.SpecialInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SpecialInfoAction extends BaseAction {

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

    private int specialId;
    public void setSpecialId(int specialId) {
        this.specialId = specialId;
    }
    public int getSpecialId() {
        return specialId;
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
    @Resource SpecialInfoDAO specialInfoDAO;

    /*待操作的SpecialInfo对象*/
    private SpecialInfo specialInfo;
    public void setSpecialInfo(SpecialInfo specialInfo) {
        this.specialInfo = specialInfo;
    }
    public SpecialInfo getSpecialInfo() {
        return this.specialInfo;
    }

    /*跳转到添加SpecialInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加SpecialInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddSpecialInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            specialInfoDAO.AddSpecialInfo(specialInfo);
            ctx.put("message",  java.net.URLEncoder.encode("SpecialInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SpecialInfo添加失败!"));
            return "error";
        }
    }

    /*查询SpecialInfo信息*/
    public String QuerySpecialInfo() {
        if(currentPage == 0) currentPage = 1;
        List<SpecialInfo> specialInfoList = specialInfoDAO.QuerySpecialInfoInfo(currentPage);
        /*计算总的页数和总的记录数*/
        specialInfoDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = specialInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = specialInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("specialInfoList",  specialInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QuerySpecialInfoOutputToExcel() { 
        List<SpecialInfo> specialInfoList = specialInfoDAO.QuerySpecialInfoInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SpecialInfo信息记录"; 
        String[] headers = { "专业id","专业名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<specialInfoList.size();i++) {
        	SpecialInfo specialInfo = specialInfoList.get(i); 
        	dataset.add(new String[]{specialInfo.getSpecialId() + "",specialInfo.getSpecialName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SpecialInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询SpecialInfo信息*/
    public String FrontQuerySpecialInfo() {
        if(currentPage == 0) currentPage = 1;
        List<SpecialInfo> specialInfoList = specialInfoDAO.QuerySpecialInfoInfo(currentPage);
        /*计算总的页数和总的记录数*/
        specialInfoDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = specialInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = specialInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("specialInfoList",  specialInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的SpecialInfo信息*/
    public String ModifySpecialInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键specialId获取SpecialInfo对象*/
        SpecialInfo specialInfo = specialInfoDAO.GetSpecialInfoBySpecialId(specialId);

        ctx.put("specialInfo",  specialInfo);
        return "modify_view";
    }

    /*查询要修改的SpecialInfo信息*/
    public String FrontShowSpecialInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键specialId获取SpecialInfo对象*/
        SpecialInfo specialInfo = specialInfoDAO.GetSpecialInfoBySpecialId(specialId);

        ctx.put("specialInfo",  specialInfo);
        return "front_show_view";
    }

    /*更新修改SpecialInfo信息*/
    public String ModifySpecialInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            specialInfoDAO.UpdateSpecialInfo(specialInfo);
            ctx.put("message",  java.net.URLEncoder.encode("SpecialInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SpecialInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除SpecialInfo信息*/
    public String DeleteSpecialInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            specialInfoDAO.DeleteSpecialInfo(specialId);
            ctx.put("message",  java.net.URLEncoder.encode("SpecialInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SpecialInfo删除失败!"));
            return "error";
        }
    }

}
