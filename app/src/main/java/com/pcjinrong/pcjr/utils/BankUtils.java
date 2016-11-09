package com.pcjinrong.pcjr.utils;

/**
 * Created by Mario on 2016/10/25.
 */

public class BankUtils {
    public static String getBankNameById(int id) {
        String name = "";
        switch (id) {
            case 100:
                name = "中国邮政储蓄银行";
                break;
            case 102:
                name = "中国工商银行";
                break;
            case 103:
                name = "中国农业银行";
                break;
            case 104:
                name = "中国银行";
                break;
            case 105:
                name = "中国建设银行";
                break;
            case 301:
                name = "交通银行";
                break;
            case 302:
                name = "中信银行";
                break;
            case 303:
                name = "中国光大银行";
                break;
            case 304:
                name = "华夏银行";
                break;
            case 305:
                name = "中国民生银行";
                break;
            case 306:
                name = "广发银行";
                break;
            case 307:
                name = "平安银行";
                break;
            case 308:
                name = "招商银行";
                break;
            case 309:
                name = "兴业银行";
                break;
            case 310:
                name = "上海浦东发展银行";
                break;
            case 311:
                name = "恒丰银行";
                break;
            case 316:
                name = "浙商银行";
                break;
            case 401:
                name = "上海银行";
                break;
            case 408:
                name = "宁波银行";
                break;
            case 424:
                name = "南京银行";
                break;
            case 470:
                name = "嘉兴银行";
                break;
            case 1563:
                name = "浙江省农村信用社联合社";
                break;
            default:
                break;
        }
        return name;
    }

}
