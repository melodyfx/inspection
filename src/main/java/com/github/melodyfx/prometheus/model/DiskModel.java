package com.github.melodyfx.prometheus.model;

/**
 * @Author: huangzhimao
 * @Date: 2021-05-21
 */
public class DiskModel {
  private String device;
  private String fstype;
  private String instance;
  private String mountpoint;
  private String time;
  private String total;
  private String avail;
  private String percent;

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getFstype() {
    return fstype;
  }

  public void setFstype(String fstype) {
    this.fstype = fstype;
  }

  public String getInstance() {
    return instance;
  }

  public void setInstance(String instance) {
    this.instance = instance;
  }

  public String getMountpoint() {
    return mountpoint;
  }

  public void setMountpoint(String mountpoint) {
    this.mountpoint = mountpoint;
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

  public String getPercent() {
    return percent;
  }

  public void setPercent(String percent) {
    this.percent = percent;
  }
}
