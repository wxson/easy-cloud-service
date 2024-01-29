package com.easy.cloud.web.service.minio.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.minio.api.vo.FileVO;
import com.easy.cloud.web.service.minio.biz.constants.MinioConstants;
import com.easy.cloud.web.service.minio.biz.service.IFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * File API
 *
 * @author Fast Java
 * @date 2023-08-08 16:28:17
 */
@Slf4j
@RestController
@RequestMapping(value = "file")
@Api(value = "File", tags = "文件管理")
public class FileController {

    @Autowired
    private IFileService fileService;

    /**
     * 文件上传
     *
     * @param file    文件
     * @param bucket  桶
     * @param fileDir 文件目录
     * @return
     */
    @ApiOperation("上传文件")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public HttpResult<FileVO> uploadFile(
            @RequestParam MultipartFile file,
            @RequestParam(defaultValue = MinioConstants.DEFAULT_BUCKET_NAME) String bucket,
            @RequestParam(required = false) String fileDir) {
        return HttpResult.ok(fileService.uploadFile(file, fileDir, bucket));
    }

    /**
     * 文件删除
     *
     * @param bucket   桶
     * @param fileName 文件名
     * @return
     */
    @ApiOperation("删除文件")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public HttpResult<Boolean> deleteFile(
            @RequestParam(defaultValue = MinioConstants.DEFAULT_BUCKET_NAME) String bucket,
            @RequestParam String fileName) {
        fileService.deleteFile(fileName, bucket);
        return HttpResult.ok(true);
    }
}