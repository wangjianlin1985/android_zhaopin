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
    @Resource QiyePropertyDAO qiyePropertyDAO;

    /*��������QiyeProperty����*/
    private QiyeProperty qiyeProperty;
    public void setQiyeProperty(QiyeProperty qiyeProperty) {
        this.qiyeProperty = qiyeProperty;
    }
    public QiyeProperty getQiyeProperty() {
        return this.qiyeProperty;
    }

    /*��ת�����QiyeProperty��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���QiyeProperty��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddQiyeProperty() {
        ActionContext ctx = ActionContext.getContext();
        try {
            qiyePropertyDAO.AddQiyeProperty(qiyeProperty);
            ctx.put("message",  java.net.URLEncoder.encode("QiyeProperty��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QiyeProperty���ʧ��!"));
            return "error";
        }
    }

    /*��ѯQiyeProperty��Ϣ*/
    public String QueryQiyeProperty() {
        if(currentPage == 0) currentPage = 1;
        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryQiyePropertyInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        qiyePropertyDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = qiyePropertyDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = qiyePropertyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("qiyePropertyList",  qiyePropertyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryQiyePropertyOutputToExcel() { 
        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryQiyePropertyInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "QiyeProperty��Ϣ��¼"; 
        String[] headers = { "��¼���","��ҵ��������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"QiyeProperty.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯQiyeProperty��Ϣ*/
    public String FrontQueryQiyeProperty() {
        if(currentPage == 0) currentPage = 1;
        List<QiyeProperty> qiyePropertyList = qiyePropertyDAO.QueryQiyePropertyInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        qiyePropertyDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = qiyePropertyDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = qiyePropertyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("qiyePropertyList",  qiyePropertyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�QiyeProperty��Ϣ*/
    public String ModifyQiyePropertyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡQiyeProperty����*/
        QiyeProperty qiyeProperty = qiyePropertyDAO.GetQiyePropertyById(id);

        ctx.put("qiyeProperty",  qiyeProperty);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�QiyeProperty��Ϣ*/
    public String FrontShowQiyePropertyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡQiyeProperty����*/
        QiyeProperty qiyeProperty = qiyePropertyDAO.GetQiyePropertyById(id);

        ctx.put("qiyeProperty",  qiyeProperty);
        return "front_show_view";
    }

    /*�����޸�QiyeProperty��Ϣ*/
    public String ModifyQiyeProperty() {
        ActionContext ctx = ActionContext.getContext();
        try {
            qiyePropertyDAO.UpdateQiyeProperty(qiyeProperty);
            ctx.put("message",  java.net.URLEncoder.encode("QiyeProperty��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QiyeProperty��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��QiyeProperty��Ϣ*/
    public String DeleteQiyeProperty() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            qiyePropertyDAO.DeleteQiyeProperty(id);
            ctx.put("message",  java.net.URLEncoder.encode("QiyePropertyɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QiyePropertyɾ��ʧ��!"));
            return "error";
        }
    }

}
