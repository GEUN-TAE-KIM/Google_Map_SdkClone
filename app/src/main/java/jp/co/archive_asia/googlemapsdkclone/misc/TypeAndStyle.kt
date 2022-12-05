package jp.co.archive_asia.googlemapsdkclone.misc

import android.content.Context
import android.util.Log
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import jp.co.archive_asia.googlemapsdkclone.R
import java.lang.Exception

class TypeAndStyle {

    // 구글지도 스타일을 제이손으로 불러서 적용한 것
    // https://mapstyle.withgoogle.com/ 들어가서 하면됨
    fun setMapStyle(googleMap: GoogleMap, context: Context) {
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context,
                    R.raw.style
                )
            )
            if (!success) {
                Log.d("Maps", "Failed to add Style.")
            }
        } catch (e: Exception) {
            Log.d("Maps", e.toString())
        }
    }

    fun setMapType(item: MenuItem, map: GoogleMap) {

        when (item.itemId) {
            R.id.normal_map -> {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
            R.id.hybrid_map -> {
                map.mapType = GoogleMap.MAP_TYPE_HYBRID
            }

            R.id.satellite_map -> {
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }

            R.id.terrain_map -> {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }
            R.id.none_map -> {
                map.mapType = GoogleMap.MAP_TYPE_NONE
            }
        }
    }

}