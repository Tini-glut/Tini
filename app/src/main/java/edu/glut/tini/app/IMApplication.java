package edu.glut.tini.app;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.preference.PreferenceManager;


import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import cn.bmob.v3.Bmob;
import edu.glut.tini.R;
import edu.glut.tini.adapter.EMMessageListenerAdapter;
import edu.glut.tini.ui.activity.ChatActivity;

public class IMApplication extends Application {
    private static final String TAG = "IMApplication";
    private static final String key = "60f184af6b1abc4ae4a4b03565f1af10";
    private static final String CHANNEL_ID = "edu.glut.tini";
    public static boolean AUTOLOGIN = true;

    /**
     * 新信息通知播放通知声音
     */
    private SoundPool mSoundPool;
    private static final int DEFAULT_INVALID_SOUND_ID = 1;
    private int mSoundId = 1;
    private int mStreamId = 1;
    private NotificationManager manager;

    /**************************/

    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //AUTOLOGIN = preferences.getBoolean(getString(R.string.auto_login_key),true);
        manager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//         manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                //判断是否前台
                if (isForgeground()) {
                    playAudio();
//                    Log.d(TAG, "onMessageReceived: 前台");
                } else {
//                    Log.d(TAG, "onMessageReceived: 后台");
                    playAudio();
                    showNotification(list);
                }

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification(List<EMMessage> list) {
//        Log.d(TAG, "showNotification: 未读消息数量 "+list.size());

        for (EMMessage emMessage : list) {
            EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
            String message = body.getMessage();
            String fromWho = list.get(0).getFrom();
            if (emMessage.getType() != (EMMessage.Type.TXT)) {
                message = "非文本消息";
            }
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("userName", emMessage.conversationId());
//            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this).addParentStack(ChatActivity.class).addNextIntent(intent);
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(fromWho)
                    .setContentText(message)
                    .setWhen(emMessage.getMsgTime())
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();

            NotificationChannel notificationChannel = getNotificationChannel();
            manager.createNotificationChannel(notificationChannel);
            manager.notify(1, notification);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NotNull
    private NotificationChannel getNotificationChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "普通消息", NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription("description");
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        return notificationChannel;
    }


    /**
     * 判断是否前台
     * @return
     */
    private boolean isForgeground() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
            if (runningAppProcess.processName.equals(this.getPackageName())) {
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
            mSoundId = mSoundPool.load(this, R.raw.general, 1/*0*/);
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
