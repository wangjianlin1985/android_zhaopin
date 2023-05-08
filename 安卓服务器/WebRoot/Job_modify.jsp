<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Job" %>
<%@ page import="com.chengxusheji.domain.Qiye" %>
<%@ page import="com.chengxusheji.domain.JobType" %>
<%@ page import="com.chengxusheji.domain.SpecialInfo" %>
<%@ page import="com.chengxusheji.domain.SchoolRecord" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
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

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�ְλ</TITLE>
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
    var positionName = document.getElementById("job.positionName").value;
    if(positionName=="") {
        alert('��������Ƹְλ!');
        return false;
    }
    var personNum = document.getElementById("job.personNum").value;
    if(personNum=="") {
        alert('��������Ƹ����!');
        return false;
    }
    var city = document.getElementById("job.city").value;
    if(city=="") {
        alert('���������ڳ���!');
        return false;
    }
    var salary = document.getElementById("job.salary").value;
    if(salary=="") {
        alert('������н�ʴ���!');
        return false;
    }
    var workYears = document.getElementById("job.workYears").value;
    if(workYears=="") {
        alert('�����빤������!');
        return false;
    }
    var workAddress = document.getElementById("job.workAddress").value;
    if(workAddress=="") {
        alert('�����빤����ַ!');
        return false;
    }
    var welfare = document.getElementById("job.welfare").value;
    if(welfare=="") {
        alert('�����븣������!');
        return false;
    }
    var positionDesc = document.getElementById("job.positionDesc").value;
    if(positionDesc=="") {
        alert('�������λҪ��!');
        return false;
    }
    var connectPerson = document.getElementById("job.connectPerson").value;
    if(connectPerson=="") {
        alert('��������ϵ��!');
        return false;
    }
    var telephone = document.getElementById("job.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�绰!');
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
    <TD align="left" vAlign=top ><s:form action="Job/Job_ModifyJob.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>ְλid:</td>
    <td width=70%><input id="job.jobId" name="job.jobId" type="text" value="<%=job.getJobId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��Ƹ��ҵ:</td>
    <td width=70%>
      <select name="job.qiyeObj.qiyeUserName">
      <%
        for(Qiye qiye:qiyeList) {
          String selected = "";
          if(qiye.getQiyeUserName().equals(job.getQiyeObj().getQiyeUserName()))
            selected = "selected";
      %>
          <option value='<%=qiye.getQiyeUserName() %>' <%=selected %>><%=qiye.getQiyeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ƹְλ:</td>
    <td width=70%><input id="job.positionName" name="job.positionName" type="text" size="40" value='<%=job.getPositionName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>ְλ����:</td>
    <td width=70%>
      <select name="job.jobTypeObj.jobTypeId">
      <%
        for(JobType jobType:jobTypeList) {
          String selected = "";
          if(jobType.getJobTypeId() == job.getJobTypeObj().getJobTypeId())
            selected = "selected";
      %>
          <option value='<%=jobType.getJobTypeId() %>' <%=selected %>><%=jobType.getTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����רҵ:</td>
    <td width=70%>
      <select name="job.specialObj.specialId">
      <%
        for(SpecialInfo specialInfo:specialInfoList) {
          String selected = "";
          if(specialInfo.getSpecialId() == job.getSpecialObj().getSpecialId())
            selected = "selected";
      %>
          <option value='<%=specialInfo.getSpecialId() %>' <%=selected %>><%=specialInfo.getSpecialName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ƹ����:</td>
    <td width=70%><input id="job.personNum" name="job.personNum" type="text" size="20" value='<%=job.getPersonNum() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���ڳ���:</td>
    <td width=70%><input id="job.city" name="job.city" type="text" size="20" value='<%=job.getCity() %>'/></td>
  </tr>

  <tr>
    <td width=30%>н�ʴ���:</td>
    <td width=70%><input id="job.salary" name="job.salary" type="text" size="20" value='<%=job.getSalary() %>'/></td>
  </tr>

  <tr>
    <td width=30%>ѧ��Ҫ��:</td>
    <td width=70%>
      <select name="job.schoolRecordObj.id">
      <%
        for(SchoolRecord schoolRecord:schoolRecordList) {
          String selected = "";
          if(schoolRecord.getId() == job.getSchoolRecordObj().getId())
            selected = "selected";
      %>
          <option value='<%=schoolRecord.getId() %>' <%=selected %>><%=schoolRecord.getSchooRecordName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="job.workYears" name="job.workYears" type="text" size="20" value='<%=job.getWorkYears() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������ַ:</td>
    <td width=70%><input id="job.workAddress" name="job.workAddress" type="text" size="20" value='<%=job.getWorkAddress() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><textarea id="job.welfare" name="job.welfare" rows=5 cols=50><%=job.getWelfare() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>��λҪ��:</td>
    <td width=70%><textarea id="job.positionDesc" name="job.positionDesc" rows=5 cols=50><%=job.getPositionDesc() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>��ϵ��:</td>
    <td width=70%><input id="job.connectPerson" name="job.connectPerson" type="text" size="20" value='<%=job.getConnectPerson() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="job.telephone" name="job.telephone" type="text" size="20" value='<%=job.getTelephone() %>'/></td>
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
