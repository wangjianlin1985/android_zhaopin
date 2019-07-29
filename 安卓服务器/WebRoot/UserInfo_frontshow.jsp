<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.SchoolRecord" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的SchoolRecord信息
    List<SchoolRecord> schoolRecordList = (List<SchoolRecord>)request.getAttribute("schoolRecordList");
    UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

%>
<HTML><HEAD><TITLE>查看求职者</TITLE>
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
    <td width=30%>用户名:</td>
    <td width=70%><%=userInfo.getUser_name() %></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><%=userInfo.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><%=userInfo.getName() %></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><%=userInfo.getGender() %></td>
  </tr>

  <tr>
    <td width=30%>出生日期:</td>
        <% java.text.DateFormat birthDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=birthDateSDF.format(userInfo.getBirthDate()) %></td>
  </tr>

  <tr>
    <td width=30%>用户照片:</td>
    <td width=70%><img src="<%=basePath %><%=userInfo.getUserPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>学历:</td>
    <td width=70%>
      <%=userInfo.getMyShoolRecord().getSchooRecordName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>毕业学校:</td>
    <td width=70%><%=userInfo.getSchoolName() %></td>
  </tr>

  <tr>
    <td width=30%>工作经验:</td>
    <td width=70%><%=userInfo.getWorkYears() %></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><%=userInfo.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>邮箱:</td>
    <td width=70%><%=userInfo.getEmail() %></td>
  </tr>

  <tr>
    <td width=30%>现居地址:</td>
    <td width=70%><%=userInfo.getAddress() %></td>
  </tr>

  <tr>
    <td width=30%>求职意向:</td>
    <td width=70%><%=userInfo.getQzyx() %></td>
  </tr>

  <tr>
    <td width=30%>工作经历:</td>
    <td width=70%><%=userInfo.getGzjl() %></td>
  </tr>

  <tr>
    <td width=30%>教育经历:</td>
    <td width=70%><%=userInfo.getJyjl() %></td>
  </tr>

  <tr>
    <td width=30%>自我评价:</td>
    <td width=70%><%=userInfo.getZwpj() %></td>
  </tr>

  <tr>
    <td width=30%>注册时间:</td>
    <td width=70%><%=userInfo.getRegTime() %></td>
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
