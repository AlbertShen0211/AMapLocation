package com.amap.location.demo;

import com.amap.api.maps.MapsInitializer;
import com.amap.location.demo.view.FeatureView;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 高德定位SDK功能接口实例 更多SDK请进入官网“http://lbs.amap.com/api/”查看
 * 官方论坛：http://lbsbbs.amap.com/portal.php
 *
 * @项目名称： AMapLocationDemo2.x
 * 
 * @author hongming.wang
 * @文件名称: StartActivity.java
 * @类型名称: StartActivity
 */
public class StartActivity extends ListActivity {
	private static class DemoDetails {
		private final int titleId;
		private final int descriptionId;
		private final Class<? extends android.app.Activity> activityClass;
		public DemoDetails(int titleId, int descriptionId,
				Class<? extends android.app.Activity> activityClass) {
			super();
			this.titleId = titleId;
			this.descriptionId = descriptionId;
			this.activityClass = activityClass;
		}
	}

	private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
		public CustomArrayAdapter(Context context, DemoDetails[] demos) {
			super(context, R.layout.feature, R.id.title, demos);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			FeatureView featureView;
			if (convertView instanceof FeatureView) {
				featureView = (FeatureView) convertView;
			} else {
				featureView = new FeatureView(getContext());
			}
			DemoDetails demo = getItem(position);
			featureView.setTitleId(demo.titleId);
			featureView.setDescriptionId(demo.descriptionId);
			return featureView;
		}
	}

	private static final DemoDetails[] demos = {
//			new DemoDetails(R.string.locDiagnose_title,
//					R.string.locDiagnose_desc, DiagnoseDemo_Activity.class),
			new DemoDetails(R.string.location,
					R.string.location_dec, Location_Activity.class),
			new DemoDetails(R.string.locationPurpose,
					R.string.locationPurpose_dec, LocationPurpose_Activity.class),
			new DemoDetails(R.string.locationBackground,
					R.string.locationBackground_dec, Location_BackGround_Activity.class),
			new DemoDetails(R.string.geoFence, R.string.geoFence_dec,
					GeoFence_Activity.class),
			new DemoDetails(R.string.assistantLocation,
					R.string.assistantLocation_dec,
					Assistant_Location_Activity.class),
			new DemoDetails(R.string.tools, R.string.tools_dec,
					Tools_Activity.class),
			new DemoDetails(R.string.lastLocation, R.string.lastLocation_dec,
					LastLocation_Activity.class),
			new DemoDetails(R.string.alarmCPU, R.string.alarmCPU_dec,
					Alarm_Location_Activity.class),
			new DemoDetails(R.string.errorCode, R.string.errorCode_dec,
					ErrorCode_Activity.class),
			};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		/**
		 * setApiKey必须在启动Activity或者Application的onCreate里进行， 在其他地方使用有可能无效,
		 * 如果使用setApiKey设置key，则AndroidManifest.xml里的key会失效
		 */
		// AMapLocationClient.setApiKey("需要更换的key");

		setTitle(R.string.title_main);
		ListAdapter adapter = new CustomArrayAdapter(
				this.getApplicationContext(), demos);
		setListAdapter(adapter);
//		permChecker = new PermissionsChecker(this);
		privacyCompliance();
	}

	private void privacyCompliance() {
		MapsInitializer.updatePrivacyShow(StartActivity.this,true,true);
		SpannableStringBuilder spannable = new SpannableStringBuilder("\"亲，感谢您对XXX一直以来的信任！我们依据最新的监管要求更新了XXX《隐私权政策》，特向您说明如下\n1.为向您提供交易相关基本功能，我们会收集、使用必要的信息；\n2.基于您的明示授权，我们可能会获取您的位置（为您提供附近的商品、店铺及优惠资讯等）等信息，您有权拒绝或取消授权；\n3.我们会采取业界先进的安全措施保护您的信息安全；\n4.未经您同意，我们不会从第三方处获取、共享或向提供您的信息；\n");
		spannable.setSpan(new ForegroundColorSpan(Color.BLUE), 35, 42, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		new AlertDialog.Builder(this)
				.setTitle("温馨提示(隐私合规示例)")
				.setMessage(spannable)
				.setPositiveButton("同意", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						MapsInitializer.updatePrivacyAgree(StartActivity.this,true);
					}
				})
				.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						MapsInitializer.updatePrivacyAgree(StartActivity.this,false);
					}
				})
				.show();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.exit(0);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
		startActivity(
				new Intent(this.getApplicationContext(), demo.activityClass));
	}

}
