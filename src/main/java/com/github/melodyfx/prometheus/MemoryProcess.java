package com.github.melodyfx.prometheus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.math.BigDecimal;

import com.github.melodyfx.prometheus.model.MemoryModel;
import com.github.melodyfx.prometheus.utils.BytesConvert;
import com.github.melodyfx.prometheus.utils.ProcessUrl;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @Author: huangzhimao
 * @Date: 2021-05-21
 */
public class MemoryProcess {

  public MemoryModel process(String type, String url) throws Exception {
    JSONObject resultJson = ProcessUrl.process(url);
    JSONArray jsonArray = resultJson.getJSONObject("data").getJSONArray("result");
      MemoryModel memory = new MemoryModel();
      JSONObject metric = jsonArray.getJSONObject(0).getJSONObject("metric");
      JSONArray value = jsonArray.getJSONObject(0).getJSONArray("value");
      memory.setInstance(metric.getString("instance"));
    long timeLong=value.getBigDecimal(0).multiply(new BigDecimal(1000)).longValue();
    DateTime dateTime=new DateTime(timeLong);
    DateTimeFormatter forPattern = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    String dateString=forPattern.print(dateTime);
      memory.setTime(dateString);
      switch (type) {
        case "total":
          memory.setTotal(BytesConvert.getNetFileSizeDescription(value.getLong(1)));
          break;
        case "avail":
          memory.setAvail(BytesConvert.getNetFileSizeDescription(value.getLong(1)));
          break;
      }
      return memory;
  }
}
