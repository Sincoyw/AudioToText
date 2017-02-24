package com.sincoyw.foryuanbao;

import com.iflytek.voicecloud.client.LfasrClient;
import com.iflytek.voicecloud.model.LfasrType;
import com.iflytek.voicecloud.model.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    static String appId = "";
    static String secretKey = "";
    static String taskId;
    static Message uploadMessage = null;
    static Message resultMessage = null;
    public static void main(String[] args) throws Exception {
        //
        InputStreamReader inputStreamReader=new InputStreamReader(System.in);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        try {
//            //输入appId
//            System.out.println("Please type your appId:");
//            appId = bufferedReader.readLine();
//            System.out.println("your input=" + appId);
//
//            //输入secretKey
//            System.out.println("Please type your secretKey:");
//            secretKey = bufferedReader.readLine();
//            System.out.println("your input=" + secretKey);

            //上传音频文件
            System.out.println("Please type your file path:");
            String filePath = bufferedReader.readLine();
            System.out.println("your input=" + filePath);
            upload(filePath);

            while (true) {
                Thread.sleep(1000);
                System.out.println("Waiting...");
                if (uploadMessage.getOk() == 0) {
                    System.out.println("Upload success!!");
                    taskId = uploadMessage.getData();

                    result();
                } else {
                    System.out.println("Upload failed!!:" + uploadMessage.getFailed());
                    break;
                }

                //获取转写文字结果
                if (null != resultMessage) {
                    if (resultMessage.getOk() == 0) {
                        System.out.println("Transform success!!");
                        System.out.println(resultMessage.getData());
                        break;
                    } else {
                        System.out.println("Transforming!!!");
                        System.out.println(resultMessage.getFailed());
                    }
                }

            }

            //before exit
            System.out.println("Type 'Enter' to exit.");
            bufferedReader.readLine();
        }
        catch (Exception exception) {
            System.exit(1);
        }
    }

    public static void upload(String filePath) throws Exception {
        LfasrType type = LfasrType.LFASR_TELEPHONY_RECORDED_AUDIO;
        LfasrClient client = LfasrClient.InitClient(appId, secretKey, type);
        uploadMessage = client.lfasr_upload(filePath);
        System.out.println(uploadMessage);
    }

    public static void result() throws Exception {
        LfasrType type = LfasrType.LFASR_TELEPHONY_RECORDED_AUDIO;
        LfasrClient client = LfasrClient.InitClient(appId, secretKey, type);
        resultMessage = client.lfasr_get_result(taskId);
        System.out.println(resultMessage);
    }

}
