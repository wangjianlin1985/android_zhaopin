package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Qiye;
import com.mobileclient.service.QiyeService;
import com.mobileclient.domain.QiyeProperty;
import com.mobileclient.service.QiyePropertyService;
import com.mobileclient.domain.QiyeProfession;
import com.mobileclient.service.QiyeProfessionService;
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
public class QiyeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明企业账号控件
	private TextView TV_qiyeUserName;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明企业名称控件
	private TextView TV_qiyeName;
	// 声明企业资质控件
	private TextView TV_qiyeQualify;
	// 声明企业性质控件
	private TextView TV_qiyePropertyObj;
	// 声明企业行业控件
	private TextView TV_qiyeProfessionObj;
	// 声明企业规模控件
	private TextView TV_qiyeScale;
	// 声明联系人控件
	private TextView TV_connectPerson;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明企业邮箱控件
	private TextView TV_email;
	// 声明企业地址控件
	private TextView TV_address;
	/* 要保存的企业信息 */
	Qiye qiye = new Qiye(); 
	/* 企业管理业务逻辑层 */
	private QiyeService qiyeService = new QiyeService();
	private QiyePropertyService qiyePropertyService = new QiyePropertyService();
	private QiyeProfessionService qiyeProfessionService = new QiyeProfessionService();
	private String qiyeUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.qiye_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看企业详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_qiyeUserName = (TextView) findViewById(R.id.TV_qiyeUserName);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_qiyeName = (TextView) findViewById(R.id.TV_qiyeName);
		TV_qiyeQualify = (TextView) findViewById(R.id.TV_qiyeQualify);
		TV_qiyePropertyObj = (TextView) findViewById(R.id.TV_qiyePropertyObj);
		TV_qiyeProfessionObj = (TextView) findViewById(R.id.TV_qiyeProfessionObj);
		TV_qiyeScale = (TextView) findViewById(R.id.TV_qiyeScale);
		TV_connectPerson = (TextView) findViewById(R.id.TV_connectPerson);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_email = (TextView) findViewById(R.id.TV_email);
		TV_address = (TextView) findViewById(R.id.TV_address);
		Bundle extras = this.getIntent().getExtras();
		qiyeUserName = extras.getString("qiyeUserName");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				QiyeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    qiye = qiyeService.GetQiye(qiyeUserName); 
		this.TV_qiyeUserName.setText(qiye.getQiyeUserName());
		this.TV_password.setText(qiye.getPassword());
		this.TV_qiyeName.setText(qiye.getQiyeName());
		this.TV_qiyeQualify.setText(qiye.getQiyeQualify());
		QiyeProperty qiyePropertyObj = qiyePropertyService.GetQiyeProperty(qiye.getQiyePropertyObj());
		this.TV_qiyePropertyObj.setText(qiyePropertyObj.getPropertyName());
		QiyeProfession qiyeProfessionObj = qiyeProfessionService.GetQiyeProfession(qiye.getQiyeProfessionObj());
		this.TV_qiyeProfessionObj.setText(qiyeProfessionObj.getProfessionName());
		this.TV_qiyeScale.setText(qiye.getQiyeScale());
		this.TV_connectPerson.setText(qiye.getConnectPerson());
		this.TV_telephone.setText(qiye.getTelephone());
		this.TV_email.setText(qiye.getEmail());
		this.TV_address.setText(qiye.getAddress());
	} 
}
