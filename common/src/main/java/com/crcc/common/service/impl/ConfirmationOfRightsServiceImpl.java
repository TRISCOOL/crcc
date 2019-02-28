package com.crcc.common.service.impl;

import com.crcc.common.exception.CrccException;
import com.crcc.common.mapper.ConfirmationOfRightsMapper;
import com.crcc.common.model.ConfirmationOfRights;
import com.crcc.common.service.ConfirmationOfRightsService;
import com.crcc.common.utils.DateTimeUtil;
import com.crcc.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ConfirmationOfRightsServiceImpl implements ConfirmationOfRightsService{

    @Autowired
    private ConfirmationOfRightsMapper confirmationOfRightsMapper;

    @Override
    public ConfirmationOfRights add(ConfirmationOfRights confirmationOfRights) {
        confirmationOfRights.setCreateTime(new Date());
        supplement(confirmationOfRights);
        confirmationOfRightsMapper.insertSelective(confirmationOfRights);
        return confirmationOfRights;
    }

    @Override
    public boolean updateConfirmationOfRights(ConfirmationOfRights confirmationOfRights) {
        if (confirmationOfRights.getId() == null)
            throw new CrccException("id is not null when update");

        supplement(confirmationOfRights);
        int result = confirmationOfRightsMapper.updateByPrimaryKeySelective(confirmationOfRights);
        if (result != 0){
            return true;
        }
        return false;
    }

    @Override
    public List<ConfirmationOfRights> listForPage(Long projectId, String projectName, String year, String quarter, Integer offset, Integer length) {
        List<ConfirmationOfRights> confirmationOfRights = confirmationOfRightsMapper.listForPage(projectId,
                projectName,year,quarter,offset,length);

        confirmationOfRights.forEach(confirmationOfRight ->{
            String reportYear = confirmationOfRight.getYear();
            //可能存在一种特殊情况，就是第一次使用的时候，可能会在数据库默认填写年初余额，即这部分数据不采用获取上一年的数据
            if (reportYear != null && reportYear.equals("2019") &&
                    confirmationOfRight.getBalanceInspectionValue() != null &&
                    confirmationOfRight.getBalanceCompleteValue() != null &&
                    confirmationOfRight.getBalanceShould() != null &&
                    confirmationOfRight.getBalanceChange() != null){
                confirmationOfRight.setSumBalance(confirmationOfRight.getBalanceShould().add(confirmationOfRight.getBalanceChange()));
            }else {
                Integer lastYear = Integer.parseInt(reportYear) - 1;
                ConfirmationOfRights lastConfirmation =
                        confirmationOfRightsMapper.foundConfirmByProjectAndYear(confirmationOfRight.getProjectId(),lastYear+"");
                confirmationOfRight.setBalanceCompleteValue(lastConfirmation.getCompletedValue());
                confirmationOfRight.setBalanceInspectionValue(lastConfirmation.getInspection());
                confirmationOfRight.setBalanceChange(lastConfirmation.getFinalPeriodChange());
                confirmationOfRight.setBalanceShould(lastConfirmation.getFinalPeriodShould());
                if (lastConfirmation.getFinalPeriodChange() != null && lastConfirmation.getFinalPeriodShould() != null){
                    confirmationOfRight.setSumBalance(lastConfirmation.getFinalPeriodChange().add(lastConfirmation.getFinalPeriodShould()));
                }
            }
        });
        return confirmationOfRights;
    }

    @Override
    public Integer listForPageSize(Long projectId, String projectName, String year, String quarter) {
        return confirmationOfRightsMapper.listForPageSize(projectId,projectName,year,quarter);
    }

    @Override
    public ConfirmationOfRights foundConfirmForLastYear(Long projectId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer year = calendar.get(Calendar.YEAR);
        ConfirmationOfRights last = confirmationOfRightsMapper.foundConfirmByProjectAndYear(projectId,
                year+"");

        ConfirmationOfRights confirmationOfRights = new ConfirmationOfRights();
        if (last == null || last.getId() == null){
            confirmationOfRights.setBalanceChange(new BigDecimal(0));
            confirmationOfRights.setBalanceCompleteValue(new BigDecimal(0));
            confirmationOfRights.setBalanceInspectionValue(new BigDecimal(0));
            confirmationOfRights.setBalanceShould(new BigDecimal(0));
            confirmationOfRights.setSumBalance(new BigDecimal(0));
        }else {
            confirmationOfRights.setBalanceShould(last.getFinalPeriodShould());
            confirmationOfRights.setBalanceChange(last.getFinalPeriodChange());
            confirmationOfRights.setBalanceInspectionValue(last.getInspection());
            confirmationOfRights.setBalanceCompleteValue(last.getCompletedValue());
            if (last.getFinalPeriodShould() != null && last.getFinalPeriodChange() != null){
                confirmationOfRights.setSumBalance(last.getFinalPeriodChange().add(last.getFinalPeriodShould()));
            }
        }
        return confirmationOfRights;
    }

    public void supplement(ConfirmationOfRights confirmationOfRights){

        if (confirmationOfRights.getReportTime() != null){
            confirmationOfRights.setYear(DateTimeUtil.getYear(confirmationOfRights.getReportTime())+"");
            confirmationOfRights.setQuarter(Utils.getQuarter(confirmationOfRights.getReportTime())+"");
        }

        if (confirmationOfRights.getHalfCompletedValue() != null && confirmationOfRights.getOneCompletedValue() != null){
            BigDecimal sum = confirmationOfRights.getHalfCompletedValue().add(confirmationOfRights.getOneCompletedValue());
            confirmationOfRights.setSumHalfOne(sum);
            if (confirmationOfRights.getBalanceInspectionValue() != null){
                confirmationOfRights.setInspection(sum.add(confirmationOfRights.getBalanceInspectionValue()));
            }
        }

        if (confirmationOfRights.getBalanceCompleteValue() != null && confirmationOfRights.getCurrentProductionValue() != null){
            confirmationOfRights.setCompletedValue(confirmationOfRights.getBalanceCompleteValue().add(confirmationOfRights.getCurrentProductionValue()));
        }

        if (confirmationOfRights.getFinalPeriodShould() != null && confirmationOfRights.getFinalPeriodChange() != null){
            confirmationOfRights.setSumFinalPeriod(confirmationOfRights.getFinalPeriodChange().add(confirmationOfRights.getFinalPeriodShould()));
        }
    }
}
