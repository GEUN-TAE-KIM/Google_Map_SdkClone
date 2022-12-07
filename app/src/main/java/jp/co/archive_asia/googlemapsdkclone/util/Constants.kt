package jp.co.archive_asia.googlemapsdkclone.util

object Constants {

    const val PERMISSION_LOCATION_REQUEST_CODE = 1
    const val PERMISSION_BACKGROUND_LOCATION_REQUEST_CODE = 2

    const val ACTION_SERVICE_START = "ACTION_SERVICE_START"
    const val ACTION_SERVICE_STOP = "ACTION_SERVICE_STOP"
    const val ACTION_NAVIGATE_TO_MAPS_FRAGMENT = "ACTION_NAVIGATE_TO_MAPS_FRAGMENT"

    //포그라운드 서비스중에서 스톱을 해야 알림을 제거 할 수가 있음
    // 그렇다면 여태 앱에서 알림을 제거하지 못하게 해놓은건 스톱 기능을 활성화 하지 않아서 제거를 못한거였군
    // 그래서 알림을 못뜨게 따로 핸드폰에서 제거하는 방법 밖에 없던거
    const val NOTIFICATION_CHANNEL_ID = "tracker_notification_id"
    const val NOTIFICATION_CHANNEL_NAME = "tracker_notification"
    const val NOTIFICATION_ID = 3

    const val PENDING_INTENT_REQUEST_CODE = 99

}