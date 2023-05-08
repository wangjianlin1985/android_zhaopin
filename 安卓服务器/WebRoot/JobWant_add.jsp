<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
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
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加求职</TITLE> 
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
    var positionName = document.getElementById("jobWant.positionName").value;
    if(positionName=="") {
        alert('请输入职位名称!');
        return false;
    }
    var salary = document.getElementById("jobWant.salary").value;
    if(salary=="") {
        alert('请输入期望薪资!');
        return false;
    }
    var workCity = document.getElementById("jobWant.workCity").value;
    if(workCity=="") {
        alert('请输入工作地点!');
        return false;
    }
    var addTime = document.getElementById("jobWant.addTime").value;
    if(addTime=="") {
        alert('请输入发布时间!');
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
    <s:form action="JobWant/JobWant_AddJobWant.action" method="post" id="jobWantAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>职位分类:</td>
    <td width=70%>
      <select name="jobWant.jobTypeObj.jobTypeId">
      <%
        for(JobType jobType:jobTypeList) {
      %>
          <option value='<%=jobType.getJobTypeId() %>'><%=jobType.getTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>求职专业:</td>
    <td width=70%>
      <select name="jobWant.specialObj.specialId">
      <%
        for(SpecialInfo specialInfo:specialInfoList) {
      %>
          <option value='<%=specialInfo.getSpecialId() %>'><%=specialInfo.getSpecialName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>职位名称:</td>
    <td width=70%><input id="jobWant.positionName" name="jobWant.positionName" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>期望薪资:</td>
    <td width=70%><input id="jobWant.salary" name="jobWant.salary" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>工作地点:</td>
    <td width=70%><input id="jobWant.workCity" name="jobWant.workCity" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>备注说明:</td>
    <td width=70%><textarea id="jobWant.wantMemo" name="jobWant.wantMemo" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>求职人:</td>
    <td width=70%>
      <select name="jobWant.userObj.user_name">
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
    <td width=30%>发布时间:</td>
    <td width=70%><input id="jobWant.addTime" name="jobWant.addTime" type="text" size="20" /></td>
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
