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

    /*�������Ҫ��ѯ������: ��ҵ�˺�*/
    private String qiyeUserName;
    public void setQiyeUserName(String qiyeUserName) {
        this.qiyeUserName = qiyeUserName;
    }
    public String getQiyeUserName() {
        return this.qiyeUserName;
    }

    /*�������Ҫ��ѯ������: ��ҵ����*/
    private String qiyeName;
    public void setQiyeName(String qiyeName) {
        this.qiyeName = qiyeName;
    }
    public String getQiyeName() {
        return this.qiyeName;
    }

    /*�������Ҫ��ѯ������: ��ҵ����*/
    private QiyeProperty qiyePropertyObj;
    public void setQiyePropertyObj(QiyeProperty qiyePropertyObj) {
        this.qiyePropertyObj = qiyePropertyObj;
    }
    public QiyeProperty getQiyePropertyObj() {
        return this.qiyePropertyObj;
    }

    /*�������Ҫ��ѯ������: ��ҵ��ҵ*/
    private QiyeProfession qiyeProfessionObj;
    public void setQiyeProfessionObj(QiyeProfession qiyeProfessionObj) {
        this.qiyeProfessionObj = qiyeProfessionObj;
    }
    public QiyeProfession getQiyeProfessionObj() {
        return this.qiyeProfessionObj;
    }

    /*�������Ҫ��ѯ������: ��ϵ��*/
    private String connectPerson;
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }
    public String getConnectPerson() {
        return this.connectPerson;
    }

    /*�������Ҫ��ѯ������: ��ϵ�绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource QiyePropertyDAO qiyePropertyDAO;
    @Resource QiyeProfessionDAO qiyeProfessionDAO;
    @Resource QiyeDAO qiyeDAO;

    /*��������Qiye����*/
    private Qiye qiye;
    public void setQiye(Qiye qiye) {
        this.qiye = qiye;
    }
    public Qiye getQiye() {
        return this.qiye;
    }

    /*��ת�����Qiye��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�QiyeProperty��Ϣ*/
        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryAllQiyePropertyInfo();
        ctx.put("qiyePropertyList", qiyePropertyList);
        /*��ѯ���е�QiyeProfession��Ϣ*/
        List<QiyeProfession> qiyeProfessionList = qiyeProfessionDAO.QueryAllQiyeProfessionInfo();
        ctx.put("qiyeProfessionList", qiyeProfessionList);
        return "add_view";
    }

    /*���Qiye��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddQiye() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��ҵ�˺��Ƿ��Ѿ�����*/
        String qiyeUserName = qiye.getQiyeUserName();
        Qiye db_qiye = qiyeDAO.GetQiyeByQiyeUserName(qiyeUserName);
        if(null != db_qiye) {
            ctx.put("error",  java.net.URLEncoder.encode("����ҵ�˺��Ѿ�����!"));
            return "error";
        }
        try {
            QiyeProperty qiyePropertyObj = qiyePropertyDAO.GetQiyePropertyById(qiye.getQiyePropertyObj().getId());
            qiye.setQiyePropertyObj(qiyePropertyObj);
            QiyeProfession qiyeProfessionObj = qiyeProfessionDAO.GetQiyeProfessionById(qiye.getQiyeProfessionObj().getId());
            qiye.setQiyeProfessionObj(qiyeProfessionObj);
            qiyeDAO.AddQiye(qiye);
            ctx.put("message",  java.net.URLEncoder.encode("Qiye��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Qiye���ʧ��!"));
            return "error";
        }
    }

    /*��ѯQiye��Ϣ*/
    public String QueryQiye() {
        if(currentPage == 0) currentPage = 1;
        if(qiyeUserName == null) qiyeUserName = "";
        if(qiyeName == null) qiyeName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        List<Qiye> qiyeList = qiyeDAO.QueryQiyeInfo(qiyeUserName, qiyeName, qiyePropertyObj, qiyeProfessionObj, connectPerson, telephone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        qiyeDAO.CalculateTotalPageAndRecordNumber(qiyeUserName, qiyeName, qiyePropertyObj, qiyeProfessionObj, connectPerson, telephone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = qiyeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryQiyeOutputToExcel() { 
        if(qiyeUserName == null) qiyeUserName = "";
        if(qiyeName == null) qiyeName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        List<Qiye> qiyeList = qiyeDAO.QueryQiyeInfo(qiyeUserName,qiyeName,qiyePropertyObj,qiyeProfessionObj,connectPerson,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Qiye��Ϣ��¼"; 
        String[] headers = { "��ҵ�˺�","��ҵ����","��ҵ����","��ҵ����","��ҵ��ҵ","��ҵ��ģ","��ϵ��","��ϵ�绰","��ҵ����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Qiye.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯQiye��Ϣ*/
    public String FrontQueryQiye() {
        if(currentPage == 0) currentPage = 1;
        if(qiyeUserName == null) qiyeUserName = "";
        if(qiyeName == null) qiyeName = "";
        if(connectPerson == null) connectPerson = "";
        if(telephone == null) telephone = "";
        List<Qiye> qiyeList = qiyeDAO.QueryQiyeInfo(qiyeUserName, qiyeName, qiyePropertyObj, qiyeProfessionObj, connectPerson, telephone, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        qiyeDAO.CalculateTotalPageAndRecordNumber(qiyeUserName, qiyeName, qiyePropertyObj, qiyeProfessionObj, connectPerson, telephone);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = qiyeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Qiye��Ϣ*/
    public String ModifyQiyeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������qiyeUserName��ȡQiye����*/
        Qiye qiye = qiyeDAO.GetQiyeByQiyeUserName(qiyeUserName);

        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryAllQiyePropertyInfo();
        ctx.put("qiyePropertyList", qiyePropertyList);
        List<QiyeProfession> qiyeProfessionList = qiyeProfessionDAO.QueryAllQiyeProfessionInfo();
        ctx.put("qiyeProfessionList", qiyeProfessionList);
        ctx.put("qiye",  qiye);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Qiye��Ϣ*/
    public String FrontShowQiyeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������qiyeUserName��ȡQiye����*/
        Qiye qiye = qiyeDAO.GetQiyeByQiyeUserName(qiyeUserName);

        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryAllQiyePropertyInfo();
        ctx.put("qiyePropertyList", qiyePropertyList);
        List<QiyeProfession> qiyeProfessionList = qiyeProfessionDAO.QueryAllQiyeProfessionInfo();
        ctx.put("qiyeProfessionList", qiyeProfessionList);
        ctx.put("qiye",  qiye);
        return "front_show_view";
    }

    /*�����޸�Qiye��Ϣ*/
    public String ModifyQiye() {
        ActionContext ctx = ActionContext.getContext();
        try {
            QiyeProperty qiyePropertyObj = qiyePropertyDAO.GetQiyePropertyById(qiye.getQiyePropertyObj().getId());
            qiye.setQiyePropertyObj(qiyePropertyObj);
            QiyeProfession qiyeProfessionObj = qiyeProfessionDAO.GetQiyeProfessionById(qiye.getQiyeProfessionObj().getId());
            qiye.setQiyeProfessionObj(qiyeProfessionObj);
            qiyeDAO.UpdateQiye(qiye);
            ctx.put("message",  java.net.URLEncoder.encode("Qiye��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Qiye��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Qiye��Ϣ*/
    public String DeleteQiye() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            qiyeDAO.DeleteQiye(qiyeUserName);
            ctx.put("message",  java.net.URLEncoder.encode("Qiyeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Qiyeɾ��ʧ��!"));
            return "error";
        }
    }

}
