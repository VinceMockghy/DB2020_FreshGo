package xyz.zghy.freshgo.model;

import java.util.Date;

/**
 * @author ghy
 * @date 2020/7/4 上午12:09
 */
public class BeanLimitTimePromotion {
    private int limitTimePromotionId;
    private int limitTimePromotionOrder;
    private int goodsId;
    private String goodsName;
    private int limitTimePromotionPrice;
    private int limitTimePromotionCount;
    private Date limitTimePromotionStartTime;
    private Date limitTimePromotionEndTime;

    public int getLimitTimePromotionOrder() {
        return limitTimePromotionOrder;
    }

    public void setLimitTimePromotionOrder(int limitTimePromotionOrder) {
        this.limitTimePromotionOrder = limitTimePromotionOrder;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getLimitTimePromotionId() {
        return limitTimePromotionId;
    }

    public void setLimitTimePromotionId(int limitTimePromotionId) {
        this.limitTimePromotionId = limitTimePromotionId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getLimitTimePromotionPrice() {
        return limitTimePromotionPrice;
    }

    public void setLimitTimePromotionPrice(int limitTimePromotionPrice) {
        this.limitTimePromotionPrice = limitTimePromotionPrice;
    }

    public int getLimitTimePromotionCount() {
        return limitTimePromotionCount;
    }

    public void setLimitTimePromotionCount(int limitTimePromotionCount) {
        this.limitTimePromotionCount = limitTimePromotionCount;
    }

    public Date getLimitTimePromotionStartTime() {
        return limitTimePromotionStartTime;
    }

    public void setLimitTimePromotionStartTime(Date limitTimePromotionStartTime) {
        this.limitTimePromotionStartTime = limitTimePromotionStartTime;
    }

    public Date getLimitTimePromotionEndTime() {
        return limitTimePromotionEndTime;
    }

    public void setLimitTimePromotionEndTime(Date limitTimePromotionEndTime) {
        this.limitTimePromotionEndTime = limitTimePromotionEndTime;
    }
}
