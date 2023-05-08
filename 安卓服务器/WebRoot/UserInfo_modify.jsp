<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.SchoolRecord" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�SchoolRecord��Ϣ
    List<SchoolRecord> schoolRecordList = (List<SchoolRecord>)request.getAttribute("schoolRecordList");
    UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸���ְ��</TITLE>
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
    var user_name = document.getElementById("userInfo.user_name").value;
    if(user_name=="") {
        alert('�������û���!');
        return false;
    }
    var password = document.getElementById("userInfo.password").value;
    if(password=="") {
        alert('�������¼����!');
        return false;
    }
    var name = document.getElementById("userInfo.name").value;
    if(name=="") {
        alert('����������!');
        return false;
    }
    var gender = document.getElementById("userInfo.gender").value;
    if(gender=="") {
        alert('�������Ա�!');
        return false;
    }
    var schoolName = document.getElementById("userInfo.schoolName").value;
    if(schoolName=="") {
        alert('�������ҵѧУ!');
        return false;
    }
    var workYears = document.getElementById("userInfo.workYears").value;
    if(workYears=="") {
        alert('�����빤������!');
        return false;
    }
    var telephone = document.getElementById("userInfo.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�绰!');
        return false;
    }
    var email = document.getElementById("userInfo.email").value;
    if(email=="") {
        alert('����������!');
        return false;
    }
    var qzyx = document.getElementById("userInfo.qzyx").value;
    if(qzyx=="") {
        alert('��������ְ����!');
        return false;
    }
    var gzjl = document.getElementById("userInfo.gzjl").value;
    if(gzjl=="") {
        alert('�����빤������!');
        return false;
    }
    var jyjl = document.getElementById("userInfo.jyjl").value;
    if(jyjl=="") {
        alert('�������������!');
        return false;
    }
    var zwpj = document.getElementById("userInfo.zwpj").value;
    if(zwpj=="") {
        alert('��������������!');
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
    <TD align="left" vAlign=top ><s:form action="UserInfo/UserInfo_ModifyUserInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>�û���:</td>
    <td width=70%><input id="userInfo.user_name" name="userInfo.user_name" type="text" value="<%=userInfo.getUser_name() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="userInfo.password" name="userInfo.password" type="text" size="30" value='<%=userInfo.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="userInfo.name" name="userInfo.name" type="text" size="20" value='<%=userInfo.getName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="userInfo.gender" name="userInfo.gender" type="text" size="4" value='<%=userInfo.getGender() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <% DateFormat birthDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="userInfo.birthDate"  name="userInfo.birthDate" onclick="setDay(this);" value='<%=birthDateSDF.format(userInfo.getBirthDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>�û���Ƭ:</td>
    <td width=70%><img src="<%=basePath %><%=userInfo.getUserPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="userInfo.userPhoto" value="<%=userInfo.getUserPhoto() %>" />
    <input id="userPhotoFile" name="userPhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>ѧ��:</td>
    <td width=70%>
      <select name="userInfo.myShoolRecord.id">
      <%
        for(SchoolRecord schoolRecord:schoolRecordList) {
          String selected = "";
          if(schoolRecord.getId() == userInfo.getMyShoolRecord().getId())
            selected = "selected";
      %>
          <option value='<%=schoolRecord.getId() %>' <%=selected %>><%=schoolRecord.getSchooRecordName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ҵѧУ:</td>
    <td width=70%><input id="userInfo.schoolName" name="userInfo.schoolName" type="text" size="20" value='<%=userInfo.getSchoolName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="userInfo.workYears" name="userInfo.workYears" type="text" size="50" value='<%=userInfo.getWorkYears() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="userInfo.telephone" name="userInfo.telephone" type="text" size="20" value='<%=userInfo.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="userInfo.email" name="userInfo.email" type="text" size="50" value='<%=userInfo.getEmail() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�־ӵ�ַ:</td>
    <td width=70%><input id="userInfo.address" name="userInfo.address" type="text" size="80" value='<%=userInfo.getAddress() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ְ����:</td>
    <td width=70%><textarea id="userInfo.qzyx" name="userInfo.qzyx" rows=5 cols=50><%=userInfo.getQzyx() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><textarea id="userInfo.gzjl" name="userInfo.gzjl" rows=5 cols=50><%=userInfo.getGzjl() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><textarea id="userInfo.jyjl" name="userInfo.jyjl" rows=5 cols=50><%=userInfo.getJyjl() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><textarea id="userInfo.zwpj" name="userInfo.zwpj" rows=5 cols=50><%=userInfo.getZwpj() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>ע��ʱ��:</td>
    <td width=70%><input id="userInfo.regTime" name="userInfo.regTime" type="text" size="30" value='<%=userInfo.getRegTime() %>'/></td>
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
