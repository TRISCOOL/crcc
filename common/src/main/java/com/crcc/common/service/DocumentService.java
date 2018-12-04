package com.crcc.common.service;

import com.crcc.common.model.DocumentManagement;

import java.util.List;

public interface DocumentService {
    Long addDocument(DocumentManagement documentManagement);

    boolean update(DocumentManagement documentManagement);

    DocumentManagement getDetails(Long id);

    List<DocumentManagement> listFile(String fileName,String fileType,Integer offset,Integer length);

    Integer listFileSize(String fileName,String fileType);

    Integer listReferencesSize(String fileName);

    List<DocumentManagement> listReferences(String fileName,Integer offset,Integer length);

}
