package com.easy.cloud.web.service.minio.biz.service.impl;

import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.service.minio.api.vo.FileVO;
import com.easy.cloud.web.service.minio.biz.service.IFileBucketService;
import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Minio bucket业务逻辑
 *
 * @author GR
 * @date 2024/1/29 14:25
 */
@Slf4j
@Service
public class FileBucketServiceImpl implements IFileBucketService {

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
            return minioClient.listBuckets().stream().map(Bucket::name).collect(Collectors.toList());
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

}
