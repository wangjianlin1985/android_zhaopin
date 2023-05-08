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
import com.chengxusheji.dao.QiyeDAO;
import com.chengxusheji.domain.Qiye;
import com.chengxusheji.dao.QiyePropertyDAO;
import com.chengxusheji.domain.QiyeProperty;
import com.chengxusheji.dao.QiyeProfessionDAO;
import com.chengxusheji.domain.QiyeProfession;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class QiyeAction extends BaseAction {

    /*界面层需要查询的属性: 企业账号*/
    private String qiyeUserName;
    public void setQiyeUserName(String qiyeUserName) {
        this.qiyeUserName = qiyeUserName;
    }
    public String getQiyeUserName() {
        return this.qiyeUserName;
    }

    /*界面层需要查询的属性: 企业名称*/
    private String qiyeName;
    public void setQiyeName(String qiyeName) {
        this.qiyeName = qiyeName;
    }
    public String getQiyeName() {
        return this.qiyeName;
    }

    /*界面层需要查询的属性: 企业性质*/
    private QiyeProperty qiyePropertyObj;
    public void setQiyePropertyObj(QiyeProperty qiyePropertyObj) {
        this.qiyePropertyObj = qiyePropertyObj;
    }
    public QiyeProperty getQiyePropertyObj() {
        return this.qiyePropertyObj;
    }

    /*界面层需要查询的属性: 企业行业*/
    private QiyeProfession qiyeProfessionObj;
    public void setQiyeProfessionObj(QiyeProfession qiyeProfessionObj) {
        this.qiyeProfessionObj = qiyeProfessionObj;
    }
    public QiyeProfession getQiyeProfessionObj() {
        return this.qiyeProfessionObj;
    }

    /*界面层需要查询的属性: 联系人*/
    private String connectPerson;
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }
    public String getConnectPerson() {
        return this.connectPerson;
    }

    /*界面层需要查询的属性: 联系电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
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
    @Resource QiyeProfessionDAO qiyeProfessionDAO;
    @Resource QiyeDAO qiyeDAO;

    /*待操作的Qiye对象*/
    private Qiye qiye;
    public void setQiye(Qiye qiye) {
        this.qiye = qiye;
    }
    public Qiye getQiye() {
        return this.qiye;
    }

    /*跳转到添加Qiye视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的QiyeProperty信息*/
        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryAllQiyePropertyInfo();
        ctx.put("qiyePropertyList", qiyePropertyList);
        /*查询所有的QiyeProfession信息*/
        List<QiyeProfession> qiyeProfessionList = qiyeProfessionDAO.QueryAllQiyeProfessionInfo();
        ctx.put("qiyeProfessionList", qiyeProfessionList);
        return "add_view";
    }

    /*添加Qiye信息*/
    @SuppressWarnings("deprecation")
    public String AddQiye() {
        ActionContext ctx = ActionContext.getContext();
        /*验证企业账号是否已经存在*/
        String qiyeUserName = qiye.getQiyeUserName();
        Qiye db_qiye = qiyeDAO.GetQiyeByQiyeUserName(qiyeUserName);
        if(null != db_qiye) {
            ctx.put("error",  java.net.URLEncoder.encode("该企业账号已经存在!"));
            return "error";
        }
        try {
            QiyeProperty qiyePropertyObj = qiyePropertyDAO.GetQiyePropertyById(qiye.getQiyePropertyObj().getId());
            qiye.setQiyePropertyObj(qiyePropertyObj);
            QiyeProfession qiyeProfessionObj = qiyeProfessionDAO.GetQiyeProfessionById(qiye.getQiyeProfessionObj().getId());
            qiye.setQiyeProfessionObj(qiyeProfessionObj);
            qiyeDAO.AddQiye(qiye);
            ctx.put("message",  java.net.URLEncoder.encode("Qiye添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Qiye添加失败!"));
            return "error";
        }
    }

    /*查询Qiye信息*/
    public String QueryQiye() {
        if(currentPage == 0) currentPage = 1;
        if(qiyeUserName == null) qiyeUserName = "";
        if(qiyeName == null) qiyeName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        List<Qiye> qiyeList = qiyeDAO.QueryQiyeInfo(qiyeUserName, qiyeName, qiyePropertyObj, qiyeProfessionObj, connectPerson, telephone, currentPage);
        /*计算总的页数和总的记录数*/
        qiyeDAO.CalculateTotalPageAndRecordNumber(qiyeUserName, qiyeName, qiyePropertyObj, qiyeProfessionObj, connectPerson, telephone);
        /*获取到总的页码数目*/
        totalPage = qiyeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = qiyeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("qiyeList",  qiyeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("qiyeUserName", qiyeUserName);
        ctx.put("qiyeName", qiyeName);
        ctx.put("qiyePropertyObj", qiyePropertyObj);
        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryAllQiyePropertyInfo();
        ctx.put("qiyePropertyList", qiyePropertyList);
        ctx.put("qiyeProfessionObj", qiyeProfessionObj);
        List<QiyeProfession> qiyeProfessionList = qiyeProfessionDAO.QueryAllQiyeProfessionInfo();
        ctx.put("qiyeProfessionList", qiyeProfessionList);
        ctx.put("connectPerson", connectPerson);
        ctx.put("telephone", telephone);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryQiyeOutputToExcel() { 
        if(qiyeUserName == null) qiyeUserName = "";
        if(qiyeName == null) qiyeName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        List<Qiye> qiyeList = qiyeDAO.QueryQiyeInfo(qiyeUserName,qiyeName,qiyePropertyObj,qiyeProfessionObj,connectPerson,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Qiye信息记录"; 
        String[] headers = { "企业账号","企业名称","企业资质","企业性质","企业行业","企业规模","联系人","联系电话","企业邮箱"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<qiyeList.size();i++) {
        	Qiye qiye = qiyeList.get(i); 
        	dataset.add(new String[]{qiye.getQiyeUserName(),qiye.getQiyeName(),qiye.getQiyeQualify(),qiye.getQiyePropertyObj().getPropertyName(),
qiye.getQiyeProfessionObj().getProfessionName(),
qiye.getQiyeScale(),qiye.getConnectPerson(),qiye.getTelephone(),qiye.getEmail()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Qiye.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Qiye信息*/
    public String FrontQueryQiye() {
        if(currentPage == 0) currentPage = 1;
        if(qiyeUserName == null) qiyeUserName = "";
        if(qiyeName == null) qiyeName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        List<Qiye> qiyeList = qiyeDAO.QueryQiyeInfo(qiyeUserName, qiyeName, qiyePropertyObj, qiyeProfessionObj, connectPerson, telephone, currentPage);
        /*计算总的页数和总的记录数*/
        qiyeDAO.CalculateTotalPageAndRecordNumber(qiyeUserName, qiyeName, qiyePropertyObj, qiyeProfessionObj, connectPerson, telephone);
        /*获取到总的页码数目*/
        totalPage = qiyeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = qiyeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("qiyeList",  qiyeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("qiyeUserName", qiyeUserName);
        ctx.put("qiyeName", qiyeName);
        ctx.put("qiyePropertyObj", qiyePropertyObj);
        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryAllQiyePropertyInfo();
        ctx.put("qiyePropertyList", qiyePropertyList);
        ctx.put("qiyeProfessionObj", qiyeProfessionObj);
        List<QiyeProfession> qiyeProfessionList = qiyeProfessionDAO.QueryAllQiyeProfessionInfo();
        ctx.put("qiyeProfessionList", qiyeProfessionList);
        ctx.put("connectPerson", connectPerson);
        ctx.put("telephone", telephone);
        return "front_query_view";
    }

    /*查询要修改的Qiye信息*/
    public String ModifyQiyeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键qiyeUserName获取Qiye对象*/
        Qiye qiye = qiyeDAO.GetQiyeByQiyeUserName(qiyeUserName);

        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryAllQiyePropertyInfo();
        ctx.put("qiyePropertyList", qiyePropertyList);
        List<QiyeProfession> qiyeProfessionList = qiyeProfessionDAO.QueryAllQiyeProfessionInfo();
        ctx.put("qiyeProfessionList", qiyeProfessionList);
        ctx.put("qiye",  qiye);
        return "modify_view";
    }

    /*查询要修改的Qiye信息*/
    public String FrontShowQiyeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键qiyeUserName获取Qiye对象*/
        Qiye qiye = qiyeDAO.GetQiyeByQiyeUserName(qiyeUserName);

        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryAllQiyePropertyInfo();
        ctx.put("qiyePropertyList", qiyePropertyList);
        List<QiyeProfession> qiyeProfessionList = qiyeProfessionDAO.QueryAllQiyeProfessionInfo();
        ctx.put("qiyeProfessionList", qiyeProfessionList);
        ctx.put("qiye",  qiye);
        return "front_show_view";
    }

    /*更新修改Qiye信息*/
    public String ModifyQiye() {
        ActionContext ctx = ActionContext.getContext();
        try {
            QiyeProperty qiyePropertyObj = qiyePropertyDAO.GetQiyePropertyById(qiye.getQiyePropertyObj().getId());
            qiye.setQiyePropertyObj(qiyePropertyObj);
            QiyeProfession qiyeProfessionObj = qiyeProfessionDAO.GetQiyeProfessionById(qiye.getQiyeProfessionObj().getId());
            qiye.setQiyeProfessionObj(qiyeProfessionObj);
            qiyeDAO.UpdateQiye(qiye);
            ctx.put("message",  java.net.URLEncoder.encode("Qiye信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Qiye信息更新失败!"));
            return "error";
       }
   }

    /*删除Qiye信息*/
    public String DeleteQiye() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            qiyeDAO.DeleteQiye(qiyeUserName);
            ctx.put("message",  java.net.URLEncoder.encode("Qiye删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Qiye删除失败!"));
            return "error";
        }
    }

}
