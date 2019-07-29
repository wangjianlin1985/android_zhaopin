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
import com.chengxusheji.dao.QiyePropertyDAO;
import com.chengxusheji.domain.QiyeProperty;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class QiyePropertyAction extends BaseAction {

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
    @Resource QiyePropertyDAO qiyePropertyDAO;

    /*待操作的QiyeProperty对象*/
    private QiyeProperty qiyeProperty;
    public void setQiyeProperty(QiyeProperty qiyeProperty) {
        this.qiyeProperty = qiyeProperty;
    }
    public QiyeProperty getQiyeProperty() {
        return this.qiyeProperty;
    }

    /*跳转到添加QiyeProperty视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加QiyeProperty信息*/
    @SuppressWarnings("deprecation")
    public String AddQiyeProperty() {
        ActionContext ctx = ActionContext.getContext();
        try {
            qiyePropertyDAO.AddQiyeProperty(qiyeProperty);
            ctx.put("message",  java.net.URLEncoder.encode("QiyeProperty添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QiyeProperty添加失败!"));
            return "error";
        }
    }

    /*查询QiyeProperty信息*/
    public String QueryQiyeProperty() {
        if(currentPage == 0) currentPage = 1;
        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryQiyePropertyInfo(currentPage);
        /*计算总的页数和总的记录数*/
        qiyePropertyDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = qiyePropertyDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = qiyePropertyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("qiyePropertyList",  qiyePropertyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryQiyePropertyOutputToExcel() { 
        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryQiyePropertyInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "QiyeProperty信息记录"; 
        String[] headers = { "记录编号","企业性质名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<qiyePropertyList.size();i++) {
        	QiyeProperty qiyeProperty = qiyePropertyList.get(i); 
        	dataset.add(new String[]{qiyeProperty.getId() + "",qiyeProperty.getPropertyName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"QiyeProperty.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询QiyeProperty信息*/
    public String FrontQueryQiyeProperty() {
        if(currentPage == 0) currentPage = 1;
        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryQiyePropertyInfo(currentPage);
        /*计算总的页数和总的记录数*/
        qiyePropertyDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = qiyePropertyDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = qiyePropertyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("qiyePropertyList",  qiyePropertyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的QiyeProperty信息*/
    public String ModifyQiyePropertyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取QiyeProperty对象*/
        QiyeProperty qiyeProperty = qiyePropertyDAO.GetQiyePropertyById(id);

        ctx.put("qiyeProperty",  qiyeProperty);
        return "modify_view";
    }

    /*查询要修改的QiyeProperty信息*/
    public String FrontShowQiyePropertyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取QiyeProperty对象*/
        QiyeProperty qiyeProperty = qiyePropertyDAO.GetQiyePropertyById(id);

        ctx.put("qiyeProperty",  qiyeProperty);
        return "front_show_view";
    }

    /*更新修改QiyeProperty信息*/
    public String ModifyQiyeProperty() {
        ActionContext ctx = ActionContext.getContext();
        try {
            qiyePropertyDAO.UpdateQiyeProperty(qiyeProperty);
            ctx.put("message",  java.net.URLEncoder.encode("QiyeProperty信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QiyeProperty信息更新失败!"));
            return "error";
       }
   }

    /*删除QiyeProperty信息*/
    public String DeleteQiyeProperty() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            qiyePropertyDAO.DeleteQiyeProperty(id);
            ctx.put("message",  java.net.URLEncoder.encode("QiyeProperty删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QiyeProperty删除失败!"));
            return "error";
        }
    }

}
