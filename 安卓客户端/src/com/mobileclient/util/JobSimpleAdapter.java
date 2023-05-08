package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.QiyeService;
import com.mobileclient.service.JobTypeService;
import com.mobileclient.service.SpecialInfoService;
import com.mobileclient.service.SchoolRecordService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class JobSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public JobSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.job_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_qiyeObj = (TextView)convertView.findViewById(R.id.tv_qiyeObj);
	  holder.tv_positionName = (TextView)convertView.findViewById(R.id.tv_positionName);
	  holder.tv_jobTypeObj = (TextView)convertView.findViewById(R.id.tv_jobTypeObj);
	  holder.tv_specialObj = (TextView)convertView.findViewById(R.id.tv_specialObj);
	  holder.tv_personNum = (TextView)convertView.findViewById(R.id.tv_personNum);
	  holder.tv_city = (TextView)convertView.findViewById(R.id.tv_city);
	  holder.tv_salary = (TextView)convertView.findViewById(R.id.tv_salary);
	  holder.tv_schoolRecordObj = (TextView)convertView.findViewById(R.id.tv_schoolRecordObj);
	  holder.tv_workYears = (TextView)convertView.findViewById(R.id.tv_workYears);
	  /*设置各个控件的展示内容*/
	  holder.tv_qiyeObj.setText("招聘企业：" + (new QiyeService()).GetQiye(mData.get(position).get("qiyeObj").toString()).getQiyeName());
	  holder.tv_positionName.setText("招聘职位：" + mData.get(position).get("positionName").toString());
	  holder.tv_jobTypeObj.setText("职位分类：" + (new JobTypeService()).GetJobType(Integer.parseInt(mData.get(position).get("jobTypeObj").toString())).getTypeName());
	  holder.tv_specialObj.setText("所属专业：" + (new SpecialInfoService()).GetSpecialInfo(Integer.parseInt(mData.get(position).get("specialObj").toString())).getSpecialName());
	  holder.tv_personNum.setText("招聘人数：" + mData.get(position).get("personNum").toString());
	  holder.tv_city.setText("所在城市：" + mData.get(position).get("city").toString());
	  holder.tv_salary.setText("薪资待遇：" + mData.get(position).get("salary").toString());
	  holder.tv_schoolRecordObj.setText("学历要求：" + (new SchoolRecordService()).GetSchoolRecord(Integer.parseInt(mData.get(position).get("schoolRecordObj").toString())).getSchooRecordName());
	  holder.tv_workYears.setText("工作年限：" + mData.get(position).get("workYears").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_qiyeObj;
    	TextView tv_positionName;
    	TextView tv_jobTypeObj;
    	TextView tv_specialObj;
    	TextView tv_personNum;
    	TextView tv_city;
    	TextView tv_salary;
    	TextView tv_schoolRecordObj;
    	TextView tv_workYears;
    }
} 
