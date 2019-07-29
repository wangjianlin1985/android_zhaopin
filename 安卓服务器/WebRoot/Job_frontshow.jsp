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
    //获取所有的Qiye信息
    List<Qiye> qiyeList = (List<Qiye>)request.getAttribute("qiyeList");
    //获取所有的JobType信息
    List<JobType> jobTypeList = (List<JobType>)request.getAttribute("jobTypeList");
    //获取所有的SpecialInfo信息
    List<SpecialInfo> specialInfoList = (List<SpecialInfo>)request.getAttribute("specialInfoList");
    //获取所有的SchoolRecord信息
    List<SchoolRecord> schoolRecordList = (List<SchoolRecord>)request.getAttribute("schoolRecordList");
    Job job = (Job)request.getAttribute("job");

%>
<HTML><HEAD><TITLE>查看职位</TITLE>
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
    <td width=30%>职位id:</td>
    <td width=70%><%=job.getJobId() %></td>
  </tr>

  <tr>
    <td width=30%>招聘企业:</td>
    <td width=70%>
      <%=job.getQiyeObj().getQiyeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>招聘职位:</td>
    <td width=70%><%=job.getPositionName() %></td>
  </tr>

  <tr>
    <td width=30%>职位分类:</td>
    <td width=70%>
      <%=job.getJobTypeObj().getTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>所属专业:</td>
    <td width=70%>
      <%=job.getSpecialObj().getSpecialName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>招聘人数:</td>
    <td width=70%><%=job.getPersonNum() %></td>
  </tr>

  <tr>
    <td width=30%>所在城市:</td>
    <td width=70%><%=job.getCity() %></td>
  </tr>

  <tr>
    <td width=30%>薪资待遇:</td>
    <td width=70%><%=job.getSalary() %></td>
  </tr>

  <tr>
    <td width=30%>学历要求:</td>
    <td width=70%>
      <%=job.getSchoolRecordObj().getSchooRecordName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>工作年限:</td>
    <td width=70%><%=job.getWorkYears() %></td>
  </tr>

  <tr>
    <td width=30%>工作地址:</td>
    <td width=70%><%=job.getWorkAddress() %></td>
  </tr>

  <tr>
    <td width=30%>福利待遇:</td>
    <td width=70%><%=job.getWelfare() %></td>
  </tr>

  <tr>
    <td width=30%>岗位要求:</td>
    <td width=70%><%=job.getPositionDesc() %></td>
  </tr>

  <tr>
    <td width=30%>联系人:</td>
    <td width=70%><%=job.getConnectPerson() %></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><%=job.getTelephone() %></td>
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
