package com.crcc.api.controller.dict;

import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.model.Dict;
import com.crcc.common.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class TypeController extends BaseController{

    @Autowired
    private DictService dictService;

    /**
     * project_type : 项目类别
     * prefession_type : 专业类别
     * taxpayer_type : 纳税人类别
     * subcontractor_type ：分包商类型
     * @param type
     * @return
     */
    @GetMapping("/list/v1.1")
    public ResponseVo getDictListByType(@RequestParam("type")String type){
        List<Dict> dicts = dictService.findDictByType(type);
        return ResponseVo.ok(dicts);
    }
}
