package com.zb.controller;

import com.zb.Result.ResultBuilder;
import com.zb.entity.Cases;
import com.zb.entity.Uploaded;
import com.zb.service.*;
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
    public HttpResponse<List<Integer>> addPictureAndFileNameAndResult(@PathVariable("caseId") int caseId, @RequestParam("images") List<MultipartFile> files) {
        BufferedReader in = null;

        List<Integer> list = new ArrayList<>();
        Integer uploadedId = null;
        Integer case_file_id = null;

        // 检查上传的文件是否为空
        if (files.isEmpty() || files.stream().anyMatch(MultipartFile::isEmpty)) {
            return ResultBuilder.faile(ResultCode.USER_NULL_PICTURE_ERROR);
        }

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String file_path = AppRootPath.getappRootPath_ori() + fileName;
            File destinationFile = new File(file_path);

            try {
                file.transferTo(destinationFile); // 保存上传的图片到本地
                // 保存上传的图片到数据库
                uploadedService.createUpload(caseId, file_path);

                CallPad_test.Call(fileName, in, caseId); // 调用python程序

                // 增加上传计数
                uploadCounts.put(caseId, uploadCounts.getOrDefault(caseId, 0) + 1);

                System.out.println(uploadCounts.get(caseId) + "===========================");

                if (uploadCounts.get(caseId) == 2) {
                    int dotIndex = fileName.lastIndexOf('.');
                    String newFileName = null;
                    if (dotIndex > 0) {
                        newFileName = fileName.substring(0, dotIndex);
                    }
                    uploadedId = uploadedService.findIdByCaseIdAndName(caseId, newFileName);

                    // 把结果的文件夹名字以及地址保存到数据库
                    String directoryPath = AppRootPath.getappRootPath_result() + caseId + "\\picture";
                    File directory = new File(directoryPath);
                    if (directory.isDirectory()) {
                        File[] subfolders = directory.listFiles(File::isDirectory);
                        if (subfolders != null) {
                            for (File subfolder : subfolders) {
                                String folderName = subfolder.getName();
                                String folderPath = subfolder.getAbsolutePath();

                                case_file_id = casefileService.insert(folderName, folderPath, caseId, uploadedId); // 文件名，文件路径入库
                                list.add(case_file_id);
                            }
                        }
                    } else {
                        System.out.println("Provided path is not a directory.");
                    }

                    // 进行图片裁剪
                    CropTool cropTool = new CropTool();
                    cropTool.CropToDB(fileName, caseId, uploadedService, cropService, casefileService);

                    // 收集上传的ID
                    list.add(uploadedId);//第二张图片的 uploadedId

                    // 重置计数
                    uploadCounts.put(caseId, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResultBuilder.faile(ResultCode.CODE_ERROR);
            }
        }

        System.out.println("程序结束");
        return ResultBuilder.success(list, ResultCode.SAVE_SUCCESS);
    }


    @DeleteMapping("/delete/{caseId}/{uploadedId}")//ok
    public HttpResponse delete(@PathVariable Integer caseId, @PathVariable Integer uploadedId) {

        //删除ori文件夹下的相应文件
        List<String> imagePaths = uploadedService.findPathByCaseId(caseId);
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
        //删除uploaded数据库,一次删除两个
        uploadedService.deleteByCaseId(caseId);//ok
        //根据uploadedId删除casefile数据库
        casefileService.deleteByUploadedId(uploadedId);
        //根据uploadedId删除crop数据库
        cropService.deleteByUploadedId(uploadedId);

        //删除color数据库
        cropService.deleteByCaseId(caseId);
        //删除his数据库
        histogramService.deleteByCaseId(caseId);
        //删除two数据库
        twoService.deleteByCaseId(caseId);
        //删除three数据库
        threeService.deleteByCaseId(caseId);
        //删除four数据库
        fourService.deleteByCaseId(caseId);

        //删除match数据库
        matchService.deleteByCaseId(caseId);

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



        return ResultBuilder.successNoData(ResultCode.DELETE_SUCCESS);


    }

    //加载数据ok
    @RequestMapping("/load")
    public HttpResponse<Uploaded> load(Integer id) {
        return ResultBuilder.success(uploadedService.getById(id), ResultCode.QUERY_SUCCESS);
    }

    //
}
