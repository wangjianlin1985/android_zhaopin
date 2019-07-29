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
    List<Job> jobList = (List<Job>)request.getAttribute("jobList");
    //获取所有的Qiye信息
    List<Qiye> qiyeList = (List<Qiye>)request.getAttribute("qiyeList");
    Qiye qiyeObj = (Qiye)request.getAttribute("qiye");

    //获取所有的JobType信息
    List<JobType> jobTypeList = (List<JobType>)request.getAttribute("jobTypeList");
    JobType jobTypeObj = (JobType)request.getAttribute("jobType");

    //获取所有的SpecialInfo信息
    List<SpecialInfo> specialInfoList = (List<SpecialInfo>)request.getAttribute("specialInfoList");
    SpecialInfo specialObj = (SpecialInfo)request.getAttribute("specialInfo");

    //获取所有的SchoolRecord信息
    List<SchoolRecord> schoolRecordList = (List<SchoolRecord>)request.getAttribute("schoolRecordList");
    SchoolRecord schoolRecordObj = (SchoolRecord)request.getAttribute("schoolRecord");

    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int  recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String positionName = (String)request.getAttribute("positionName"); //招聘职位查询关键字
    String city = (String)request.getAttribute("city"); //所在城市查询关键字
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>职位查询</title>
<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
.STYLE1 {font-size: 12px}
.STYLE3 {font-size: 12px; font-weight: bold; }
.STYLE4 {
    color: #03515d;
    font-size: 12px;
}
-->
</style>

 <script src="<%=basePath %>calendar.js"></script>
<script>
var  highlightcolor='#c1ebff';
//此处clickcolor只能用win系统颜色代码才能成功,如果用#xxxxxx的代码就不行,还没搞清楚为什么:(
var  clickcolor='#51b2f6';
function  changeto(){
source=event.srcElement;
if  (source.tagName=="TR"||source.tagName=="TABLE")
return;
while(source.tagName!="TD")
source=source.parentElement;
source=source.parentElement;
cs  =  source.children;
//alert(cs.length);
if  (cs[1].style.backgroundColor!=clickcolor&&source.id!="nc")
for(i=0;i<cs.length;i++){
    cs[i].style.backgroundColor=clickcolor;
}
else
for(i=0;i<cs.length;i++){
    cs[i].style.backgroundColor="";
}
}

function  changeback(){
if  (event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="nc")
return
if  (event.toElement!=source&&cs[1].style.backgroundColor!=clickcolor)
//source.style.backgroundColor=originalcolor
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor="";
}
}

/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.forms[0].currentPage.value = currentPage;
    document.forms[0].submit();

}

function changepage(totalPage)
{
    var pageValue=document.bookQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.jobQueryForm.currentPage.value = pageValue;
    document.jobQueryForm.submit();
}

</script>
</head>

<body>
<form action="<%=basePath %>/Job/Job_FrontQueryJob.action" name="jobQueryForm" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" background="<%=basePath %>images/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"><img src="<%=basePath %>images/tab_03.gif" width="12" height="30" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="46%" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="5%"><div align="center"><img src="<%=basePath %>images/tb.gif" width="16" height="16" /></div></td>
                <td width="95%" class="STYLE1"><span class="STYLE3">你当前的位置</span>：[职位管理]-[职位查询]</td>
              </tr>
            </table></td>
            <td width="54%"><table border="0" align="right" cellpadding="0" cellspacing="0">

            </table></td>
          </tr>
        </table></td>
        <td width="16"><img src="<%=basePath %>images/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table></td>
  </tr>


  <tr>
  <td>
招聘企业：<select name="qiyeObj.qiyeUserName">
 				<option value="">不限制</option>
 				<%
 					for(Qiye qiyeTemp:qiyeList) {
 			   %>
 			   <option value="<%=qiyeTemp.getQiyeUserName() %>"><%=qiyeTemp.getQiyeName() %></option>
 			   <%
 					}
 				%>
 			</select>
