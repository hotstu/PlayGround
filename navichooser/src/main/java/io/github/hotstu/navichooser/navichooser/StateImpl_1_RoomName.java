package io.github.hotstu.navichooser.navichooser;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Map;



public class StateImpl_1_RoomName implements State, OnItemClickListener{
	private static final String TAG = "StateImpl_1_RoomName";
	State child = null;
	StateProcessProxy mProcessor;
	QueryMap map;

	public StateImpl_1_RoomName(StateProcessProxy p) {
		this.mProcessor = p;
		mProcessor.setState(this);
	}
	
	@Override
	public State getParent() {
		return null;
	}

	@Override
	public State getChild() {
		return child;
	}

	@Override
	public void setParent(State p) {
		Log.w(TAG, "机房名称已是根节点");
	}

	@Override
	public void setChild(State c) {
		this.child = c;
	}
	
	public void setProcessor(StateProcessProxy mProcessor) {
		this.mProcessor = mProcessor;
	}
	
	@Override
	public StateProcessProxy getProcessor() {
		return mProcessor;
	}

	
	@Override
	public void setQueryMap(QueryMap map) {
		this.map = new QueryMap(map);
	}

	@Override
	public QueryMap getQueryMap() {
		return new QueryMap(this.map);
	}
	
	@Override
	public void getSubmitValues(Map<String, String> input) {
		// do no thing
	}
	
	@Override
	public State factory(CheckItem src) {
		//TODO 使用依赖注入动态组合层级关系
		StateImpl_2_deviceName child = new StateImpl_2_deviceName(mProcessor);
		child.setRoomName(src.getDictId());
		child.setParent(this);
		QueryMap paramsMap = new QueryMap();
		paramsMap.put("method", src.getDictId());
		child.setQueryMap(paramsMap);
		return child;
	}

	@Override
	public LoadResult loadImpl() {
		return debugonly();
	}
	
	@Override
	public String getPseudoUrl() {
		return getTitle() + getQueryMap();
	}
	
	/**
	 * 测试用
	 */
    private LoadResult debugonly() {
		SystemClock.sleep(1000);
		LoadResult r = new LoadResult();
		ArrayList<CheckItem> items = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			items.add( new CheckItem("id" + i, "机房名称" + i));
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
	
	/**
	 * 对服务器发起请求
	 * @return
	 */
	protected String qurey() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 解析服务器返回结果
	 * @param result
	 * @param r 传入空白的LoadResult， 写入解析后的结果
	 */
	protected void parse(String result, LoadResult r) {
		throw new UnsupportedOperationException();
    }

	@Override
	public void onBeforeloading() {
		Log.d(TAG, "onBeforeloading");
			ViewHolder holder =  mProcessor.getViewHolder();
			holder.progress.setVisibility(View.VISIBLE);
			holder.btn_back.setEnabled(false);
			holder.btn_forward.setEnabled(false);
			holder.tv_title.setText("加载中");
		
	}

	@Override
	public boolean onLoadingProgress() {
		//TODO 不支持
		return false;
	}

	@Override
	public void onloadingFinished(LoadResult r) {
		Log.d(TAG, "onloadingSuccess");
		Log.d(TAG, "meta:" + r.meta);

		ViewHolder holder = mProcessor.getViewHolder();
		if (r.errCode != 0 || r.items == null) {
			//加载失败
			holder.tv_empty_msg.setText("加载出错:" + r.errMsg);
		}
		else if (r.items.size() == 0) {
			holder.tv_empty_msg.setText("没有查询到数据");
		}
		if (r.items == null) {
			holder.listView.setAdapter(new ArrayAdapter<CheckItem>(getProcessor().getContext(), android.R.layout.simple_list_item_1, new ArrayList<CheckItem>()));
		}
		else {
			holder.listView.setAdapter(new ArrayAdapter<CheckItem>(getProcessor().getContext(), android.R.layout.simple_list_item_1, r.items));
		}
		holder.listView.setOnItemClickListener(this);
		onFinish();
	}


	@Override
	public void onLoadingCanceled() {
		Log.d(TAG, "onLoadingCanceled");
		ViewHolder holder = mProcessor.getViewHolder();
		holder.tv_empty_msg.setText("载入任务已取消");
		holder.listView.setAdapter(new ArrayAdapter<CheckItem>(getProcessor().getContext(), android.R.layout.simple_list_item_1, new ArrayList<CheckItem>()));
		holder.listView.setOnItemClickListener(this);
		onFinish();
	}
	
	protected void onFinish() {
		ViewHolder holder = mProcessor.getViewHolder();
		holder.progress.setVisibility(View.GONE);
		holder.btn_back.setEnabled(getParent() != null);
		holder.btn_forward.setEnabled(getChild() != null);
		holder.tv_title.setText(getTitle());
		if(holder.listView.getCount() <= 0) {
			holder.empty.setVisibility(View.VISIBLE);
			holder.listView.setVisibility(View.GONE);
		} else {
			holder.empty.setVisibility(View.GONE);
			holder.listView.setVisibility(View.VISIBLE);
			holder.listView.startLayoutAnimation();
		}
	}
	
	protected String getTitle() {
		return "请选择机房";
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





}
