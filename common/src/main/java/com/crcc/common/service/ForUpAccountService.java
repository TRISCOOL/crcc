package com.crcc.common.service;

import com.crcc.common.model.MeteringAccount;
import com.crcc.common.model.MeteringAccountTotal;

import java.util.Date;
import java.util.List;

public interface ForUpAccountService {

    Long addMeteringAccount(MeteringAccount meteringAccount);

    boolean updateMeteringAccount(MeteringAccount meteringAccount);

    MeteringAccount getMeteringAccountDetails(Long meteringAccountId);

    List<MeteringAccount> listForPage(Long projectId,String projectName,Date meteringNum,
                                      Double minPayProportion,Double maxPayProportion,
                                      Double minProductionValue,Double maxProductionValue,
                                      Integer offset,Integer length);

    Integer listForPageSize(Long projectId,String projectName,Date meteringNum,
                        Double minPayProportion,Double maxPayProportion,
                        Double minProductionValue,Double maxProductionValue);

    MeteringAccountTotal getTotal(Long projectId,String projectName,Date meteringNum,
                                  Double minPayProportion,Double maxPayProportion,
                                  Double minProductionValue,Double maxProductionValue);

}
