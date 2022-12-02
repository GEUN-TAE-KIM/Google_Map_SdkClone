package jp.co.archive_asia.googlemapsdkclone.misc

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class CameraAndViewport {

    val kyoto: CameraPosition = CameraPosition.Builder()
        .target(LatLng(34.99490705490703, 135.7851237570075))
        .zoom(10f)
        .bearing(20f)
        .tilt(45f)
        .build()

    val tokyo = LatLngBounds(
        LatLng(35.70080424053555, 139.70750775208046),
        LatLng(35.70833159142713, 139.7092243659008)
    )
}