package com.easy.cloud.web.service.minio.biz.service;

import com.easy.cloud.web.service.minio.api.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * File interface
 *
 * @author Fast Java
 * @date 2023-08-08 16:28:17
 */
public interface IFileService {

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