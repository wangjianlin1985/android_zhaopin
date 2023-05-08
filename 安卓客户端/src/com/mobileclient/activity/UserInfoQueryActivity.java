package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.domain.SchoolRecord;
import com.mobileclient.service.SchoolRecordService;

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
public class UserInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明用户名输入框
	private EditText ET_user_name;
	// 声明姓名输入框
	private EditText ET_name;
	// 出生日期控件
	private DatePicker dp_birthDate;
	private CheckBox cb_birthDate;
	// 声明学历下拉框
	private Spinner spinner_myShoolRecord;
	private ArrayAdapter<String> myShoolRecord_adapter;
	private static  String[] myShoolRecord_ShowText  = null;
	private List<SchoolRecord> schoolRecordList = null; 
	/*学历管理业务逻辑层*/
	private SchoolRecordService schoolRecordService = new SchoolRecordService();
	// 声明毕业学校输入框
	private EditText ET_schoolName;
	// 声明工作经验输入框
	private EditText ET_workYears;
	// 声明联系电话输入框
	private EditText ET_telephone;
	/*查询过滤条件保存到这个对象中*/
	private UserInfo queryConditionUserInfo = new UserInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.userinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置求职者查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_user_name = (EditText) findViewById(R.id.ET_user_name);
		ET_name = (EditText) findViewById(R.id.ET_name);
		dp_birthDate = (DatePicker) findViewById(R.id.dp_birthDate);
		cb_birthDate = (CheckBox) findViewById(R.id.cb_birthDate);
		spinner_myShoolRecord = (Spinner) findViewById(R.id.Spinner_myShoolRecord);
		// 获取所有的学历
		try {
			schoolRecordList = schoolRecordService.QuerySchoolRecord(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int schoolRecordCount = schoolRecordList.size();
		myShoolRecord_ShowText = new String[schoolRecordCount+1];
		myShoolRecord_ShowText[0] = "不限制";
		for(int i=1;i<=schoolRecordCount;i++) { 
			myShoolRecord_ShowText[i] = schoolRecordList.get(i-1).getSchooRecordName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		myShoolRecord_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, myShoolRecord_ShowText);
		// 设置学历下拉列表的风格
		myShoolRecord_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_myShoolRecord.setAdapter(myShoolRecord_adapter);
		// 添加事件Spinner事件监听
		spinner_myShoolRecord.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionUserInfo.setMyShoolRecord(schoolRecordList.get(arg2-1).getId()); 
				else
					queryConditionUserInfo.setMyShoolRecord(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_myShoolRecord.setVisibility(View.VISIBLE);
		ET_schoolName = (EditText) findViewById(R.id.ET_schoolName);
		ET_workYears = (EditText) findViewById(R.id.ET_workYears);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionUserInfo.setUser_name(ET_user_name.getText().toString());
					queryConditionUserInfo.setName(ET_name.getText().toString());
					if(cb_birthDate.isChecked()) {
						/*获取出生日期*/
						Date birthDate = new Date(dp_birthDate.getYear()-1900,dp_birthDate.getMonth(),dp_birthDate.getDayOfMonth());
						queryConditionUserInfo.setBirthDate(new Timestamp(birthDate.getTime()));
					} else {
						queryConditionUserInfo.setBirthDate(null);
					} 
					queryConditionUserInfo.setSchoolName(ET_schoolName.getText().toString());
					queryConditionUserInfo.setWorkYears(ET_workYears.getText().toString());
					queryConditionUserInfo.setTelephone(ET_telephone.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionUserInfo", queryConditionUserInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
