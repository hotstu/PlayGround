package io.github.hotstu.navichooser.navichooser;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class StateImpl_3_SignalType extends StateImpl_1_RoomName{
	private static final String TAG = "StateImpl_3_SignalType";
	State parent;
	String deviceCode;
	String deviceName;

	public StateImpl_3_SignalType(StateProcessProxy p) {
		super(p);
	}
	
	@Override
	public State getParent() {
		return parent;
	}
	
	/**
	 * @param deviceCode
	 */
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	
	public String getDeviceCode() {
		return deviceCode;
	}
	
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getDeviceName() {
		return deviceName;
	}
	
	@Override
	public void getSubmitValues(Map<String, String> input) {
		input.put("deviceName", deviceName);
		input.put("deviceCode", deviceCode);
	}
	

	@Override
	public void setParent(State p) {
		this.parent = p;
	}

	
	@Override
	public State factory(CheckItem src) {
		return null;
	}
	
	@Override
	public LoadResult loadImpl() {
		return debugonly();
	}

	
	private LoadResult debugonly() {
		SystemClock.sleep(1000);
		LoadResult r = new LoadResult();
		ArrayList<CheckItem> items = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			items.add( new CheckItem("id" + i, "信号量名" + i));
		}
		double random = Math.random();
		if (random > 0.2) {
			r.items = new ArrayList<>(items);
		}
		else if (random > 0.1) {
			r.items = new ArrayList<>();
		}
		else {
			r.errCode = -1;
			r.errMsg = "测试异常情况";
		}
		return r;
	}

    @Override
    protected String getTitle() {
    	return "请选择信号量名称";
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d(TAG, "onItemClick");
		final HashMap<String, String> inputMap = new HashMap<>();
		inputMap.put("signalType", ((CheckItem)parent.getAdapter().getItem(position)).getDictId());

		State start = this;
		while(start.getParent() != null) {
			start.getSubmitValues(inputMap);
			start = start.getParent();
		}
		start.getSubmitValues(inputMap);//root
		Log.d(TAG, "submitvalues:" + inputMap);
		StringBuilder queryType = new StringBuilder();
		queryType.append("查询类型:");
		queryType.append('\n');
		queryType.append("机房名称:").append(inputMap.get("roomName")).append('\n');
		queryType.append("设备名称:").append(inputMap.get("deviceName")).append('\n');
		queryType.append("设备编码:").append(inputMap.get("deviceCode")).append('\n');
		queryType.append("信号量:").append(inputMap.get("signalType"));
		ViewHolder holder = getProcessor().getViewHolder();
		holder.tv_summary_msg.setText(queryType.toString());
		holder.btn_summary_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StateProcessProxy.Callback callback = getProcessor().getSubmitAction();
				if (callback != null) {
					callback.onResult(inputMap);
				}
				else {
					Log.w(TAG, "没有为dialog设置回调");
				}
			}
		});
		holder.btn_summary_reselect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getProcessor().getViewHolder().summary.setVisibility(View.GONE);
				
			}
		});
		getProcessor().getViewHolder().summary.setVisibility(View.VISIBLE);

	}



}
