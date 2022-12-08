package jp.co.archive_asia.googlemapsdkclone.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import jp.co.archive_asia.googlemapsdkclone.util.Constants.ACTION_SERVICE_START
import jp.co.archive_asia.googlemapsdkclone.util.Constants.ACTION_SERVICE_STOP
import jp.co.archive_asia.googlemapsdkclone.util.Constants.LOCATION_FASTEST_UPDATE_INTERVAL
import jp.co.archive_asia.googlemapsdkclone.util.Constants.LOCATION_UPDATE_INTERVAL
import jp.co.archive_asia.googlemapsdkclone.util.Constants.NOTIFICATION_CHANNEL_ID
import jp.co.archive_asia.googlemapsdkclone.util.Constants.NOTIFICATION_CHANNEL_NAME
import jp.co.archive_asia.googlemapsdkclone.util.Constants.NOTIFICATION_ID
import java.nio.file.attribute.AclEntry
import javax.inject.Inject

@AndroidEntryPoint
class TrackerService: LifecycleService() {

    // 객체를 주입
    // @Inject 어노테이션이 붙은 변수는 의존성을 주입받는 포인트를 선언한다 는 의미
    @Inject
    lateinit var notification: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManager

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        val started = MutableLiveData<Boolean>()
        val startTime = MutableLiveData<Long>()
        val stopTime = MutableLiveData<Long>()

        val locationList = MutableLiveData<MutableList<LatLng>>()
    }

    private fun setInitialValues() {
        started.postValue(false)
        startTime.postValue(0L)
        stopTime.postValue(0L)

        locationList.postValue(mutableListOf())
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            result.locations.let { locations ->
                for (location in locations) {
                    updateLocationList(location)

                }

            }
        }
    }

    // 새로운 정보를 받을 때 마다 위치 목록을 업데이트 할려는 것
    private fun updateLocationList(location: Location) {
        val newLatLng = LatLng(location.latitude, location.longitude)
        locationList.value?.apply {
            add(newLatLng)
            locationList.postValue(this)
        }
    }

    override fun onCreate() {
        setInitialValues()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        super.onCreate()
    }

    // startService() 함수가 호출되면 호출된다. 독립적인 서비스가 필요할 때 구현
    // 반드시 topSelf() 혹은 stopService()로 종료 시켜야 함
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when(it.action) {
                ACTION_SERVICE_START -> {
                    started.postValue(true)
                    startForegroundService()
                    startLocationUpdates()
                }
                ACTION_SERVICE_STOP -> {
                    started.postValue(false)
                    stopForegroundService()
                }
                else -> {}
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    // 포그라운드 서브스의 알림 기능 생성한걸 호출
    private fun startForegroundService() {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, notification.build())
    }

    // 위치를 업데이트 하는 함수 (LocationRequest 찾아보기 -> 라이브러리로 새롭게 하는거 같음)
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {

        val locationRequest = LocationRequest.create().apply {
            interval = LOCATION_UPDATE_INTERVAL
            fastestInterval = LOCATION_FASTEST_UPDATE_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
        startTime.postValue(System.currentTimeMillis())
    }

    //포그라운드 서비스 스타트 한것을 스톱하는 것
    private fun stopForegroundService() {
        removeLocationUpdates()
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(
            NOTIFICATION_ID
        )
        stopForeground(true)
        stopSelf()
        stopTime.postValue(System.currentTimeMillis())
    }

    // 위치 업데이트를 제거 하며 콜백하여 전달
    private fun removeLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    // api 26이상을 사용하는 경우의 알림 채널을 공지
    @SuppressLint("ObsoleteSdkInt")
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}