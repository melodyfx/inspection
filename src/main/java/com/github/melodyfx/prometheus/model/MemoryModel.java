package com.github.melodyfx.prometheus.model;

/**
 * @Author: huangzhimao
 * @Date: 2021-05-24
 */
public class MemoryModel {
  private String instance;
  private String time;
  private String total;
  private String avail;

  public String getInstance() {
    return instance;
  }

  public void setInstance(String instance) {
    this.instance = instance;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public String getAvail() {
    return avail;
  }

  public void setAvail(String avail) {
    this.avail = avail;
  }
}
