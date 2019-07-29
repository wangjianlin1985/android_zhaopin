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

    private int newsClassId;
    public void setNewsClassId(int newsClassId) {
        this.newsClassId = newsClassId;
    }
    public int getNewsClassId() {
        return newsClassId;
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
    @Resource NewsClassDAO newsClassDAO;

    /*待操作的NewsClass对象*/
    private NewsClass newsClass;
    public void setNewsClass(NewsClass newsClass) {
        this.newsClass = newsClass;
    }
    public NewsClass getNewsClass() {
        return this.newsClass;
    }

    /*跳转到添加NewsClass视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加NewsClass信息*/
    @SuppressWarnings("deprecation")
    public String AddNewsClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            newsClassDAO.AddNewsClass(newsClass);
            ctx.put("message",  java.net.URLEncoder.encode("NewsClass添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NewsClass添加失败!"));
            return "error";
        }
    }

    /*查询NewsClass信息*/
    public String QueryNewsClass() {
        if(currentPage == 0) currentPage = 1;
        List<NewsClass> newsClassList = newsClassDAO.QueryNewsClassInfo(currentPage);
        /*计算总的页数和总的记录数*/
        newsClassDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = newsClassDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = newsClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("newsClassList",  newsClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryNewsClassOutputToExcel() { 
        List<NewsClass> newsClassList = newsClassDAO.QueryNewsClassInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "NewsClass信息记录"; 
        String[] headers = { "新闻分类id","新闻分类名称"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"NewsClass.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询NewsClass信息*/
    public String FrontQueryNewsClass() {
        if(currentPage == 0) currentPage = 1;
        List<NewsClass> newsClassList = newsClassDAO.QueryNewsClassInfo(currentPage);
        /*计算总的页数和总的记录数*/
        newsClassDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = newsClassDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = newsClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("newsClassList",  newsClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的NewsClass信息*/
    public String ModifyNewsClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键newsClassId获取NewsClass对象*/
        NewsClass newsClass = newsClassDAO.GetNewsClassByNewsClassId(newsClassId);

        ctx.put("newsClass",  newsClass);
        return "modify_view";
    }

    /*查询要修改的NewsClass信息*/
    public String FrontShowNewsClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键newsClassId获取NewsClass对象*/
        NewsClass newsClass = newsClassDAO.GetNewsClassByNewsClassId(newsClassId);

        ctx.put("newsClass",  newsClass);
        return "front_show_view";
    }

    /*更新修改NewsClass信息*/
    public String ModifyNewsClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            newsClassDAO.UpdateNewsClass(newsClass);
            ctx.put("message",  java.net.URLEncoder.encode("NewsClass信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NewsClass信息更新失败!"));
            return "error";
       }
   }

    /*删除NewsClass信息*/
    public String DeleteNewsClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            newsClassDAO.DeleteNewsClass(newsClassId);
            ctx.put("message",  java.net.URLEncoder.encode("NewsClass删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NewsClass删除失败!"));
            return "error";
        }
    }

}
