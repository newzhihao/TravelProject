package com.newzhihao.travelproject.controller;

import com.newzhihao.travelproject.pojo.Publish;
import com.newzhihao.travelproject.pojo.Result;
import com.newzhihao.travelproject.service.AliyunService;
import com.newzhihao.travelproject.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
public class UploadController {

    @Autowired
    private AliyunService aliyunService;

    @Autowired
    private PublishService publishService;

    // 允许的文件类型
    private static final List<String> ALLOWED_TYPES = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".webp");
    // 单个文件最大大小 5MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    // 总文件最大大小 20MB
    private static final long MAX_TOTAL_SIZE = 20 * 1024 * 1024;

    @PostMapping("/uploads") // 与前端统一接口路径
    public Result uploadImages(
            @RequestPart("publish") Publish publish, // 接收普通表单字段
            @RequestPart("images") MultipartFile[] files // 显式接收文件数组
    ) {
        // 基础参数校验
        if (files == null || files.length == 0) {
            return Result.error("请选择要上传的图片");
        }

        // 业务字段校验
        if (publish.getTitle() == null || publish.getTitle().trim().isEmpty()) {
            return Result.error("标题不能为空");
        }
        if (publish.getCategory() == null || publish.getCategory().length == 0) {
            return Result.error("至少选择一个分类");
        }
        if (publish.getSummary() == null || publish.getSummary().trim().isEmpty()) {
            return Result.error("摘要不能为空");
        }
        if (publish.getContent() == null || publish.getContent().trim().isEmpty()) {
            return Result.error("内容不能为空");
        }

        // 计算总文件大小
        long totalSize = 0;
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                totalSize += file.getSize();
            }
        }
        if (totalSize > MAX_TOTAL_SIZE) {
            return Result.error("文件总大小超过20MB限制");
        }

        // 分别记录成功和失败的文件
        List<String> successUrls = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();
        Map<String, String> fileUrlMap = new HashMap<>(); // 记录文件名与URL的映射

        // 逐个处理文件
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; // 跳过空文件
            }

            try {
                // 文件类型校验
                String originalFilename = file.getOriginalFilename();
                if (originalFilename == null || originalFilename.lastIndexOf(".") == -1) {
                    failedFiles.add(originalFilename + "：无法识别文件类型");
                    continue;
                }

                String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
                if (!ALLOWED_TYPES.contains(extension)) {
                    failedFiles.add(originalFilename + "：不支持的文件类型，仅支持" + String.join(",", ALLOWED_TYPES));
                    continue;
                }

                // 文件大小校验
                if (file.getSize() > MAX_FILE_SIZE) {
                    failedFiles.add(originalFilename + "：文件大小超过5MB限制");
                    continue;
                }

                // 生成唯一文件名（UUID+扩展名）
                String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extension;

                // 上传到阿里云
                String url = aliyunService.upload(file.getBytes(), uniqueFileName);

                // 记录成功的URL
                successUrls.add(url);
                fileUrlMap.put(originalFilename, url);

            } catch (Exception e) {
                // 记录上传失败的文件及原因
                failedFiles.add(file.getOriginalFilename() + "：上传失败，原因：" + e.getMessage());
            }
        }

        // 处理上传结果
        if (successUrls.isEmpty()) {
            return Result.error("所有文件上传失败：" + String.join("; ", failedFiles));
        }

        // 设置图片URL到Publish对象
        publish.setImageUrl(successUrls.toArray(new String[0]));

        try {
            // 保存内容到数据库
            publishService.publishContent(publish);

            // 构建完整的成功返回结果
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("successUrls", successUrls);
            resultData.put("fileUrlMap", fileUrlMap);
            if (!failedFiles.isEmpty()) {
                resultData.put("failedFiles", failedFiles);
                return Result.success(resultData);
            }

            return Result.success(resultData);

        } catch (Exception e) {
            return Result.error("内容保存失败：" + e.getMessage());
        }
    }
}