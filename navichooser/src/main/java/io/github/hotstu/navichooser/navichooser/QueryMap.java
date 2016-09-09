package io.github.hotstu.navichooser.navichooser;

import java.util.HashMap;
import java.util.Map;



public class QueryMap extends HashMap<String, String> {
	
	public QueryMap() {
		super();
	}

	public QueryMap(Map<? extends String, ? extends String> map) {
		super(map);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QueryMap))
			return false;
		QueryMap that = (QueryMap) object;
		if (this.size() != that.size())
			return false;
		for (Entry<String, String> entry : this.entrySet()) {
			if (entry.getValue() == null ) {
				if (that.get(entry.getKey()) != null)
					return false;
			}
			else {
				if (!entry.getValue().equals(that.get(entry.getKey())))
					return false;
			}
		}
		return true;
	}
}
