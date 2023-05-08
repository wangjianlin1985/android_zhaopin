<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Job" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.DeliveryState" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Job��Ϣ
    List<Job> jobList = (List<Job>)request.getAttribute("jobList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //��ȡ���е�DeliveryState��Ϣ
    List<DeliveryState> deliveryStateList = (List<DeliveryState>)request.getAttribute("deliveryStateList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>���ְλͶ��</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*��֤��*/
function checkForm() {
    var deliveryTime = document.getElementById("delivery.deliveryTime").value;
    if(deliveryTime=="") {
        alert('������Ͷ��ʱ��!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="Delivery/Delivery_AddDelivery.action" method="post" id="deliveryAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>Ͷ�ݵ�ְλ:</td>
    <td width=70%>
      <select name="delivery.jobObj.jobId">
      <%
        for(Job job:jobList) {
      %>
          <option value='<%=job.getJobId() %>'><%=job.getPositionName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>Ͷ����:</td>
    <td width=70%>
      <select name="delivery.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
      %>
          <option value='<%=userInfo.getUser_name() %>'><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>Ͷ��ʱ��:</td>
    <td width=70%><input id="delivery.deliveryTime" name="delivery.deliveryTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>Ͷ��״̬:</td>
    <td width=70%>
      <select name="delivery.stateObj.stateId">
      <%
        for(DeliveryState deliveryState:deliveryStateList) {
      %>
          <option value='<%=deliveryState.getStateId() %>'><%=deliveryState.getStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ҵ�ظ�:</td>
    <td width=70%><textarea id="delivery.deliveryDemo" name="delivery.deliveryDemo" rows="5" cols="50"></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
