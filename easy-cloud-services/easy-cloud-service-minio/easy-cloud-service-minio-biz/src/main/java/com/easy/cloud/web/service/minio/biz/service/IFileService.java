package com.easy.cloud.web.service.minio.biz.service;

import com.easy.cloud.web.service.minio.api.vo.FileVO;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * File interface
 *
 * @author Fast Java
 * @date 2023-08-08 16:28:17
 */
public interface IFileService {

  /**
   * 创建桶名：即存储路径
   *
   * @param bucket 桶名
   */
  void createBucket(String bucket);

  /**
   * 获取目录树
   *
   * @return
   */
  List<String> buckets();

  /**
   * 获取Bucket下的文件
   *
   * @return
   */
  List<FileVO> files(String bucket);

  /**
   * 删除桶
   *
   * @param bucket
   */
  void deleteBucket(String bucket);

  /**
   * 上传文件信息
   *
   * @param multipartFile
   * @param fileDir
   * @param bucket
   * @return
   */
  FileVO uploadFile(MultipartFile multipartFile, String fileDir, String bucket);

  /**
   * 下载文件
   *
   * @param bucket
   * @param fileName
   * @param httpServletResponse
   */
  void downloadFile(String bucket, String fileName, HttpServletResponse httpServletResponse);

  /**
   * 删除文件
   *
   * @param fileName
   * @param bucket
   */
  void deleteFile(String fileName, String bucket);
}