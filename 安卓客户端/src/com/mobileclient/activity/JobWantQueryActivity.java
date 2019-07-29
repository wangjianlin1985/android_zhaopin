package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.JobWant;
import com.mobileclient.domain.JobType;
import com.mobileclient.service.JobTypeService;
import com.mobileclient.domain.SpecialInfo;
import com.mobileclient.service.SpecialInfoService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class JobWantQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明职位分类下拉框
	private Spinner spinner_jobTypeObj;
	private ArrayAdapter<String> jobTypeObj_adapter;
	private static  String[] jobTypeObj_ShowText  = null;
	private List<JobType> jobTypeList = null; 
	/*职位分类管理业务逻辑层*/
	private JobTypeService jobTypeService = new JobTypeService();
	// 声明求职专业下拉框
	private Spinner spinner_specialObj;
	private ArrayAdapter<String> specialObj_adapter;
	private static  String[] specialObj_ShowText  = null;
	private List<SpecialInfo> specialInfoList = null; 
	/*专业管理业务逻辑层*/
	private SpecialInfoService specialInfoService = new SpecialInfoService();
	// 声明职位名称输入框
	private EditText ET_positionName;
	// 声明工作地点输入框
	private EditText ET_workCity;
	// 声明求职人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*求职者管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明发布时间输入框
	private EditText ET_addTime;
	/*查询过滤条件保存到这个对象中*/
	private JobWant queryConditionJobWant = new JobWant();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.jobwant_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置求职查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_jobTypeObj = (Spinner) findViewById(R.id.Spinner_jobTypeObj);
		// 获取所有的职位分类
		try {
			jobTypeList = jobTypeService.QueryJobType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int jobTypeCount = jobTypeList.size();
		jobTypeObj_ShowText = new String[jobTypeCount+1];
		jobTypeObj_ShowText[0] = "不限制";
		for(int i=1;i<=jobTypeCount;i++) { 
			jobTypeObj_ShowText[i] = jobTypeList.get(i-1).getTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		jobTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, jobTypeObj_ShowText);
		// 设置职位分类下拉列表的风格
		jobTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_jobTypeObj.setAdapter(jobTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_jobTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionJobWant.setJobTypeObj(jobTypeList.get(arg2-1).getJobTypeId()); 
				else
					queryConditionJobWant.setJobTypeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_jobTypeObj.setVisibility(View.VISIBLE);
		spinner_specialObj = (Spinner) findViewById(R.id.Spinner_specialObj);
		// 获取所有的专业
		try {
			specialInfoList = specialInfoService.QuerySpecialInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int specialInfoCount = specialInfoList.size();
		specialObj_ShowText = new String[specialInfoCount+1];
		specialObj_ShowText[0] = "不限制";
		for(int i=1;i<=specialInfoCount;i++) { 
			specialObj_ShowText[i] = specialInfoList.get(i-1).getSpecialName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		specialObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, specialObj_ShowText);
		// 设置求职专业下拉列表的风格
		specialObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_specialObj.setAdapter(specialObj_adapter);
		// 添加事件Spinner事件监听
		spinner_specialObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionJobWant.setSpecialObj(specialInfoList.get(arg2-1).getSpecialId()); 
				else
					queryConditionJobWant.setSpecialObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_specialObj.setVisibility(View.VISIBLE);
		ET_positionName = (EditText) findViewById(R.id.ET_positionName);
		ET_workCity = (EditText) findViewById(R.id.ET_workCity);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的求职者
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "不限制";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置求职人下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionJobWant.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionJobWant.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionJobWant.setPositionName(ET_positionName.getText().toString());
					queryConditionJobWant.setWorkCity(ET_workCity.getText().toString());
					queryConditionJobWant.setAddTime(ET_addTime.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionJobWant", queryConditionJobWant);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
