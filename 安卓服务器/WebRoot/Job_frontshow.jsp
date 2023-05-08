<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Job" %>
<%@ page import="com.chengxusheji.domain.Qiye" %>
<%@ page import="com.chengxusheji.domain.JobType" %>
<%@ page import="com.chengxusheji.domain.SpecialInfo" %>
<%@ page import="com.chengxusheji.domain.SchoolRecord" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Qiye��Ϣ
    List<Qiye> qiyeList = (List<Qiye>)request.getAttribute("qiyeList");
    //��ȡ���е�JobType��Ϣ
    List<JobType> jobTypeList = (List<JobType>)request.getAttribute("jobTypeList");
    //��ȡ���е�SpecialInfo��Ϣ
    List<SpecialInfo> specialInfoList = (List<SpecialInfo>)request.getAttribute("specialInfoList");
    //��ȡ���е�SchoolRecord��Ϣ
    List<SchoolRecord> schoolRecordList = (List<SchoolRecord>)request.getAttribute("schoolRecordList");
    Job job = (Job)request.getAttribute("job");

%>
<HTML><HEAD><TITLE>�鿴ְλ</TITLE>
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
    <td width=30%>ְλid:</td>
    <td width=70%><%=job.getJobId() %></td>
  </tr>

  <tr>
    <td width=30%>��Ƹ��ҵ:</td>
    <td width=70%>
      <%=job.getQiyeObj().getQiyeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ƹְλ:</td>
    <td width=70%><%=job.getPositionName() %></td>
  </tr>

  <tr>
    <td width=30%>ְλ����:</td>
    <td width=70%>
      <%=job.getJobTypeObj().getTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>����רҵ:</td>
    <td width=70%>
      <%=job.getSpecialObj().getSpecialName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ƹ����:</td>
    <td width=70%><%=job.getPersonNum() %></td>
  </tr>

  <tr>
    <td width=30%>���ڳ���:</td>
    <td width=70%><%=job.getCity() %></td>
  </tr>

  <tr>
    <td width=30%>н�ʴ���:</td>
    <td width=70%><%=job.getSalary() %></td>
  </tr>

  <tr>
    <td width=30%>ѧ��Ҫ��:</td>
    <td width=70%>
      <%=job.getSchoolRecordObj().getSchooRecordName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><%=job.getWorkYears() %></td>
  </tr>

  <tr>
    <td width=30%>������ַ:</td>
    <td width=70%><%=job.getWorkAddress() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><%=job.getWelfare() %></td>
  </tr>

  <tr>
    <td width=30%>��λҪ��:</td>
    <td width=70%><%=job.getPositionDesc() %></td>
  </tr>

  <tr>
    <td width=30%>��ϵ��:</td>
    <td width=70%><%=job.getConnectPerson() %></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><%=job.getTelephone() %></td>
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
