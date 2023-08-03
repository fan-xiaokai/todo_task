package com.todo.task.utils;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author chenhu
 * @date 2021/9/9
 */
@Component
public class MinioClientUtil {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucket}")
    private String bucket;

    private MinioClient client;

    /**
     * 桶占位符
     */
    private String BUCKET_PARAM = "${bucket}";

    /**
     * bucket权限-只读
     */
    private String READ_ONLY = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "/*\"]}]}";

    /**
     * bucket权限-只读
     */
    private String WRITE_ONLY = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "/*\"]}]}";

    /**
     * bucket权限-读写
     */
    private String READ_WRITE = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:DeleteObject\",\"s3:GetObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\",\"s3:AbortMultipartUpload\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "/*\"]}]}";

    /**
     * 文件url前半段
     *
     * @param bucket 桶
     * @return 前半段
     */
    public String getObjectPrefixUrl(String bucket) {
        return String.format("%s/%s/", endpoint, bucket);
    }

    /**
     * 创建桶
     */
    public void makeBucket() throws Exception {
        // 创建客户端
        getClient();

        // 判断桶是否存在
        boolean isExist = client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!isExist) {
            // 新建桶
            client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }

        setBucketPolicy("read-write");

    }

    private void getClient() {
        if(client != null) {
            return;
        }

        MinioClient build = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
        this.client = build;
    }

    /**
     * 更新桶权限策略
     *
     * @param policy 权限
     */
    public void setBucketPolicy(String policy) throws Exception {
        switch (policy) {
            case "read-only":
                client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(READ_ONLY.replace(BUCKET_PARAM, bucket)).build());
                break;
            case "write-only":
                client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(WRITE_ONLY.replace(BUCKET_PARAM, bucket)).build());
                break;
            case "read-write":
                client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(READ_WRITE.replace(BUCKET_PARAM, bucket)).build());
                break;
            case "none":
            default:
                break;
        }
    }

    /**
     * 上传本地文件
     *
     * @param objectKey 文件key
     * @param filePath  文件路径
     * @return 文件url
     */
    public String uploadFile(String objectKey, String filePath) throws Exception {
        makeBucket();
        client.uploadObject(UploadObjectArgs.builder().bucket(bucket).object(objectKey).filename(filePath).contentType("image/png").build());
        return getObjectPrefixUrl(bucket) + objectKey;
    }

    /**
     * 流式上传文件
     *
     * @param objectKey   文件key
     * @param inputStream 文件输入流
     * @return 文件url
     */
    public String uploadInputStream(String objectKey, InputStream inputStream) throws Exception {
        makeBucket();
        client.putObject(PutObjectArgs.builder().bucket(bucket).object(objectKey).stream(inputStream, inputStream.available(), -1).build());
        return getObjectPrefixUrl(bucket) + objectKey;
    }

    /**
     * 下载文件
     *
     * @param objectKey 文件key
     * @return 文件流
     */
    public InputStream download(String objectKey) throws Exception {
        makeBucket();
        return client.getObject(GetObjectArgs.builder().bucket(bucket).object(objectKey).build());
    }

    /**
     * 文件复制
     *
     * @param sourceBucket    源桶
     * @param sourceObjectKey 源文件key
     * @param bucket          桶
     * @param objectKey       文件key
     * @return 新文件url
     */
    public String copyFile(String sourceBucket, String sourceObjectKey, String bucket, String objectKey) throws Exception {
        makeBucket();
        CopySource source = CopySource.builder().bucket(sourceBucket).object(sourceObjectKey).build();
        client.copyObject(CopyObjectArgs.builder().bucket(bucket).object(objectKey).source(source).build());
        return getObjectPrefixUrl(bucket) + objectKey;
    }

    /**
     * 删除文件
     *
     * @param objectKey 文件key
     */
    public void deleteFile(String objectKey) throws Exception {
        makeBucket();
        client.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectKey).build());
    }

    /**
     * 获取文件签名url
     *
     * @param objectKey 文件key
     * @param expires   签名有效时间  单位秒
     * @return 文件签名地址
     */
    public String getSignedUrl(String objectKey, int expires) throws Exception {
        makeBucket();
        return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucket).object(objectKey).expiry(expires).build());
    }
}
