package io.github.hotstu.navichooser.navichooser;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Map;


public class StateImpl_2_deviceName extends StateImpl_1_RoomName {

	private static final String TAG = "StateImpl_2_deviceName";
	State parent;
	String roomName;

	public StateImpl_2_deviceName( StateProcessProxy p) {
		super(p);
	}
	
	@Override
	public void setParent(State p) {
		this.parent = p;
	}
	
	@Override
	public State getParent() {
		return parent;
	}
	
	@Override
	public void getSubmitValues(Map<String, String> input) {
		input.put("roomName", roomName);
	}
	
	@Override
	public State factory(CheckItem src) {
		StateImpl_3_SignalType child = new StateImpl_3_SignalType( mProcessor);
		child.setDeviceName(src.getDictId());
		child.setDeviceCode((String) src.getAdditionalMsg());
		child.setParent(this);
		QueryMap paramsMap = new QueryMap();
		paramsMap.put("method", src.getDictId());
		child.setQueryMap(paramsMap);
		return child;
	}
	
	@Override
	public LoadResult loadImpl() {
		return debugOnly();
	}
	
	private LoadResult debugOnly() {
		SystemClock.sleep(1000);
		LoadResult r = new LoadResult();
		ArrayList<CheckItem> items = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			CheckItem item = new CheckItem("id" + i, "设备名称" + i);
			item.setAdditionalMsg("设备编号" + i);
			items.add(item);
		}
		double random = Math.random();
		if (random > 0.2) {
			r.items = new ArrayList<>(items);
		} else if (random > 0.1) {
			r.items = new ArrayList<>();
		} else {
			r.errCode = -1;
			r.errMsg = "测试异常情况";
		}
		return r;
	}
	
    @Override
    protected String getTitle() {
    	return "请选择设备";
    }
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d(TAG, "onItemClick");
		State child = factory((CheckItem) parent.getAdapter().getItem(position));
		getProcessor().setState(child);
		setChild(child);
		getProcessor().load();
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public String getRoomName() {
		return roomName;
	};

}
