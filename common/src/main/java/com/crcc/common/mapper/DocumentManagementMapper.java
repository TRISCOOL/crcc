package com.crcc.common.mapper;

import com.crcc.common.model.DocumentManagement;

public interface DocumentManagementMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DocumentManagement record);

    int insertSelective(DocumentManagement record);

    DocumentManagement selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DocumentManagement record);

    int updateByPrimaryKey(DocumentManagement record);
}