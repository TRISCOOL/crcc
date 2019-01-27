package com.crcc.common.service.impl;

import com.crcc.common.mapper.EngineeringChangeMonthlyMapper;
import com.crcc.common.mapper.ProjectInfoMapper;
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
