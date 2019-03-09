package com.crcc.common.service.impl;

import com.crcc.common.mapper.LiabilityCostMapper;
import com.crcc.common.model.LiabilityCost;
import com.crcc.common.model.LiabilityCostForList;
import com.crcc.common.service.LiabilityCostService;
import com.crcc.common.utils.DateTimeUtil;
import com.crcc.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<LiabilityCostForList> listForPage(Long projectId,String projectName, Integer year, Integer month, Integer offset, Integer length) {
        List<LiabilityCost> liabilityCosts = liabilityCostMapper.listForPage(projectId,projectName,year,month,offset,length);
        List<LiabilityCostForList> liabilityCostForLists = new ArrayList<LiabilityCostForList>();
        if (liabilityCosts != null && liabilityCosts.size() > 0){
            List<LiabilityCost> all = liabilityCostMapper.listForPage(projectId,projectName,year,month,offset,length);
            liabilityCosts.forEach(liabilityCost -> {

                LiabilityCostForList liabilityCostForList = new LiabilityCostForList();
                liabilityCostForList.setContractEndTime(liabilityCost.getContractEndTime());
                liabilityCostForList.setContractStartTime(liabilityCost.getContractStartTime());
                liabilityCostForList.setProjectName(liabilityCost.getProjectName());
                liabilityCostForList.setProjectStatus(liabilityCost.getProjectStatus());
                liabilityCostForList.setProjectType(liabilityCost.getProjectType());

                Integer y = DateTimeUtil.getYear(liabilityCost.getReportTime());
                Integer lastYear = y-1;
                LiabilityCost last = getLcByYear(all,lastYear);
                LiabilityCost thisYear = getThisYear(last,liabilityCost);

                liabilityCostForList.setLastYear(last);
                liabilityCostForList.setOpenTired(liabilityCost);
                liabilityCostForList.setThisYear(thisYear);
                liabilityCostForLists.add(liabilityCostForList);
            });

        }
        return liabilityCostForLists;
    }

    private LiabilityCost getThisYear(LiabilityCost last,LiabilityCost mid){
        if (last == null || mid == null)
            return null;

        LiabilityCost result = new LiabilityCost();

        result.setContractPrice(Utils.subtract(mid.getContractPrice(),last.getContractPrice()));

        BigDecimal c9 = Utils.subtract(mid.getConstructionOutputValue(),last.getConstructionOutputValue());
        result.setConstructionOutputValue(c9);

        BigDecimal c10 = Utils.subtract(mid.getSumOwnerTotal(),last.getSumOwnerTotal());
        result.setSumOwnerTotal(c10);

        BigDecimal c11 = Utils.subtract(mid.getOwnerTotal(),last.getOwnerTotal());
        result.setOwnerTotal(c11);
        result.setShouldPrice(Utils.subtract(mid.getShouldPrice(),last.getShouldPrice()));
        result.setCompletedUncalculated(Utils.subtract(mid.getCompletedUncalculated(),last.getCompletedUncalculated()));
        result.setAdvancePricing(Utils.subtract(mid.getAdvancePricing(),last.getAdvancePricing()));

        BigDecimal c15 = Utils.subtract(mid.getBudget(),last.getBudget());
        result.setBudget(c15);

        BigDecimal c16 = Utils.subtract(mid.getActualSum(),last.getActualSum());
        result.setActualSum(c16);

        BigDecimal c17 = Utils.subtract(mid.getActualManage(),last.getActualManage());
        result.setActualManage(c17);
        result.setConfirmPrice(Utils.subtract(mid.getConfirmPrice(),last.getConfirmPrice()));

        BigDecimal c19 = Utils.subtract(mid.getComprehensiveIncome(),last.getComprehensiveIncome());
        result.setComprehensiveIncome(c19);

        result.setUnconfirmPrice(Utils.subtract(mid.getUnconfirmPrice(),last.getUnconfirmPrice()));
        result.setShouldAppropriation(Utils.subtract(mid.getShouldAppropriation(),last.getShouldAppropriation()));

        BigDecimal c26 = Utils.subtract(mid.getRealAppropriation(),last.getRealAppropriation());
        result.setRealAppropriation(c26);

        result.setComprehensiveIncomePercentage(Utils.computerDivide(c19,c10,4));
        BigDecimal mid2 = Utils.subtract(c15,c16);
        result.setCostSuperPercentage(Utils.computerDivide(mid2,c15,4));
        result.setProductionValuePercentage(Utils.computerDivide(c11,c9,4));
        result.setManagementPercentage(Utils.computerDivide(c17,c10,4));
        result.setInPlacePercentage(Utils.computerDivide(c26,c15,4));

        return result;

    }

    private LiabilityCost getLcByYear(List<LiabilityCost> all,Integer year){
        if (all == null)
            return null;

        if (all.size() <= 0)
            return null;

        List<LiabilityCost> liabilityCosts = all.stream().filter(liabilityCost -> {
            if (liabilityCost.getReportTime() !=  null){
                Integer y = DateTimeUtil.getYear(liabilityCost.getReportTime());
                if (y.equals(year))
                    return true;
            }
            return false;
        }).collect(Collectors.toList());

        if (liabilityCosts == null || liabilityCosts.size() <= 0){
            LiabilityCost liabilityCost = new LiabilityCost();
            liabilityCost.setInPlacePercentage(new BigDecimal(0));
            liabilityCost.setUnconfirmPrice(new BigDecimal(0));
            liabilityCost.setManagementPercentage(new BigDecimal(0));
            liabilityCost.setProductionValuePercentage(new BigDecimal(0));
            liabilityCost.setCostSuperPercentage(new BigDecimal(0));
            liabilityCost.setComprehensiveIncomePercentage(new BigDecimal(0));
            liabilityCost.setSumOwnerTotal(new BigDecimal(0));
            liabilityCost.setActualManage(new BigDecimal(0));
            liabilityCost.setActualSum(new BigDecimal(0));
            liabilityCost.setAdvancePricing(new BigDecimal(0));
            liabilityCost.setBudget(new BigDecimal(0));
            liabilityCost.setCompletedUncalculated(new BigDecimal(0));
            liabilityCost.setConfirmPrice(new BigDecimal(0));
            liabilityCost.setConstructionOutputValue(new BigDecimal(0));
            liabilityCost.setContractPrice(new BigDecimal(0));
            liabilityCost.setOwnerTotal(new BigDecimal(0));
            liabilityCost.setRealAppropriation(new BigDecimal(0));
            liabilityCost.setShouldAppropriation(new BigDecimal(0));
            liabilityCost.setShouldPrice(new BigDecimal(0));
            liabilityCost.setComprehensiveIncome(new BigDecimal(0));
            return liabilityCost;
        }

        liabilityCosts.sort((a,b)->{
            Long report1 = a.getReportTime().getTime()/1000;
            Long report2 = b.getReportTime().getTime()/1000;
            return Integer.parseInt(report1+"")-Integer.parseInt(report2+"");
        });
        return liabilityCosts.get(liabilityCosts.size()-1);
    }

    @Override
    public Integer listForPageSize(Long projectId,String projectName, Integer year, Integer month) {
        return liabilityCostMapper.listForPageSize(projectId,projectName,year,month);
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
