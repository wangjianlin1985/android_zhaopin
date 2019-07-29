<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.NewsClass" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的NewsClass信息
    List<NewsClass> newsClassList = (List<NewsClass>)request.getAttribute("newsClassList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加新闻公告</TITLE> 
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
    var title = document.getElementById("news.title").value;
    if(title=="") {
        alert('请输入标题!');
        return false;
    }
    var content = document.getElementById("news.content").value;
    if(content=="") {
        alert('请输入新闻内容!');
        return false;
    }
    var publishDate = document.getElementById("news.publishDate").value;
    if(publishDate=="") {
        alert('请输入发布时间!');
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
    <s:form action="News/News_AddNews.action" method="post" id="newsAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>新闻分类:</td>
    <td width=70%>
      <select name="news.newsClassObj.newsClassId">
      <%
        for(NewsClass newsClass:newsClassList) {
      %>
          <option value='<%=newsClass.getNewsClassId() %>'><%=newsClass.getNewsClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>标题:</td>
    <td width=70%><input id="news.title" name="news.title" type="text" size="80" /></td>
  </tr>

  <tr>
    <td width=30%>新闻内容:</td>
    <td width=70%><textarea id="news.content" name="news.content" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>发布时间:</td>
    <td width=70%><input id="news.publishDate" name="news.publishDate" type="text" size="30" /></td>
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
