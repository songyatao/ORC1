package com.zb.controller;

import com.zb.Result.ResultBuilder;
import com.zb.entity.Cases;
import com.zb.entity.Uploaded;
import com.zb.service.CasefileService;
import com.zb.service.CropService;
import com.zb.service.UploadedService;
import com.zb.tools.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@RestController
@RequestMapping("/uploaded")
@CrossOrigin//解决跨域问题
@Slf4j
public class UploadedController {
    @Autowired
    private UploadedService uploadedService;

    @Autowired
    CasefileService casefileService;
    @Autowired
    private CropService cropService;
    private Map<Integer, Integer> uploadCounts = new HashMap<>();
    private CropTool cropTool = null;
    String newFileName = null;

    //上传图片，保存原图，剪裁文件名，剪裁结果到数据库ok
    /*

            返回了caseId 和 case_file_id 前端可以从这里获取
            list.add(uploadedId);
            list.add(case_file_id);
     */
    @PostMapping("/{caseId}/add")
    public HttpResponse<List<Integer>> addPictureAndFileNameAndResult(@PathVariable("caseId") int caseId, @RequestParam("image") MultipartFile file) {
        BufferedReader in = null;

        Map<String, String> response = new HashMap<>();
        Integer uploadedId = null;
        Integer case_file_id = null;
        List<Integer> list = new ArrayList<>();


        if (file.isEmpty()) { // 上传的图片文件为空
            return ResultBuilder.faile(ResultCode.USER_NULL_PICTURE_ERROR);
        }

        String fileName = file.getOriginalFilename();
        String file_path = AppRootPath.getappRootPath_ori() + fileName;
        File destinationFile = new File(file_path);

        try {
            file.transferTo(destinationFile); // 保存上传的图片到本地
            //保存上传的图片到数据库
            uploadedService.createUpload(caseId, file_path);


            CallPad_test.Call(fileName, in, caseId);//调用python程序,剪裁后的图片保存到相应案件的文件夹中


            // 增加上传计数
            uploadCounts.put(caseId, uploadCounts.getOrDefault(caseId, 0) + 1);

            if (uploadCounts.get(caseId) == 2) {
                int dotIndex = fileName.lastIndexOf('.');
                if (dotIndex > 0) {
                    newFileName = fileName.substring(0, dotIndex);
                }
                uploadedId = uploadedService.findIdByCaseIdAndName(caseId, newFileName);

                //把结果的文件夹名字以及地址保存到数据库
                String directoryPath = AppRootPath.getappRootPath_result() + caseId + "\\picture";
                File directory = new File(directoryPath);
                if (directory.isDirectory()) {
                    File[] subfolders = directory.listFiles(File::isDirectory);
                    if (subfolders != null) {
                        for (File subfolder : subfolders) {
                            String folderName = subfolder.getName();
                            String folderPath = subfolder.getAbsolutePath();

                            case_file_id = casefileService.insert(folderName, folderPath, caseId, uploadedId);//文件名，文件路径入库
                        }
                    }
                } else {
                    System.out.println("Provided path is not a directory.");
                }
                cropTool = new CropTool();
                cropTool.CropToDB(fileName, caseId, uploadedService, cropService, casefileService);
                System.out.println(uploadedId);
                System.out.println(case_file_id);
                list.add(uploadedId);
                list.add(case_file_id);
                // 重置计数
                uploadCounts.put(caseId, 0);
            }
            return ResultBuilder.success(list, ResultCode.SAVE_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultBuilder.faile(ResultCode.CODE_ERROR);

        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    @DeleteMapping("/delete/{caseId}/{uploadedId}")//ok
    public HttpResponse delete(@PathVariable Integer caseId, @PathVariable Integer uploadedId) {

        //删除uploaded数据库,一次删除两个
        uploadedService.deleteByCaseId(caseId);//ok
        //根据uploadedId删除casefile数据库
        casefileService.deleteByUploadedId(uploadedId);
        //根据uploadedId删除crop数据库
        cropService.deleteByUploadedId(uploadedId);

        //删除caseId文件夹下的所有文件
        Path directory1 = Paths.get(AppRootPath.getappRootPath_result() + caseId);
        try {
            // 列出文件夹中的所有文件和子目录并删除
            Files.list(directory1).forEach(path -> {
                try {
                    if (Files.isDirectory(path)) {
                        DeleteTools.deleteContents(path); // 递归删除子目录
                    }
                    Files.delete(path); // 删除文件
                } catch (IOException e) {
                    System.err.println("删除失败: " + path + " " + e.getMessage());
                }
            });
            System.out.println("所有文件已删除，文件夹1保留。");
        } catch (IOException e) {
            System.err.println("读取文件夹失败: " + e.getMessage());
        }

        //删除ori文件夹下的所有文件
        Path directory = Paths.get(AppRootPath.getappRootPath_ori());
        try {
            // 删除文件夹中的所有文件
            Files.list(directory).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.err.println("删除文件失败: " + path + " " + e.getMessage());
                }
            });
            System.out.println("所有文件已删除。");
        } catch (IOException e) {
            System.err.println("读取文件夹失败: " + e.getMessage());
        }


        return ResultBuilder.successNoData(ResultCode.DELETE_SUCCESS);


    }

    //加载数据ok
    @RequestMapping("/load")
    public HttpResponse<Uploaded> load(Integer id) {
        return ResultBuilder.success(uploadedService.getById(id), ResultCode.QUERY_SUCCESS);
    }

    //不需要修改
}
