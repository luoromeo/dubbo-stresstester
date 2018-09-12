# taobao stresstester压力测试包使用

淘宝提供的一种压测工具，开发人员很方便的进行压测

1、引入pom

```
<dependency>
    <groupId>com.taobao</groupId>
    <artifactId>stresstester</artifactId>
    <version>1.0</version>
</dependency>
```

2、dubbo客户端配置

3、写个main方法就可以压测了

```java
public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:conf/spring.xml" });
        final PromotionCartServiceClient demoService = (PromotionCartServiceClient) context.getBean("PromotionCartServiceClient"); // 获取远程服
        //100是并发数
        //1000000是总请求次数
        StressTestUtils.testAndPrint(100, 1000000, new StressTask() {

            public Object doTask() throws Exception {
                List<String> promoIds = new ArrayList<String>();
                promoIds.add("0722d1aa9dcb4881bb6c7ad7cae76df6");
                ResultDTO<Boolean> deductionPriceResource = demoService.deductionPriceResource(promoIds, UUID.randomUUID().toString(), "1443", "951023562960");

                return null;
            }
        });
        System.exit(0);
    }
```
4、压测结果

```
 Concurrency Level: 100--并发数
 Time taken for tests:  41.175274 ms--测试耗时
 Complete Requests: 100--完成测试次数
 Failed Requests:   0--失败次数
 Requests per second:   3633.8547--QPS
 Time per request:  27.518986 ms--平均耗时
 Time per request:  0.27518988 ms (across all concurrent requests)--平均耗时，忽略并发影响
 Shortest request:  10.093374 ms--最短耗时
 Percentage of the requests served within a certain time (ms)
  50%   29.744219--50% 的耗时在0.005703毫秒以下
  66%   32.11875
  75%   33.670345
  80%   34.80021
  90%   38.767384
  95%   40.011738
  98%   40.90684
  99%   41.175274
 100%   41.175274 (longest request)--最长的耗时
```