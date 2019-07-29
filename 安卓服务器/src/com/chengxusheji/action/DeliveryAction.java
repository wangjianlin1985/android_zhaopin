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
import com.chengxusheji.dao.DeliveryDAO;
import com.chengxusheji.domain.Delivery;
import com.chengxusheji.dao.JobDAO;
import com.chengxusheji.domain.Job;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.dao.DeliveryStateDAO;
import com.chengxusheji.domain.DeliveryState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class DeliveryAction extends BaseAction {

    /*界面层需要查询的属性: 投递的职位*/
    private Job jobObj;
    public void setJobObj(Job jobObj) {
        this.jobObj = jobObj;
    }
    public Job getJobObj() {
        return this.jobObj;
    }

    /*界面层需要查询的属性: 投递人*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 投递时间*/
    private String deliveryTime;
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    public String getDeliveryTime() {
        return this.deliveryTime;
    }

    /*界面层需要查询的属性: 投递状态*/
    private DeliveryState stateObj;
    public void setStateObj(DeliveryState stateObj) {
        this.stateObj = stateObj;
    }
    public DeliveryState getStateObj() {
        return this.stateObj;
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

    private int deliveryId;
    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }
    public int getDeliveryId() {
        return deliveryId;
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
    @Resource JobDAO jobDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource DeliveryStateDAO deliveryStateDAO;
    @Resource DeliveryDAO deliveryDAO;

    /*待操作的Delivery对象*/
    private Delivery delivery;
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    public Delivery getDelivery() {
        return this.delivery;
    }

    /*跳转到添加Delivery视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Job信息*/
        List<Job> jobList = jobDAO.QueryAllJobInfo();
        ctx.put("jobList", jobList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*查询所有的DeliveryState信息*/
        List<DeliveryState> deliveryStateList = deliveryStateDAO.QueryAllDeliveryStateInfo();
        ctx.put("deliveryStateList", deliveryStateList);
        return "add_view";
    }

    /*添加Delivery信息*/
    @SuppressWarnings("deprecation")
    public String AddDelivery() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Job jobObj = jobDAO.GetJobByJobId(delivery.getJobObj().getJobId());
            delivery.setJobObj(jobObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(delivery.getUserObj().getUser_name());
            delivery.setUserObj(userObj);
            DeliveryState stateObj = deliveryStateDAO.GetDeliveryStateByStateId(delivery.getStateObj().getStateId());
            delivery.setStateObj(stateObj);
            deliveryDAO.AddDelivery(delivery);
            ctx.put("message",  java.net.URLEncoder.encode("Delivery添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Delivery添加失败!"));
            return "error";
        }
    }

    /*查询Delivery信息*/
    public String QueryDelivery() {
        if(currentPage == 0) currentPage = 1;
        if(deliveryTime == null) deliveryTime = "";
        List<Delivery> deliveryList = deliveryDAO.QueryDeliveryInfo(jobObj, userObj, deliveryTime, stateObj, currentPage);
        /*计算总的页数和总的记录数*/
        deliveryDAO.CalculateTotalPageAndRecordNumber(jobObj, userObj, deliveryTime, stateObj);
        /*获取到总的页码数目*/
        totalPage = deliveryDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = deliveryDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("deliveryList",  deliveryList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("jobObj", jobObj);
        List<Job> jobList = jobDAO.QueryAllJobInfo();
        ctx.put("jobList", jobList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("deliveryTime", deliveryTime);
        ctx.put("stateObj", stateObj);
        List<DeliveryState> deliveryStateList = deliveryStateDAO.QueryAllDeliveryStateInfo();
        ctx.put("deliveryStateList", deliveryStateList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryDeliveryOutputToExcel() { 
        if(deliveryTime == null) deliveryTime = "";
        List<Delivery> deliveryList = deliveryDAO.QueryDeliveryInfo(jobObj,userObj,deliveryTime,stateObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Delivery信息记录"; 
        String[] headers = { "投递id","投递的职位","投递人","投递时间","投递状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<deliveryList.size();i++) {
        	Delivery delivery = deliveryList.get(i); 
        	dataset.add(new String[]{delivery.getDeliveryId() + "",delivery.getJobObj().getPositionName(),
delivery.getUserObj().getName(),
delivery.getDeliveryTime(),delivery.getStateObj().getStateName()
});
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
			response.setHeader("Content-disposition","attachment; filename="+"Delivery.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Delivery信息*/
    public String FrontQueryDelivery() {
        if(currentPage == 0) currentPage = 1;
        if(deliveryTime == null) deliveryTime = "";
        List<Delivery> deliveryList = deliveryDAO.QueryDeliveryInfo(jobObj, userObj, deliveryTime, stateObj, currentPage);
        /*计算总的页数和总的记录数*/
        deliveryDAO.CalculateTotalPageAndRecordNumber(jobObj, userObj, deliveryTime, stateObj);
        /*获取到总的页码数目*/
        totalPage = deliveryDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = deliveryDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("deliveryList",  deliveryList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("jobObj", jobObj);
        List<Job> jobList = jobDAO.QueryAllJobInfo();
        ctx.put("jobList", jobList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("deliveryTime", deliveryTime);
        ctx.put("stateObj", stateObj);
        List<DeliveryState> deliveryStateList = deliveryStateDAO.QueryAllDeliveryStateInfo();
        ctx.put("deliveryStateList", deliveryStateList);
        return "front_query_view";
    }

    /*查询要修改的Delivery信息*/
    public String ModifyDeliveryQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键deliveryId获取Delivery对象*/
        Delivery delivery = deliveryDAO.GetDeliveryByDeliveryId(deliveryId);

        List<Job> jobList = jobDAO.QueryAllJobInfo();
        ctx.put("jobList", jobList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<DeliveryState> deliveryStateList = deliveryStateDAO.QueryAllDeliveryStateInfo();
        ctx.put("deliveryStateList", deliveryStateList);
        ctx.put("delivery",  delivery);
        return "modify_view";
    }

    /*查询要修改的Delivery信息*/
    public String FrontShowDeliveryQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键deliveryId获取Delivery对象*/
        Delivery delivery = deliveryDAO.GetDeliveryByDeliveryId(deliveryId);

        List<Job> jobList = jobDAO.QueryAllJobInfo();
        ctx.put("jobList", jobList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<DeliveryState> deliveryStateList = deliveryStateDAO.QueryAllDeliveryStateInfo();
        ctx.put("deliveryStateList", deliveryStateList);
        ctx.put("delivery",  delivery);
        return "front_show_view";
    }

    /*更新修改Delivery信息*/
    public String ModifyDelivery() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Job jobObj = jobDAO.GetJobByJobId(delivery.getJobObj().getJobId());
            delivery.setJobObj(jobObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(delivery.getUserObj().getUser_name());
            delivery.setUserObj(userObj);
            DeliveryState stateObj = deliveryStateDAO.GetDeliveryStateByStateId(delivery.getStateObj().getStateId());
            delivery.setStateObj(stateObj);
            deliveryDAO.UpdateDelivery(delivery);
            ctx.put("message",  java.net.URLEncoder.encode("Delivery信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Delivery信息更新失败!"));
            return "error";
       }
   }

    /*删除Delivery信息*/
    public String DeleteDelivery() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            deliveryDAO.DeleteDelivery(deliveryId);
            ctx.put("message",  java.net.URLEncoder.encode("Delivery删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Delivery删除失败!"));
            return "error";
        }
    }

}
