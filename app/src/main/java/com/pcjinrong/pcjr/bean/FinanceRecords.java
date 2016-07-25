package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

/**
 * 资金记录实体类
 * Created by Mario on 2016/7/25.
 */
public class FinanceRecords {
    @Expose
    private String available_balance;
    @Expose
    private String capital;
    @Expose
    private String interest;
    @Expose
    private String earned_interest;
    @Expose
    private String reward_amount;
    @Expose
    private String total_reward_amount;
    @Expose
    private String recharge_success_amount;
    @Expose
    private String total_amount;
    @Expose
    private String invest_success_amount;
    @Expose
    private String withdraw_success_amount;

    public String getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(String available_balance) {
        this.available_balance = available_balance;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getEarned_interest() {
        return earned_interest;
    }

    public void setEarned_interest(String earned_interest) {
        this.earned_interest = earned_interest;
    }

    public String getReward_amount() {
        return reward_amount;
    }

    public void setReward_amount(String reward_amount) {
        this.reward_amount = reward_amount;
    }

    public String getTotal_reward_amount() {
        return total_reward_amount;
    }

    public void setTotal_reward_amount(String total_reward_amount) {
        this.total_reward_amount = total_reward_amount;
    }

    public String getRecharge_success_amount() {
        return recharge_success_amount;
    }

    public void setRecharge_success_amount(String recharge_success_amount) {
        this.recharge_success_amount = recharge_success_amount;
    }

    public String getInvest_success_amount() {
        return invest_success_amount;
    }

    public void setInvest_success_amount(String invest_success_amount) {
        this.invest_success_amount = invest_success_amount;
    }

    public String getWithdraw_success_amount() {
        return withdraw_success_amount;
    }

    public void setWithdraw_success_amount(String withdraw_success_amount) {
        this.withdraw_success_amount = withdraw_success_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
}
