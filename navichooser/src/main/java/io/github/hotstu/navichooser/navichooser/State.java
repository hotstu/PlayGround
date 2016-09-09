package io.github.hotstu.navichooser.navichooser;

import java.util.Map;


/**
 * 定义一个状态, 当前处于下钻到的深度， 处理显示， 按钮事件
 *
 * 
 */
public interface State {
	/**
	 * 当用户回退时会用到的方法
	 * 
	 * @return
	 */
	State getParent();

	/**
	 * 当用户前进时会用到的方法
	 * 
	 * @return
	 */
	State getChild();

	/**
	 * 当用户点击一个列表项进入下一级应执行的方法
	 */
	void setParent(State parent);

	/**
	 * 当用户点击一个列表项进入下一级应执行的方法
	 */
	void setChild(State child);
	
	public void setProcessor(StateProcessProxy mProcessor);
	
	public StateProcessProxy getProcessor();
	
	void setQueryMap(QueryMap map);
	/**
	 * return a new instance of the queryMap
	 * @return
	 */
	QueryMap getQueryMap();
	/**
	 * 赋予input 当前状态下用户选取的参数
	 * @param input
	 */
	void getSubmitValues(Map<String, String> input);
	/**
	 * 
	 * @return null if you dont want use cache
	 */
	 String getPseudoUrl();
	/**
	 * 查询过程的具体实现, 以后可能将加载和解析分离
	 * @return
	 */
	LoadResult loadImpl();
	
	/**
	 * 根据Item 创建state的工厂方法
	 * @param src
	 * @return
	 */
	State factory(CheckItem src);

	/**
	 * 可以在这里对按钮禁用
	 */
	void onBeforeloading();
	boolean onLoadingProgress();
	void onloadingFinished(LoadResult r);
	void onLoadingCanceled();
}
