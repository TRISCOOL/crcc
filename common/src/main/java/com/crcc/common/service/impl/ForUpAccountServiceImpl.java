package com.crcc.common.service.impl;

import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.mapper.MeteringAccountMapper;
import com.crcc.common.model.MeteringAccount;
import com.crcc.common.service.ForUpAccountService;
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

        return meteringAccountMapper.listMeteringAccountForPage(projectId,projectName,meteringNum,
                minPayProportion,maxPayProportion,minProductionValue,maxProductionValue,offset,length);
    }

    private void supplement(MeteringAccount meteringAccount){
        if (meteringAccount.getValuationAmountTax() != null && meteringAccount.getTax() != null){
            meteringAccount.setValuationAmountNotTax(computerNotTax(meteringAccount.getValuationAmountTax(),
                    meteringAccount.getTax()));
        }

        if (meteringAccount.getRealAmountTax() != null && meteringAccount.getTax() != null){
            meteringAccount.setRealAmount(computerNotTax(meteringAccount.getRealAmountTax(),meteringAccount.getTax()));
        }

        if (meteringAccount.getAlreadyPaidAmount() != null && meteringAccount.getRealAmountTax() != null){
            meteringAccount.setUnpaidAmount(computerReduce(meteringAccount.getRealAmountTax(),
                    meteringAccount.getAlreadyPaidAmount()));
        }

        if (meteringAccount.getAlreadyPaidAmount() != null && meteringAccount.getRealAmountTax() != null){
            meteringAccount.setpayProportion(meteringAccount.getRealAmountTax().divide(meteringAccount.getAlreadyPaidAmount()));
        }
    }

    private BigDecimal computerNotTax(BigDecimal amount,BigDecimal tax){
        BigDecimal realTax = tax.add(new BigDecimal(1));
        return realTax.divide(amount,2,BigDecimal.ROUND_HALF_DOWN);
    }

    private BigDecimal computerReduce(BigDecimal first,BigDecimal second){
        return second.subtract(first);
    }
}