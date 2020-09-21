package com.hx_ai.nlp.simple;

import com.hx_ai.nlp.simple.query.RadioQuery;
import com.hx_ai.nlp.simple.query.stock.StockQueryBak;
import com.hx_ai.nlp.simple.query.tuling.TulingQuery;

/**
 * Created by dzkan on 2016/11/19.
 * 总NLP返回
 */
public class HxNLP {
    private final String query;

    public HxNLP(String query) {
        this.query = query;
    }

    public String getAnswer() {
        // 判断是否是电台
        RadioQuery rq = new RadioQuery(query);
        if(rq.isRadio())
            return rq.getRadioResult();

        // 判断是否是股票
//        StockQuery sq = new StockQuery(query);
        StockQueryBak sq = new StockQueryBak(query);
        if(sq.isStock()) {
            return sq.getStockResult();
        }

        // 以上两种都不是，调用图灵
        TulingQuery tq = new TulingQuery(query);
        return tq.getTulingResult();
    }

    public static void main(String[] args) {
        HxNLP nlp = new HxNLP("天能重工 当前价格");
//        HxNLP nlp = new HxNLP("暂停");
        String result = nlp.getAnswer();
        System.out.println(result);
    }
}
