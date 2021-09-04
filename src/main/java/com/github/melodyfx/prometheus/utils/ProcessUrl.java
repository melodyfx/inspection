package com.github.melodyfx.prometheus.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.net.UrlEscapers;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * @Author: huangzhimao
 * @Date: 2021-05-21
 */
public class ProcessUrl {

  public static JSONObject process(String url) throws Exception {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    String escape = UrlEscapers.urlFragmentEscaper().escape(url).replace("+","%2B");
    HttpGet httpGet = new HttpGet(escape);
    CloseableHttpResponse response = httpClient.execute(httpGet);
    HttpEntity responseEntity = response.getEntity();
    if (responseEntity == null) {
      return null;
    }
    String body = EntityUtils.toString(responseEntity);
    httpClient.close();
    JSONObject resultJson = JSON.parseObject(body);
    return resultJson;
  }
}
