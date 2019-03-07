package com.crcc.common.model;

import java.math.BigDecimal;

public class EngineerChangeTotal {
    private BigDecimal sumTemporarilyPrice; //合同金额
    private BigDecimal sumConstructionOutputValue; //施工产值
    private BigDecimal sumChangeClaimAmount; //变更索赔额
    private BigDecimal sumPercentage; //变更索赔率（%）

    public BigDecimal getSumPercentage() {
        return sumPercentage;
    }

    public void setSumPercentage(BigDecimal sumPercentage) {
        this.sumPercentage = sumPercentage;
    }

    public BigDecimal getSumTemporarilyPrice() {
        return sumTemporarilyPrice;
    }

    public void setSumTemporarilyPrice(BigDecimal sumTemporarilyPrice) {
        this.sumTemporarilyPrice = sumTemporarilyPrice;
    }

    public BigDecimal getSumConstructionOutputValue() {
        return sumConstructionOutputValue;
    }

    public void setSumConstructionOutputValue(BigDecimal sumConstructionOutputValue) {
        this.sumConstructionOutputValue = sumConstructionOutputValue;
    }

    public BigDecimal getSumChangeClaimAmount() {
        return sumChangeClaimAmount;
    }

    public void setSumChangeClaimAmount(BigDecimal sumChangeClaimAmount) {
        this.sumChangeClaimAmount = sumChangeClaimAmount;
    }
}
