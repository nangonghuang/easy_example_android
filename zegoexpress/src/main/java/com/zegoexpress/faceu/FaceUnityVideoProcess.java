package com.tastielivefriends.zegoexpress.faceu;


import android.util.Log;
import com.faceunity.core.enumeration.FUInputTextureEnum;
import com.faceunity.nama.FURenderer;
import com.faceunity.nama.listener.FURendererListener;
import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zegoexpress.callback.IZegoCustomVideoProcessHandler;
import im.zego.zegoexpress.constants.ZegoPublishChannel;
/**
 * VideoFilterByProcess2
 * 通过Zego视频前处理，用户可以获取到Zego SDK采集到的摄像头数据。用户后续将数据塞给FaceUnity处理，最终将处理后的数据塞回Zego SDK进行推流。
 * 采用GL_TEXTURE_2D方式传递数据
 */
/**
 * VideoFilterByProcess2
 * Through the Zego video pre-processing, users can obtain the camera data collected by the Zego SDK. The user then stuffs the data to FaceUnity for processing, and finally stuffs the processed data back to Zego SDK for publishing stream.
 *Use GL_TEXTURE_2D to transfer data
 */
public class FaceUnityVideoProcess extends IZegoCustomVideoProcessHandler {

    private static final String TAG = "FaceUnityVideoProcess";
    private FURendererListener listener;

    public FaceUnityVideoProcess(){
        FURenderer.getInstance().setInputTextureType(FUInputTextureEnum.FU_ADM_FLAG_COMMON_TEXTURE);
    }

    @Override
    public void onCapturedUnprocessedTextureData(int textureID, int width, int height, long referenceTimeMillisecond, ZegoPublishChannel channel) {
        int fuTextureId = FURenderer.getInstance().onDrawFrameDualInput(null,textureID, width, height);
        ZegoExpressEngine.getEngine().sendCustomVideoProcessedTextureData(fuTextureId,width,height,referenceTimeMillisecond);
    }

    @Override
    public void onStart(ZegoPublishChannel zegoPublishChannel) {
        super.onStart(zegoPublishChannel);
        Log.d(TAG, "debug>>> onStart: ");
        FURenderer.getInstance().prepareRenderer(listener);
    }

    @Override
    public void onStop(ZegoPublishChannel zegoPublishChannel) {
        super.onStop(zegoPublishChannel);
        Log.d(TAG, "debug>>> onStop: ");
        FURenderer.getInstance().release();
    }

    public void setListener(FURendererListener listener) {
        this.listener = listener;
    }
}
