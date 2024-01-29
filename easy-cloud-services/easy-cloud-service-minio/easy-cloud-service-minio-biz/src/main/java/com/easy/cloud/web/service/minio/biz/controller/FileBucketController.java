package com.easy.cloud.web.service.minio.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.minio.api.vo.FileVO;
import com.easy.cloud.web.service.minio.biz.constants.MinioConstants;
import com.easy.cloud.web.service.minio.biz.service.IFileBucketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * File API
 *
 * @author Fast Java
 * @date 2023-08-08 16:28:17
 */
@Slf4j
@RestController
@RequestMapping(value = "bucket")
@Api(value = "FileBucket", tags = "文件路径管理")
public class FileBucketController {

    @Autowired
    private IFileBucketService fileBucketService;

    /**
     * 列出所有的桶
     *
     * @return
     */
    @ApiOperation("列出所有的桶")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public HttpResult<List<String>> buckets() {
        return HttpResult.ok(fileBucketService.buckets());
    }

    /**
     * 递归列出桶中的所有文件和目录
     *
     * @param bucket 桶名称
     * @return
     */
    @ApiOperation("递归列出桶中的所有文件和目录")
    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public HttpResult<List<FileVO>> listFiles(
            @RequestParam(defaultValue = MinioConstants.DEFAULT_BUCKET_NAME) String bucket) {
        return HttpResult.ok(fileBucketService.files(bucket));
    }

    /**
     * 删除桶
     *
     * @param bucket 桶名称
     * @return
     */
    @ApiOperation("删除桶")
    @RequestMapping(value = "/delete/bucket", method = RequestMethod.GET)
    public HttpResult<Boolean> deleteBucket(
            @RequestParam(defaultValue = MinioConstants.DEFAULT_BUCKET_NAME) String bucket) {
        fileBucketService.deleteBucket(bucket);
        return HttpResult.ok(true);
    }
}