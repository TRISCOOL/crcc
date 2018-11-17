package com.crcc.common.service;

import com.crcc.common.model.Dict;

import java.util.List;

public interface DictService {
    List<Dict> findDictByType(String type);
}
