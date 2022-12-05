package jp.co.archive_asia.googlemapsdkclone.misc

import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import jp.co.archive_asia.googlemapsdkclone.R
import kotlinx.coroutines.delay
import com.google.android.gms.maps.model.Dot as Dot

class Shapes {

    private val kotoy = LatLng(34.99490705490703, 135.7851237570075)
    private val husimi = LatLng(34.96795042596563, 135.77569956219304)
    private val unicon = LatLng(35.624562979332424, 139.77562095676834)

    private val po = LatLng(34.99490705490703, 135.7851237570075)
    private val p1 = LatLng(34.96795042596563, 135.77569956219304)
    private val p2 = LatLng(35.624562979332424, 139.77562095676834)

    private suspend fun addPolyline(map: GoogleMap) {

        val pattern = listOf(Dot(), Gap(30f))

        val polyline = map.addPolyline(
            PolylineOptions().apply {
                add(kotoy, husimi)
                width(5f)
                color(Color.BLUE)
                // 직선을 유연하게 바꿈
                geodesic(true)
                clickable(true)
                //pattern
                //jointType(JointType.ROUND)
                // 시작과 끝을 설정
                //startCap(CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_foreground), 100f))
                //endCap(ButtCap())
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
                add(po, p1, p2)
                fillColor(R.color.black)
                strokeColor(R.color.black)
                zIndex(1f)
            }
        )
    }

    // 동그란 폴리곤
    fun addCircle(map: GoogleMap) {
        val circle = map.addCircle(
            CircleOptions().apply {
                center(kotoy)
                radius(50000.0)
                fillColor(R.color.purple_200)
            }
        )
    }
}