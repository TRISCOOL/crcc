package com.crcc.common.service.impl;

import com.crcc.common.exception.CrccException;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.mapper.MeteringAccountMapper;
import com.crcc.common.model.MeteringAccount;
import com.crcc.common.model.MeteringAccountTotal;
import com.crcc.common.service.ForUpAccountService;
import com.crcc.common.utils.DateTimeUtil;
import com.crcc.common.utils.ExcelUtils;
import com.crcc.common.utils.Utils;
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

    @Override
    public MeteringAccountTotal getTotal(Long projectId, String projectName, Date meteringNum, Double minPayProportion, Double maxPayProportion, Double minProductionValue, Double maxProductionValue) {
        List<MeteringAccount> meteringAccounts = listForPage(projectId,projectName,meteringNum,minPayProportion,maxPayProportion,
                minProductionValue,maxProductionValue,null,null);
        MeteringAccountTotal meteringAccountTotal = new MeteringAccountTotal();
        BigDecimal sumAlreadyAmount = new BigDecimal(0);
        BigDecimal sumRealTaxAmount = new BigDecimal(0);
        BigDecimal sumTaxAmount = new BigDecimal(0);
        BigDecimal sumEndAmount = new BigDecimal(0);
        BigDecimal sumExtraAmount = new BigDecimal(0);
        BigDecimal sumPrepaymentAmount = new BigDecimal(0);
        BigDecimal sumAmountNotTax = new BigDecimal(0);
        BigDecimal sumRealAmount = new BigDecimal(0);
        BigDecimal sumUnpaidAmount = new BigDecimal(0);
        BigDecimal percentagePayProportion = new BigDecimal(0);
        BigDecimal percentageProductionValue = new BigDecimal(0);
        if (meteringAccounts != null && meteringAccounts.size() > 0){
            for (MeteringAccount meteringAccount : meteringAccounts){
                sumAlreadyAmount = Utils.addBigDecimal(sumAlreadyAmount,meteringAccount.getAlreadyPaidAmount());
                sumRealTaxAmount = Utils.addBigDecimal(sumRealTaxAmount,meteringAccount.getRealAmountTax());
                sumTaxAmount = Utils.addBigDecimal(sumTaxAmount,meteringAccount.getValuationAmountTax());
                sumEndAmount = Utils.addBigDecimal(sumEndAmount,meteringAccount.getNotCalculatedAmount());
                sumExtraAmount = Utils.addBigDecimal(sumExtraAmount,meteringAccount.getExtraAmount());
                sumPrepaymentAmount = Utils.addBigDecimal(sumPrepaymentAmount,meteringAccount.getPrepaymentAmount());
                sumAmountNotTax = Utils.addBigDecimal(sumAmountNotTax,meteringAccount.getValuationAmountNotTax());
                sumRealAmount = Utils.addBigDecimal(sumRealAmount,meteringAccount.getRealAmount());
                sumUnpaidAmount = Utils.addBigDecimal(sumUnpaidAmount,meteringAccount.getUnpaidAmount());
            }
        }
        if (sumRealTaxAmount != null && sumAlreadyAmount != null){
            BigDecimal sumPercentagePayProportion = computerDivide(sumRealTaxAmount,sumAlreadyAmount,4);
            meteringAccountTotal.setPercentagePayProportion(new BigDecimal(sumPercentagePayProportion.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue()*100));
        }

        if (sumTaxAmount != null && sumEndAmount != null && sumExtraAmount != null){
            BigDecimal sumProductionValue = computerDivide(sumTaxAmount,sumTaxAmount.add(sumEndAmount).add(sumExtraAmount),4);
            meteringAccountTotal.setPercentageProductionValue(new BigDecimal(sumProductionValue.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue()*100));

        }

        meteringAccountTotal.setSumAlreadyAmount(sumAlreadyAmount);
        meteringAccountTotal.setSumAmountNotTax(sumAmountNotTax);
        meteringAccountTotal.setSumEndAmount(sumEndAmount);
        meteringAccountTotal.setSumExtraAmount(sumExtraAmount);
        meteringAccountTotal.setSumPrepaymentAmount(sumPrepaymentAmount);
        meteringAccountTotal.setSumRealAmount(sumRealAmount);
        meteringAccountTotal.setSumRealTaxAmount(sumRealTaxAmount);
        meteringAccountTotal.setSumTaxAmount(sumTaxAmount);
        meteringAccountTotal.setSumUnpaidAmount(sumUnpaidAmount);

        return meteringAccountTotal;
    }

    @Override
    public boolean logicDeletedById(Long id, Long updateUser, Date updateTime) {
        int result = meteringAccountMapper.logicDeletedById(id,updateUser,updateTime);
        if (result != 0){
            return true;
        }
        return false;
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
            meteringAccount.setpayProportion(computerDivide(meteringAccount.getAlreadyPaidAmount(),meteringAccount.getRealAmountTax(),4));
        }

        if (meteringAccount.getValuationAmountTax() != null && meteringAccount.getExtraAmount() != null
                && meteringAccount.getNotCalculatedAmount() != null){
            BigDecimal midValue = add(meteringAccount.getExtraAmount(),
                    meteringAccount.getNotCalculatedAmount(),
                    meteringAccount.getValuationAmountTax());
            meteringAccount.setProductionValue(computerDivide(meteringAccount.getValuationAmountTax(),midValue,4));
        }
    }

    private BigDecimal computerNotTax(BigDecimal amount,BigDecimal tax){
        BigDecimal realTax = tax.add(new BigDecimal(1));
        return amount.divide(realTax,2,BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal computerReduce(BigDecimal first,BigDecimal second){
        return first.subtract(second);
    }

    public BigDecimal computerDivide(BigDecimal bcs,BigDecimal cs,int decimalPoint){
        if (bcs.doubleValue() == 0d || cs.doubleValue() == 0d)
            return new BigDecimal(0);

        return bcs.divide(cs,decimalPoint,BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal add(BigDecimal first,BigDecimal second,BigDecimal third){
        if (first != null && second != null && third != null){
            return first.add(second).add(third);
        }
        return null;
    }
}
