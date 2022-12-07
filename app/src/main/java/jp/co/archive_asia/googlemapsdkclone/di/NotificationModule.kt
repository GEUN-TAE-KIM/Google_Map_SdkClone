package jp.co.archive_asia.googlemapsdkclone.di

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import jp.co.archive_asia.googlemapsdkclone.ui.MainActivity
import jp.co.archive_asia.googlemapsdkclone.R
import jp.co.archive_asia.googlemapsdkclone.util.Constants.ACTION_NAVIGATE_TO_MAPS_FRAGMENT
import jp.co.archive_asia.googlemapsdkclone.util.Constants.NOTIFICATION_CHANNEL_ID
import jp.co.archive_asia.googlemapsdkclone.util.Constants.PENDING_INTENT_REQUEST_CODE

// dagger2에서는 모듈만 선언해도 사용자가 정의한 component에 해당 모듈 클래스를 직접 포함해주지만
// hilt에서는 InstallIn하여 모듈을 인스톨 가능
@Module
// 어떤 component 인스톨 할지 정해야함
@InstallIn(ServiceComponent::class)
object NotificationModule {

    @SuppressLint("UnspecifiedImmutableFlag")
    @ServiceScoped
    @Provides
    fun providePendingIntent(
        @ApplicationContext context: Context
    ): PendingIntent {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context,
                PENDING_INTENT_REQUEST_CODE,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(
                context,
                PENDING_INTENT_REQUEST_CODE,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }


    // 알림 표시
    @ServiceScoped
    @Provides
    // 포그라운드 서비스에는 Notification을 표시 해야함
    fun provideNotificationBuilder(
        @ApplicationContext context: Context,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_baseline_directions_run_24)
            .setContentIntent(pendingIntent)
    }

    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}