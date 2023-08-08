package com.easy.cloud.web.service.minio.biz.service.impl;

import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.minio.api.vo.FileVO;
import com.easy.cloud.web.service.minio.biz.service.IFileService;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * File 业务逻辑
 *
 * @author Fast Java
 * @date 2023-08-08 16:28:17
 */
@Slf4j
@Service
public class FileServiceImpl implements IFileService {

  @Autowired
  private MinioClient minioClient;

  @Override
  public void createBucket(String bucket) {
    try {
      boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
      if (!found) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
      }
    } catch (Exception exception) {
      log.error("create bucket：{}， fail：{}", bucket, exception.getMessage());
      throw new BusinessException(exception.getMessage());
    }
  }

  @Override
  public List<String> buckets() {
    try {
      return minioClient.listBuckets().stream()
          .map(Bucket::name)
          .collect(Collectors.toList());
    } catch (Exception exception) {
      log.error("read buckets fail：{}", exception.getMessage());
      throw new BusinessException(exception.getMessage());
    }
  }

  @Override
  public List<FileVO> files(String bucket) {
    Iterable<Result<Item>> results = minioClient
        .listObjects(ListObjectsArgs.builder().bucket(bucket).recursive(true).build());
    Iterator<Result<Item>> iterator = results.iterator();
    List<FileVO> files = new ArrayList<>();
    while (iterator.hasNext()) {
      try {
        Result<Item> next = iterator.next();
        Item item = next.get();
        FileVO fileVO = FileVO.builder()
            .name(item.objectName())
            .isDir(item.isDir())
            .size(item.size())
            .build();
        files.add(fileVO);
      } catch (Exception exception) {
        log.error("read file fail：{}", exception.getMessage());
      }
    }
    return files;
  }

  @Override
  public void deleteBucket(String bucket) {
    try {
      minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
    } catch (Exception exception) {
      log.error("delete Bucket：{}， fail：{}", bucket, exception.getMessage());
      throw new BusinessException(exception.getMessage());
    }
  }

  @Override
  public FileVO uploadFile(MultipartFile multipartFile, String fileDir, String bucket) {
    try {
      // 创建桶
      this.createBucket(bucket);
      // 源文件名称
      String originalFilename = multipartFile.getOriginalFilename();
      // 根据文件名称生存文件ID
//      originalFilename = HashUtil.mixHash(originalFilename)+"";
      // 文件目录存在
      if (fileDir != null) {
        fileDir = fileDir + "/" + originalFilename;
      } else {
        fileDir = originalFilename;
      }
      // 上传文件
      minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(fileDir)
          .stream(multipartFile.getInputStream(), -1, 10485760).build());
      // 构建返回文件信息
      return FileVO.builder()
          .url(fileDir)
          .name(originalFilename)
          .size(multipartFile.getSize())
          .build();
    } catch (Exception exception) {
      throw new BusinessException(exception.getMessage());
    }
  }

  @Override
  public void downloadFile(String bucket, String fileName,
      HttpServletResponse httpServletResponse) {
    try {
      // 读取文件流
      InputStream stream = minioClient.getObject(
          GetObjectArgs.builder().bucket(bucket).object(fileName).build());
      // 获取输出流
      ServletOutputStream output = httpServletResponse.getOutputStream();
      // 构建文件名
      fileName = URLEncoder.encode(fileName.substring(fileName.lastIndexOf("/") + 1), "UTF-8");
      httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + fileName);
      httpServletResponse.setContentType("application/octet-stream");
      httpServletResponse.setCharacterEncoding("UTF-8");
      // 写入输出流
      IOUtils.copy(stream, output);
    } catch (Exception exception) {
      log.error("download file：{} fail：{}", bucket + "/" + fileName, exception.getMessage());
      throw new BusinessException(exception.getMessage());
    }
  }

  @Override
  public void deleteFile(String fileName, String bucket) {
    try {
      minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(fileName).build());
    } catch (Exception exception) {
      log.error("delete file：{} fail：{}", bucket + "/" + fileName, exception.getMessage());
      throw new BusinessException(exception.getMessage());
    }
  }
}