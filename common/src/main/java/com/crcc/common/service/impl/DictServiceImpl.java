package com.crcc.common.service.impl;

import com.crcc.common.mapper.DictMapper;
import com.crcc.common.model.Dict;
import com.crcc.common.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ALL")
@Service
public class DictServiceImpl implements DictService{

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Dict> findDictByType(String type) {
        return dictMapper.findByType(type);
    }
}
