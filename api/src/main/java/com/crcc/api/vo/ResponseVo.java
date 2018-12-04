package com.crcc.api.vo;

import com.crcc.common.exception.ResponseCode;

public class ResponseVo {
    private String code;
    private String message;
    private boolean status;
    private Object entity;

    private PageVo pagination;
    private Object list;

    public PageVo getPagination() {
        return pagination;
    }

    public void setPagination(PageVo pagination) {
        this.pagination = pagination;
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }

    public static ResponseVo ok(){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode("200");
        responseVo.setStatus(true);
        return responseVo;
    }

    public static ResponseVo ok(Integer total,Integer current,Integer pageSize,Object object){
        ResponseVo responseVo = new ResponseVo();
        PageVo page = new PageVo();
        page.setCurrent(current);
        page.setPageSize(pageSize);
        page.setTotal(total);

        responseVo.setStatus(true);
        responseVo.setCode(ResponseCode.OK.getCode());
        responseVo.setList(object);
        responseVo.setPagination(page);
        responseVo.setMessage(ResponseCode.OK.getMessage());

        return responseVo;
    }

    public static ResponseVo ok(Object entity){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(true);
        responseVo.setCode(ResponseCode.OK.getCode());
        responseVo.setEntity(entity);
        responseVo.setMessage(ResponseCode.OK.getMessage());
        return responseVo;
    }

    public static ResponseVo error(ResponseCode responseCode){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(responseCode.getCode());
        responseVo.setMessage(responseCode.getMessage());
        responseVo.setStatus(false);

        return responseVo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
