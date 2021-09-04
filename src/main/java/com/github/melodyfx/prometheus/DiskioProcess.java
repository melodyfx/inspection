package com.github.melodyfx.prometheus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.math.BigDecimal;
import java.util.List;

import com.github.melodyfx.prometheus.model.DiskioModel;
import com.github.melodyfx.prometheus.utils.BytesConvert;
import com.github.melodyfx.prometheus.utils.ProcessUrl;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @Author: huangzhimao
 * @Date: 2021-05-21
 */
public class DiskioProcess {

  public void process(List<DiskioModel> diskioModelList, String type, String url) throws Exception {
    JSONObject resultJson = ProcessUrl.process(url);
    JSONArray jsonArray = resultJson.getJSONObject("data").getJSONArray("result");
    for (int i = 0; i < jsonArray.size(); i++) {
      DiskioModel diskio = new DiskioModel();
      JSONObject metric = jsonArray.getJSONObject(i).getJSONObject("metric");
      JSONArray value = jsonArray.getJSONObject(i).getJSONArray("value");
      diskio.setDevice(metric.getString("device"));
      diskio.setInstance(metric.getString("instance"));
      long timeLong=value.getBigDecimal(0).multiply(new BigDecimal(1000)).longValue();
      DateTime dateTime=new DateTime(timeLong);
      DateTimeFormatter forPattern = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
      String dateString=forPattern.print(dateTime);
      diskio.setTime(dateString);
      long byteValue=value.getBigDecimal(1).setScale( 0, BigDecimal.ROUND_HALF_UP ).longValue();
      String convValue= BytesConvert.getNetFileSizeDescription(byteValue);
      switch (type) {
        case "read":
          diskio.setRead(convValue);
          break;
        case "written":
          diskio.setWritten(convValue);
          break;
      }
      diskioModelList.add(diskio);
    }
  }
}
