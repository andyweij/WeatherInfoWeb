package com.eland.weatherInfo.util;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class APILimiter {
    private static final int LIMIT_PER_MINUTE = 300;
    private static final long MINUTE_IN_MILLIS = 60 * 60 * 1000;

    private AtomicInteger requestCount = new AtomicInteger(0);
    private long lastMinuteStart = System.currentTimeMillis();

    public synchronized void checkLimit() throws InterruptedException {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMinuteStart >= MINUTE_IN_MILLIS) {
            // 如果当前时间超过上一小時，重置请求计数和时间
            requestCount.set(0);
            lastMinuteStart = currentTime;
        }

        // 检查请求计数是否达到限制
        if (requestCount.get() >= LIMIT_PER_MINUTE) {
            // 等待到新的一小時开始
            long sleepTime = MINUTE_IN_MILLIS - (currentTime - lastMinuteStart);
            TimeUnit.MILLISECONDS.sleep(sleepTime);

            // 更新请求计数和时间
            requestCount.set(0);
            lastMinuteStart = System.currentTimeMillis();
        }

        // 增加请求计数
        requestCount.incrementAndGet();

        // 此处添加发送API请求的代码
        System.out.println("Sending API request...");
    }

    // 在这里实现你的发送API请求的逻辑


    // 在你的请求处理方法中使用 APILimiter
    public void handleApiRequest() throws InterruptedException {
        APILimiter apiLimiter = new APILimiter();
        for (int i = 0; i < 1000; i++) {
            apiLimiter.checkLimit();
            // 此处添加你的API请求代码
        }
    }
}