<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Job" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.DeliveryState" %>
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
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加职位投递</TITLE> 
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
    <TD align="left" vAlign=top >
    <s:form action="Delivery/Delivery_AddDelivery.action" method="post" id="deliveryAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>投递的职位:</td>
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
    <td width=30%>投递人:</td>
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
    <td width=30%>投递时间:</td>
    <td width=70%><input id="delivery.deliveryTime" name="delivery.deliveryTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>投递状态:</td>
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
    <td width=30%>企业回复:</td>
    <td width=70%><textarea id="delivery.deliveryDemo" name="delivery.deliveryDemo" rows="5" cols="50"></textarea></td>
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
