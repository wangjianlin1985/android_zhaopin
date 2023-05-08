package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Delivery;
import com.mobileclient.service.DeliveryService;
import com.mobileclient.domain.Job;
import com.mobileclient.service.JobService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.DeliveryState;
import com.mobileclient.service.DeliveryStateService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class DeliveryAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明投递的职位下拉框
	private Spinner spinner_jobObj;
	private ArrayAdapter<String> jobObj_adapter;
	private static  String[] jobObj_ShowText  = null;
	private List<Job> jobList = null;
	/*投递的职位管理业务逻辑层*/
	private JobService jobService = new JobService();
	// 声明投递人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*投递人管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明投递时间输入框
	private EditText ET_deliveryTime;
	// 声明投递状态下拉框
	private Spinner spinner_stateObj;
	private ArrayAdapter<String> stateObj_adapter;
	private static  String[] stateObj_ShowText  = null;
	private List<DeliveryState> deliveryStateList = null;
	/*投递状态管理业务逻辑层*/
	private DeliveryStateService deliveryStateService = new DeliveryStateService();
	// 声明企业回复输入框
	private EditText ET_deliveryDemo;
	protected String carmera_path;
	/*要保存的职位投递信息*/
	Delivery delivery = new Delivery();
	/*职位投递管理业务逻辑层*/
	private DeliveryService deliveryService = new DeliveryService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.delivery_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加职位投递");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_jobObj = (Spinner) findViewById(R.id.Spinner_jobObj);
		// 获取所有的投递的职位
		try {
			jobList = jobService.QueryJob(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int jobCount = jobList.size();
		jobObj_ShowText = new String[jobCount];
		for(int i=0;i<jobCount;i++) { 
			jobObj_ShowText[i] = jobList.get(i).getPositionName();
		}
		// 将可选内容与ArrayAdapter连接起来
		jobObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, jobObj_ShowText);
		// 设置下拉列表的风格
		jobObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_jobObj.setAdapter(jobObj_adapter);
		// 添加事件Spinner事件监听
		spinner_jobObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				delivery.setJobObj(jobList.get(arg2).getJobId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_jobObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的投递人
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
		// 设置下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				delivery.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_deliveryTime = (EditText) findViewById(R.id.ET_deliveryTime);
		spinner_stateObj = (Spinner) findViewById(R.id.Spinner_stateObj);
		// 获取所有的投递状态
		try {
			deliveryStateList = deliveryStateService.QueryDeliveryState(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int deliveryStateCount = deliveryStateList.size();
		stateObj_ShowText = new String[deliveryStateCount];
		for(int i=0;i<deliveryStateCount;i++) { 
			stateObj_ShowText[i] = deliveryStateList.get(i).getStateName();
		}
		// 将可选内容与ArrayAdapter连接起来
		stateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stateObj_ShowText);
		// 设置下拉列表的风格
		stateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_stateObj.setAdapter(stateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_stateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				delivery.setStateObj(deliveryStateList.get(arg2).getStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_stateObj.setVisibility(View.VISIBLE);
		ET_deliveryDemo = (EditText) findViewById(R.id.ET_deliveryDemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加职位投递按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取投递时间*/ 
					if(ET_deliveryTime.getText().toString().equals("")) {
						Toast.makeText(DeliveryAddActivity.this, "投递时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_deliveryTime.setFocusable(true);
						ET_deliveryTime.requestFocus();
						return;	
					}
					delivery.setDeliveryTime(ET_deliveryTime.getText().toString());
					/*验证获取企业回复*/ 
					if(ET_deliveryDemo.getText().toString().equals("")) {
						Toast.makeText(DeliveryAddActivity.this, "企业回复输入不能为空!", Toast.LENGTH_LONG).show();
						ET_deliveryDemo.setFocusable(true);
						ET_deliveryDemo.requestFocus();
						return;	
					}
					delivery.setDeliveryDemo(ET_deliveryDemo.getText().toString());
					/*调用业务逻辑层上传职位投递信息*/
					DeliveryAddActivity.this.setTitle("正在上传职位投递信息，稍等...");
					String result = deliveryService.AddDelivery(delivery);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
