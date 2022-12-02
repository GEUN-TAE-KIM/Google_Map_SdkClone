package jp.co.archive_asia.googlemapsdkclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import jp.co.archive_asia.googlemapsdkclone.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // 구글지도 켜서 해당 위치 오른쪽 마우스 클릭하면 위도경도 다 나옴
        val kotoy = LatLng(34.99490705490703, 135.7851237570075)
        map.addMarker(MarkerOptions().position(kotoy).title("기요미즈데라"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(kotoy, 10f))
        map.uiSettings.apply {
            // 화면에 줌할수있는 +/- 를 나타내는 것
            isZoomControlsEnabled = true
            // 손가락 터치로 줌을 할수있냐없냐 를 나타낸 것
           // isZoomGesturesEnabled = false
            // 정적으로 맵을 보여주는 것 (동작불가 정지 화면)
           // isScrollGesturesEnabled = false

            // 자신의 장소를 버튼으로 활성화 하는 것 (자신의 위치 활성화 해야함)
            isMyLocationButtonEnabled = true
        }
    }
}