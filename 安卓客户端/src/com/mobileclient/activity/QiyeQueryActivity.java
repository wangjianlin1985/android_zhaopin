package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Qiye;
import com.mobileclient.domain.QiyeProperty;
import com.mobileclient.service.QiyePropertyService;
import com.mobileclient.domain.QiyeProfession;
import com.mobileclient.service.QiyeProfessionService;

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
public class QiyeQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明企业账号输入框
	private EditText ET_qiyeUserName;
	// 声明企业名称输入框
	private EditText ET_qiyeName;
	// 声明企业性质下拉框
	private Spinner spinner_qiyePropertyObj;
	private ArrayAdapter<String> qiyePropertyObj_adapter;
	private static  String[] qiyePropertyObj_ShowText  = null;
	private List<QiyeProperty> qiyePropertyList = null; 
	/*企业性质管理业务逻辑层*/
	private QiyePropertyService qiyePropertyService = new QiyePropertyService();
	// 声明企业行业下拉框
	private Spinner spinner_qiyeProfessionObj;
	private ArrayAdapter<String> qiyeProfessionObj_adapter;
	private static  String[] qiyeProfessionObj_ShowText  = null;
	private List<QiyeProfession> qiyeProfessionList = null; 
	/*企业行业管理业务逻辑层*/
	private QiyeProfessionService qiyeProfessionService = new QiyeProfessionService();
	// 声明联系人输入框
	private EditText ET_connectPerson;
	// 声明联系电话输入框
	private EditText ET_telephone;
	/*查询过滤条件保存到这个对象中*/
	private Qiye queryConditionQiye = new Qiye();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.qiye_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置企业查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_qiyeUserName = (EditText) findViewById(R.id.ET_qiyeUserName);
		ET_qiyeName = (EditText) findViewById(R.id.ET_qiyeName);
		spinner_qiyePropertyObj = (Spinner) findViewById(R.id.Spinner_qiyePropertyObj);
		// 获取所有的企业性质
		try {
			qiyePropertyList = qiyePropertyService.QueryQiyeProperty(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int qiyePropertyCount = qiyePropertyList.size();
		qiyePropertyObj_ShowText = new String[qiyePropertyCount+1];
		qiyePropertyObj_ShowText[0] = "不限制";
		for(int i=1;i<=qiyePropertyCount;i++) { 
			qiyePropertyObj_ShowText[i] = qiyePropertyList.get(i-1).getPropertyName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		qiyePropertyObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, qiyePropertyObj_ShowText);
		// 设置企业性质下拉列表的风格
		qiyePropertyObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_qiyePropertyObj.setAdapter(qiyePropertyObj_adapter);
		// 添加事件Spinner事件监听
		spinner_qiyePropertyObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionQiye.setQiyePropertyObj(qiyePropertyList.get(arg2-1).getId()); 
				else
					queryConditionQiye.setQiyePropertyObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_qiyePropertyObj.setVisibility(View.VISIBLE);
		spinner_qiyeProfessionObj = (Spinner) findViewById(R.id.Spinner_qiyeProfessionObj);
		// 获取所有的企业行业
		try {
			qiyeProfessionList = qiyeProfessionService.QueryQiyeProfession(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int qiyeProfessionCount = qiyeProfessionList.size();
		qiyeProfessionObj_ShowText = new String[qiyeProfessionCount+1];
		qiyeProfessionObj_ShowText[0] = "不限制";
		for(int i=1;i<=qiyeProfessionCount;i++) { 
			qiyeProfessionObj_ShowText[i] = qiyeProfessionList.get(i-1).getProfessionName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		qiyeProfessionObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, qiyeProfessionObj_ShowText);
		// 设置企业行业下拉列表的风格
		qiyeProfessionObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_qiyeProfessionObj.setAdapter(qiyeProfessionObj_adapter);
		// 添加事件Spinner事件监听
		spinner_qiyeProfessionObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionQiye.setQiyeProfessionObj(qiyeProfessionList.get(arg2-1).getId()); 
				else
					queryConditionQiye.setQiyeProfessionObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_qiyeProfessionObj.setVisibility(View.VISIBLE);
		ET_connectPerson = (EditText) findViewById(R.id.ET_connectPerson);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionQiye.setQiyeUserName(ET_qiyeUserName.getText().toString());
					queryConditionQiye.setQiyeName(ET_qiyeName.getText().toString());
					queryConditionQiye.setConnectPerson(ET_connectPerson.getText().toString());
					queryConditionQiye.setTelephone(ET_telephone.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionQiye", queryConditionQiye);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
