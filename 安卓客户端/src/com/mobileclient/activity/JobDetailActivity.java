package com.mobileclient.activity;

import java.util.Date;
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
public class JobDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明职位id控件
	private TextView TV_jobId;
	// 声明招聘企业控件
	private TextView TV_qiyeObj;
	// 声明招聘职位控件
	private TextView TV_positionName;
	// 声明职位分类控件
	private TextView TV_jobTypeObj;
	// 声明所属专业控件
	private TextView TV_specialObj;
	// 声明招聘人数控件
	private TextView TV_personNum;
	// 声明所在城市控件
	private TextView TV_city;
	// 声明薪资待遇控件
	private TextView TV_salary;
	// 声明学历要求控件
	private TextView TV_schoolRecordObj;
	// 声明工作年限控件
	private TextView TV_workYears;
	// 声明工作地址控件
	private TextView TV_workAddress;
	// 声明福利待遇控件
	private TextView TV_welfare;
	// 声明岗位要求控件
	private TextView TV_positionDesc;
	// 声明联系人控件
	private TextView TV_connectPerson;
	// 声明联系电话控件
	private TextView TV_telephone;
	/* 要保存的职位信息 */
	Job job = new Job(); 
	/* 职位管理业务逻辑层 */
	private JobService jobService = new JobService();
	private QiyeService qiyeService = new QiyeService();
	private JobTypeService jobTypeService = new JobTypeService();
	private SpecialInfoService specialInfoService = new SpecialInfoService();
	private SchoolRecordService schoolRecordService = new SchoolRecordService();
	private int jobId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.job_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看职位详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_jobId = (TextView) findViewById(R.id.TV_jobId);
		TV_qiyeObj = (TextView) findViewById(R.id.TV_qiyeObj);
		TV_positionName = (TextView) findViewById(R.id.TV_positionName);
		TV_jobTypeObj = (TextView) findViewById(R.id.TV_jobTypeObj);
		TV_specialObj = (TextView) findViewById(R.id.TV_specialObj);
		TV_personNum = (TextView) findViewById(R.id.TV_personNum);
		TV_city = (TextView) findViewById(R.id.TV_city);
		TV_salary = (TextView) findViewById(R.id.TV_salary);
		TV_schoolRecordObj = (TextView) findViewById(R.id.TV_schoolRecordObj);
		TV_workYears = (TextView) findViewById(R.id.TV_workYears);
		TV_workAddress = (TextView) findViewById(R.id.TV_workAddress);
		TV_welfare = (TextView) findViewById(R.id.TV_welfare);
		TV_positionDesc = (TextView) findViewById(R.id.TV_positionDesc);
		TV_connectPerson = (TextView) findViewById(R.id.TV_connectPerson);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		Bundle extras = this.getIntent().getExtras();
		jobId = extras.getInt("jobId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				JobDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    job = jobService.GetJob(jobId); 
		this.TV_jobId.setText(job.getJobId() + "");
		Qiye qiyeObj = qiyeService.GetQiye(job.getQiyeObj());
		this.TV_qiyeObj.setText(qiyeObj.getQiyeName());
		this.TV_positionName.setText(job.getPositionName());
		JobType jobTypeObj = jobTypeService.GetJobType(job.getJobTypeObj());
		this.TV_jobTypeObj.setText(jobTypeObj.getTypeName());
		SpecialInfo specialObj = specialInfoService.GetSpecialInfo(job.getSpecialObj());
		this.TV_specialObj.setText(specialObj.getSpecialName());
		this.TV_personNum.setText(job.getPersonNum());
		this.TV_city.setText(job.getCity());
		this.TV_salary.setText(job.getSalary());
		SchoolRecord schoolRecordObj = schoolRecordService.GetSchoolRecord(job.getSchoolRecordObj());
		this.TV_schoolRecordObj.setText(schoolRecordObj.getSchooRecordName());
		this.TV_workYears.setText(job.getWorkYears());
		this.TV_workAddress.setText(job.getWorkAddress());
		this.TV_welfare.setText(job.getWelfare());
		this.TV_positionDesc.setText(job.getPositionDesc());
		this.TV_connectPerson.setText(job.getConnectPerson());
		this.TV_telephone.setText(job.getTelephone());
	} 
}
