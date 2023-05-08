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
import com.chengxusheji.dao.DeliveryStateDAO;
import com.chengxusheji.domain.DeliveryState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class DeliveryStateAction extends BaseAction {

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

    private int stateId;
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    public int getStateId() {
        return stateId;
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
    @Resource DeliveryStateDAO deliveryStateDAO;

    /*��������DeliveryState����*/
    private DeliveryState deliveryState;
    public void setDeliveryState(DeliveryState deliveryState) {
        this.deliveryState = deliveryState;
    }
    public DeliveryState getDeliveryState() {
        return this.deliveryState;
    }

    /*��ת�����DeliveryState��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���DeliveryState��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddDeliveryState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            deliveryStateDAO.AddDeliveryState(deliveryState);
            ctx.put("message",  java.net.URLEncoder.encode("DeliveryState��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DeliveryState���ʧ��!"));
            return "error";
        }
    }

    /*��ѯDeliveryState��Ϣ*/
    public String QueryDeliveryState() {
        if(currentPage == 0) currentPage = 1;
        List<DeliveryState> deliveryStateList = deliveryStateDAO.QueryDeliveryStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        deliveryStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = deliveryStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = deliveryStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("deliveryStateList",  deliveryStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryDeliveryStateOutputToExcel() { 
        List<DeliveryState> deliveryStateList = deliveryStateDAO.QueryDeliveryStateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "DeliveryState��Ϣ��¼"; 
        String[] headers = { "Ͷ��״̬id","Ͷ��״̬"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<deliveryStateList.size();i++) {
        	DeliveryState deliveryState = deliveryStateList.get(i); 
        	dataset.add(new String[]{deliveryState.getStateId() + "",deliveryState.getStateName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"DeliveryState.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯDeliveryState��Ϣ*/
    public String FrontQueryDeliveryState() {
        if(currentPage == 0) currentPage = 1;
        List<DeliveryState> deliveryStateList = deliveryStateDAO.QueryDeliveryStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        deliveryStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = deliveryStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = deliveryStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("deliveryStateList",  deliveryStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�DeliveryState��Ϣ*/
    public String ModifyDeliveryStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stateId��ȡDeliveryState����*/
        DeliveryState deliveryState = deliveryStateDAO.GetDeliveryStateByStateId(stateId);

        ctx.put("deliveryState",  deliveryState);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�DeliveryState��Ϣ*/
    public String FrontShowDeliveryStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stateId��ȡDeliveryState����*/
        DeliveryState deliveryState = deliveryStateDAO.GetDeliveryStateByStateId(stateId);

        ctx.put("deliveryState",  deliveryState);
        return "front_show_view";
    }

    /*�����޸�DeliveryState��Ϣ*/
    public String ModifyDeliveryState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            deliveryStateDAO.UpdateDeliveryState(deliveryState);
            ctx.put("message",  java.net.URLEncoder.encode("DeliveryState��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DeliveryState��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��DeliveryState��Ϣ*/
    public String DeleteDeliveryState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            deliveryStateDAO.DeleteDeliveryState(stateId);
            ctx.put("message",  java.net.URLEncoder.encode("DeliveryStateɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("DeliveryStateɾ��ʧ��!"));
            return "error";
        }
    }

}
