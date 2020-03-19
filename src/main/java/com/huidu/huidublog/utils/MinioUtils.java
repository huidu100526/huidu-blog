package com.huidu.huidublog.utils;

import io.minio.MinioClient;
import org.springframework.web.multipart.MultipartFile;

/**
 * @auther huidu
 * @create 2020/3/16 15:36
 * @Description: 文件服务器相关工具类
 */
public class MinioUtils {
    /**
     * 上传图片的方法
     * @param multipartFile 文件对象
     * @param fileName 文件名称
     * @param minioUrl minio服务器的url
     * @param username minio用户名
     * @param password minio密码
     * @param bucketName 添加的桶名称
     */
    public static String uploadFile(MultipartFile multipartFile, String fileName,
                               String minioUrl, String username, String password, String bucketName) throws Exception {
        // 创建一个MinioClient对象
        MinioClient minioClient = new MinioClient(minioUrl, username, password);
        // 检查存储桶是否已经存在
        boolean isExist = minioClient.bucketExists(bucketName);
        // 不存在就创建
        if (!isExist) minioClient.makeBucket(bucketName);
        // 上传文件
        minioClient.putObject(bucketName, fileName, multipartFile.getInputStream(), "image/jpeg");
        // 上传成功查询图片地址进行返回
        String url = minioClient.getObjectUrl(bucketName, fileName);
        return url;
    }

    /**
     * 删除文件的方法
     */
    public static void delFile(String fileName, String minioUrl, String username, String password, String bucketName) throws Exception {
        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        MinioClient minioClient = new MinioClient(minioUrl, username, password);
        minioClient.removeObject(bucketName, fileName);
    }
}
