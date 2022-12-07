package jp.co.archive_asia.googlemapsdkclone.service

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import jp.co.archive_asia.googlemapsdkclone.util.Constants.ACTION_SERVICE_START
import jp.co.archive_asia.googlemapsdkclone.util.Constants.ACTION_SERVICE_STOP

class TrackerService: LifecycleService() {

    companion object {
        val started = MutableLiveData<Boolean>()
    }

    private fun setInitialValues() {
        started.postValue(false)
    }

    override fun onCreate() {
        setInitialValues()
        super.onCreate()
    }

    // startService() 함수가 호출되면 호출된다. 독립적인 서비스가 필요할 때 구현
    // 반드시 topSelf() 혹은 stopService()로 종료 시켜야 함
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when(it.action) {
                ACTION_SERVICE_START -> {
                    started.postValue(true)
                }
                ACTION_SERVICE_STOP -> {
                    started.postValue(false)
                }
                else -> {}
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}