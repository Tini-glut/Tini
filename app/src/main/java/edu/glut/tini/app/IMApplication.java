package edu.glut.tini.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import androidx.preference.PreferenceManager;


import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;


import java.util.List;

import cn.bmob.v3.Bmob;
import edu.glut.tini.R;
import edu.glut.tini.adapter.EMMessageListenerAdapter;

public class IMApplication extends Application {
    private static final String TAG = "IMApplication";
    private static final String key = "60f184af6b1abc4ae4a4b03565f1af10";
    public static boolean AUTOLOGIN = true;

    /**
     * 新信息通知播放通知声音
     */
    private SoundPool mSoundPool;
    private static final int DEFAULT_INVALID_SOUND_ID = 1;
    private int mSoundId = 1;
    private int mStreamId = 1;
    /**************************/

    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //AUTOLOGIN = preferences.getBoolean(getString(R.string.auto_login_key),true);

        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        options.setAcceptInvitationAlways(true);
        options.setAutoLogin(AUTOLOGIN);

        //初始化
        EMClient.getInstance().init(IMApplication.this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        //初始化Bmod

        Bmob.initialize(IMApplication.this, key);


        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListenerAdapter() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                //判断是否前台
                if(isForgeground()){
                    playAudio();
                }else {
                    playAudio();
                }

            }
        });


    }



    /**
     * 判断是否前台
     * @return
     */
    private boolean isForgeground () {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
            if (runningAppProcess.processName.equals(this.getPackageName())){
                return runningAppProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }
        return false;
    }



    private void playAudio() {
        SoundPool mSoundPool = createSoundPool();
        if (mSoundPool == null) return;
        mSoundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            if (mSoundPool != null) {
                mStreamId = mSoundPool.play(mSoundId, 1.0f,
                        1.0f, 16, 0, 1.0f);
            }
        });
        if (mSoundId == DEFAULT_INVALID_SOUND_ID) {
            mSoundId = mSoundPool.load(getApplicationContext(), R.raw.general, 1/*0*/);
        } else {
            if (mStreamId == DEFAULT_INVALID_SOUND_ID)
                onLoadComplete(mSoundPool, 0, 0);
        }
    }
    private SoundPool createSoundPool() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes mAudioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(16)
                    .setAudioAttributes(mAudioAttributes)
                    .build();
        } else {
            mSoundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
        }
        return mSoundPool;
    }

    /*
    1.mSoundId load方法返回的值,指向某个已加载的音频资源
    2.leftVolume\rightVolume 用来这种左右声道的值.范围 0.0f ~ 1.0f
    3.priority 流的优先级
    4.loop 循环播放的次数, -1 表示无限循环
    5.rate 播放的速率 , 2 表示2倍速度
    */

    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        if (mSoundPool != null) {
            mStreamId = mSoundPool.play(mSoundId, 1.0f, 1.0f, 16, -1, 1.0f);
        }
    }
    public void pause() {
        if (mSoundPool != null) {
            mSoundPool.pause(mStreamId);
        }
    }
    public void resume() {
        if (mSoundPool != null) {
            mSoundPool.resume(mStreamId);
        }
    }
    public void stop() {
        if (mSoundPool != null) {
            mSoundPool.stop(mStreamId);
            mStreamId = DEFAULT_INVALID_SOUND_ID;
        }
    }
    public void releaseSound() {
        if (mSoundPool != null) {
            mSoundPool.autoPause();
            mSoundPool.unload(mSoundId);
            mSoundId = DEFAULT_INVALID_SOUND_ID;
            mSoundPool.release();
            mSoundPool = null;
        }
    }
}
