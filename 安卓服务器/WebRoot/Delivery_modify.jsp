<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Delivery" %>
<%@ page import="com.chengxusheji.domain.Job" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.DeliveryState" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Job信息
    List<Job> jobList = (List<Job>)request.getAttribute("jobList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //获取所有的DeliveryState信息
    List<DeliveryState> deliveryStateList = (List<DeliveryState>)request.getAttribute("deliveryStateList");
    Delivery delivery = (Delivery)request.getAttribute("delivery");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改职位投递</TITLE>
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
/*验证表单*/
function checkForm() {
    var deliveryTime = document.getElementById("delivery.deliveryTime").value;
    if(deliveryTime=="") {
        alert('请输入投递时间!');
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
    <TD align="left" vAlign=top ><s:form action="Delivery/Delivery_ModifyDelivery.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>投递id:</td>
    <td width=70%><input id="delivery.deliveryId" name="delivery.deliveryId" type="text" value="<%=delivery.getDeliveryId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>投递的职位:</td>
    <td width=70%>
      <select name="delivery.jobObj.jobId">
      <%
        for(Job job:jobList) {
          String selected = "";
          if(job.getJobId() == delivery.getJobObj().getJobId())
            selected = "selected";
      %>
          <option value='<%=job.getJobId() %>' <%=selected %>><%=job.getPositionName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>投递人:</td>
    <td width=70%>
      <select name="delivery.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(delivery.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>投递时间:</td>
    <td width=70%><input id="delivery.deliveryTime" name="delivery.deliveryTime" type="text" size="20" value='<%=delivery.getDeliveryTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>投递状态:</td>
    <td width=70%>
      <select name="delivery.stateObj.stateId">
      <%
        for(DeliveryState deliveryState:deliveryStateList) {
          String selected = "";
          if(deliveryState.getStateId() == delivery.getStateObj().getStateId())
            selected = "selected";
      %>
          <option value='<%=deliveryState.getStateId() %>' <%=selected %>><%=deliveryState.getStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>企业回复:</td>
    <td width=70%><textarea id="delivery.deliveryDemo" name="delivery.deliveryDemo" rows=5 cols=50><%=delivery.getDeliveryDemo() %></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
