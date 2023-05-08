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
import com.chengxusheji.dao.NewsDAO;
import com.chengxusheji.domain.News;
import com.chengxusheji.dao.NewsClassDAO;
import com.chengxusheji.domain.NewsClass;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class NewsAction extends BaseAction {

    /*�������Ҫ��ѯ������: ���ŷ���*/
    private NewsClass newsClassObj;
    public void setNewsClassObj(NewsClass newsClassObj) {
        this.newsClassObj = newsClassObj;
    }
    public NewsClass getNewsClassObj() {
        return this.newsClassObj;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String publishDate;
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishDate() {
        return this.publishDate;
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

    private int newsId;
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }
    public int getNewsId() {
        return newsId;
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
    @Resource NewsDAO newsDAO;

    /*��������News����*/
    private News news;
    public void setNews(News news) {
        this.news = news;
    }
    public News getNews() {
        return this.news;
    }

    /*��ת�����News��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�NewsClass��Ϣ*/
        List<NewsClass> newsClassList = newsClassDAO.QueryAllNewsClassInfo();
        ctx.put("newsClassList", newsClassList);
        return "add_view";
    }

    /*���News��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddNews() {
        ActionContext ctx = ActionContext.getContext();
        try {
            NewsClass newsClassObj = newsClassDAO.GetNewsClassByNewsClassId(news.getNewsClassObj().getNewsClassId());
            news.setNewsClassObj(newsClassObj);
            newsDAO.AddNews(news);
            ctx.put("message",  java.net.URLEncoder.encode("News��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("News���ʧ��!"));
            return "error";
        }
    }

    /*��ѯNews��Ϣ*/
    public String QueryNews() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<News> newsList = newsDAO.QueryNewsInfo(newsClassObj, title, publishDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        newsDAO.CalculateTotalPageAndRecordNumber(newsClassObj, title, publishDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = newsDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = newsDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("newsList",  newsList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("newsClassObj", newsClassObj);
        List<NewsClass> newsClassList = newsClassDAO.QueryAllNewsClassInfo();
        ctx.put("newsClassList", newsClassList);
        ctx.put("title", title);
        ctx.put("publishDate", publishDate);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryNewsOutputToExcel() { 
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<News> newsList = newsDAO.QueryNewsInfo(newsClassObj,title,publishDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "News��Ϣ��¼"; 
        String[] headers = { "����id","���ŷ���","����","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<newsList.size();i++) {
        	News news = newsList.get(i); 
        	dataset.add(new String[]{news.getNewsId() + "",news.getNewsClassObj().getNewsClassName(),
news.getTitle(),news.getPublishDate()});
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
			response.setHeader("Content-disposition","attachment; filename="+"News.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯNews��Ϣ*/
    public String FrontQueryNews() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<News> newsList = newsDAO.QueryNewsInfo(newsClassObj, title, publishDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        newsDAO.CalculateTotalPageAndRecordNumber(newsClassObj, title, publishDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = newsDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = newsDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("newsList",  newsList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("newsClassObj", newsClassObj);
        List<NewsClass> newsClassList = newsClassDAO.QueryAllNewsClassInfo();
        ctx.put("newsClassList", newsClassList);
        ctx.put("title", title);
        ctx.put("publishDate", publishDate);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�News��Ϣ*/
    public String ModifyNewsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������newsId��ȡNews����*/
        News news = newsDAO.GetNewsByNewsId(newsId);

        List<NewsClass> newsClassList = newsClassDAO.QueryAllNewsClassInfo();
        ctx.put("newsClassList", newsClassList);
        ctx.put("news",  news);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�News��Ϣ*/
    public String FrontShowNewsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������newsId��ȡNews����*/
        News news = newsDAO.GetNewsByNewsId(newsId);

        List<NewsClass> newsClassList = newsClassDAO.QueryAllNewsClassInfo();
        ctx.put("newsClassList", newsClassList);
        ctx.put("news",  news);
        return "front_show_view";
    }

    /*�����޸�News��Ϣ*/
    public String ModifyNews() {
        ActionContext ctx = ActionContext.getContext();
        try {
            NewsClass newsClassObj = newsClassDAO.GetNewsClassByNewsClassId(news.getNewsClassObj().getNewsClassId());
            news.setNewsClassObj(newsClassObj);
            newsDAO.UpdateNews(news);
            ctx.put("message",  java.net.URLEncoder.encode("News��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("News��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��News��Ϣ*/
    public String DeleteNews() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            newsDAO.DeleteNews(newsId);
            ctx.put("message",  java.net.URLEncoder.encode("Newsɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Newsɾ��ʧ��!"));
            return "error";
        }
    }

}
