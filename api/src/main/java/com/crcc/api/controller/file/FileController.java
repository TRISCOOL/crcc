package com.crcc.api.controller.file;

import com.crcc.api.annotations.AuthRequire;
import com.crcc.api.controller.BaseController;
import com.crcc.api.vo.ResponseVo;
import com.crcc.common.exception.ResponseCode;
import com.crcc.common.model.DocumentManagement;
import com.crcc.common.model.User;
import com.crcc.common.service.DocumentService;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController extends BaseController{

    @Autowired
    private DocumentService documentService;

    @Value("${file.qiniu.ACCESS_KEY}")
    String accessKey;

    @Value("${file.qiniu.SECRET_KEY}")
    String secretKey;

    @Value("${file.qiniu.FILE_URL}")
    String fileUrl;
    @Value("${file.qiniu.UP_HOST}")
    String upHost;

    private static final String BUCKET = "crcc-cb-2";//7niu文件夹的名字


    /**
     * 新增文件预览
     * @param documentManagement
     * @param request
     * @return
     */
    @SuppressWarnings("Duplicates")
    @PostMapping("/add_file/v1.1")
    @AuthRequire
    public ResponseVo add(@RequestBody DocumentManagement documentManagement, HttpServletRequest request){
        User user = curUser(request);
        documentManagement.setCreateUser(user.getId());
        documentManagement.setType(1);
        Long id = documentService.addDocument(documentManagement);
        if (id != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("id",id);
            return ResponseVo.ok(result);
        }
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    @PostMapping("/add_references/v1.1")
    @AuthRequire
    public ResponseVo addReferences(@RequestBody DocumentManagement documentManagement, HttpServletRequest request){
        User user = curUser(request);
        documentManagement.setCreateUser(user.getId());
        documentManagement.setType(0);
        Long id = documentService.addDocument(documentManagement);
        if (id != null){
            Map<String,Long> result = new HashMap<String, Long>();
            result.put("id",id);
            return ResponseVo.ok(result);
        }
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 更新
     * @param documentManagement
     * @param request
     * @return
     */
    @SuppressWarnings("Duplicates")
    @PostMapping("/update/v1.1")
    @AuthRequire
    public ResponseVo update(@RequestBody DocumentManagement documentManagement,HttpServletRequest request){
        User user = curUser(request);
        documentManagement.setUpdateUser(user.getId());
        boolean result = documentService.update(documentManagement);
        if (result)
            return ResponseVo.ok();
        return ResponseVo.error(ResponseCode.SERVER_ERROR);
    }

    /**
     * 查看
     * @param id
     * @return
     */
    @GetMapping("/details/v1.1")
    public ResponseVo getDetails(@RequestParam("id")Long id){
        return ResponseVo.ok(documentService.getDetails(id));
    }

    /**
     * 文件预览列表
     * @param fileName
     * @param fileType
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/list_file/v1.1")
    public ResponseVo listForFile(@RequestParam(value = "fileName",required = false)String fileName,
                                  @RequestParam(value = "fileType",required = false)String fileType,
                                  @RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize){
        Integer offset = page - 1 < 0 ? 0 : page-1;
        List<DocumentManagement> documentManagementList = documentService.listFile(fileName,fileType,
                offset*pageSize,pageSize);

        Integer total = documentService.listFileSize(fileName,fileType);

        return ResponseVo.ok(total,page,pageSize,documentManagementList);
    }

    /**
     * 参考文献
     * @param fileName
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/list_references/v1.1")
    public ResponseVo listForReferences(@RequestParam(value = "fileName",required = false)String fileName,
                                        @RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize){
        Integer offset = page - 1 < 0 ? 0 : page-1;
        List<DocumentManagement> documentManagements = documentService.listReferences(fileName,offset*pageSize,pageSize);

        Integer total= documentService.listReferencesSize(fileName);

        return ResponseVo.ok(total,page,pageSize,documentManagements);
    }

    @GetMapping("/qiniu_auth/v1.1")
    public ResponseVo getQiNiuToken(){

        Auth auth = Auth.create(accessKey,secretKey);
        String token = auth.uploadToken(BUCKET);
        Map<String,String> result = new HashMap<String, String>();
        result.put("token",token);
        return ResponseVo.ok(result);
    }
}
