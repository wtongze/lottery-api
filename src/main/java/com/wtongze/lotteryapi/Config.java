package com.wtongze.lotteryapi;

import org.springframework.http.HttpHeaders;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static java.util.Map.entry;

public class Config {
    private static final Map<String, String> headersMap = Map.ofEntries(
            entry("user-agent", "CA Lottery/3.9.0 (com.calottery.calottery; build:190; iOS 16.4.1) Alamofire/5.3.0"),
            entry("X-Originator-Id", "10002,1,I,3.9.0"),
            entry("X-Site-Id", "35"),
            entry("X-Device-Uuid", "i" + UUID.randomUUID().toString().toUpperCase().substring(1))
    );

    public static Consumer<HttpHeaders> setUserAgent = headers ->
            headers.set("user-agent", headersMap.get("user-agent"));

    public static Consumer<HttpHeaders> setAll = headers ->
            headers.setAll(headersMap);
}
