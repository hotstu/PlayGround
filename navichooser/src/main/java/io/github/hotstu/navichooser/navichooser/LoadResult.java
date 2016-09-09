package io.github.hotstu.navichooser.navichooser;

import java.util.HashMap;
import java.util.List;


public class LoadResult {
	/**
	 * 为0 表示无错误, 程序员确保为0时 items不为null
	 */
	public int errCode = 0;
	public String errMsg = null;
	public List<CheckItem> items = null;
	public Meta meta;
	
	public static class Meta {
		public int totalCount;
		public int page;
		public int pageSize;
		public HashMap<String, String> fieldNameMapping;
		@Override
		public String toString() {
			return "{'totalCount':"+ totalCount +",'page':"+ page+ ",'pageSize':"+pageSize+"}";
		}
	}
}
