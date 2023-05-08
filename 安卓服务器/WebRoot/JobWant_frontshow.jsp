<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.JobWant" %>
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
    JobWant jobWant = (JobWant)request.getAttribute("jobWant");

%>
<HTML><HEAD><TITLE>�鿴��ְ</TITLE>
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
    <td width=30%>��¼id:</td>
    <td width=70%><%=jobWant.getWantId() %></td>
  </tr>

  <tr>
    <td width=30%>ְλ����:</td>
    <td width=70%>
      <%=jobWant.getJobTypeObj().getTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ְרҵ:</td>
    <td width=70%>
      <%=jobWant.getSpecialObj().getSpecialName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>ְλ����:</td>
    <td width=70%><%=jobWant.getPositionName() %></td>
  </tr>

  <tr>
    <td width=30%>����н��:</td>
    <td width=70%><%=jobWant.getSalary() %></td>
  </tr>

  <tr>
    <td width=30%>�����ص�:</td>
    <td width=70%><%=jobWant.getWorkCity() %></td>
  </tr>

  <tr>
    <td width=30%>��ע˵��:</td>
    <td width=70%><%=jobWant.getWantMemo() %></td>
  </tr>

  <tr>
    <td width=30%>��ְ��:</td>
    <td width=70%>
      <%=jobWant.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><%=jobWant.getAddTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
