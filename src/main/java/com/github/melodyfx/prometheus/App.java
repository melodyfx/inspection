package com.github.melodyfx.prometheus;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.melodyfx.prometheus.model.CpuModel;
import com.github.melodyfx.prometheus.model.DiskModel;
import com.github.melodyfx.prometheus.model.DiskioModel;
import com.github.melodyfx.prometheus.model.MemoryModel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 */
public class App {

  private static final Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws Exception {
    //InputStreamReader in = new InputStreamReader(App.class.getClassLoader().getResourceAsStream("config.properties"),"UTF-8");
    InputStreamReader in = new InputStreamReader(new FileInputStream("config.properties"),"UTF-8");
    Properties properties = new Properties();
    properties.load(in);
    String promHosts=properties.getProperty("prom.hosts");
    String threshold=properties.getProperty("threshold");
    String targets=properties.getProperty("exporter.targets");
    String subjects=properties.getProperty("mail.subjects");
    String[] exports=targets.split(",");
    //查询磁盘使用率
    logger.info("===查询磁盘使用率===");
    AssembleInfo assembleInfo=new AssembleInfo();
    StringBuilder sbTotal=new StringBuilder();
    sbTotal.append("<h3>磁盘使用情况:</h3>");
    for (String host : exports) {
      String url1 = promHosts+"/query?query=node_filesystem_size_bytes{instance='"+host+"',device=~'^/.*'}-0";
      String url2 = promHosts+"/query?query=node_filesystem_free_bytes{instance='"+host+"',device=~'^/.*'}-0";
      String url3=promHosts+"/query?query=(1-(node_filesystem_free_bytes{instance='"+host+"',device=~'^/.*'} / node_filesystem_size_bytes{instance='"+host+"',device=~'^/.*'}))*100";
      logger.info(url1);
      logger.info(url2);
      logger.info(url3);
      DiskProcess diskProcess=new DiskProcess();
      List<DiskModel> diskModelTotalList=new ArrayList<>();
      List<DiskModel> diskModelAvailList=new ArrayList<>();
      List<DiskModel> diskModelPercentList=new ArrayList<>();
      diskProcess.process(diskModelTotalList,"total",url1);
      diskProcess.process(diskModelAvailList,"avail",url2);
      diskProcess.process(diskModelPercentList,"percent",url3);
      //组装成新的
      String diskStr=assembleInfo.assembleDisk(diskModelTotalList,diskModelAvailList,diskModelPercentList,threshold);
      sbTotal.append(diskStr);
      sbTotal.append("<br/>");
    }

    //查询内存使用
    logger.info("===查询内存使用===");
    List<MemoryModel> memoryModelList=new ArrayList<>();
    for (String host : exports) {
      String memUrl1 = promHosts + "/query?query=node_memory_MemTotal_bytes{instance='" + host +"'}-0";
      String memUrl2 = promHosts+"/query?query=node_memory_MemAvailable_bytes{instance='"+host+"'} or (node_memory_MemFree_bytes{instance='"+host+"'}+node_memory_Buffers_bytes{instance='"+host+"'}+node_memory_Cached_bytes{instance='"+host+"'})-0";
      logger.info(memUrl1);
      logger.info(memUrl2);
      MemoryProcess memoryProcess = new MemoryProcess();
      MemoryModel totalMemModel=memoryProcess.process( "total", memUrl1);
      MemoryModel availMemModel=memoryProcess.process( "avail", memUrl2);
      MemoryModel newMem = assembleInfo.assembleMemoryOne(totalMemModel, availMemModel);
      memoryModelList.add(newMem);
    }
    String memStr=assembleInfo.assembleMemory(memoryModelList);
    sbTotal.append("<h3>内存使用情况:</h3>");
    sbTotal.append(memStr);
    sbTotal.append("<br/>");

    //查询CPU使用
    logger.info("===查询CPU使用===");
    List<CpuModel> cpuModelList=new ArrayList<>();
    for (String host : exports) {
      String cpuUrl=promHosts+"/query?query=sum(rate(node_cpu_seconds_total{instance='"+host+"',mode='user'}[1m])) * 100/ count(node_cpu_seconds_total{instance='"+host+"',mode='user'})";
      logger.info(cpuUrl);
      CpuProcess cpuProcess=new CpuProcess();
      CpuModel cpuModel=cpuProcess.process(host,cpuUrl);
      cpuModelList.add(cpuModel);
    }
    String cpuStr = assembleInfo.assembleCpu(cpuModelList);
    sbTotal.append("<h3>CPU使用率:</h3>");
    sbTotal.append(cpuStr);
    sbTotal.append("<br/>");

    //查询磁盘IO
    logger.info("===查询磁盘IO===");
    sbTotal.append("<h3>磁盘I/O情况:</h3>");
    for (String host : exports) {
      String url1 = promHosts+"/query?query=irate(node_disk_read_bytes_total{instance='"+host+"'}[5m])";
      String url2 = promHosts+"/query?query=irate(node_disk_written_bytes_total{instance='"+host+"'}[5m])";
      logger.info(url1);
      logger.info(url2);
      DiskioProcess diskioProcess=new DiskioProcess();
      List<DiskioModel> diskioModelReadList=new ArrayList<>();
      List<DiskioModel> diskioModelWrittenList=new ArrayList<>();
      diskioProcess.process(diskioModelReadList,"read",url1);
      diskioProcess.process(diskioModelWrittenList,"written",url2);
      //组装成新的
      String diskStr=assembleInfo.assembleDiskio(diskioModelReadList,diskioModelWrittenList);
      sbTotal.append(diskStr);
      sbTotal.append("<br/>");
    }

    //发送邮件
    logger.info("===发送邮件===");
    DateTime dateTime=new DateTime();
    DateTimeFormatter forPattern = DateTimeFormat.forPattern("yyyy-MM-dd");
    String dateString=forPattern.print(dateTime);
    SendMail sendMail = new SendMail(properties, subjects+dateString, sbTotal.toString());
    sendMail.sendMail();
  }
}
