package com.crcc.api.controller.project;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.ProjectEvaluation;
import com.crcc.common.model.User;
import com.crcc.common.service.ProjectEvaluationService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/evaluation")
public class ProjectEvaluationController extends BaseController{

    @Autowired
    private ProjectEvaluationService projectEvaluationService;

    /**
     * 新增项目评价
     * @param projectEvaluation
     * @param request
     * @return
     */
    @PostMapping("/add/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody ProjectEvaluation projectEvaluation, HttpServletRequest request){
        User user = curUser(request);
        projectEvaluation.setCreateUser(user.getId());
        Long evaluationId = projectEvaluationService.addEvaluation(projectEvaluation);
        if (evaluationId != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("id",evaluationId);
            return ResponseVo.ok(result);
        }
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新
     * @param projectEvaluation
     * @param request
     * @return
     */
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody ProjectEvaluation projectEvaluation,HttpServletRequest request){
        User user = curUser(request);
        projectEvaluation.setUpdateUser(user.getId());
        boolean result = projectEvaluationService.update(projectEvaluation);
        if (!result){
            return ResponseVo.error(ResponseCode.SERVER_ERROR);
        }
        return ResponseVo.ok();
    }

    /**
     * 列表
     * @param projectName
     * @param evaluationStatus
     * @param projectStatus
     * @param isSign
     * @param isResponsibility
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/list/v1.1")
    @AuthRequire
    public ResponseVo list(@RequestParam(value = "projectName",required = false) String projectName,
                           @RequestParam(value = "evaluationStatus",required = false) String evaluationStatus,
                           @RequestParam(value = "projectStatus",required = false) String projectStatus,
                           @RequestParam(value = "isSign",required = false) String isSign,
                           @RequestParam(value = "isResponsibility",required = false) Integer isResponsibility,
                           @Param("page") Integer page, @Param("pageSize") Integer pageSize,
                           HttpServletRequest request){

        Long projectId = permissionProject(request);

        Integer offset = page - 1 < 0 ? 0 : page-1;
        List<ProjectEvaluation> projectEvaluations = projectEvaluationService.listForPage(projectId,projectName,evaluationStatus,
                projectStatus,isSign,isResponsibility,offset*pageSize,pageSize);

        Integer total = projectEvaluationService.listForPageSize(projectId,projectName,evaluationStatus,
                projectStatus,isSign,isResponsibility);

        return ResponseVo.ok(total,page,pageSize,projectEvaluations);
    }

    /**
     * 查看
     * @param id
     * @return
     */
    @GetMapping("/details/v1.1")
    public ResponseVo getDetails(@RequestParam("id")Long id){
        return ResponseVo.ok(projectEvaluationService.getDetails(id));
    }
}
