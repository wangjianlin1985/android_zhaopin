<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.JobType" %>
<%@ page import="com.chengxusheji.domain.SpecialInfo" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�JobType��Ϣ
    List<JobType> jobTypeList = (List<JobType>)request.getAttribute("jobTypeList");
    //��ȡ���е�SpecialInfo��Ϣ
    List<SpecialInfo> specialInfoList = (List<SpecialInfo>)request.getAttribute("specialInfoList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�����ְ</TITLE> 
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
    var positionName = document.getElementById("jobWant.positionName").value;
    if(positionName=="") {
        alert('������ְλ����!');
        return false;
    }
    var salary = document.getElementById("jobWant.salary").value;
    if(salary=="") {
        alert('����������н��!');
        return false;
    }
    var workCity = document.getElementById("jobWant.workCity").value;
    if(workCity=="") {
        alert('�����빤���ص�!');
        return false;
    }
    var addTime = document.getElementById("jobWant.addTime").value;
    if(addTime=="") {
        alert('�����뷢��ʱ��!');
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
    <td width=30%>ְλ����:</td>
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
    <td width=30%>��ְרҵ:</td>
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
    <td width=30%>ְλ����:</td>
    <td width=70%><input id="jobWant.positionName" name="jobWant.positionName" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>����н��:</td>
    <td width=70%><input id="jobWant.salary" name="jobWant.salary" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�����ص�:</td>
    <td width=70%><input id="jobWant.workCity" name="jobWant.workCity" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ע˵��:</td>
    <td width=70%><textarea id="jobWant.wantMemo" name="jobWant.wantMemo" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>��ְ��:</td>
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
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="jobWant.addTime" name="jobWant.addTime" type="text" size="20" /></td>
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
