package com.hx_ai.nlp.simple.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dzkan on 2016/11/18.
 * 载入股票的固定属性
 */

public class StockProperties {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, String> nameToId;
    private List<String> negative;
    private Map<String, Long> dateToDay;

    public static Map<String, String> stockProps;

    public static void main(String[] args) {
        StockProperties stockProperties = new StockProperties();
        stockProperties.setupNameAndId();
    }

    private void setupNameAndId() {
        nameToId = new HashMap<>();
        try {
            InputStream shStockStream = this.getClass().getResourceAsStream("/sh_3.txt");
            List<String> shLines = new BufferedReader(
                    new InputStreamReader(shStockStream, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());

            InputStream szStockStream = this.getClass().getResourceAsStream("/sz_3.txt");
            List<String> szLines = new BufferedReader(
                    new InputStreamReader(szStockStream, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
            for (String shLine : shLines) {
                String[] shStock = shLine.split(",");
                nameToId.putIfAbsent(shStock[0], "sh"+shStock[1]);
            }
            for (String szLine : szLines) {
                String[] szStock = szLine.split(",");
                nameToId.putIfAbsent(szStock[0], "sz"+szStock[1]);
            }
        } catch (Exception e) {
            logger.error("读取文件异常errMsg={}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupNegative() {
        String[] negativeWords = {"不想", "不喜欢", "不要", "不爱", "别", "不能"};
        negative = Arrays.asList(negativeWords);
    }

    private void setupDate() {
        dateToDay = new HashMap<>();

        Date today = new Date();
        String[] dates = {"前天", "昨天", "今天", "明天", "后天", "前日", "昨日", "今日", "明日", "后日"};
        int[] dateId = {-2, -1, 0, 1, 2, -2, -1, 0, 1, 2};
        for (int i = 0; i < dates.length; i++) {
            dateToDay.put(dates[i], today.getTime() + dateId[i] * 86400000);
        }

        String[] weekCh1 = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String[] weekCh2 = {"礼拜天", "礼拜一", "礼拜二", "礼拜三", "礼拜四", "礼拜五", "礼拜六"};
        String[] weekCh3 = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        for (int i = 0; i < weekCh1.length; i++) {
            Date dt = new Date();
            int dd = i <= dayOfWeek ? i - dayOfWeek : i - dayOfWeek - 7;
            dt.setTime(today.getTime() + dd * 86400000);
            dateToDay.put(weekCh1[i], dt.getTime());
            dateToDay.put(weekCh2[i], dt.getTime());
            dateToDay.put(weekCh3[i], dt.getTime());
        }
    }

    private void setupProps() {
        stockProps = new HashMap<>();
        String[] props = {"今日开盘价", "昨日收盘价", "当前价格", "今日最高价", "今日最低价", "竞买价", "竞卖价", "成交量", "成交额"};
        String[] propKeys = {"openPrice", "closePrice", "curPrice", "todayHigh", "todayLow", "bpprice", "cpprice", "finalPrice", "volumeBusiness"};
        for (int i = 0; i < props.length; i++) {
            stockProps.put(propKeys[i], props[i]);
        }
    }

    public StockProperties() {
        setupNameAndId();
        setupNegative();
//        setupDate();
        setupProps();
    }

    public Map<String, String> getNameToId() {
        return nameToId;
    }

    public List<String> getNegative() {
        return negative;
    }

    public Map<String, Long> getDateToDay() {
        return dateToDay;
    }

    public Map<String, String> getStockProps() {
        return stockProps;
    }
}
