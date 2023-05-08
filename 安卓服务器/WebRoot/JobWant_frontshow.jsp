<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.JobWant" %>
<%@ page import="com.chengxusheji.domain.JobType" %>
<%@ page import="com.chengxusheji.domain.SpecialInfo" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的JobType信息
    List<JobType> jobTypeList = (List<JobType>)request.getAttribute("jobTypeList");
    //获取所有的SpecialInfo信息
    List<SpecialInfo> specialInfoList = (List<SpecialInfo>)request.getAttribute("specialInfoList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    JobWant jobWant = (JobWant)request.getAttribute("jobWant");

%>
<HTML><HEAD><TITLE>查看求职</TITLE>
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
    <td width=30%>记录id:</td>
    <td width=70%><%=jobWant.getWantId() %></td>
  </tr>

  <tr>
    <td width=30%>职位分类:</td>
    <td width=70%>
      <%=jobWant.getJobTypeObj().getTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>求职专业:</td>
    <td width=70%>
      <%=jobWant.getSpecialObj().getSpecialName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>职位名称:</td>
    <td width=70%><%=jobWant.getPositionName() %></td>
  </tr>

  <tr>
    <td width=30%>期望薪资:</td>
    <td width=70%><%=jobWant.getSalary() %></td>
  </tr>

  <tr>
    <td width=30%>工作地点:</td>
    <td width=70%><%=jobWant.getWorkCity() %></td>
  </tr>

  <tr>
    <td width=30%>备注说明:</td>
    <td width=70%><%=jobWant.getWantMemo() %></td>
  </tr>

  <tr>
    <td width=30%>求职人:</td>
    <td width=70%>
      <%=jobWant.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>发布时间:</td>
    <td width=70%><%=jobWant.getAddTime() %></td>
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
