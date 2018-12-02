package com.crcc.api.controller.user;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.Personnel;
import com.crcc.common.model.User;
import com.crcc.common.service.PersonnelService;
import com.crcc.common.utils.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/people")
public class ManagerPeopleController extends BaseController{

    @Autowired
    private PersonnelService personnelService;

    /**
     * 新增
     * @param personnel
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody Personnel personnel, HttpServletRequest request){
        User user = curUser(request);
        personnel.setCreateUser(user.getId());
        Long id = personnelService.addPersonnel(personnel);
        if (id != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("id",id);
            return ResponseVo.ok(result);
        }

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新
     * @param personnel
     * @param request
     * @return
     */
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody Personnel personnel,HttpServletRequest request){
        User user = curUser(request);
        boolean result = personnelService.updatePersonnel(personnel);
        if (result)
            return ResponseVo.ok();

        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 列表
     * @param name
     * @param projectName
     * @param position
     * @param workTime
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/list/v1.1")
    public ResponseVo listForPage(@RequestParam(value = "name",required = false) String name,
                                  @RequestParam(value = "projectName",required = false) String projectName,
                                  @RequestParam(value = "position",required = false) String position,
                                  @RequestParam(value = "workTime",required = false) Integer workTime,
                                  @RequestParam(value = "page") Integer page,@RequestParam("pageSize") Integer pageSize){

        Integer offset = page - 1<0 ? 0:page-1;

        List<Personnel> personnels = personnelService.listForPage(name,projectName,position,workTime,offset*pageSize,pageSize);
        return ResponseVo.ok(personnels);
    }

    /**
     * 查看
     * @param id
     * @return
     */
    @GetMapping("/details/v1.1")
    public ResponseVo details(@RequestParam(value = "id")Long id){
        return ResponseVo.ok(personnelService.getDetails(id));
    }

    /**
     * 导出
     * @param name
     * @param projectName
     * @param position
     * @param workTime
     * @param response
     */
    @GetMapping("/export/v1.1")
    public void export(@RequestParam(value = "name",required = false) String name,
                       @RequestParam(value = "projectName",required = false) String projectName,
                       @RequestParam(value = "position",required = false) String position,
                       @RequestParam(value = "workTime",required = false) Integer workTime,
                       HttpServletResponse response){

        String[] titles = {"人员编码","姓名","性别","当前状态","项目名称","职务","职称","参加工作年限","学历","手机号码","QQ号码",
        "身份证号码","已取得证书","籍贯（省市区/县）","创建时间","备注"};

        List<Personnel> personnelList = personnelService.listForPage(name,projectName,position,workTime,null,null);
        HSSFWorkbook wb = ExcelUtils.getPersonnelExcel("经管人员",titles,personnelList);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename="+new String("经管人员".getBytes( "utf-8" ), "ISO8859-1" )+".xls");
            response.setContentType("application/msexcel");
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
