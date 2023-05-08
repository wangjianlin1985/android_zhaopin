<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.QiyeProperty" %>
<%@ page import="com.chengxusheji.domain.QiyeProfession" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�QiyeProperty��Ϣ
    List<QiyeProperty> qiyePropertyList = (List<QiyeProperty>)request.getAttribute("qiyePropertyList");
    //��ȡ���е�QiyeProfession��Ϣ
    List<QiyeProfession> qiyeProfessionList = (List<QiyeProfession>)request.getAttribute("qiyeProfessionList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�����ҵ</TITLE> 
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
    var qiyeUserName = document.getElementById("qiye.qiyeUserName").value;
    if(qiyeUserName=="") {
        alert('��������ҵ�˺�!');
        return false;
    }
    var password = document.getElementById("qiye.password").value;
    if(password=="") {
        alert('�������¼����!');
        return false;
    }
    var qiyeName = document.getElementById("qiye.qiyeName").value;
    if(qiyeName=="") {
        alert('��������ҵ����!');
        return false;
    }
    var qiyeQualify = document.getElementById("qiye.qiyeQualify").value;
    if(qiyeQualify=="") {
        alert('��������ҵ����!');
        return false;
    }
    var qiyeScale = document.getElementById("qiye.qiyeScale").value;
    if(qiyeScale=="") {
        alert('��������ҵ��ģ!');
        return false;
    }
    var connectPerson = document.getElementById("qiye.connectPerson").value;
    if(connectPerson=="") {
        alert('��������ϵ��!');
        return false;
    }
    var telephone = document.getElementById("qiye.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�绰!');
        return false;
    }
    var email = document.getElementById("qiye.email").value;
    if(email=="") {
        alert('��������ҵ����!');
        return false;
    }
    var address = document.getElementById("qiye.address").value;
    if(address=="") {
        alert('��������ҵ��ַ!');
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
    <s:form action="Qiye/Qiye_AddQiye.action" method="post" id="qiyeAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>��ҵ�˺�:</td>
    <td width=70%><input id="qiye.qiyeUserName" name="qiye.qiyeUserName" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="qiye.password" name="qiye.password" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%><input id="qiye.qiyeName" name="qiye.qiyeName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%><input id="qiye.qiyeQualify" name="qiye.qiyeQualify" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%>
      <select name="qiye.qiyePropertyObj.id">
      <%
        for(QiyeProperty qiyeProperty:qiyePropertyList) {
      %>
          <option value='<%=qiyeProperty.getId() %>'><%=qiyeProperty.getPropertyName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ҵ��ҵ:</td>
    <td width=70%>
      <select name="qiye.qiyeProfessionObj.id">
      <%
        for(QiyeProfession qiyeProfession:qiyeProfessionList) {
      %>
          <option value='<%=qiyeProfession.getId() %>'><%=qiyeProfession.getProfessionName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ҵ��ģ:</td>
    <td width=70%><input id="qiye.qiyeScale" name="qiye.qiyeScale" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵ��:</td>
    <td width=70%><input id="qiye.connectPerson" name="qiye.connectPerson" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="qiye.telephone" name="qiye.telephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%><input id="qiye.email" name="qiye.email" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>��ҵ��ַ:</td>
    <td width=70%><input id="qiye.address" name="qiye.address" type="text" size="80" /></td>
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
