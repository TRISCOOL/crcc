package com.crcc.common.mapper;

import com.crcc.common.model.ExportConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExportConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExportConfig record);

    int insertSelective(ExportConfig record);

    ExportConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExportConfig record);

    int updateByPrimaryKey(ExportConfig record);

    List<ExportConfig> findExportConfigs(@Param("exportType")String exportType,@Param("sorts")List<Integer> sorts);
}