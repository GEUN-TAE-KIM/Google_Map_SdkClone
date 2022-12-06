package jp.co.archive_asia.googlemapsdkclone.util

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import com.vmadalin.easypermissions.EasyPermissions
import jp.co.archive_asia.googlemapsdkclone.util.Constants.PERMISSION_BACKGROUND_LOCATION_REQUEST_CODE
import jp.co.archive_asia.googlemapsdkclone.util.Constants.PERMISSION_LOCATION_REQUEST_CODE
import java.nio.file.attribute.AclEntry

object Permissions {

    // 위치 권한이 있는지 확인하는 함수
    fun hasLocationPermission(context: Context) =
        // 요청하는 권환
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )


    // 위치 권한 요청 함수
    fun requestLocationPermission(fragment: Fragment) {
        EasyPermissions.requestPermissions(
            fragment,
            "요청을 하세요",
            // content로 만든 권한, 위치, 요청 코드를 가져 오는 것
            PERMISSION_LOCATION_REQUEST_CODE,
            // 요청하는 실제 권환
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    // 백그라운드 위치 권한 요청 함수 API 29이상
    fun hasBackgroundLocationPermission(context: Context): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
        return true
    }

    // 백그라운드 권한이 있을 때 새 권한을 요청하는 함수
    fun requestBackgroundLocationPermission(fragment: Fragment) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                fragment,
                "권한 요청",
                PERMISSION_BACKGROUND_LOCATION_REQUEST_CODE,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

}