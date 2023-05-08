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

    /*�������Ҫ��ѯ������: Ͷ�ݵ�ְλ*/
    private Job jobObj;
    public void setJobObj(Job jobObj) {
        this.jobObj = jobObj;
    }
    public Job getJobObj() {
        return this.jobObj;
    }

    /*�������Ҫ��ѯ������: Ͷ����*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: Ͷ��ʱ��*/
    private String deliveryTime;
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    public String getDeliveryTime() {
        return this.deliveryTime;
    }

    /*�������Ҫ��ѯ������: Ͷ��״̬*/
    private DeliveryState stateObj;
    public void setStateObj(DeliveryState stateObj) {
        this.stateObj = stateObj;
    }
    public DeliveryState getStateObj() {
        return this.stateObj;
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

    private int deliveryId;
    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }
    public int getDeliveryId() {
        return deliveryId;
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
    @Resource JobDAO jobDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource DeliveryStateDAO deliveryStateDAO;
    @Resource DeliveryDAO deliveryDAO;

    /*��������Delivery����*/
    private Delivery delivery;
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    public Delivery getDelivery() {
        return this.delivery;
    }

    /*��ת�����Delivery��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Job��Ϣ*/
        List<Job> jobList = jobDAO.QueryAllJobInfo();
        ctx.put("jobList", jobList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*��ѯ���е�DeliveryState��Ϣ*/
        List<DeliveryState> deliveryStateList = deliveryStateDAO.QueryAllDeliveryStateInfo();
        ctx.put("deliveryStateList", deliveryStateList);
        return "add_view";
    }

    /*���Delivery��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("Delivery��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Delivery���ʧ��!"));
            return "error";
        }
    }

    /*��ѯDelivery��Ϣ*/
    public String QueryDelivery() {
        if(currentPage == 0) currentPage = 1;
        if(deliveryTime == null) deliveryTime = "";
        List<Delivery> deliveryList = deliveryDAO.QueryDeliveryInfo(jobObj, userObj, deliveryTime, stateObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        deliveryDAO.CalculateTotalPageAndRecordNumber(jobObj, userObj, deliveryTime, stateObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = deliveryDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryDeliveryOutputToExcel() { 
        if(deliveryTime == null) deliveryTime = "";
        List<Delivery> deliveryList = deliveryDAO.QueryDeliveryInfo(jobObj,userObj,deliveryTime,stateObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Delivery��Ϣ��¼"; 
        String[] headers = { "Ͷ��id","Ͷ�ݵ�ְλ","Ͷ����","Ͷ��ʱ��","Ͷ��״̬"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Delivery.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯDelivery��Ϣ*/
    public String FrontQueryDelivery() {
        if(currentPage == 0) currentPage = 1;
        if(deliveryTime == null) deliveryTime = "";
        List<Delivery> deliveryList = deliveryDAO.QueryDeliveryInfo(jobObj, userObj, deliveryTime, stateObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        deliveryDAO.CalculateTotalPageAndRecordNumber(jobObj, userObj, deliveryTime, stateObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = deliveryDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Delivery��Ϣ*/
    public String ModifyDeliveryQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������deliveryId��ȡDelivery����*/
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

    /*��ѯҪ�޸ĵ�Delivery��Ϣ*/
    public String FrontShowDeliveryQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������deliveryId��ȡDelivery����*/
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

    /*�����޸�Delivery��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("Delivery��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Delivery��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Delivery��Ϣ*/
    public String DeleteDelivery() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            deliveryDAO.DeleteDelivery(deliveryId);
            ctx.put("message",  java.net.URLEncoder.encode("Deliveryɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Deliveryɾ��ʧ��!"));
            return "error";
        }
    }

}
