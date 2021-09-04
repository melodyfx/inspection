package com.github.melodyfx.prometheus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.math.BigDecimal;
import java.util.List;
import com.github.melodyfx.prometheus.model.DiskModel;
import com.github.melodyfx.prometheus.utils.BytesConvert;
import com.github.melodyfx.prometheus.utils.ProcessUrl;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @Author: huangzhimao
 * @Date: 2021-05-21
 */
public class DiskProcess {

  public void process(List<DiskModel> diskModelList, String type, String url) throws Exception {
    JSONObject resultJson = ProcessUrl.process(url);
    JSONArray jsonArray = resultJson.getJSONObject("data").getJSONArray("result");
    for (int i = 0; i < jsonArray.size(); i++) {
      DiskModel disk = new DiskModel();
      JSONObject metric = jsonArray.getJSONObject(i).getJSONObject("metric");
      JSONArray value = jsonArray.getJSONObject(i).getJSONArray("value");
      disk.setDevice(metric.getString("device"));
      disk.setFstype(metric.getString("fstype"));
      disk.setInstance(metric.getString("instance"));
      disk.setMountpoint(metric.getString("mountpoint"));
      long timeLong=value.getBigDecimal(0).multiply(new BigDecimal(1000)).longValue();
      DateTime dateTime=new DateTime(timeLong);
      DateTimeFormatter forPattern = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
      String dateString=forPattern.print(dateTime);
      disk.setTime(dateString);
      switch (type) {
        case "total":
          disk.setTotal(BytesConvert.getNetFileSizeDescription(value.getLong(1)));
          break;
        case "avail":
          disk.setAvail(BytesConvert.getNetFileSizeDescription(value.getLong(1)));
          break;
        case "percent":
          disk.setPercent(String.valueOf(value.getBigDecimal(1).setScale(1, BigDecimal.ROUND_HALF_UP))+"%");
          break;
      }
      diskModelList.add(disk);
    }
  }
}
