package com.crcc.common.service;

import com.crcc.common.model.ExportConfig;

import java.util.List;

public interface ExportConfigService {
    List<ExportConfig> findExportConfigs(String exportType,List<Integer> sorts);
}
