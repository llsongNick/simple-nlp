# NLP 接口说明

## 1 使用方法

注意：JDK版本为1.8

```
    public static void main(String[] args) {
        com.hx_ai.HxNLP nlp = new com.hx_ai.HxNLP("浦发银行开盘价");
        String result = nlp.getAnswer();
        System.out.println(result);
    }
```

接口返回请参照：[wiki-语义理解API定义]

## 2 Jar包

jar包在target文件夹下，要使用jar包还需要添加pom.xml中的相关依赖，请使用Maven管理项目，或手动添加依赖。
