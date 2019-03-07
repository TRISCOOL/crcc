package com.crcc.common.service.impl;

import com.crcc.common.mapper.LiabilityCostMapper;
import com.crcc.common.model.LiabilityCost;
import com.crcc.common.service.LiabilityCostService;
import com.crcc.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class LiabilityCostServiceImpl implements LiabilityCostService{

    @Autowired
    private LiabilityCostMapper liabilityCostMapper;

    @Override
    public Long add(LiabilityCost liabilityCost) {
        liabilityCost.setCreateTime(new Date());
        supplement(liabilityCost);
        liabilityCostMapper.insertSelective(liabilityCost);
        return liabilityCost.getId();
    }

    @Override
    public boolean update(LiabilityCost liabilityCost) {
        liabilityCost.setUpdateTime(new Date());
        supplement(liabilityCost);
        int result = liabilityCostMapper.updateByPrimaryKeySelective(liabilityCost);
        if (result != 0){
            return true;
        }
        return false;
    }

    @Override
    public List<LiabilityCost> listForPage(String projectName, Integer year, Integer month, Integer offset, Integer length) {
        return null;
    }

    @Override
    public Integer listForPageSize(String projectName, Integer year, Integer month) {
        return null;
    }

    private void supplement(LiabilityCost liabilityCost){
        //业主收入小计
        BigDecimal mid = Utils.addBigDecimal(liabilityCost.getAdvancePricing(),liabilityCost.getCompletedUncalculated());
        BigDecimal sumOwnerTotal = Utils.addBigDecimal(mid,liabilityCost.getOwnerTotal());
        liabilityCost.setSumOwnerTotal(sumOwnerTotal);

        //项目综合收益额
        BigDecimal comprehensiveIncome = Utils.subtract(sumOwnerTotal,liabilityCost.getActualSum());
        liabilityCost.setComprehensiveIncome(comprehensiveIncome);

        //项目综合收益率（%）
        liabilityCost.setComprehensiveIncomePercentage(Utils.computerDivide(comprehensiveIncome,sumOwnerTotal,4));

        //责任成本节超率（%）
        BigDecimal bcs = Utils.subtract(liabilityCost.getBudget(),liabilityCost.getActualSum());
        liabilityCost.setCostSuperPercentage(Utils.computerDivide(bcs,liabilityCost.getBudget(),4));

        //产值计价率%）
        liabilityCost.setProductionValuePercentage(Utils.computerDivide(liabilityCost.getOwnerTotal(),
                liabilityCost.getConstructionOutputValue(),4));

        //产值计价率%）
        liabilityCost.setManagementPercentage(Utils.computerDivide(liabilityCost.getActualManage(),sumOwnerTotal,4));

        //财务未确认利润（+潜盈-潜亏）
        liabilityCost.setUnconfirmPrice(Utils.subtract(comprehensiveIncome,liabilityCost.getConfirmPrice()));

        //项目资金拨付到位率（%）
        liabilityCost.setInPlacePercentage(Utils.computerDivide(liabilityCost.getRealAppropriation(),liabilityCost.getBudget(),4));





    }
}