招聘职位:<input type=text name="positionName" size="8" value="<%=positionName %>" />&nbsp;
职位分类：<select name="jobTypeObj.jobTypeId">
 				<option value="0">不限制</option>
 				<%
 					for(JobType jobTypeTemp:jobTypeList) {
 			   %>
 			   <option value="<%=jobTypeTemp.getJobTypeId() %>"><%=jobTypeTemp.getTypeName() %></option>
 			   <%
 					}
 				%>
 			</select>
所属专业：<select name="specialObj.specialId">
 				<option value="0">不限制</option>
 				<%
 					for(SpecialInfo specialInfoTemp:specialInfoList) {
 			   %>
 			   <option value="<%=specialInfoTemp.getSpecialId() %>"><%=specialInfoTemp.getSpecialName() %></option>
 			   <%
 					}
 				%>
 			</select>
所在城市:<input type=text name="city" size="8" value="<%=city %>" />&nbsp;
学历要求：<select name="schoolRecordObj.id">
 				<option value="0">不限制</option>
 				<%
 					for(SchoolRecord schoolRecordTemp:schoolRecordList) {
 			   %>
 			   <option value="<%=schoolRecordTemp.getId() %>"><%=schoolRecordTemp.getSchooRecordName() %></option>
 			   <%
 					}
 				%>
 			</select>
    <input type=hidden name=currentPage value="1" />
    <input type=submit value="查询" />
  </td>
</tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="<%=basePath %>images/tab_12.gif">&nbsp;</td>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
          <tr>
          <!-- 
            <td width="3%" height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center">
              <input type="checkbox" name="checkall" onclick="checkAll();" />
            </div></td> -->
            <td width="3%" height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">序号</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">职位id</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">招聘企业</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">招聘职位</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">职位分类</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">所属专业</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">招聘人数</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">所在城市</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">薪资待遇</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">学历要求</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">工作年限</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">工作地址</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">详情</span></div></td> 
          </tr>
           <%
           		/*计算起始序号*/
            	int startIndex = (currentPage -1) * 3;
            	/*遍历记录*/
            	for(int i=0;i<jobList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Job job = jobList.get(i); //获取到Job对象
             %>
          <tr>
            <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1">
              <div align="center"><%=currentIndex %></div>
            </div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=job.getJobId() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=job.getQiyeObj().getQiyeName() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=job.getPositionName() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=job.getJobTypeObj().getTypeName() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=job.getSpecialObj().getSpecialName() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=job.getPersonNum() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=job.getCity() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=job.getSalary() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=job.getSchoolRecordObj().getSchooRecordName() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=job.getWorkYears() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=job.getWorkAddress() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><a href="<%=basePath  %>Job/Job_FrontShowJobQuery.action?jobId=<%=job.getJobId() %>">查看</a></span></div></td> 
          </tr>
          <%	} %>
        </table></td>
        <td width="8" background="images/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>

  <tr>
    <td height="35" background="<%=basePath %>images/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="<%=basePath %>images/tab_18.gif" width="12" height="35" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="STYLE4">&nbsp;&nbsp;共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</td>
            <td><table border="0" align="right" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="40"><img src="<%=basePath %>images/first.gif" width="37" height="15" style="cursor:hand;" onclick="GoToPage(1,<%=totalPage %>);" /></td>
                  <td width="45"><img src="<%=basePath %>images/back.gif" width="43" height="15" style="cursor:hand;" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);"/></td>
                  <td width="45"><img src="<%=basePath %>images/next.gif" width="43" height="15" style="cursor:hand;" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);" /></td>
                  <td width="40"><img src="<%=basePath %>images/last.gif" width="37" height="15" style="cursor:hand;" onclick="GoToPage(<%=totalPage %>,<%=totalPage %>);"/></td>
                  <td width="100"><div align="center"><span class="STYLE1">转到第
                    <input name="pageValue" type="text" size="4" style="height:12px; width:20px; border:1px solid #999999;" />
                    页 </span></div></td>
                  <td width="40"><img src="<%=basePath %>images/go.gif" onclick="changepage(<%=totalPage %>);" width="37" height="15" /></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
        <td width="16"><img src="<%=basePath %>images/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
  </form>
</body>
</html>
