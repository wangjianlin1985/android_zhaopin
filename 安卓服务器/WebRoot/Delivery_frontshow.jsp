<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Delivery" %>
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
    Delivery delivery = (Delivery)request.getAttribute("delivery");

%>
<HTML><HEAD><TITLE>查看职位投递</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>投递id:</td>
    <td width=70%><%=delivery.getDeliveryId() %></td>
  </tr>

  <tr>
    <td width=30%>投递的职位:</td>
    <td width=70%>
      <%=delivery.getJobObj().getPositionName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>投递人:</td>
    <td width=70%>
      <%=delivery.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>投递时间:</td>
    <td width=70%><%=delivery.getDeliveryTime() %></td>
  </tr>

  <tr>
    <td width=30%>投递状态:</td>
    <td width=70%>
      <%=delivery.getStateObj().getStateName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>企业回复:</td>
    <td width=70%><%=delivery.getDeliveryDemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
