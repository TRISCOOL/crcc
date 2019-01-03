package com.crcc.common.service.impl;

import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.mapper.MeteringAccountMapper;
import com.crcc.common.model.MeteringAccount;
import com.crcc.common.service.ForUpAccountService;
import com.crcc.common.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class ForUpAccountServiceImpl implements ForUpAccountService{

    @Autowired
    private MeteringAccountMapper meteringAccountMapper;

    @Override
    public Long addMeteringAccount(MeteringAccount meteringAccount) {
        meteringAccount.setCreateTime(new Date());
        supplement(meteringAccount);
        meteringAccountMapper.insertSelective(meteringAccount);
        return meteringAccount.getId();
    }

    @Override
    public boolean updateMeteringAccount(MeteringAccount meteringAccount) {
        if (meteringAccount.getId() == null)
            throw new CrccException(ResponseCode.PARAM_ILLEGAL);

        supplement(meteringAccount);
        meteringAccount.setUpdateTime(new Date());
        int result = meteringAccountMapper.updateByPrimaryKeySelective(meteringAccount);
        if (result != 0)
            return true;
        return false;
    }

    @Override
    public MeteringAccount getMeteringAccountDetails(Long meteringAccountId) {
        return meteringAccountMapper.getDetailsById(meteringAccountId);
    }

    @Override
    public List<MeteringAccount> listForPage(Long projectId, String projectName,
                                             Date meteringNum, Double minPayProportion,
                                             Double maxPayProportion, Double minProductionValue,
                                             Double maxProductionValue, Integer offset, Integer length) {

        String time = DateTimeUtil.getYYYYMM(meteringNum);
        return meteringAccountMapper.listMeteringAccountForPage(projectId,projectName,time,
                minPayProportion,maxPayProportion,minProductionValue,maxProductionValue,offset,length);
    }

    @Override
    public Integer listForPageSize(Long projectId, String projectName, Date meteringNum, Double minPayProportion, Double maxPayProportion, Double minProductionValue, Double maxProductionValue) {
        String time = DateTimeUtil.getYYYYMM(meteringNum);
        return meteringAccountMapper.listMeteringAccountForPageSize(projectId,projectName,time,
                minPayProportion,maxPayProportion,minProductionValue,maxProductionValue);
    }

    private void supplement(MeteringAccount meteringAccount){
        if (meteringAccount.getValuationAmountTax() != null && meteringAccount.getTax() != null){
            meteringAccount.setValuationAmountNotTax(computerNotTax(meteringAccount.getValuationAmountTax(),
                    meteringAccount.getTax().divide(new BigDecimal(100))));
        }

        if (meteringAccount.getRealAmountTax() != null && meteringAccount.getTax() != null){
            meteringAccount.setRealAmount(computerNotTax(meteringAccount.getRealAmountTax(),meteringAccount.getTax().divide(new BigDecimal(100))));
        }

        if (meteringAccount.getAlreadyPaidAmount() != null && meteringAccount.getRealAmountTax() != null){
            meteringAccount.setUnpaidAmount(computerReduce(meteringAccount.getRealAmountTax(),
                    meteringAccount.getAlreadyPaidAmount()));
        }

        if (meteringAccount.getAlreadyPaidAmount() != null && meteringAccount.getRealAmountTax() != null){
            meteringAccount.setpayProportion(computerDivide(meteringAccount.getAlreadyPaidAmount(),meteringAccount.getRealAmountTax()));
        }

        if (meteringAccount.getValuationAmountTax() != null && meteringAccount.getExtraAmount() != null
                && meteringAccount.getNotCalculatedAmount() != null){
            BigDecimal midValue = add(meteringAccount.getExtraAmount(),
                    meteringAccount.getNotCalculatedAmount(),
                    meteringAccount.getValuationAmountTax());
            meteringAccount.setProductionValue(computerDivide(meteringAccount.getValuationAmountTax(),midValue));
        }
    }

    private BigDecimal computerNotTax(BigDecimal amount,BigDecimal tax){
        BigDecimal realTax = tax.add(new BigDecimal(1));
        return amount.divide(realTax,2,BigDecimal.ROUND_HALF_DOWN);
    }

    private BigDecimal computerReduce(BigDecimal first,BigDecimal second){
        return first.subtract(second);
    }

    private BigDecimal computerDivide(BigDecimal bcs,BigDecimal cs){
        if (bcs.doubleValue() == 0d)
            return new BigDecimal(0);

        return bcs.divide(cs,2,BigDecimal.ROUND_HALF_DOWN);
    }

    private BigDecimal add(BigDecimal first,BigDecimal second,BigDecimal third){
        if (first != null && second != null && third != null){
            return first.add(second).add(third);
        }
        return null;
    }
}
