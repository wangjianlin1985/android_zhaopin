package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.JobType;
import com.mobileclient.service.JobTypeService;
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
public class JobTypeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明职位分类id控件
	private TextView TV_jobTypeId;
	// 声明分类名称控件
	private TextView TV_typeName;
	/* 要保存的职位分类信息 */
	JobType jobType = new JobType(); 
	/* 职位分类管理业务逻辑层 */
	private JobTypeService jobTypeService = new JobTypeService();
	private int jobTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.jobtype_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看职位分类详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_jobTypeId = (TextView) findViewById(R.id.TV_jobTypeId);
		TV_typeName = (TextView) findViewById(R.id.TV_typeName);
		Bundle extras = this.getIntent().getExtras();
		jobTypeId = extras.getInt("jobTypeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				JobTypeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    jobType = jobTypeService.GetJobType(jobTypeId); 
		this.TV_jobTypeId.setText(jobType.getJobTypeId() + "");
		this.TV_typeName.setText(jobType.getTypeName());
	} 
}
