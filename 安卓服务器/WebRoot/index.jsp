<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>D367����Android����ְ��ƸϵͳAPP-��ҳ</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">��ҳ</a></li>
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">��ְ��</a></li> 
			<li><a href="<%=basePath %>SchoolRecord/SchoolRecord_FrontQuerySchoolRecord.action" target="OfficeMain">ѧ��</a></li> 
			<li><a href="<%=basePath %>Qiye/Qiye_FrontQueryQiye.action" target="OfficeMain">��ҵ</a></li> 
			<li><a href="<%=basePath %>QiyeProperty/QiyeProperty_FrontQueryQiyeProperty.action" target="OfficeMain">��ҵ����</a></li> 
			<li><a href="<%=basePath %>QiyeProfession/QiyeProfession_FrontQueryQiyeProfession.action" target="OfficeMain">��ҵ��ҵ</a></li> 
			<li><a href="<%=basePath %>Job/Job_FrontQueryJob.action" target="OfficeMain">ְλ</a></li> 
			<li><a href="<%=basePath %>JobType/JobType_FrontQueryJobType.action" target="OfficeMain">ְλ����</a></li> 
			<li><a href="<%=basePath %>SpecialInfo/SpecialInfo_FrontQuerySpecialInfo.action" target="OfficeMain">רҵ</a></li> 
			<li><a href="<%=basePath %>Delivery/Delivery_FrontQueryDelivery.action" target="OfficeMain">ְλͶ��</a></li> 
			<li><a href="<%=basePath %>DeliveryState/DeliveryState_FrontQueryDeliveryState.action" target="OfficeMain">Ͷ��״̬</a></li> 
			<li><a href="<%=basePath %>JobWant/JobWant_FrontQueryJobWant.action" target="OfficeMain">��ְ</a></li> 
			<li><a href="<%=basePath %>News/News_FrontQueryNews.action" target="OfficeMain">���Ź���</a></li> 
			<li><a href="<%=basePath %>NewsClass/NewsClass_FrontQueryNewsClass.action" target="OfficeMain">���ŷ���</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>˫������� QQ:287307421��254540457 &copy;��Ȩ���� <a href="http://www.shuangyulin.com" target="_blank">˫���������</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>��̨��½</font></a></p>
	</div>
</div>
</body>
</html>
