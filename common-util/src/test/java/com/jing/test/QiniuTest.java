package com.jing.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;

public class QiniuTest {
    @Test
    public void uploadFile(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone1());
//        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "WuDJ30b9eTTTM93ikvhzvHnAPrppGxNbwimoUoIC";
        String secretKey = "p2iaY6YsLLBytpnH5-mFd5fUfMoysQ89fNSLFiRj";
        String bucket = "shf-001";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "D:\\picture\\touxiang.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "touxiang.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    @Test
    public void deleteFile(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone1());
//...其他参数参考类注释

        String accessKey = "WuDJ30b9eTTTM93ikvhzvHnAPrppGxNbwimoUoIC";
        String secretKey = "p2iaY6YsLLBytpnH5-mFd5fUfMoysQ89fNSLFiRj";
        String bucket = "shf-001";
        String key = "Fqg2IqKn_sCS6SvtxXFVwOE7lJDP";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }
}
