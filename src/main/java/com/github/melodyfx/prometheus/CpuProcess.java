package com.github.melodyfx.prometheus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.math.BigDecimal;

import com.github.melodyfx.prometheus.model.CpuModel;
import com.github.melodyfx.prometheus.utils.ProcessUrl;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @Author: huangzhimao
 * @Date: 2021-05-21
 */
public class CpuProcess {

  public CpuModel process(String instance, String url) throws Exception {
    JSONObject resultJson = ProcessUrl.process(url);
    JSONArray jsonArray = resultJson.getJSONObject("data").getJSONArray("result");
      CpuModel cpu = new CpuModel();
      JSONArray value = jsonArray.getJSONObject(0).getJSONArray("value");
      cpu.setInstance(instance);
    long timeLong=value.getBigDecimal(0).multiply(new BigDecimal(1000)).longValue();
    DateTime dateTime=new DateTime(timeLong);
    DateTimeFormatter forPattern = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    String dateString=forPattern.print(dateTime);
      cpu.setTime(dateString);
      cpu.setPercent(String.valueOf(value.getBigDecimal(1).setScale(1, BigDecimal.ROUND_HALF_UP))+"%");
      return cpu;
  }
}
