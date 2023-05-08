package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.JobWant;
import com.mobileclient.service.JobWantService;
import com.mobileclient.domain.JobType;
import com.mobileclient.service.JobTypeService;
import com.mobileclient.domain.SpecialInfo;
import com.mobileclient.service.SpecialInfoService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class JobWantDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录id控件
	private TextView TV_wantId;
	// 声明职位分类控件
	private TextView TV_jobTypeObj;
	// 声明求职专业控件
	private TextView TV_specialObj;
	// 声明职位名称控件
	private TextView TV_positionName;
	// 声明期望薪资控件
	private TextView TV_salary;
	// 声明工作地点控件
	private TextView TV_workCity;
	// 声明备注说明控件
	private TextView TV_wantMemo;
	// 声明求职人控件
	private TextView TV_userObj;
	// 声明发布时间控件
	private TextView TV_addTime;
	/* 要保存的求职信息 */
	JobWant jobWant = new JobWant(); 
	/* 求职管理业务逻辑层 */
	private JobWantService jobWantService = new JobWantService();
	private JobTypeService jobTypeService = new JobTypeService();
	private SpecialInfoService specialInfoService = new SpecialInfoService();
	private UserInfoService userInfoService = new UserInfoService();
	private int wantId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.jobwant_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看求职详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_wantId = (TextView) findViewById(R.id.TV_wantId);
		TV_jobTypeObj = (TextView) findViewById(R.id.TV_jobTypeObj);
		TV_specialObj = (TextView) findViewById(R.id.TV_specialObj);
		TV_positionName = (TextView) findViewById(R.id.TV_positionName);
		TV_salary = (TextView) findViewById(R.id.TV_salary);
		TV_workCity = (TextView) findViewById(R.id.TV_workCity);
		TV_wantMemo = (TextView) findViewById(R.id.TV_wantMemo);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		Bundle extras = this.getIntent().getExtras();
		wantId = extras.getInt("wantId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				JobWantDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    jobWant = jobWantService.GetJobWant(wantId); 
		this.TV_wantId.setText(jobWant.getWantId() + "");
		JobType jobTypeObj = jobTypeService.GetJobType(jobWant.getJobTypeObj());
		this.TV_jobTypeObj.setText(jobTypeObj.getTypeName());
		SpecialInfo specialObj = specialInfoService.GetSpecialInfo(jobWant.getSpecialObj());
		this.TV_specialObj.setText(specialObj.getSpecialName());
		this.TV_positionName.setText(jobWant.getPositionName());
		this.TV_salary.setText(jobWant.getSalary());
		this.TV_workCity.setText(jobWant.getWorkCity());
		this.TV_wantMemo.setText(jobWant.getWantMemo());
		UserInfo userObj = userInfoService.GetUserInfo(jobWant.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_addTime.setText(jobWant.getAddTime());
	} 
}
