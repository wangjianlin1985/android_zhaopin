package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Delivery;
import com.mobileclient.service.DeliveryService;
import com.mobileclient.domain.Job;
import com.mobileclient.service.JobService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.DeliveryState;
import com.mobileclient.service.DeliveryStateService;
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
public class DeliveryDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明投递id控件
	private TextView TV_deliveryId;
	// 声明投递的职位控件
	private TextView TV_jobObj;
	// 声明投递人控件
	private TextView TV_userObj;
	// 声明投递时间控件
	private TextView TV_deliveryTime;
	// 声明投递状态控件
	private TextView TV_stateObj;
	// 声明企业回复控件
	private TextView TV_deliveryDemo;
	/* 要保存的职位投递信息 */
	Delivery delivery = new Delivery(); 
	/* 职位投递管理业务逻辑层 */
	private DeliveryService deliveryService = new DeliveryService();
	private JobService jobService = new JobService();
	private UserInfoService userInfoService = new UserInfoService();
	private DeliveryStateService deliveryStateService = new DeliveryStateService();
	private int deliveryId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.delivery_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看职位投递详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_deliveryId = (TextView) findViewById(R.id.TV_deliveryId);
		TV_jobObj = (TextView) findViewById(R.id.TV_jobObj);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_deliveryTime = (TextView) findViewById(R.id.TV_deliveryTime);
		TV_stateObj = (TextView) findViewById(R.id.TV_stateObj);
		TV_deliveryDemo = (TextView) findViewById(R.id.TV_deliveryDemo);
		Bundle extras = this.getIntent().getExtras();
		deliveryId = extras.getInt("deliveryId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DeliveryDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    delivery = deliveryService.GetDelivery(deliveryId); 
		this.TV_deliveryId.setText(delivery.getDeliveryId() + "");
		Job jobObj = jobService.GetJob(delivery.getJobObj());
		this.TV_jobObj.setText(jobObj.getPositionName());
		UserInfo userObj = userInfoService.GetUserInfo(delivery.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_deliveryTime.setText(delivery.getDeliveryTime());
		DeliveryState stateObj = deliveryStateService.GetDeliveryState(delivery.getStateObj());
		this.TV_stateObj.setText(stateObj.getStateName());
		this.TV_deliveryDemo.setText(delivery.getDeliveryDemo());
	} 
}
