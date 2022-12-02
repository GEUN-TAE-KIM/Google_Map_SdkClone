package jp.co.archive_asia.googlemapsdkclone.misc

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class CameraAndViewport {

    val kyoto: CameraPosition = CameraPosition.Builder()
        .target(LatLng(34.99490705490703, 135.7851237570075))
        .zoom(10f)
        .bearing(20f)
        .tilt(45f)
        .build()
}