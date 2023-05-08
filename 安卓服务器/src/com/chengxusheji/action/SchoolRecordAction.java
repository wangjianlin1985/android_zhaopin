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

    private int id;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
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
    @Resource SchoolRecordDAO schoolRecordDAO;

    /*��������SchoolRecord����*/
    private SchoolRecord schoolRecord;
    public void setSchoolRecord(SchoolRecord schoolRecord) {
        this.schoolRecord = schoolRecord;
    }
    public SchoolRecord getSchoolRecord() {
        return this.schoolRecord;
    }

    /*��ת�����SchoolRecord��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���SchoolRecord��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSchoolRecord() {
        ActionContext ctx = ActionContext.getContext();
        try {
            schoolRecordDAO.AddSchoolRecord(schoolRecord);
            ctx.put("message",  java.net.URLEncoder.encode("SchoolRecord��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SchoolRecord���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSchoolRecord��Ϣ*/
    public String QuerySchoolRecord() {
        if(currentPage == 0) currentPage = 1;
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QuerySchoolRecordInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        schoolRecordDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = schoolRecordDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = schoolRecordDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("schoolRecordList",  schoolRecordList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QuerySchoolRecordOutputToExcel() { 
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QuerySchoolRecordInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SchoolRecord��Ϣ��¼"; 
        String[] headers = { "��¼���","ѧ������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"SchoolRecord.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯSchoolRecord��Ϣ*/
    public String FrontQuerySchoolRecord() {
        if(currentPage == 0) currentPage = 1;
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QuerySchoolRecordInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        schoolRecordDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = schoolRecordDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = schoolRecordDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("schoolRecordList",  schoolRecordList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�SchoolRecord��Ϣ*/
    public String ModifySchoolRecordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡSchoolRecord����*/
        SchoolRecord schoolRecord = schoolRecordDAO.GetSchoolRecordById(id);

        ctx.put("schoolRecord",  schoolRecord);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�SchoolRecord��Ϣ*/
    public String FrontShowSchoolRecordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡSchoolRecord����*/
        SchoolRecord schoolRecord = schoolRecordDAO.GetSchoolRecordById(id);

        ctx.put("schoolRecord",  schoolRecord);
        return "front_show_view";
    }

    /*�����޸�SchoolRecord��Ϣ*/
    public String ModifySchoolRecord() {
        ActionContext ctx = ActionContext.getContext();
        try {
            schoolRecordDAO.UpdateSchoolRecord(schoolRecord);
            ctx.put("message",  java.net.URLEncoder.encode("SchoolRecord��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SchoolRecord��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SchoolRecord��Ϣ*/
    public String DeleteSchoolRecord() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            schoolRecordDAO.DeleteSchoolRecord(id);
            ctx.put("message",  java.net.URLEncoder.encode("SchoolRecordɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SchoolRecordɾ��ʧ��!"));
            return "error";
        }
    }

}
