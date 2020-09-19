package com.hx_ai.nlp.simple.query.stock;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description [股票标签与数据对应关系]
 * @Author lidisong
 * @Date [2020.09.19/17:17]
 * @Version 1.0
 */
public class StockRelation {

    public static final Map<String, String> map;

    public static String stockTemplateStr1 = "%s" + "是" + "%s" + "元,";
    public static String stockTemplateStr2 = "%s" + "是" + "%s" + "万手,";
    public static String stockTemplateStr3 = "%s" + "是" + "%s" + "亿元,";

    static {
        map = new HashMap<>();
        map.put("openPrice", 1+"|"+stockTemplateStr1);
        map.put("closePrice", 2+"|"+stockTemplateStr1);
        map.put("curPrice", 3+"|"+stockTemplateStr1);
        map.put("todayHigh", 4+"|"+stockTemplateStr1);
        map.put("todayLow", 5+"|"+stockTemplateStr1);
        map.put("bpprice", 6+"|"+stockTemplateStr1);
        map.put("cpprice", 7+"|"+stockTemplateStr1);
        map.put("finalPrice", 8+"|"+stockTemplateStr2);
        map.put("volumeBusiness", 9+"|"+stockTemplateStr3);
    }

}
