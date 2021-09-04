package com.github.melodyfx.prometheus.model;

/**
 * @Author: huangzhimao
 * @Date: 2021-05-24
 */
public class CpuModel {
  private String instance;
  private String time;
  private String percent;

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

  public String getPercent() {
    return percent;
  }

  public void setPercent(String percent) {
    this.percent = percent;
  }
}
