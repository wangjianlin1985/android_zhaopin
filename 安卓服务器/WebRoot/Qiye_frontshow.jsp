<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Qiye" %>
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
    Qiye qiye = (Qiye)request.getAttribute("qiye");

%>
<HTML><HEAD><TITLE>�鿴��ҵ</TITLE>
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
    <td width=30%>��ҵ�˺�:</td>
    <td width=70%><%=qiye.getQiyeUserName() %></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><%=qiye.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%><%=qiye.getQiyeName() %></td>
  </tr>

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%><%=qiye.getQiyeQualify() %></td>
  </tr>

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%>
      <%=qiye.getQiyePropertyObj().getPropertyName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ҵ��ҵ:</td>
    <td width=70%>
      <%=qiye.getQiyeProfessionObj().getProfessionName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ҵ��ģ:</td>
    <td width=70%><%=qiye.getQiyeScale() %></td>
  </tr>

  <tr>
    <td width=30%>��ϵ��:</td>
    <td width=70%><%=qiye.getConnectPerson() %></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><%=qiye.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%><%=qiye.getEmail() %></td>
  </tr>

  <tr>
    <td width=30%>��ҵ��ַ:</td>
    <td width=70%><%=qiye.getAddress() %></td>
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
