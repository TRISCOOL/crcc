package com.crcc.common.service;

import com.crcc.common.model.DocumentManagement;

import java.util.List;

public interface DocumentService {
    Long addDocument(DocumentManagement documentManagement);

    boolean update(DocumentManagement documentManagement);

    DocumentManagement getDetails(Long id);

    List<DocumentManagement> listFile(String fileName,String fileType,Integer offset,Integer length);

    List<DocumentManagement> listReferences(String fileName,Integer offset,Integer length);

}
