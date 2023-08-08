package com.easy.cloud.web.service.minio.biz.controller;

import com.easy.cloud.web.component.core.response.HttpResult;
import com.easy.cloud.web.service.minio.api.vo.FileVO;
import com.easy.cloud.web.service.minio.biz.constants.MinioConstants;
import com.easy.cloud.web.service.minio.biz.service.IFileService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
public class FileController {

  @Autowired
  private IFileService fileService;

  @ApiOperation("上传文件")
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public HttpResult<FileVO> uploadFile(
      @RequestParam MultipartFile multipartFile,
      @RequestParam(defaultValue = MinioConstants.DEFAULT_BUCKET_NAME) String bucket,
      @RequestParam(required = false) String fileDir) {
    return HttpResult.ok(fileService.uploadFile(multipartFile, fileDir, bucket));
  }

  @ApiOperation("列出所有的桶")
  @RequestMapping(value = "/listBuckets", method = RequestMethod.GET)
  public HttpResult<List<String>> buckets() {
    return HttpResult.ok(fileService.buckets());
  }

  @ApiOperation("递归列出桶中的所有文件和目录")
  @RequestMapping(value = "/listFiles", method = RequestMethod.GET)
  public HttpResult<List<FileVO>> listFiles(
      @RequestParam(defaultValue = MinioConstants.DEFAULT_BUCKET_NAME) String bucket) {
    return HttpResult.ok(fileService.files(bucket));
  }

  @ApiOperation("下载文件")
  @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
  public void downloadFile(
      @RequestParam(defaultValue = MinioConstants.DEFAULT_BUCKET_NAME) String bucket,
      @RequestParam String fileName,
      HttpServletResponse httpServletResponse) {
    fileService.downloadFile(bucket, fileName, httpServletResponse);
  }


  @ApiOperation("删除文件")
  @RequestMapping(value = "/delete/file", method = RequestMethod.GET)
  public HttpResult<Boolean> deleteFile(
      @RequestParam(defaultValue = MinioConstants.DEFAULT_BUCKET_NAME) String bucket,
      @RequestParam String fileName) {
    fileService.deleteFile(bucket, fileName);
    return HttpResult.ok(true);
  }

  @ApiOperation("删除桶")
  @RequestMapping(value = "/delete/bucket", method = RequestMethod.GET)
  public HttpResult<Boolean> deleteBucket(
      @RequestParam(defaultValue = MinioConstants.DEFAULT_BUCKET_NAME) String bucket) {
    fileService.deleteBucket(bucket);
    return HttpResult.ok(true);
  }
}