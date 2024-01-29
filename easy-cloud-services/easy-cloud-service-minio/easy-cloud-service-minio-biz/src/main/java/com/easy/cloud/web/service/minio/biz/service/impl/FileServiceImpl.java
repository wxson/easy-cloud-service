package com.easy.cloud.web.service.minio.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.util.SnowflakeUtils;
import com.easy.cloud.web.service.minio.api.vo.FileVO;
import com.easy.cloud.web.service.minio.biz.configuration.MinioProperties;
import com.easy.cloud.web.service.minio.biz.service.IFileBucketService;
import com.easy.cloud.web.service.minio.biz.service.IFileService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

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
    private MinioProperties minioProperties;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private IFileBucketService fileBucketService;

    @Override
    public FileVO uploadFile(MultipartFile multipartFile, String fileDir, String bucket) {
        try {
            // 创建桶
            fileBucketService.createBucket(bucket);
            // 源文件名称
            String originalFilename = multipartFile.getOriginalFilename();
            // 文件后缀名
            String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 根据生成文件唯一编码
            String fileFullPath = SnowflakeUtils.next() + fileSuffix;
            // 文件目录存在
            if (StrUtil.isNotBlank(fileDir)) {
                fileFullPath = StrUtil.format("{}/{}", fileDir, fileFullPath);
            }

            // 文件路径
            String filePath = StrUtil.format("/{}/{}", bucket, fileFullPath);
            // 创建文件读取地址
            String fileUrl = minioProperties.getEndpoint() + filePath;
            // 上传文件
            minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(fileFullPath)
                    .stream(multipartFile.getInputStream(), -1, 10485760).build());
            // 构建返回文件信息
            return FileVO.builder()
                    .url(fileUrl)
                    .path(filePath)
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