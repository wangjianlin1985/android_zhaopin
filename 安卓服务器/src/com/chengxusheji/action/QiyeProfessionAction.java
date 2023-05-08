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
import com.chengxusheji.dao.QiyeProfessionDAO;
import com.chengxusheji.domain.QiyeProfession;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class QiyeProfessionAction extends BaseAction {

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
    @Resource QiyeProfessionDAO qiyeProfessionDAO;

    /*��������QiyeProfession����*/
    private QiyeProfession qiyeProfession;
    public void setQiyeProfession(QiyeProfession qiyeProfession) {
        this.qiyeProfession = qiyeProfession;
    }
    public QiyeProfession getQiyeProfession() {
        return this.qiyeProfession;
    }

    /*��ת�����QiyeProfession��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���QiyeProfession��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddQiyeProfession() {
        ActionContext ctx = ActionContext.getContext();
        try {
            qiyeProfessionDAO.AddQiyeProfession(qiyeProfession);
            ctx.put("message",  java.net.URLEncoder.encode("QiyeProfession��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QiyeProfession���ʧ��!"));
            return "error";
        }
    }

    /*��ѯQiyeProfession��Ϣ*/
    public String QueryQiyeProfession() {
        if(currentPage == 0) currentPage = 1;
        List<QiyeProfession> qiyeProfessionList = qiyeProfessionDAO.QueryQiyeProfessionInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        qiyeProfessionDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = qiyeProfessionDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = qiyeProfessionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("qiyeProfessionList",  qiyeProfessionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryQiyeProfessionOutputToExcel() { 
        List<QiyeProfession> qiyeProfessionList = qiyeProfessionDAO.QueryQiyeProfessionInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "QiyeProfession��Ϣ��¼"; 
        String[] headers = { "��¼���","��ҵ����"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<qiyeProfessionList.size();i++) {
        	QiyeProfession qiyeProfession = qiyeProfessionList.get(i); 
        	dataset.add(new String[]{qiyeProfession.getId() + "",qiyeProfession.getProfessionName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"QiyeProfession.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯQiyeProfession��Ϣ*/
    public String FrontQueryQiyeProfession() {
        if(currentPage == 0) currentPage = 1;
        List<QiyeProfession> qiyeProfessionList = qiyeProfessionDAO.QueryQiyeProfessionInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        qiyeProfessionDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = qiyeProfessionDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = qiyeProfessionDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("qiyeProfessionList",  qiyeProfessionList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�QiyeProfession��Ϣ*/
    public String ModifyQiyeProfessionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡQiyeProfession����*/
        QiyeProfession qiyeProfession = qiyeProfessionDAO.GetQiyeProfessionById(id);

        ctx.put("qiyeProfession",  qiyeProfession);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�QiyeProfession��Ϣ*/
    public String FrontShowQiyeProfessionQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡQiyeProfession����*/
        QiyeProfession qiyeProfession = qiyeProfessionDAO.GetQiyeProfessionById(id);

        ctx.put("qiyeProfession",  qiyeProfession);
        return "front_show_view";
    }

    /*�����޸�QiyeProfession��Ϣ*/
    public String ModifyQiyeProfession() {
        ActionContext ctx = ActionContext.getContext();
        try {
            qiyeProfessionDAO.UpdateQiyeProfession(qiyeProfession);
            ctx.put("message",  java.net.URLEncoder.encode("QiyeProfession��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QiyeProfession��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��QiyeProfession��Ϣ*/
    public String DeleteQiyeProfession() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            qiyeProfessionDAO.DeleteQiyeProfession(id);
            ctx.put("message",  java.net.URLEncoder.encode("QiyeProfessionɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QiyeProfessionɾ��ʧ��!"));
            return "error";
        }
    }

}
