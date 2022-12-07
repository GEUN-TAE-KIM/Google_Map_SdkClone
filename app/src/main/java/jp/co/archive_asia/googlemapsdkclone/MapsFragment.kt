package jp.co.archive_asia.googlemapsdkclone

import android.annotation.SuppressLint
import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import jp.co.archive_asia.googlemapsdkclone.databinding.FragmentMapsBinding
import jp.co.archive_asia.googlemapsdkclone.databinding.FragmentPermissionBinding
import jp.co.archive_asia.googlemapsdkclone.service.TrackerService
import jp.co.archive_asia.googlemapsdkclone.util.Constants.ACTION_SERVICE_START
import jp.co.archive_asia.googlemapsdkclone.util.ExtensionFunctions.disable
import jp.co.archive_asia.googlemapsdkclone.util.ExtensionFunctions.hide
import jp.co.archive_asia.googlemapsdkclone.util.ExtensionFunctions.show
import jp.co.archive_asia.googlemapsdkclone.util.Permissions.hasBackgroundLocationPermission
import jp.co.archive_asia.googlemapsdkclone.util.Permissions.requestBackgroundLocationPermission
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)

        binding.startButton.setOnClickListener {
            onStartButtonClicked()
        }
        binding.stopButton.setOnClickListener { }
        binding.resetButton.setOnClickListener { }

        return binding.root
    }


    private fun onStartButtonClicked() {
        if (hasBackgroundLocationPermission(requireContext())) {
            startCountDown()
            binding.startButton.disable()
            binding.stopButton.hide()
            binding.resetButton.show()
        } else {
            requestBackgroundLocationPermission(this)
        }

    }

    //버튼을 클릭하면 해당 함수가 호출 되면서 숫자들이 나옴
    private fun startCountDown() {
        binding.timerTextView.show()
        binding.startButton.disable()
        val timer: CountDownTimer = object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val currentSecond = millisUntilFinished / 1000
                if(currentSecond.toString() == "0") {
                    binding.timerTextView.text = "go"
                    binding.timerTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }
                else {
                    binding.timerTextView.text = currentSecond.toString()
                    binding.timerTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_700))

                }
            }

            override fun onFinish() {
                sendActionCommandToService(ACTION_SERVICE_START)
                binding.timerTextView.hide()
            }
        }
        timer.start()
    }

    // 서비스에 작업 명령을 보내는 것
    private fun sendActionCommandToService(action: String) {
        Intent(
            requireContext(),
            TrackerService::class.java
        ).apply {
            this.action = action
            requireContext().startService(this)
        }
    }

    // 백그라운드 권한드거부할때 조건문
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestBackgroundLocationPermission(this)
        }
    }

    // 권환이 부여될때 시작버튼 누르면 호출
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        onStartButtonClicked()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    // 맵이 준비 될때마다 트리거하는 함수
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.isMyLocationEnabled = true
        map.setOnMyLocationButtonClickListener(this)
        map.uiSettings.apply {
            // 이동, 줌 등 다 하지 못하게 한것
            isZoomControlsEnabled = false
            isZoomGesturesEnabled = false
            isRotateGesturesEnabled = false
            isTiltGesturesEnabled = false
            isCompassEnabled = false
            isScrollGesturesEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMyLocationButtonClick(): Boolean {
        binding.hintTextView.animate().alpha(0f).duration = 1500
        lifecycleScope.launch {
            delay(2500)
            binding.hintTextView.hide()
            binding.startButton.show()
        }
        return false
    }
}