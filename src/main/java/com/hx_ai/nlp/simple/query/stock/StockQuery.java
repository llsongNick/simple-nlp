package com.hx_ai.nlp.simple.query.stock;

import org.json.JSONObject;
import com.hx_ai.nlp.simple.utils.HttpClientUtil;
import com.hx_ai.nlp.simple.utils.StockProperties;

import java.util.Date;
import java.util.Map;

/**
 * Created by dzkan on 2016/11/18.
 * 股票查询
 */
public class StockQuery {

    private final StockProperties properties = new StockProperties();
    private final String query;// 查询
    private String stockName;  // 股票名称
    private String stockCode;  // 股票代码
    private long stockDate;    // 日期
    private String stockDateQ; // 日期中文
    private String stockProp;  // 属性
    private String stockPropQ; // 属性中文

    public StockQuery(String query) {
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

    private void printParameters() {
        System.out.println("问    题：" + query);
        System.out.println("股票名称：" + stockName);
        System.out.println("股票代码：" + stockCode);
        System.out.println("时    间：" + new Date(stockDate).toString());
        System.out.println("属    性：" + stockProp);
    }

    private String parseJson(JSONObject json) {
        try {
            if (!json.get("state").equals("ok") || !json.get("code").equals("10000") || !json.get("msg").equals("ok"))
                return "暂时没有" + stockName + "，代号" + stockCode + "的股票信息";

            JSONObject data = (JSONObject) json.get("data");
            String result = stockName + "，代号" + stockCode + "，" + stockDateQ + "的";

            if (!stockProp.equals("default"))
                result += stockPropQ + "价格是" + data.get(stockProp) + "元";
            else {
                result += "开盘价是" + data.get("opprice") + "元，";
                result += "收盘价是" + data.get("clprice") + "元，";
                result += "买点价是" + data.get("bpprice") + "元，";
                result += "卖点价是" + data.get("cpprice") + "元。";
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
            stockPropQ = "价格";
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
            String urlTemplate = "http://api.qiandaren.com/api/stock/item/%s.json";
            String html = HttpClientUtil.grabHtmlOnce(String.format(urlTemplate, stockCode));
            JSONObject jo = new JSONObject(html);
            json.put("data", jo.get("data"));
            json.put("error", "");
            json.put("answer", parseJson(jo));
        } catch (Exception e) {
            //e.printStackTrace();
            json.put("data", new JSONObject());
            json.put("error", "找不到您查询的股票信息");
            json.put("answer", "找不到您查询的股票信息");
        }
        return json.toString();
    }
}
