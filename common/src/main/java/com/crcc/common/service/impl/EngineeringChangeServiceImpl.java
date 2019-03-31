package com.crcc.common.service.impl;

import com.crcc.common.mapper.EngineeringChangeMonthlyMapper;
import com.crcc.common.mapper.ProjectInfoMapper;
import com.crcc.common.model.EngineerChangeTotal;
import com.crcc.common.model.EngineeringChangeMonthly;
import com.crcc.common.model.ProjectInfo;
import com.crcc.common.service.EngineeringChangeService;
import com.crcc.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class EngineeringChangeServiceImpl implements EngineeringChangeService{

    @Autowired
    private EngineeringChangeMonthlyMapper engineeringChangeMonthlyMapper;

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Override
    public Long addEngineeringChangeMonthly(EngineeringChangeMonthly engineeringChangeMonthly) {
        engineeringChangeMonthly.setCreateTime(new Date());
        supplement(engineeringChangeMonthly);
        engineeringChangeMonthlyMapper.insertSelective(engineeringChangeMonthly);
        return engineeringChangeMonthly.getId();
    }

    @Override
    public boolean updateEngineeringChangeMonthly(EngineeringChangeMonthly engineeringChangeMonthly) {
        engineeringChangeMonthly.setUpdateTime(new Date());
        supplement(engineeringChangeMonthly);
        int result = engineeringChangeMonthlyMapper.updateByPrimaryKeySelective(engineeringChangeMonthly);
        if (result > 0){
            return true;
        }
        return false;
    }

    @Override
    public EngineeringChangeMonthly getDetails(Long id) {
        EngineeringChangeMonthly engineeringChangeMonthly =  getDetails(id);
        if (engineeringChangeMonthly != null){
            resetAmount(engineeringChangeMonthly,0);
        }
        return engineeringChangeMonthly;
    }

    private void resetAmount(EngineeringChangeMonthly engineeringChangeMonthly,Integer type){
        ProjectInfo info = projectInfoMapper.getInfoByProjectId(engineeringChangeMonthly.getProjectId());
        if (info != null && info.getTemporarilyPrice() != null){
            engineeringChangeMonthly.setTemporarilyPrice(info.getTemporarilyPrice());
            if (type == 0 && engineeringChangeMonthly.getChangeClaimAmount() != null){
                engineeringChangeMonthly.setPercentage(Utils.computerDivide(engineeringChangeMonthly.getChangeClaimAmount(),
                        info.getTemporarilyPrice(), 4));
            }else if (type == 1 && engineeringChangeMonthly.getChangeClaimAmountStatistics() != null){
                engineeringChangeMonthly.setPercentageStatistics(Utils.computerDivide(engineeringChangeMonthly.getChangeClaimAmountStatistics(),
                        info.getTemporarilyPrice(),4));
            }
        }
    }

    @Override
    public List<EngineeringChangeMonthly> listForPage(Long projectId, String projectName, String year, Integer quarter, Integer offset, Integer length) {
        List<EngineeringChangeMonthly> engineeringChangeMonthlies = engineeringChangeMonthlyMapper.listForPage(projectName,
                projectId,year,quarter,offset,length);
        engineeringChangeMonthlies.forEach(engineeringChangeMonthly -> {
            resetAmount(engineeringChangeMonthly,0);
        });
        return engineeringChangeMonthlies;
    }

    @Override
    public Integer listForPageSize(String projectName, Long projectId,String year, Integer q) {
        return engineeringChangeMonthlyMapper.listForPageSize(projectName,projectId,year,q);
    }

    @Override
    public List<EngineeringChangeMonthly> listStatisticsForPage(Long projectId, String projectName, Integer offset, Integer length) {
        List<EngineeringChangeMonthly> engineeringChangeMonthlies =
                engineeringChangeMonthlyMapper.listStatisticsForPage(projectName,projectId,offset,length);

        engineeringChangeMonthlies.forEach(engineeringChangeMonthly -> {
            resetAmount(engineeringChangeMonthly,1);
        });

        return engineeringChangeMonthlies;
    }

    @Override
    public Integer listStatisticsForPageSize(Long projectId, String projectName) {
        return engineeringChangeMonthlyMapper.listStatisticsForPageSize(projectName,projectId);
    }

    @Override
    public EngineerChangeTotal getTotal(String projectName, Long projectId, String year, Integer q) {
        List<EngineeringChangeMonthly> engineeringChangeMonthlies = listForPage(projectId,projectName,year,q,null,null);
        EngineerChangeTotal total = new EngineerChangeTotal();
        BigDecimal sumTemporarilyPrice = new BigDecimal(0);
        BigDecimal sumConstructionOutputValue = new BigDecimal(0);
        BigDecimal sumChangeClaimAmount = new BigDecimal(0);
        if (engineeringChangeMonthlies != null && engineeringChangeMonthlies.size() > 0){
            for (EngineeringChangeMonthly engineeringChangeMonthly : engineeringChangeMonthlies){
                sumTemporarilyPrice = Utils.addBigDecimal(sumTemporarilyPrice,engineeringChangeMonthly.getTemporarilyPrice());
                sumChangeClaimAmount = Utils.addBigDecimal(sumChangeClaimAmount,engineeringChangeMonthly.getChangeClaimAmount());
                sumConstructionOutputValue = Utils.addBigDecimal(sumConstructionOutputValue,engineeringChangeMonthly.getConstructionOutputValue());
            }
        }
        if (sumChangeClaimAmount != null && sumTemporarilyPrice != null){
            BigDecimal result = Utils.computerDivide(sumChangeClaimAmount, sumTemporarilyPrice,4);
            total.setSumPercentage(new BigDecimal(result.doubleValue()*100));
        }
        total.setSumChangeClaimAmount(sumChangeClaimAmount);
        total.setSumConstructionOutputValue(sumConstructionOutputValue);
        total.setSumTemporarilyPrice(sumTemporarilyPrice);
        return total;
    }

    @Override
    public EngineerChangeTotal getStatisticsTotal(Long projectId,String projectName) {
        List<EngineeringChangeMonthly> engineeringChangeMonthlyList = listStatisticsForPage(projectId,projectName,null,null);
        EngineerChangeTotal total = new EngineerChangeTotal();
        BigDecimal sumTemporarilyPrice = new BigDecimal(0);
        BigDecimal sumConstructionOutputValue = new BigDecimal(0);
        BigDecimal sumChangeClaimAmount = new BigDecimal(0);
        if (engineeringChangeMonthlyList != null && engineeringChangeMonthlyList.size() > 0){
            for (EngineeringChangeMonthly engineeringChangeMonthly : engineeringChangeMonthlyList){
                sumTemporarilyPrice = Utils.addBigDecimal(sumTemporarilyPrice,engineeringChangeMonthly.getTemporarilyPrice());
                sumChangeClaimAmount = Utils.addBigDecimal(sumChangeClaimAmount,engineeringChangeMonthly.getChangeClaimAmountStatistics());
                sumConstructionOutputValue = Utils.addBigDecimal(sumConstructionOutputValue,engineeringChangeMonthly.getConstructionOutputValueStatistics());
            }
        }
        if (sumChangeClaimAmount != null && sumTemporarilyPrice != null){
            BigDecimal result = Utils.computerDivide(sumChangeClaimAmount, sumTemporarilyPrice,4);
            total.setSumPercentage(new BigDecimal(result.doubleValue()*100));
        }
        total.setSumChangeClaimAmount(sumChangeClaimAmount);
        total.setSumConstructionOutputValue(sumConstructionOutputValue);
        total.setSumTemporarilyPrice(sumTemporarilyPrice);
        return total;
    }

    @Override
    public boolean deleteOneById(Long id) {
        int result = engineeringChangeMonthlyMapper.deleteByPrimaryKey(id);
        if (result != 0)
            return true;
        return false;
    }

    private void supplement(EngineeringChangeMonthly engineeringChangeMonthly){
        BigDecimal changeAmount = engineeringChangeMonthly.getChangeClaimAmount();

        ProjectInfo projectInfo = projectInfoMapper.getInfoByProjectId(engineeringChangeMonthly.getProjectId());

        if (changeAmount != null && projectInfo != null && projectInfo.getTemporarilyPrice() != null){
            BigDecimal result = Utils.computerDivide(changeAmount,projectInfo.getTemporarilyPrice(),4);
            engineeringChangeMonthly.setPercentage(result);
        }

        if (engineeringChangeMonthly.getReportTime() != null){
            engineeringChangeMonthly.setQuarter(Utils.getQuarter(engineeringChangeMonthly.getReportTime()));
        }

    }
}
