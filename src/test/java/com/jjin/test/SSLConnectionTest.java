package com.jjin.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class SSLConnectionTest {
    @Test
    public void testSslRequest() throws IOException {
        HttpClientBuilder builder = HttpClientBuilder.create();
        try (CloseableHttpClient client = builder.build()) {
            HttpGet httpGet = new HttpGet("https://localhost:8443/hello");
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            Assert.assertTrue(responseString.startsWith("Greeting,"));
        }
    }
}
