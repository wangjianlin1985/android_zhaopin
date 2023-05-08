package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Delivery;
import com.mobileclient.domain.Job;
import com.mobileclient.service.JobService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.DeliveryState;
import com.mobileclient.service.DeliveryStateService;

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
public class DeliveryQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明投递的职位下拉框
	private Spinner spinner_jobObj;
	private ArrayAdapter<String> jobObj_adapter;
	private static  String[] jobObj_ShowText  = null;
	private List<Job> jobList = null; 
	/*职位管理业务逻辑层*/
	private JobService jobService = new JobService();
	// 声明投递人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*求职者管理业务逻辑层*/
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
	/*查询过滤条件保存到这个对象中*/
	private Delivery queryConditionDelivery = new Delivery();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.delivery_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置职位投递查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_jobObj = (Spinner) findViewById(R.id.Spinner_jobObj);
		// 获取所有的职位
		try {
			jobList = jobService.QueryJob(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int jobCount = jobList.size();
		jobObj_ShowText = new String[jobCount+1];
		jobObj_ShowText[0] = "不限制";
		for(int i=1;i<=jobCount;i++) { 
			jobObj_ShowText[i] = jobList.get(i-1).getPositionName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		jobObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, jobObj_ShowText);
		// 设置投递的职位下拉列表的风格
		jobObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_jobObj.setAdapter(jobObj_adapter);
		// 添加事件Spinner事件监听
		spinner_jobObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionDelivery.setJobObj(jobList.get(arg2-1).getJobId()); 
				else
					queryConditionDelivery.setJobObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_jobObj.setVisibility(View.VISIBLE);
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
		// 设置投递人下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionDelivery.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionDelivery.setUserObj("");
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
		stateObj_ShowText = new String[deliveryStateCount+1];
		stateObj_ShowText[0] = "不限制";
		for(int i=1;i<=deliveryStateCount;i++) { 
			stateObj_ShowText[i] = deliveryStateList.get(i-1).getStateName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		stateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stateObj_ShowText);
		// 设置投递状态下拉列表的风格
		stateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_stateObj.setAdapter(stateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_stateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionDelivery.setStateObj(deliveryStateList.get(arg2-1).getStateId()); 
				else
					queryConditionDelivery.setStateObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_stateObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionDelivery.setDeliveryTime(ET_deliveryTime.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionDelivery", queryConditionDelivery);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
