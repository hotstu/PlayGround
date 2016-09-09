package io.github.hotstu.navichooser.navichooser;

import java.util.Map;


public class BaseState implements State{
	State child = null;
	State parent = null;
	StateProcessProxy mProcessor;
	QueryMap map;

	public BaseState(StateProcessProxy p) {
		this.mProcessor = p;
		mProcessor.setState(this);
	}
	
	@Override
	public State getParent() {
		return parent;
	}

	@Override
	public State getChild() {
		return child;
	}

	@Override
	public void setParent(State p) {
		this.parent = p;
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
		throw new UnsupportedOperationException("子类必须继承重写此方法");
	}

	@Override
	public LoadResult loadImpl() {
		throw new UnsupportedOperationException("子类必须继承重写此方法");
	}
	
	@Override
	public String getPseudoUrl() {
		throw new UnsupportedOperationException("子类必须继承重写此方法");
	}
	
	/**
	 * 对服务器发起请求
	 * @return
	 */
	protected String qurey() {
		throw new UnsupportedOperationException("子类必须继承重写此方法");
	}
	
	/**
	 * 解析服务器返回结果
	 * @param result
	 * @param r 传入空白的LoadResult， 写入解析后的结果
	 */
	protected void parse(String result, LoadResult r) {
		throw new UnsupportedOperationException("子类必须继承重写此方法");
    }

	@Override
	public void onBeforeloading() {
	}

	@Override
	public boolean onLoadingProgress() {
		//TODO 不支持
		return false;
	}

	@Override
	public void onloadingFinished(LoadResult r) {
	}


	@Override
	public void onLoadingCanceled() {
	}
	
	
	protected String getTitle() {
		return "";
	}

}
