package com.github.melodyfx.prometheus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.github.melodyfx.prometheus.model.CpuModel;
import com.github.melodyfx.prometheus.model.DiskModel;
import com.github.melodyfx.prometheus.model.DiskioModel;
import com.github.melodyfx.prometheus.model.MemoryModel;
import org.apache.commons.beanutils.BeanUtils;

/**
 * @Author: huangzhimao
 * @Date: 2021-05-24
 */
public class AssembleInfo {
  public String assembleDisk(List<DiskModel> diskModelTotalList, List<DiskModel> diskModelAvailList, List<DiskModel> diskModelPercentList,String threshold) throws Exception{
    List<DiskModel> diskModelNew=new ArrayList<>();
    for (DiskModel diskModel : diskModelTotalList) {
      DiskModel newDisk=new DiskModel();
      BeanUtils.copyProperties(newDisk,diskModel);
      for (DiskModel model : diskModelAvailList) {
        if(diskModel.getDevice().equals(model.getDevice())){
          newDisk.setAvail(model.getAvail());
        }
      }
      diskModelNew.add(newDisk);
    }

    List<DiskModel> diskModelNew2=new ArrayList<>();
    for (DiskModel diskModel : diskModelNew) {
      DiskModel newDisk=new DiskModel();
      BeanUtils.copyProperties(newDisk,diskModel);
      for (DiskModel model : diskModelPercentList) {
        if(diskModel.getDevice().equals(model.getDevice())){
          newDisk.setPercent(model.getPercent());
        }
      }
      diskModelNew2.add(newDisk);
    }

    StringBuilder sb=new StringBuilder();
    sb.append("<table style=\"width:100%;\" cellpadding=\"2\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\"><tbody>");

    sb.append("<tr>");
    sb.append("<td>"+"device"+"</td>");
    sb.append("<td>"+"fstype"+"</td>");
    sb.append("<td>"+"instance"+"</td>");
    sb.append("<td>"+"mountpoint"+"</td>");
    sb.append("<td>"+"time"+"</td>");
    sb.append("<td>"+"total"+"</td>");
    sb.append("<td>"+"avail"+"</td>");
    sb.append("<td>"+"used_percent"+"</td>");
    sb.append("</tr>");

    for (DiskModel diskModel : diskModelNew2) {
      sb.append("<tr>");
      sb.append("<td>"+diskModel.getDevice()+"</td>");
      sb.append("<td>"+diskModel.getFstype()+"</td>");
      sb.append("<td>"+diskModel.getInstance()+"</td>");
      sb.append("<td>"+diskModel.getMountpoint()+"</td>");
      sb.append("<td>"+diskModel.getTime()+"</td>");
      sb.append("<td>"+diskModel.getTotal()+"</td>");
      sb.append("<td>"+diskModel.getAvail()+"</td>");
      String percent=diskModel.getPercent().split("%")[0];
      BigDecimal ori=new BigDecimal(percent);
      BigDecimal thres=new BigDecimal(threshold);
      if(ori.compareTo(thres) > -1){
        sb.append("<td><font color=\"red\">"+diskModel.getPercent()+"</font></td>");
      }else{
        sb.append("<td>"+diskModel.getPercent()+"</td>");
      }
      sb.append("</tr>");
    }
    sb.append("</tbody></table>");
    return sb.toString();
  }

  public String assembleDiskio(List<DiskioModel> diskioModelReadList, List<DiskioModel> diskioModelWrittenList) throws Exception{
    List<DiskioModel> diskioModelNew=new ArrayList<>();
    for (DiskioModel diskioModel : diskioModelReadList) {
      DiskioModel newioDisk=new DiskioModel();
      BeanUtils.copyProperties(newioDisk,diskioModel);
      for (DiskioModel model : diskioModelWrittenList) {
        if(diskioModel.getDevice().equals(model.getDevice())){
          newioDisk.setWritten(model.getWritten());
        }
      }
      diskioModelNew.add(newioDisk);
    }

    StringBuilder sb=new StringBuilder();
    sb.append("<table style=\"width:100%;\" cellpadding=\"2\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\"><tbody>");

    sb.append("<tr>");
    sb.append("<td>"+"device"+"</td>");
    sb.append("<td>"+"instance"+"</td>");
    sb.append("<td>"+"time"+"</td>");
    sb.append("<td>"+"read"+"</td>");
    sb.append("<td>"+"writen"+"</td>");
    sb.append("</tr>");

    for (DiskioModel diskModel : diskioModelNew) {
      sb.append("<tr>");
      sb.append("<td>"+diskModel.getDevice()+"</td>");
      sb.append("<td>"+diskModel.getInstance()+"</td>");
      sb.append("<td>"+diskModel.getTime()+"</td>");
      sb.append("<td>"+diskModel.getRead()+"</td>");
      sb.append("<td>"+diskModel.getWritten()+"</td>");
      sb.append("</tr>");
    }
    sb.append("</tbody></table>");
    return sb.toString();
  }

  public MemoryModel assembleMemoryOne(MemoryModel totalMemModel, MemoryModel availMemModel) throws Exception{
      MemoryModel newMem=new MemoryModel();
      BeanUtils.copyProperties(newMem,totalMemModel);
      newMem.setAvail(availMemModel.getAvail());
    return newMem;
  }

  public String assembleMemory(List<MemoryModel> memoryModelList) throws Exception{
    StringBuilder sb=new StringBuilder();
    sb.append("<table style=\"width:100%;\" cellpadding=\"2\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\"><tbody>");

    sb.append("<tr>");
    sb.append("<td>"+"instance"+"</td>");
    sb.append("<td>"+"time"+"</td>");
    sb.append("<td>"+"total"+"</td>");
    sb.append("<td>"+"avail"+"</td>");
    sb.append("</tr>");

    for (MemoryModel memModel : memoryModelList) {
      sb.append("<tr>");
      sb.append("<td>"+memModel.getInstance()+"</td>");
      sb.append("<td>"+memModel.getTime()+"</td>");
      sb.append("<td>"+memModel.getTotal()+"</td>");
      sb.append("<td>"+memModel.getAvail()+"</td>");
      sb.append("</tr>");
    }
    sb.append("</tbody></table>");
    return sb.toString();
  }

  public String assembleCpu(List<CpuModel> cpuModelList) throws Exception{

    StringBuilder sb=new StringBuilder();
    sb.append("<table style=\"width:100%;\" cellpadding=\"2\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\"><tbody>");

    sb.append("<tr>");
    sb.append("<td>"+"instance"+"</td>");
    sb.append("<td>"+"time"+"</td>");
    sb.append("<td>"+"used_percent"+"</td>");
    sb.append("</tr>");

    for (CpuModel cpuModel : cpuModelList) {
      sb.append("<tr>");
      sb.append("<td>"+cpuModel.getInstance()+"</td>");
      sb.append("<td>"+cpuModel.getTime()+"</td>");
      sb.append("<td>"+cpuModel.getPercent()+"</td>");
      sb.append("</tr>");
    }
    sb.append("</tbody></table>");
    return sb.toString();
  }
}
