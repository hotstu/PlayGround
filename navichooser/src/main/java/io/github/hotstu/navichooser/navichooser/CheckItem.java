package io.github.hotstu.navichooser.navichooser;



public class CheckItem {
	private String dictId;
	private String title;
	private Object additionalMsg;
	
	public CheckItem(String dictId, String title) {
		super();
		this.dictId = dictId;
		this.title = title;
	}

	public String getDictId() {
		return dictId;
	}
	
	@Override
	public String toString() {
		return title;
	}
	
	public Object getAdditionalMsg() {
		return additionalMsg;
	}
	
	public void setAdditionalMsg(Object additionalMsg) {
		this.additionalMsg = additionalMsg;
	}
	
	/**
	 * 以后拓展用
	 * @return
	 */
	public String toSubString() {
		return "";
	}

}
