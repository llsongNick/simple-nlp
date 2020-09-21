package com.hx_ai.nlp.simple.query.stock;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.hx_ai.nlp.simple.utils.ArithUtil;
import com.hx_ai.nlp.simple.utils.StockProperties;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 股票查询
 */
public class StockQueryBak {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StockProperties properties = new StockProperties();
    private final String query;// 查询
    private String stockName;  // 股票名称
    private String stockCode;  // 股票代码
    private long stockDate;    // 日期
    private String stockDateQ; // 日期中文
    private String stockProp;  // 属性
    private String stockPropQ; // 属性中文

    private static final String FINAL_PRICE_UNIT = "10000";
    private static final String PRICE_UNIT = "100000000";

    public StockQueryBak(String query) {
        this.query = query;
    }

    private boolean isNegative() {
        for (String word : properties.getNegative()){
            if (query.contains(word)){
                return true;
            }
        }
        return false;
    }

    private boolean existStock() {
        Map<String, String> nameToId = properties.getNameToId();
        for (Map.Entry<String, String> x : nameToId.entrySet())
            if (query.contains(x.getKey()) || query.contains(x.getValue())) {
                stockName = x.getKey();
                stockCode = x.getValue();
                return true;
            }
        return false;
    }

    /**
     * 需要查询的股票属性
     * */
    private boolean existProp() {
        Map<String, String> props = properties.getStockProps();
        for (Map.Entry<String, String> x : props.entrySet())
            if (query.contains(x.getValue())) {
                stockPropQ = x.getValue();
                stockProp = x.getKey();
                return true;
            }
        return false;
    }

    private boolean existDate() {
        Map<String, Long> dateToDay = properties.getDateToDay();
        for (Map.Entry<String, Long> x : dateToDay.entrySet()) {
            if (query.contains(x.getKey())) {
                stockDate = x.getValue();
                stockDateQ = x.getKey();
                return true;
            }
        }
        return false;
    }

    private String parseJson(List<String> valueList) {
        try {
            String result = stockName + "，代号" + stockCode + "，" + stockDateQ + "的";

            if (stockProp.equals("default")) {
                result += stockPropQ + "价格是" + valueList.get(1) + "元";
            }else if(StockRelation.map.containsKey(stockProp)){
                String stockInfoStr = StockRelation.map.get(stockProp);
                String stockIndex = stockInfoStr.split("\\|")[0];
                String stockTemplate = stockInfoStr.split("\\|")[1];
                result += String.format(stockTemplate, stockPropQ, valueList.get(Integer.parseInt(stockIndex)));
            }else {
                result += "今日开盘价" + "是" + valueList.get(1) + "元,";
                result += "昨日收盘价" + "是" + valueList.get(2) + "元,";
                result += "当前价格" + "是" + valueList.get(3) + "元,";
                result += "今日最高" + "是" + valueList.get(4) + "元,";
                result += "今日最低" + "是" + valueList.get(5) + "元,";
                result += "竞买价" + "是" + valueList.get(6) + "元,";
                result += "竞卖价" + "是" + valueList.get(7) + "元,";
                result += "成交量" + "是" + initFinalPrice(valueList.get(8)) + "万手,";
                result += "成交额" + "是" + initMoney(valueList.get(9)) + "亿元,";
            }
            return result;
        } catch (Exception e) {
            return "暂时没有" + stockName + "，代号" + stockCode + "的股票信息";
        }
    }

    public boolean isStock() {
        if (isNegative() || !existStock()){
            return false;
        }

        if (!existProp()) {
            stockProp = "default";
            stockPropQ = "当前价格";
        }
//        if (!existDate()) {
//            stockDate = new Date().getTime();
//            stockDateQ = "今天";
//        }
        // 由于接口原因，暂且限制为今天
        stockDate = new Date().getTime();
        stockDateQ = "今天";

        return true;
    }

    public String getStockResult() {
        JSONObject json = new JSONObject();
        json.put("rc", 0);
        json.put("text", query);
        json.put("service", "stock");
        json.put("operation", "SEARCH");

        try {
            String urlTemplate = "http://hq.sinajs.cn/list=%s";
            String html = HttpUtil.get(String.format(urlTemplate, stockCode));
//            String html = HttpClientUtil.grabHtmlOnce(String.format(urlTemplate, stockCode));

//            var hq_str_sh600259="广晟有色,31.330,31.320,31.860,31.970,31.200,31.830,31.850,1953374,61544487.000,2300,31.830,200,31.820,5200,31.810,5900,31.800,4500,31.780,1000,31.850,9200,31.860,30200,31.870,1200,31.880,2100,31.890,2020-09-18,14:40:24,00,";
            String[] split = html.split("=");
            if(ObjectUtil.isEmpty(split)){
                String info = initReturnInfo();
                getErrorMsg(json, info);
            }else{
                String valueStr = split[1];
                List<String> valueList = Arrays.asList(valueStr.split(","));
//            json.put("data", jo.get("data"));
                json.put("error", "");
                json.put("answer", parseJson(valueList));
            }
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("股票查询异常errMsg={}", e.getMessage());
            getErrorMsg(json, null);
        }
        return json.toString();
    }

    private String initReturnInfo(){
        return "暂时没有" + stockName + "，代号" + stockCode + "的股票信息";
    }

    /**
     * 默认错误信息
     */
    private void getErrorMsg(JSONObject json, String info) {
        json.put("data", new JSONObject());
        if(ObjectUtil.isNotEmpty(info)){
            json.put("error", info);
            json.put("answer", info);
        }else{
            json.put("error", "找不到您查询的股票信息");
            json.put("answer", "找不到您查询的股票信息");
        }
    }

    public static void main(String[] args) {
        String s = initFinalPrice("589824680");
        System.out.println(s);
    }

    public static String initFinalPrice(String finalPrice){
        if(ObjectUtil.isNotEmpty(finalPrice)){
            return String.valueOf(ArithUtil.div(Double.parseDouble(finalPrice), Double.parseDouble(FINAL_PRICE_UNIT)));
        }
        return null;
    }

    public static BigDecimal initMoney(String price){
        if(ObjectUtil.isNotEmpty(price)){
            return BigDecimal.valueOf(ArithUtil.div(Double.parseDouble(price), Double.parseDouble(PRICE_UNIT), 2));
        }
        return null;
    }


}
