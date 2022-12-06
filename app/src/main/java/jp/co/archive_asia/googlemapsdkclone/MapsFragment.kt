package jp.co.archive_asia.googlemapsdkclone

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import jp.co.archive_asia.googlemapsdkclone.databinding.FragmentMapsBinding
import jp.co.archive_asia.googlemapsdkclone.databinding.FragmentPermissionBinding

class MapsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)

        binding.startButton.setOnClickListener {  }
        binding.stopButton.setOnClickListener {  }
        binding.resetButton.setOnClickListener {  }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    // 맵이 준비 될때마다 트리거하는 함수
    override fun onMapReady(p0: GoogleMap) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}