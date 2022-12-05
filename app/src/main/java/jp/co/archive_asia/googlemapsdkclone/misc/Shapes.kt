package jp.co.archive_asia.googlemapsdkclone.misc

import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.delay

class Shapes {

    private val kotoy = LatLng(34.99490705490703, 135.7851237570075)
    private val husimi = LatLng(34.96795042596563, 135.77569956219304)
    private val unicon = LatLng(35.624562979332424, 139.77562095676834)

    private val po = LatLng(34.99490705490703, 135.7851237570075)
    private val p1 = LatLng(34.96795042596563, 135.77569956219304)
    private val p2 = LatLng(35.624562979332424, 139.77562095676834)

    private suspend fun addPolyline(map:GoogleMap) {
        val polyline = map.addPolyline(
            PolylineOptions().apply {
                add(kotoy, husimi)
                width(5f)
                color(Color.BLUE)
                // 직선을 유연하게 바꿈
                geodesic(true)
                clickable(true)
            }
        )

        delay(5000L)

        val newList = listOf(
            kotoy, unicon, husimi
        )

        polyline.points = newList
    }

    fun addPolygon(map: GoogleMap) {
        val polygon = map.addPolygon(
            PolygonOptions().apply {
                add(po,p1,p2)
                fillColor(Color.BLUE)
                strokeColor(Color.BLUE)
            }
        )
    }
}