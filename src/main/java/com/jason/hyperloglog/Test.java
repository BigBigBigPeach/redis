package com.jason.hyperloglog;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

/**
 * <一句话简单描述>
 * <详细介绍>
 *
 * @author lihaitao on 2019/9/22
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpHost host = new HttpHost("localhost", 8080);
        for (int i = 1; i < 5000; i++) {
            HttpGet request = new HttpGet("/visit/user" + i);
            httpClient.execute(host, request);
        }
    }

}
