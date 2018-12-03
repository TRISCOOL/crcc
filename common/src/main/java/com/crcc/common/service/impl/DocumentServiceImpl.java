package com.crcc.common.service.impl;

import com.crcc.common.mapper.DocumentManagementMapper;
import com.crcc.common.model.DocumentManagement;
import com.crcc.common.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class DocumentServiceImpl implements DocumentService{

    @Autowired
    private DocumentManagementMapper documentManagementMapper;

    @Override
    public Long addDocument(DocumentManagement documentManagement) {
        documentManagement.setCreateTime(new Date());
        documentManagementMapper.insertSelective(documentManagement);
        return documentManagement.getId();
    }

    @Override
    public boolean update(DocumentManagement documentManagement) {
        documentManagement.setUpdateTime(new Date());
        int result = documentManagementMapper.updateByPrimaryKeySelective(documentManagement);
        if (result != 0)
            return true;
        return false;
    }

    @Override
    public DocumentManagement getDetails(Long id) {
        return documentManagementMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DocumentManagement> listFile(String fileName, String fileType, Integer offset, Integer length) {
        return documentManagementMapper.listForFile(fileName,fileType,offset,length);
    }

    @Override
    public List<DocumentManagement> listReferences(String fileName, Integer offset, Integer length) {
        return documentManagementMapper.listForReferences(fileName,offset,length);
    }
}
