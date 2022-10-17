package com.yly.alioss.demo.controller;

import com.aliyun.tea.TeaException;
import com.aliyun.vod20170321.models.GetPlayInfoResponse;
import com.aliyun.vod20170321.models.GetVideoPlayAuthResponse;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;


public class DemoController {

    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.vod20170321.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "vod.cn-beijing.aliyuncs.com";
        return new com.aliyun.vod20170321.Client(config);
    }
//
//    public static void main(String[] args_) throws Exception {
//        java.util.List<String> args = java.util.Arrays.asList(args_);
//        com.aliyun.vod20170321.Client client = DemoController.createClient("LTAI4GKTkNmshPHuFku28PXi", "Um6lYxAU1wKHr8gGSj005ZSJdjf0J3");
//        com.aliyun.vod20170321.models.GetVideoPlayAuthRequest getVideoPlayAuthRequest = new com.aliyun.vod20170321.models.GetVideoPlayAuthRequest()
//                .setVideoId("8716433844474384bb349cd6e24d86dd");
//        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
//        try {
//            // 复制代码运行请自行打印 API 的返回值
//            GetVideoPlayAuthResponse videoPlayAuthWithOptions = client.getVideoPlayAuthWithOptions(getVideoPlayAuthRequest, runtime);
//            System.out.println("1");
//        } catch (TeaException error) {
//            // 如有需要，请打印 error
//            com.aliyun.teautil.Common.assertAsString(error.message);
//        } catch (Exception _error) {
//            TeaException error = new TeaException(_error.getMessage(), _error);
//            // 如有需要，请打印 error
//            com.aliyun.teautil.Common.assertAsString(error.message);
//        }
//    }
public static void main(String[] args_) throws Exception {
    java.util.List<String> args = java.util.Arrays.asList(args_);
    com.aliyun.vod20170321.Client client = DemoController.createClient("LTAI4GKTkNmshPHuFku28PXi", "Um6lYxAU1wKHr8gGSj005ZSJdjf0J3");
    com.aliyun.vod20170321.models.GetPlayInfoRequest getPlayInfoRequest = new com.aliyun.vod20170321.models.GetPlayInfoRequest()
            .setVideoId("8716433844474384bb349cd6e24d86dd");
    com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
    try {
        // 复制代码运行请自行打印 API 的返回值
        GetPlayInfoResponse playInfoWithOptions = client.getPlayInfoWithOptions(getPlayInfoRequest, runtime);
        System.out.println("11");
    } catch (TeaException error) {
        // 如有需要，请打印 error
        com.aliyun.teautil.Common.assertAsString(error.message);
    } catch (Exception _error) {
        TeaException error = new TeaException(_error.getMessage(), _error);
        // 如有需要，请打印 error
        com.aliyun.teautil.Common.assertAsString(error.message);
    }
}
}
