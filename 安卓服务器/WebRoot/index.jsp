<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>D367基于Android的求职招聘系统APP-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">求职者</a></li> 
			<li><a href="<%=basePath %>SchoolRecord/SchoolRecord_FrontQuerySchoolRecord.action" target="OfficeMain">学历</a></li> 
			<li><a href="<%=basePath %>Qiye/Qiye_FrontQueryQiye.action" target="OfficeMain">企业</a></li> 
			<li><a href="<%=basePath %>QiyeProperty/QiyeProperty_FrontQueryQiyeProperty.action" target="OfficeMain">企业性质</a></li> 
			<li><a href="<%=basePath %>QiyeProfession/QiyeProfession_FrontQueryQiyeProfession.action" target="OfficeMain">企业行业</a></li> 
			<li><a href="<%=basePath %>Job/Job_FrontQueryJob.action" target="OfficeMain">职位</a></li> 
			<li><a href="<%=basePath %>JobType/JobType_FrontQueryJobType.action" target="OfficeMain">职位分类</a></li> 
			<li><a href="<%=basePath %>SpecialInfo/SpecialInfo_FrontQuerySpecialInfo.action" target="OfficeMain">专业</a></li> 
			<li><a href="<%=basePath %>Delivery/Delivery_FrontQueryDelivery.action" target="OfficeMain">职位投递</a></li> 
			<li><a href="<%=basePath %>DeliveryState/DeliveryState_FrontQueryDeliveryState.action" target="OfficeMain">投递状态</a></li> 
			<li><a href="<%=basePath %>JobWant/JobWant_FrontQueryJobWant.action" target="OfficeMain">求职</a></li> 
			<li><a href="<%=basePath %>News/News_FrontQueryNews.action" target="OfficeMain">新闻公告</a></li> 
			<li><a href="<%=basePath %>NewsClass/NewsClass_FrontQueryNewsClass.action" target="OfficeMain">新闻分类</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>双鱼林设计 QQ:287307421或254540457 &copy;版权所有 <a href="http://www.shuangyulin.com" target="_blank">双鱼林设计网</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
