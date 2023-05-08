package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.dao.SchoolRecordDAO;
import com.chengxusheji.domain.SchoolRecord;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class UserInfoAction extends BaseAction {

	/*图片或文件字段userPhoto参数接收*/
	private File userPhotoFile;
	private String userPhotoFileFileName;
	private String userPhotoFileContentType;
	public File getUserPhotoFile() {
		return userPhotoFile;
	}
	public void setUserPhotoFile(File userPhotoFile) {
		this.userPhotoFile = userPhotoFile;
	}
	public String getUserPhotoFileFileName() {
		return userPhotoFileFileName;
	}
	public void setUserPhotoFileFileName(String userPhotoFileFileName) {
		this.userPhotoFileFileName = userPhotoFileFileName;
	}
	public String getUserPhotoFileContentType() {
		return userPhotoFileContentType;
	}
	public void setUserPhotoFileContentType(String userPhotoFileContentType) {
		this.userPhotoFileContentType = userPhotoFileContentType;
	}
    /*界面层需要查询的属性: 用户名*/
    private String user_name;
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getUser_name() {
        return this.user_name;
    }

    /*界面层需要查询的属性: 姓名*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*界面层需要查询的属性: 出生日期*/
    private String birthDate;
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public String getBirthDate() {
        return this.birthDate;
    }

    /*界面层需要查询的属性: 学历*/
    private SchoolRecord myShoolRecord;
    public void setMyShoolRecord(SchoolRecord myShoolRecord) {
        this.myShoolRecord = myShoolRecord;
    }
    public SchoolRecord getMyShoolRecord() {
        return this.myShoolRecord;
    }

    /*界面层需要查询的属性: 毕业学校*/
    private String schoolName;
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    public String getSchoolName() {
        return this.schoolName;
    }

    /*界面层需要查询的属性: 工作经验*/
    private String workYears;
    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }
    public String getWorkYears() {
        return this.workYears;
    }

    /*界面层需要查询的属性: 联系电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource SchoolRecordDAO schoolRecordDAO;
    @Resource UserInfoDAO userInfoDAO;

    /*待操作的UserInfo对象*/
    private UserInfo userInfo;
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    /*跳转到添加UserInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的SchoolRecord信息*/
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QueryAllSchoolRecordInfo();
        ctx.put("schoolRecordList", schoolRecordList);
        return "add_view";
    }

    /*添加UserInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddUserInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证用户名是否已经存在*/
        String user_name = userInfo.getUser_name();
        UserInfo db_userInfo = userInfoDAO.GetUserInfoByUser_name(user_name);
        if(null != db_userInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该用户名已经存在!"));
            return "error";
        }
        try {
            SchoolRecord myShoolRecord = schoolRecordDAO.GetSchoolRecordById(userInfo.getMyShoolRecord().getId());
            userInfo.setMyShoolRecord(myShoolRecord);
            /*处理用户照片上传*/
            String userPhotoPath = "upload/noimage.jpg"; 
       	 	if(userPhotoFile != null)
       	 		userPhotoPath = photoUpload(userPhotoFile,userPhotoFileContentType);
       	 	userInfo.setUserPhoto(userPhotoPath);
            userInfoDAO.AddUserInfo(userInfo);
            ctx.put("message",  java.net.URLEncoder.encode("UserInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserInfo添加失败!"));
            return "error";
        }
    }

    /*查询UserInfo信息*/
    public String QueryUserInfo() {
        if(currentPage == 0) currentPage = 1;
        if(user_name == null) user_name = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(schoolName == null) schoolName = "";
        if(workYears == null) workYears = "";
        if(telephone == null) telephone = "";
        List<UserInfo> userInfoList = userInfoDAO.QueryUserInfoInfo(user_name, name, birthDate, myShoolRecord, schoolName, workYears, telephone, currentPage);
        /*计算总的页数和总的记录数*/
        userInfoDAO.CalculateTotalPageAndRecordNumber(user_name, name, birthDate, myShoolRecord, schoolName, workYears, telephone);
        /*获取到总的页码数目*/
        totalPage = userInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = userInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("userInfoList",  userInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("user_name", user_name);
        ctx.put("name", name);
        ctx.put("birthDate", birthDate);
        ctx.put("myShoolRecord", myShoolRecord);
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QueryAllSchoolRecordInfo();
        ctx.put("schoolRecordList", schoolRecordList);
        ctx.put("schoolName", schoolName);
        ctx.put("workYears", workYears);
        ctx.put("telephone", telephone);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryUserInfoOutputToExcel() { 
        if(user_name == null) user_name = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(schoolName == null) schoolName = "";
        if(workYears == null) workYears = "";
        if(telephone == null) telephone = "";
        List<UserInfo> userInfoList = userInfoDAO.QueryUserInfoInfo(user_name,name,birthDate,myShoolRecord,schoolName,workYears,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "UserInfo信息记录"; 
        String[] headers = { "用户名","姓名","性别","出生日期","用户照片","学历","毕业学校","工作经验","联系电话","邮箱","注册时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<userInfoList.size();i++) {
        	UserInfo userInfo = userInfoList.get(i); 
        	dataset.add(new String[]{userInfo.getUser_name(),userInfo.getName(),userInfo.getGender(),new SimpleDateFormat("yyyy-MM-dd").format(userInfo.getBirthDate()),userInfo.getUserPhoto(),userInfo.getMyShoolRecord().getSchooRecordName(),
userInfo.getSchoolName(),userInfo.getWorkYears(),userInfo.getTelephone(),userInfo.getEmail(),userInfo.getRegTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"UserInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询UserInfo信息*/
    public String FrontQueryUserInfo() {
        if(currentPage == 0) currentPage = 1;
        if(user_name == null) user_name = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(schoolName == null) schoolName = "";
        if(workYears == null) workYears = "";
        if(telephone == null) telephone = "";
        List<UserInfo> userInfoList = userInfoDAO.QueryUserInfoInfo(user_name, name, birthDate, myShoolRecord, schoolName, workYears, telephone, currentPage);
        /*计算总的页数和总的记录数*/
        userInfoDAO.CalculateTotalPageAndRecordNumber(user_name, name, birthDate, myShoolRecord, schoolName, workYears, telephone);
        /*获取到总的页码数目*/
        totalPage = userInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = userInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("userInfoList",  userInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("user_name", user_name);
        ctx.put("name", name);
        ctx.put("birthDate", birthDate);
        ctx.put("myShoolRecord", myShoolRecord);
        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QueryAllSchoolRecordInfo();
        ctx.put("schoolRecordList", schoolRecordList);
        ctx.put("schoolName", schoolName);
        ctx.put("workYears", workYears);
        ctx.put("telephone", telephone);
        return "front_query_view";
    }

    /*查询要修改的UserInfo信息*/
    public String ModifyUserInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键user_name获取UserInfo对象*/
        UserInfo userInfo = userInfoDAO.GetUserInfoByUser_name(user_name);

        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QueryAllSchoolRecordInfo();
        ctx.put("schoolRecordList", schoolRecordList);
        ctx.put("userInfo",  userInfo);
        return "modify_view";
    }

    /*查询要修改的UserInfo信息*/
    public String FrontShowUserInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键user_name获取UserInfo对象*/
        UserInfo userInfo = userInfoDAO.GetUserInfoByUser_name(user_name);

        List<SchoolRecord> schoolRecordList = schoolRecordDAO.QueryAllSchoolRecordInfo();
        ctx.put("schoolRecordList", schoolRecordList);
        ctx.put("userInfo",  userInfo);
        return "front_show_view";
    }

    /*更新修改UserInfo信息*/
    public String ModifyUserInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            SchoolRecord myShoolRecord = schoolRecordDAO.GetSchoolRecordById(userInfo.getMyShoolRecord().getId());
            userInfo.setMyShoolRecord(myShoolRecord);
            /*处理用户照片上传*/
            if(userPhotoFile != null) {
            	String userPhotoPath = photoUpload(userPhotoFile,userPhotoFileContentType);
            	userInfo.setUserPhoto(userPhotoPath);
            }
            userInfoDAO.UpdateUserInfo(userInfo);
            ctx.put("message",  java.net.URLEncoder.encode("UserInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除UserInfo信息*/
    public String DeleteUserInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            userInfoDAO.DeleteUserInfo(user_name);
            ctx.put("message",  java.net.URLEncoder.encode("UserInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserInfo删除失败!"));
            return "error";
        }
    }

}
