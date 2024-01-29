package com.easy.cloud.web.service.minio.biz.service;

import com.easy.cloud.web.service.minio.api.vo.FileVO;

import java.util.List;

/**
 * @author GR
 * @date 2024/1/29 14:24
 */
public interface IFileBucketService {

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
}
