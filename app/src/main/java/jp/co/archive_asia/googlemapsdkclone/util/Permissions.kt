package jp.co.archive_asia.googlemapsdkclone.util

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import com.vmadalin.easypermissions.EasyPermissions
import jp.co.archive_asia.googlemapsdkclone.util.Constants.PERMISSION_LOCATION_REQUEST_CODE

object Permissions {

    // 위치 권환이 있는지 확인하는 함수
    fun hasLocationPermission(context: Context) =
        // 요청하는 권환
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )


    // 위치 권환 요청 함수
    fun requestLocationPermission(fragment: Fragment) {
        EasyPermissions.requestPermissions(
            fragment,
            "요청을 하세요",
            // content로 만든 권환, 위치, 요청 코드를 가져 오는 것
            PERMISSION_LOCATION_REQUEST_CODE,
            // 요청하는 실제 권환
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

}