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
import com.chengxusheji.dao.NewsClassDAO;
import com.chengxusheji.domain.NewsClass;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class NewsClassAction extends BaseAction {

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

    private int newsClassId;
    public void setNewsClassId(int newsClassId) {
        this.newsClassId = newsClassId;
    }
    public int getNewsClassId() {
        return newsClassId;
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
    @Resource NewsClassDAO newsClassDAO;

    /*��������NewsClass����*/
    private NewsClass newsClass;
    public void setNewsClass(NewsClass newsClass) {
        this.newsClass = newsClass;
    }
    public NewsClass getNewsClass() {
        return this.newsClass;
    }

    /*��ת�����NewsClass��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���NewsClass��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddNewsClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            newsClassDAO.AddNewsClass(newsClass);
            ctx.put("message",  java.net.URLEncoder.encode("NewsClass��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NewsClass���ʧ��!"));
            return "error";
        }
    }

    /*��ѯNewsClass��Ϣ*/
    public String QueryNewsClass() {
        if(currentPage == 0) currentPage = 1;
        List<NewsClass> newsClassList = newsClassDAO.QueryNewsClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        newsClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = newsClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = newsClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("newsClassList",  newsClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryNewsClassOutputToExcel() { 
        List<NewsClass> newsClassList = newsClassDAO.QueryNewsClassInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "NewsClass��Ϣ��¼"; 
        String[] headers = { "���ŷ���id","���ŷ�������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<newsClassList.size();i++) {
        	NewsClass newsClass = newsClassList.get(i); 
        	dataset.add(new String[]{newsClass.getNewsClassId() + "",newsClass.getNewsClassName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"NewsClass.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯNewsClass��Ϣ*/
    public String FrontQueryNewsClass() {
        if(currentPage == 0) currentPage = 1;
        List<NewsClass> newsClassList = newsClassDAO.QueryNewsClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        newsClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = newsClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = newsClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("newsClassList",  newsClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�NewsClass��Ϣ*/
    public String ModifyNewsClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������newsClassId��ȡNewsClass����*/
        NewsClass newsClass = newsClassDAO.GetNewsClassByNewsClassId(newsClassId);

        ctx.put("newsClass",  newsClass);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�NewsClass��Ϣ*/
    public String FrontShowNewsClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������newsClassId��ȡNewsClass����*/
        NewsClass newsClass = newsClassDAO.GetNewsClassByNewsClassId(newsClassId);

        ctx.put("newsClass",  newsClass);
        return "front_show_view";
    }

    /*�����޸�NewsClass��Ϣ*/
    public String ModifyNewsClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            newsClassDAO.UpdateNewsClass(newsClass);
            ctx.put("message",  java.net.URLEncoder.encode("NewsClass��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NewsClass��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��NewsClass��Ϣ*/
    public String DeleteNewsClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            newsClassDAO.DeleteNewsClass(newsClassId);
            ctx.put("message",  java.net.URLEncoder.encode("NewsClassɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NewsClassɾ��ʧ��!"));
            return "error";
        }
    }

}
