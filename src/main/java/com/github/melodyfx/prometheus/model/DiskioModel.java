package com.github.melodyfx.prometheus.model;

/**
 * @Author: huangzhimao
 * @Date: 2021-05-21
 */
public class DiskioModel {
  private String device;
  private String instance;
  private String time;
  private String read;
  private String written;

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

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

  public String getRead() {
    return read;
  }

  public void setRead(String read) {
    this.read = read;
  }

  public String getWritten() {
    return written;
  }

  public void setWritten(String written) {
    this.written = written;
  }
}
