package com.easy.cloud.web.service.minio.biz.controller;

import com.easy.cloud.web.service.minio.biz.constants.MinioConstants;
import com.easy.cloud.web.service.minio.biz.service.IFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * File API
 *
 * @author Fast Java
 * @date 2023-08-08 16:28:17
 */
@Slf4j
@RestController
@RequestMapping(value = "file")
@Api(value = "FileDetail", tags = "文件详情")
public class FileDetailController {

    @Autowired
    private IFileService fileService;

    /**
     * 文件下载
     *
     * @param bucket
     * @param fileName
     * @param httpServletResponse
     */
    @ApiOperation("下载文件")
    @RequestMapping(value = "/file/download", method = RequestMethod.GET)
    public void downloadFile(
            @RequestParam(defaultValue = MinioConstants.DEFAULT_BUCKET_NAME) String bucket,
            @RequestParam String fileName,
            HttpServletResponse httpServletResponse) {
        fileService.downloadFile(bucket, fileName, httpServletResponse);
    }

    /**
     * 文件详情
     *
     * @param bucket              桶
     * @param fileName            文件名
     * @param httpServletResponse 响应体
     */
    @ApiOperation("文件详情")
    @RequestMapping(value = "/{bucket}/{fileName}", method = RequestMethod.GET)
    public void detail(@PathVariable String bucket, @PathVariable String fileName,
                       HttpServletResponse httpServletResponse) {
        fileService.downloadFile(bucket, fileName, httpServletResponse);
    }
}