package jp.co.archive_asia.googlemapsdkclone.misc

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class Markers {

    // 벡터 파일을 비트맵 개체로 변환하고 비트맵을 반환하는 방법
  /*  private fun fromVectorToBitmap(id: Int, color: Int): BitmapDescriptor {
        val vectorDrawable: Drawable? = ResourcesCompat.getDrawable(resources, id, null)
        if (vectorDrawable == null) {
            Log.d("MapsActivity", "Resource not found.")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)

    }*/
}