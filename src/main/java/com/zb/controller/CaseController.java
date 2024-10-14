package com.zb.controller;


import com.zb.Result.ResultBuilder;
import com.zb.entity.Cases;
import com.zb.service.*;
import com.zb.tools.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @auther 宋亚涛
 * @verson 1.0
 * createCase 新增案件
 * uploadImage 调用pad_test.py，上传图片，完成对图片的剪裁，
 * 剪裁结束后对剪裁后的结果进行识别，
 * 识别成功的放进picture，失败的放进unrecognized
 * 上传的图片入库
 */
@RestController
@RequestMapping("/case")
@CrossOrigin//解决跨域问题
@Slf4j
public class CaseController {
    @Autowired
    private CaseService caseService;
    @Autowired
    private UploadedService uploadedService;
    @Autowired
    private CropService cropService;
    @Autowired
    CasefileService casefileService;
    @Autowired
    private TwoService twoService;
    @Autowired
    private ThreeService threeService;
    @Autowired
    private FourService fourService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private HistogramService histogramService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private StrokeService strokeService;

    private Map<Integer, Integer> uploadCounts = new HashMap<>();

    private CropTool cropTool = null;


    //新增案件ok
    @ApiOperation("新增案件")
    @PostMapping("/add")
    public HttpResponse<Integer> add(@RequestBody Cases newCase) {
        String title = newCase.getTitle();
        String description = newCase.getDescription();
        Integer id = caseService.add(title, description);
        System.out.println(id);
        return ResultBuilder.success(id, ResultCode.SAVE_SUCCESS);
    }

    //删除案件
    @ApiOperation("删除案件")
    @DeleteMapping("/delete/{id}")
    public HttpResponse delete(@PathVariable Integer id) {

        //删除two数据库
        twoService.deleteByCaseId(id);
        //删除three数据库
        threeService.deleteByCaseId(id);
        //删除four数据库
        fourService.deleteByCaseId(id);
        //删除crop数据库
        cropService.deleteByCaseId(id);
        //删除casefile数据库
        casefileService.deleteByCaseId(id);
        //删除color数据库
        colorService.deleteByCaseId(id);
        //删除his数据库
        histogramService.deleteByCaseId(id);
        //删除match数据库
        matchService.deleteByCaseId(id);
        //删除Stroke数据库
        strokeService.deleteByCaseId(id);

        //删除ori文件夹下的相应文件
        List<String> imagePaths = uploadedService.findPathByCaseId(id);
        for (String imagePath : imagePaths) {
            try {
                Path path = Paths.get(imagePath);
                // 删除文件
                Files.deleteIfExists(path);
                System.out.println("Deleted: " + imagePath);
            } catch (IOException e) {
                System.err.println("Failed to delete " + imagePath + ": " + e.getMessage());
            }
        }

        //删除uploaded数据库
        uploadedService.deleteByCaseId(id);
        //删除id相同的案件
        caseService.removeById(id);

        //删除caseId命名的文件夹
        String folderPath = AppRootPath.getappRootPath_result() + id;
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            DeleteTools.deleteFolder(folder);
        } else {
            System.out.println("文件夹不存在或不是一个目录: " + folderPath);
        }

        return ResultBuilder.successNoData(ResultCode.DELETE_SUCCESS);


    }

    //加载数据ok
    @RequestMapping("/load/{id}")
    public HttpResponse<Cases> loadById(@PathVariable Integer id) {
        return ResultBuilder.success(caseService.getById(id), ResultCode.QUERY_SUCCESS);
    }

    //更新数据ok

    @PutMapping("/update")
    public HttpResponse update(@RequestBody Cases updateCase) {
        caseService.updateById(updateCase);
        return ResultBuilder.successNoData(ResultCode.UPDATE_SUCCESS);
    }

    //返回所有案件
    @ApiOperation("返回案件列表")
    @RequestMapping("/list")
    public HttpResponse<List<Cases>> loadAll() {
        return ResultBuilder.success(caseService.getAll(), ResultCode.QUERY_SUCCESS);
    }

}

