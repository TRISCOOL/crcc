package com.crcc.common.service.impl;

import com.crcc.common.mapper.ExportConfigMapper;
import com.crcc.common.model.ExportConfig;
import com.crcc.common.service.ExportConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ALL")
@Service
public class ExportConfigServiceImpl implements ExportConfigService {

    @Autowired
    private ExportConfigMapper exportConfigMapper;

    @Override
    public List<ExportConfig> findExportConfigs(String exportType, List<Integer> sorts) {
        return exportConfigMapper.findExportConfigs(exportType,sorts);
    }
}
