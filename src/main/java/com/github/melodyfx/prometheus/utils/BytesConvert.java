package com.github.melodyfx.prometheus.utils;

import java.text.DecimalFormat;

/**
 * @Author: huangzhimao
 * @Date: 2021-05-24
 */
public class BytesConvert {
  public static String getNetFileSizeDescription(long size) {
    StringBuffer bytes = new StringBuffer();
    DecimalFormat format = new DecimalFormat("###.0");
    if (size >= 1024 * 1024 * 1024 * 1024L) {
      double i = (size / (1024.0 * 1024.0 * 1024.0 * 1024.0));
      bytes.append(format.format(i)).append("TB");
    }
    else if (size >= 1024 * 1024 * 1024) {
      double i = (size / (1024.0 * 1024.0 * 1024.0));
      bytes.append(format.format(i)).append("GB");
    }
    else if (size >= 1024 * 1024) {
      double i = (size / (1024.0 * 1024.0));
      bytes.append(format.format(i)).append("MB");
    }
    else if (size >= 1024) {
      double i = (size / (1024.0));
      bytes.append(format.format(i)).append("KB");
    }
    else if (size < 1024) {
      if (size <= 0) {
        bytes.append("0B");
      }
      else {
        bytes.append((int) size).append("B");
      }
    }
    return bytes.toString();
  }

}
