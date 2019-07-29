package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.JobWant;
import com.mobileclient.service.JobWantService;
import com.mobileclient.domain.JobType;
import com.mobileclient.service.JobTypeService;
import com.mobileclient.domain.SpecialInfo;
import com.mobileclient.service.SpecialInfoService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class JobWantEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录idTextView
	private TextView TV_wantId;
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
	/*求职专业管理业务逻辑层*/
	private SpecialInfoService specialInfoService = new SpecialInfoService();
	// 声明职位名称输入框
	private EditText ET_positionName;
	// 声明期望薪资输入框
	private EditText ET_salary;
	// 声明工作地点输入框
	private EditText ET_workCity;
	// 声明备注说明输入框
	private EditText ET_wantMemo;
	// 声明求职人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*求职人管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明发布时间输入框
	private EditText ET_addTime;
	protected String carmera_path;
	/*要保存的求职信息*/
	JobWant jobWant = new JobWant();
	/*求职管理业务逻辑层*/
	private JobWantService jobWantService = new JobWantService();

	private int wantId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.jobwant_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑求职信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_wantId = (TextView) findViewById(R.id.TV_wantId);
		spinner_jobTypeObj = (Spinner) findViewById(R.id.Spinner_jobTypeObj);
		// 获取所有的职位分类
		try {
			jobTypeList = jobTypeService.QueryJobType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int jobTypeCount = jobTypeList.size();
		jobTypeObj_ShowText = new String[jobTypeCount];
		for(int i=0;i<jobTypeCount;i++) { 
			jobTypeObj_ShowText[i] = jobTypeList.get(i).getTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		jobTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, jobTypeObj_ShowText);
		// 设置图书类别下拉列表的风格
		jobTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_jobTypeObj.setAdapter(jobTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_jobTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				jobWant.setJobTypeObj(jobTypeList.get(arg2).getJobTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_jobTypeObj.setVisibility(View.VISIBLE);
		spinner_specialObj = (Spinner) findViewById(R.id.Spinner_specialObj);
		// 获取所有的求职专业
		try {
			specialInfoList = specialInfoService.QuerySpecialInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int specialInfoCount = specialInfoList.size();
		specialObj_ShowText = new String[specialInfoCount];
		for(int i=0;i<specialInfoCount;i++) { 
			specialObj_ShowText[i] = specialInfoList.get(i).getSpecialName();
		}
		// 将可选内容与ArrayAdapter连接起来
		specialObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, specialObj_ShowText);
		// 设置图书类别下拉列表的风格
		specialObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_specialObj.setAdapter(specialObj_adapter);
		// 添加事件Spinner事件监听
		spinner_specialObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				jobWant.setSpecialObj(specialInfoList.get(arg2).getSpecialId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_specialObj.setVisibility(View.VISIBLE);
		ET_positionName = (EditText) findViewById(R.id.ET_positionName);
		ET_salary = (EditText) findViewById(R.id.ET_salary);
		ET_workCity = (EditText) findViewById(R.id.ET_workCity);
		ET_wantMemo = (EditText) findViewById(R.id.ET_wantMemo);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的求职人
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置图书类别下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				jobWant.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		wantId = extras.getInt("wantId");
		/*单击修改求职按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取职位名称*/ 
					if(ET_positionName.getText().toString().equals("")) {
						Toast.makeText(JobWantEditActivity.this, "职位名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_positionName.setFocusable(true);
						ET_positionName.requestFocus();
						return;	
					}
					jobWant.setPositionName(ET_positionName.getText().toString());
					/*验证获取期望薪资*/ 
					if(ET_salary.getText().toString().equals("")) {
						Toast.makeText(JobWantEditActivity.this, "期望薪资输入不能为空!", Toast.LENGTH_LONG).show();
						ET_salary.setFocusable(true);
						ET_salary.requestFocus();
						return;	
					}
					jobWant.setSalary(ET_salary.getText().toString());
					/*验证获取工作地点*/ 
					if(ET_workCity.getText().toString().equals("")) {
						Toast.makeText(JobWantEditActivity.this, "工作地点输入不能为空!", Toast.LENGTH_LONG).show();
						ET_workCity.setFocusable(true);
						ET_workCity.requestFocus();
						return;	
					}
					jobWant.setWorkCity(ET_workCity.getText().toString());
					/*验证获取备注说明*/ 
					if(ET_wantMemo.getText().toString().equals("")) {
						Toast.makeText(JobWantEditActivity.this, "备注说明输入不能为空!", Toast.LENGTH_LONG).show();
						ET_wantMemo.setFocusable(true);
						ET_wantMemo.requestFocus();
						return;	
					}
					jobWant.setWantMemo(ET_wantMemo.getText().toString());
					/*验证获取发布时间*/ 
					if(ET_addTime.getText().toString().equals("")) {
						Toast.makeText(JobWantEditActivity.this, "发布时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_addTime.setFocusable(true);
						ET_addTime.requestFocus();
						return;	
					}
					jobWant.setAddTime(ET_addTime.getText().toString());
					/*调用业务逻辑层上传求职信息*/
					JobWantEditActivity.this.setTitle("正在更新求职信息，稍等...");
					String result = jobWantService.UpdateJobWant(jobWant);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    jobWant = jobWantService.GetJobWant(wantId);
		this.TV_wantId.setText(wantId+"");
		for (int i = 0; i < jobTypeList.size(); i++) {
			if (jobWant.getJobTypeObj() == jobTypeList.get(i).getJobTypeId()) {
				this.spinner_jobTypeObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < specialInfoList.size(); i++) {
			if (jobWant.getSpecialObj() == specialInfoList.get(i).getSpecialId()) {
				this.spinner_specialObj.setSelection(i);
				break;
			}
		}
		this.ET_positionName.setText(jobWant.getPositionName());
		this.ET_salary.setText(jobWant.getSalary());
		this.ET_workCity.setText(jobWant.getWorkCity());
		this.ET_wantMemo.setText(jobWant.getWantMemo());
		for (int i = 0; i < userInfoList.size(); i++) {
			if (jobWant.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_addTime.setText(jobWant.getAddTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
