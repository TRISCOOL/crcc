package com.crcc.common.model;

import java.math.BigDecimal;

public class LaborAccountTotal {

    private BigDecimal sumEstimatedContractAmount;

    private BigDecimal sumShouldAmount;

    private BigDecimal sumRealAmount;

    private BigDecimal sumSettlementAmount;

    public BigDecimal getSumEstimatedContractAmount() {
        return sumEstimatedContractAmount;
    }

    public void setSumEstimatedContractAmount(BigDecimal sumEstimatedContractAmount) {
        this.sumEstimatedContractAmount = sumEstimatedContractAmount;
    }

    public BigDecimal getSumShouldAmount() {
        return sumShouldAmount;
    }

    public void setSumShouldAmount(BigDecimal sumShouldAmount) {
        this.sumShouldAmount = sumShouldAmount;
    }

    public BigDecimal getSumRealAmount() {
        return sumRealAmount;
    }

    public void setSumRealAmount(BigDecimal sumRealAmount) {
        this.sumRealAmount = sumRealAmount;
    }

    public BigDecimal getSumSettlementAmount() {
        return sumSettlementAmount;
    }

    public void setSumSettlementAmount(BigDecimal sumSettlementAmount) {
        this.sumSettlementAmount = sumSettlementAmount;
    }
}
