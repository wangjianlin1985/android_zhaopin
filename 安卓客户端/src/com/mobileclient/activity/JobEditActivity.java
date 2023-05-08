package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Job;
import com.mobileclient.service.JobService;
import com.mobileclient.domain.Qiye;
import com.mobileclient.service.QiyeService;
import com.mobileclient.domain.JobType;
import com.mobileclient.service.JobTypeService;
import com.mobileclient.domain.SpecialInfo;
import com.mobileclient.service.SpecialInfoService;
import com.mobileclient.domain.SchoolRecord;
import com.mobileclient.service.SchoolRecordService;
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

public class JobEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明职位idTextView
	private TextView TV_jobId;
	// 声明招聘企业下拉框
	private Spinner spinner_qiyeObj;
	private ArrayAdapter<String> qiyeObj_adapter;
	private static  String[] qiyeObj_ShowText  = null;
	private List<Qiye> qiyeList = null;
	/*招聘企业管理业务逻辑层*/
	private QiyeService qiyeService = new QiyeService();
	// 声明招聘职位输入框
	private EditText ET_positionName;
	// 声明职位分类下拉框
	private Spinner spinner_jobTypeObj;
	private ArrayAdapter<String> jobTypeObj_adapter;
	private static  String[] jobTypeObj_ShowText  = null;
	private List<JobType> jobTypeList = null;
	/*职位分类管理业务逻辑层*/
	private JobTypeService jobTypeService = new JobTypeService();
	// 声明所属专业下拉框
	private Spinner spinner_specialObj;
	private ArrayAdapter<String> specialObj_adapter;
	private static  String[] specialObj_ShowText  = null;
	private List<SpecialInfo> specialInfoList = null;
	/*所属专业管理业务逻辑层*/
	private SpecialInfoService specialInfoService = new SpecialInfoService();
	// 声明招聘人数输入框
	private EditText ET_personNum;
	// 声明所在城市输入框
	private EditText ET_city;
	// 声明薪资待遇输入框
	private EditText ET_salary;
	// 声明学历要求下拉框
	private Spinner spinner_schoolRecordObj;
	private ArrayAdapter<String> schoolRecordObj_adapter;
	private static  String[] schoolRecordObj_ShowText  = null;
	private List<SchoolRecord> schoolRecordList = null;
	/*学历要求管理业务逻辑层*/
	private SchoolRecordService schoolRecordService = new SchoolRecordService();
	// 声明工作年限输入框
	private EditText ET_workYears;
	// 声明工作地址输入框
	private EditText ET_workAddress;
	// 声明福利待遇输入框
	private EditText ET_welfare;
	// 声明岗位要求输入框
	private EditText ET_positionDesc;
	// 声明联系人输入框
	private EditText ET_connectPerson;
	// 声明联系电话输入框
	private EditText ET_telephone;
	protected String carmera_path;
	/*要保存的职位信息*/
	Job job = new Job();
	/*职位管理业务逻辑层*/
	private JobService jobService = new JobService();

	private int jobId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.job_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑职位信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_jobId = (TextView) findViewById(R.id.TV_jobId);
		spinner_qiyeObj = (Spinner) findViewById(R.id.Spinner_qiyeObj);
		// 获取所有的招聘企业
		try {
			qiyeList = qiyeService.QueryQiye(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int qiyeCount = qiyeList.size();
		qiyeObj_ShowText = new String[qiyeCount];
		for(int i=0;i<qiyeCount;i++) { 
			qiyeObj_ShowText[i] = qiyeList.get(i).getQiyeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		qiyeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, qiyeObj_ShowText);
		// 设置图书类别下拉列表的风格
		qiyeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_qiyeObj.setAdapter(qiyeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_qiyeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				job.setQiyeObj(qiyeList.get(arg2).getQiyeUserName()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_qiyeObj.setVisibility(View.VISIBLE);
		ET_positionName = (EditText) findViewById(R.id.ET_positionName);
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
				job.setJobTypeObj(jobTypeList.get(arg2).getJobTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_jobTypeObj.setVisibility(View.VISIBLE);
		spinner_specialObj = (Spinner) findViewById(R.id.Spinner_specialObj);
		// 获取所有的所属专业
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
				job.setSpecialObj(specialInfoList.get(arg2).getSpecialId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_specialObj.setVisibility(View.VISIBLE);
		ET_personNum = (EditText) findViewById(R.id.ET_personNum);
		ET_city = (EditText) findViewById(R.id.ET_city);
		ET_salary = (EditText) findViewById(R.id.ET_salary);
		spinner_schoolRecordObj = (Spinner) findViewById(R.id.Spinner_schoolRecordObj);
		// 获取所有的学历要求
		try {
			schoolRecordList = schoolRecordService.QuerySchoolRecord(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int schoolRecordCount = schoolRecordList.size();
		schoolRecordObj_ShowText = new String[schoolRecordCount];
		for(int i=0;i<schoolRecordCount;i++) { 
			schoolRecordObj_ShowText[i] = schoolRecordList.get(i).getSchooRecordName();
		}
		// 将可选内容与ArrayAdapter连接起来
		schoolRecordObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, schoolRecordObj_ShowText);
		// 设置图书类别下拉列表的风格
		schoolRecordObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_schoolRecordObj.setAdapter(schoolRecordObj_adapter);
		// 添加事件Spinner事件监听
		spinner_schoolRecordObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				job.setSchoolRecordObj(schoolRecordList.get(arg2).getId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_schoolRecordObj.setVisibility(View.VISIBLE);
		ET_workYears = (EditText) findViewById(R.id.ET_workYears);
		ET_workAddress = (EditText) findViewById(R.id.ET_workAddress);
		ET_welfare = (EditText) findViewById(R.id.ET_welfare);
		ET_positionDesc = (EditText) findViewById(R.id.ET_positionDesc);
		ET_connectPerson = (EditText) findViewById(R.id.ET_connectPerson);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		jobId = extras.getInt("jobId");
		/*单击修改职位按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取招聘职位*/ 
					if(ET_positionName.getText().toString().equals("")) {
						Toast.makeText(JobEditActivity.this, "招聘职位输入不能为空!", Toast.LENGTH_LONG).show();
						ET_positionName.setFocusable(true);
						ET_positionName.requestFocus();
						return;	
					}
					job.setPositionName(ET_positionName.getText().toString());
					/*验证获取招聘人数*/ 
					if(ET_personNum.getText().toString().equals("")) {
						Toast.makeText(JobEditActivity.this, "招聘人数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_personNum.setFocusable(true);
						ET_personNum.requestFocus();
						return;	
					}
					job.setPersonNum(ET_personNum.getText().toString());
					/*验证获取所在城市*/ 
					if(ET_city.getText().toString().equals("")) {
						Toast.makeText(JobEditActivity.this, "所在城市输入不能为空!", Toast.LENGTH_LONG).show();
						ET_city.setFocusable(true);
						ET_city.requestFocus();
						return;	
					}
					job.setCity(ET_city.getText().toString());
					/*验证获取薪资待遇*/ 
					if(ET_salary.getText().toString().equals("")) {
						Toast.makeText(JobEditActivity.this, "薪资待遇输入不能为空!", Toast.LENGTH_LONG).show();
						ET_salary.setFocusable(true);
						ET_salary.requestFocus();
						return;	
					}
					job.setSalary(ET_salary.getText().toString());
					/*验证获取工作年限*/ 
					if(ET_workYears.getText().toString().equals("")) {
						Toast.makeText(JobEditActivity.this, "工作年限输入不能为空!", Toast.LENGTH_LONG).show();
						ET_workYears.setFocusable(true);
						ET_workYears.requestFocus();
						return;	
					}
					job.setWorkYears(ET_workYears.getText().toString());
					/*验证获取工作地址*/ 
					if(ET_workAddress.getText().toString().equals("")) {
						Toast.makeText(JobEditActivity.this, "工作地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_workAddress.setFocusable(true);
						ET_workAddress.requestFocus();
						return;	
					}
					job.setWorkAddress(ET_workAddress.getText().toString());
					/*验证获取福利待遇*/ 
					if(ET_welfare.getText().toString().equals("")) {
						Toast.makeText(JobEditActivity.this, "福利待遇输入不能为空!", Toast.LENGTH_LONG).show();
						ET_welfare.setFocusable(true);
						ET_welfare.requestFocus();
						return;	
					}
					job.setWelfare(ET_welfare.getText().toString());
					/*验证获取岗位要求*/ 
					if(ET_positionDesc.getText().toString().equals("")) {
						Toast.makeText(JobEditActivity.this, "岗位要求输入不能为空!", Toast.LENGTH_LONG).show();
						ET_positionDesc.setFocusable(true);
						ET_positionDesc.requestFocus();
						return;	
					}
					job.setPositionDesc(ET_positionDesc.getText().toString());
					/*验证获取联系人*/ 
					if(ET_connectPerson.getText().toString().equals("")) {
						Toast.makeText(JobEditActivity.this, "联系人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_connectPerson.setFocusable(true);
						ET_connectPerson.requestFocus();
						return;	
					}
					job.setConnectPerson(ET_connectPerson.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(JobEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					job.setTelephone(ET_telephone.getText().toString());
					/*调用业务逻辑层上传职位信息*/
					JobEditActivity.this.setTitle("正在更新职位信息，稍等...");
					String result = jobService.UpdateJob(job);
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
	    job = jobService.GetJob(jobId);
		this.TV_jobId.setText(jobId+"");
		for (int i = 0; i < qiyeList.size(); i++) {
			if (job.getQiyeObj().equals(qiyeList.get(i).getQiyeUserName())) {
				this.spinner_qiyeObj.setSelection(i);
				break;
			}
		}
		this.ET_positionName.setText(job.getPositionName());
		for (int i = 0; i < jobTypeList.size(); i++) {
			if (job.getJobTypeObj() == jobTypeList.get(i).getJobTypeId()) {
				this.spinner_jobTypeObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < specialInfoList.size(); i++) {
			if (job.getSpecialObj() == specialInfoList.get(i).getSpecialId()) {
				this.spinner_specialObj.setSelection(i);
				break;
			}
		}
		this.ET_personNum.setText(job.getPersonNum());
		this.ET_city.setText(job.getCity());
		this.ET_salary.setText(job.getSalary());
		for (int i = 0; i < schoolRecordList.size(); i++) {
			if (job.getSchoolRecordObj() == schoolRecordList.get(i).getId()) {
				this.spinner_schoolRecordObj.setSelection(i);
				break;
			}
		}
		this.ET_workYears.setText(job.getWorkYears());
		this.ET_workAddress.setText(job.getWorkAddress());
		this.ET_welfare.setText(job.getWelfare());
		this.ET_positionDesc.setText(job.getPositionDesc());
		this.ET_connectPerson.setText(job.getConnectPerson());
		this.ET_telephone.setText(job.getTelephone());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
