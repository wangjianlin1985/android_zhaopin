package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Qiye;
import com.mobileclient.service.QiyeService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.QiyeSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class QiyeListActivity extends Activity {
	QiyeSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String qiyeUserName;
	/* 企业操作业务逻辑层对象 */
	QiyeService qiyeService = new QiyeService();
	/*保存查询参数条件的企业对象*/
	private Qiye queryConditionQiye;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.qiye_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(QiyeListActivity.this, QiyeQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("企业查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(QiyeListActivity.this, QiyeAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionQiye = (Qiye)extras.getSerializable("queryConditionQiye");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionQiye = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//在子线程中进行下载数据操作
				list = getDatas();
				//发送消失到handler，通知主线程下载完成
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new QiyeSimpleAdapter(QiyeListActivity.this, list,
	        					R.layout.qiye_list_item,
	        					new String[] { "qiyeUserName","qiyeName","qiyePropertyObj","qiyeProfessionObj","qiyeScale","connectPerson","telephone" },
	        					new int[] { R.id.tv_qiyeUserName,R.id.tv_qiyeName,R.id.tv_qiyePropertyObj,R.id.tv_qiyeProfessionObj,R.id.tv_qiyeScale,R.id.tv_connectPerson,R.id.tv_telephone,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(qiyeListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String qiyeUserName = list.get(arg2).get("qiyeUserName").toString();
            	Intent intent = new Intent();
            	intent.setClass(QiyeListActivity.this, QiyeDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("qiyeUserName", qiyeUserName);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener qiyeListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "编辑企业信息"); 
			menu.add(0, 1, 0, "删除企业信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑企业信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取企业账号
			qiyeUserName = list.get(position).get("qiyeUserName").toString();
			Intent intent = new Intent();
			intent.setClass(QiyeListActivity.this, QiyeEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("qiyeUserName", qiyeUserName);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除企业信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取企业账号
			qiyeUserName = list.get(position).get("qiyeUserName").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(QiyeListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = qiyeService.DeleteQiye(qiyeUserName);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询企业信息 */
			List<Qiye> qiyeList = qiyeService.QueryQiye(queryConditionQiye);
			for (int i = 0; i < qiyeList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("qiyeUserName", qiyeList.get(i).getQiyeUserName());
				map.put("qiyeName", qiyeList.get(i).getQiyeName());
				map.put("qiyePropertyObj", qiyeList.get(i).getQiyePropertyObj());
				map.put("qiyeProfessionObj", qiyeList.get(i).getQiyeProfessionObj());
				map.put("qiyeScale", qiyeList.get(i).getQiyeScale());
				map.put("connectPerson", qiyeList.get(i).getConnectPerson());
				map.put("telephone", qiyeList.get(i).getTelephone());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
