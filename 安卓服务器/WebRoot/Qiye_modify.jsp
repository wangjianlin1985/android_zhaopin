<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Qiye" %>
<%@ page import="com.chengxusheji.domain.QiyeProperty" %>
<%@ page import="com.chengxusheji.domain.QiyeProfession" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的QiyeProperty信息
    List<QiyeProperty> qiyePropertyList = (List<QiyeProperty>)request.getAttribute("qiyePropertyList");
    //获取所有的QiyeProfession信息
    List<QiyeProfession> qiyeProfessionList = (List<QiyeProfession>)request.getAttribute("qiyeProfessionList");
    Qiye qiye = (Qiye)request.getAttribute("qiye");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改企业</TITLE>
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
    var qiyeUserName = document.getElementById("qiye.qiyeUserName").value;
    if(qiyeUserName=="") {
        alert('请输入企业账号!');
        return false;
    }
    var password = document.getElementById("qiye.password").value;
    if(password=="") {
        alert('请输入登录密码!');
        return false;
    }
    var qiyeName = document.getElementById("qiye.qiyeName").value;
    if(qiyeName=="") {
        alert('请输入企业名称!');
        return false;
    }
    var qiyeQualify = document.getElementById("qiye.qiyeQualify").value;
    if(qiyeQualify=="") {
        alert('请输入企业资质!');
        return false;
    }
    var qiyeScale = document.getElementById("qiye.qiyeScale").value;
    if(qiyeScale=="") {
        alert('请输入企业规模!');
        return false;
    }
    var connectPerson = document.getElementById("qiye.connectPerson").value;
    if(connectPerson=="") {
        alert('请输入联系人!');
        return false;
    }
    var telephone = document.getElementById("qiye.telephone").value;
    if(telephone=="") {
        alert('请输入联系电话!');
        return false;
    }
    var email = document.getElementById("qiye.email").value;
    if(email=="") {
        alert('请输入企业邮箱!');
        return false;
    }
    var address = document.getElementById("qiye.address").value;
    if(address=="") {
        alert('请输入企业地址!');
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
    <TD align="left" vAlign=top ><s:form action="Qiye/Qiye_ModifyQiye.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>企业账号:</td>
    <td width=70%><input id="qiye.qiyeUserName" name="qiye.qiyeUserName" type="text" value="<%=qiye.getQiyeUserName() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="qiye.password" name="qiye.password" type="text" size="20" value='<%=qiye.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>企业名称:</td>
    <td width=70%><input id="qiye.qiyeName" name="qiye.qiyeName" type="text" size="20" value='<%=qiye.getQiyeName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>企业资质:</td>
    <td width=70%><input id="qiye.qiyeQualify" name="qiye.qiyeQualify" type="text" size="40" value='<%=qiye.getQiyeQualify() %>'/></td>
  </tr>

  <tr>
    <td width=30%>企业性质:</td>
    <td width=70%>
      <select name="qiye.qiyePropertyObj.id">
      <%
        for(QiyeProperty qiyeProperty:qiyePropertyList) {
          String selected = "";
          if(qiyeProperty.getId() == qiye.getQiyePropertyObj().getId())
            selected = "selected";
      %>
          <option value='<%=qiyeProperty.getId() %>' <%=selected %>><%=qiyeProperty.getPropertyName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>企业行业:</td>
    <td width=70%>
      <select name="qiye.qiyeProfessionObj.id">
      <%
        for(QiyeProfession qiyeProfession:qiyeProfessionList) {
          String selected = "";
          if(qiyeProfession.getId() == qiye.getQiyeProfessionObj().getId())
            selected = "selected";
      %>
          <option value='<%=qiyeProfession.getId() %>' <%=selected %>><%=qiyeProfession.getProfessionName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>企业规模:</td>
    <td width=70%><input id="qiye.qiyeScale" name="qiye.qiyeScale" type="text" size="20" value='<%=qiye.getQiyeScale() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系人:</td>
    <td width=70%><input id="qiye.connectPerson" name="qiye.connectPerson" type="text" size="20" value='<%=qiye.getConnectPerson() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="qiye.telephone" name="qiye.telephone" type="text" size="20" value='<%=qiye.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>企业邮箱:</td>
    <td width=70%><input id="qiye.email" name="qiye.email" type="text" size="30" value='<%=qiye.getEmail() %>'/></td>
  </tr>

  <tr>
    <td width=30%>企业地址:</td>
    <td width=70%><input id="qiye.address" name="qiye.address" type="text" size="80" value='<%=qiye.getAddress() %>'/></td>
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
