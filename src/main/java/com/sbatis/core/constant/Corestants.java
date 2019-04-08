package com.sbatis.core.constant;

import java.util.HashMap;
import java.util.Map;

public class Corestants {
	
	private Corestants(){};
	
	public static final int ORDER_ASC = 0;
	public static final int ORDER_DESC = 1;
	
	public static final int DEFAULT_PAGE = 1;
	public static final int DEFAULT_ROWS = 10;
	
	public static final String BEAN_FIELD = "field";
	public static final String BEAN_VALUE = "value";
	
	public static final String PARAM_OBJ = "obj";
	public static final String PARAM_FIELD = "fields";
	public static final String PARAM_FILTER = "filters";
	public static final String PARAM_ORDER = "orders";
	
	public static final String COUNT_SQL = "count_sql";
	public static final String QUERY_SQL = "query_sql";
	
	// 存放class
	public static Map<String, Class<?>> clsMap = new HashMap<String, Class<?>>();
	
	public static final int DEFAULT_INIT = 0;
}
