<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Qiye" %>
<%@ page import="com.chengxusheji.domain.JobType" %>
<%@ page import="com.chengxusheji.domain.SpecialInfo" %>
<%@ page import="com.chengxusheji.domain.SchoolRecord" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Qiye信息
    List<Qiye> qiyeList = (List<Qiye>)request.getAttribute("qiyeList");
    //获取所有的JobType信息
    List<JobType> jobTypeList = (List<JobType>)request.getAttribute("jobTypeList");
    //获取所有的SpecialInfo信息
    List<SpecialInfo> specialInfoList = (List<SpecialInfo>)request.getAttribute("specialInfoList");
    //获取所有的SchoolRecord信息
    List<SchoolRecord> schoolRecordList = (List<SchoolRecord>)request.getAttribute("schoolRecordList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加职位</TITLE> 
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
    var positionName = document.getElementById("job.positionName").value;
    if(positionName=="") {
        alert('请输入招聘职位!');
        return false;
    }
    var personNum = document.getElementById("job.personNum").value;
    if(personNum=="") {
        alert('请输入招聘人数!');
        return false;
    }
    var city = document.getElementById("job.city").value;
    if(city=="") {
        alert('请输入所在城市!');
        return false;
    }
    var salary = document.getElementById("job.salary").value;
    if(salary=="") {
        alert('请输入薪资待遇!');
        return false;
    }
    var workYears = document.getElementById("job.workYears").value;
    if(workYears=="") {
        alert('请输入工作年限!');
        return false;
    }
    var workAddress = document.getElementById("job.workAddress").value;
    if(workAddress=="") {
        alert('请输入工作地址!');
        return false;
    }
    var welfare = document.getElementById("job.welfare").value;
    if(welfare=="") {
        alert('请输入福利待遇!');
        return false;
    }
    var positionDesc = document.getElementById("job.positionDesc").value;
    if(positionDesc=="") {
        alert('请输入岗位要求!');
        return false;
    }
    var connectPerson = document.getElementById("job.connectPerson").value;
    if(connectPerson=="") {
        alert('请输入联系人!');
        return false;
    }
    var telephone = document.getElementById("job.telephone").value;
    if(telephone=="") {
        alert('请输入联系电话!');
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
    <s:form action="Job/Job_AddJob.action" method="post" id="jobAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>招聘企业:</td>
    <td width=70%>
      <select name="job.qiyeObj.qiyeUserName">
      <%
        for(Qiye qiye:qiyeList) {
      %>
          <option value='<%=qiye.getQiyeUserName() %>'><%=qiye.getQiyeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>招聘职位:</td>
    <td width=70%><input id="job.positionName" name="job.positionName" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>职位分类:</td>
    <td width=70%>
      <select name="job.jobTypeObj.jobTypeId">
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
    <td width=30%>所属专业:</td>
    <td width=70%>
      <select name="job.specialObj.specialId">
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
    <td width=30%>招聘人数:</td>
    <td width=70%><input id="job.personNum" name="job.personNum" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>所在城市:</td>
    <td width=70%><input id="job.city" name="job.city" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>薪资待遇:</td>
    <td width=70%><input id="job.salary" name="job.salary" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>学历要求:</td>
    <td width=70%>
      <select name="job.schoolRecordObj.id">
      <%
        for(SchoolRecord schoolRecord:schoolRecordList) {
      %>
          <option value='<%=schoolRecord.getId() %>'><%=schoolRecord.getSchooRecordName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>工作年限:</td>
    <td width=70%><input id="job.workYears" name="job.workYears" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>工作地址:</td>
    <td width=70%><input id="job.workAddress" name="job.workAddress" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>福利待遇:</td>
    <td width=70%><textarea id="job.welfare" name="job.welfare" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>岗位要求:</td>
    <td width=70%><textarea id="job.positionDesc" name="job.positionDesc" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>联系人:</td>
    <td width=70%><input id="job.connectPerson" name="job.connectPerson" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="job.telephone" name="job.telephone" type="text" size="20" /></td>
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
