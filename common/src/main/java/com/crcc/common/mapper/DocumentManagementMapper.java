package com.crcc.common.mapper;

import com.crcc.common.model.DocumentManagement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DocumentManagementMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DocumentManagement record);

    int insertSelective(DocumentManagement record);

    DocumentManagement selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DocumentManagement record);

    int updateByPrimaryKey(DocumentManagement record);

    List<DocumentManagement> listForReferences(@Param("fileName") String fileName,@Param("offset")Integer offset,
                                               @Param("length")Integer length);

    Integer listForReferencesSize(@Param("fileName") String fileName);

    List<DocumentManagement> listForFile(@Param("fileName")String fileName,@Param("fileType")String fileType,
                                         @Param("offset")Integer offset,@Param("length")Integer length);

    Integer listForFileSize(@Param("fileName")String fileName,@Param("fileType")String fileType);
}