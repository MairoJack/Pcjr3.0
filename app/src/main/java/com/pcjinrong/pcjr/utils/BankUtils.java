package com.pcjinrong.pcjr.utils;

import com.pcjinrong.pcjr.R;

/**
 * Created by Mario on 2016/10/25.
 */

public class BankUtils {

    private static  Bank bank = new Bank();

    public static Bank getBankById(int id) {

        switch (id) {
            case 100:
                bank.setName("中国邮政储蓄银行");
                bank.setInfo("单笔限额5万，单日限额60万");
                bank.setIcon(R.mipmap.icon_bank_100);
                break;
            case 102:
                bank.setName("中国工商银行");
                bank.setInfo("单笔限额20万，单日限额20万");
                bank.setIcon(R.mipmap.icon_bank_102);
                break;
            case 103:
                bank.setName("中国农业银行");
                bank.setInfo("单笔限额5万，单日限额5万");
                bank.setIcon(R.mipmap.icon_bank_103);
                break;
            case 104:
                bank.setName("中国银行");
                bank.setInfo("单笔限额5万，单日限额30万");
                bank.setIcon(R.mipmap.icon_bank_104);
                break;
            case 105:
                bank.setName("中国建设银行");
                bank.setInfo("单笔限额20万，单日限额200万");
                bank.setIcon(R.mipmap.icon_bank_105);
                break;
            case 301:
                bank.setName("交通银行");
                bank.setInfo("单笔限额20万，单日限额50万");
                bank.setIcon(R.mipmap.icon_bank_301);
                break;
            case 302:
                bank.setName("中信银行");
                bank.setInfo("单笔限额5千，单日限额5千");
                bank.setIcon(R.mipmap.icon_bank_302);
                break;
            case 303:
                bank.setName("中国光大银行");
                bank.setInfo("单笔限额20万，单日无限额");
                bank.setIcon(R.mipmap.icon_bank_303);
                break;
            case 304:
                bank.setName("华夏银行");
                bank.setInfo("单笔限额10万，单日限额100万");
                bank.setIcon(R.mipmap.icon_bank_304);
                break;
            case 305:
                bank.setName("中国民生银行");
                bank.setInfo("单笔限额20万，单日限额20万");
                bank.setIcon(R.mipmap.icon_bank_305);
                break;
            case 306:
                bank.setName("广发银行");
                bank.setInfo("单笔限额20万，单日限额100万");
                bank.setIcon(R.mipmap.icon_bank_306);
                break;
            case 307:
                bank.setName("平安银行");
                bank.setInfo("单笔限额20万，单日限额100万");
                bank.setIcon(R.mipmap.icon_bank_307);
                break;
            case 308:
                bank.setName("招商银行");
                bank.setInfo("单笔限额5万，单日限额5万");
                bank.setIcon(R.mipmap.icon_bank_308);
                break;
            case 309:
                bank.setName("兴业银行");
                bank.setInfo("单笔限额5万，单日限额5万");
                bank.setIcon(R.mipmap.icon_bank_309);
                break;
            case 310:
                bank.setName("上海浦东发展银行");
                bank.setInfo("单笔限额20万，单日限额20万");
                bank.setIcon(R.mipmap.icon_bank_310);
                break;
            case 316:
                bank.setName("浙商银行");
                bank.setInfo("单笔限额5万，单日限额5万");
                bank.setIcon(R.mipmap.icon_bank_316);
                break;
            case 401:
                bank.setName("上海银行");
                bank.setInfo("单笔限额10万，单日限额100万");
                bank.setIcon(R.mipmap.icon_bank_401);
                break;
            default:
                break;
        }
        return bank;
    }
}