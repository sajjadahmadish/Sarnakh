package project.ui.map

import android.Manifest
import android.content.Intent
import android.content.res.Resources
import android.location.Location
import android.location.LocationListener
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.jakewharton.rxbinding3.view.clicks
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.R
import ir.sinapp.sarnakh.databinding.ActivityMapBinding
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import project.data.model.Marker
import project.ui.base.BaseActivity
import project.ui.mission.MissionActivity
import project.utils.AppLogger
import project.utils.Bungee
import project.utils.LocationUtils
import project.utils.launchActivity
import project.utils.map.OnMapAndViewReadyListener
import javax.inject.Inject

@RuntimePermissions
class MapActivity : BaseActivity<ActivityMapBinding, MapViewModel>(ActivityMapBinding::class.java),
    MapNavigator, OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    private lateinit var list: List<Marker>
    override val bindingVariable: Int
        get() = BR.viewModel

    @Inject
    override lateinit var viewModel: MapViewModel

    lateinit var userLocation: Location

    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map)
                as SupportMapFragment
        OnMapAndViewReadyListener(mapFragment, this)


        viewModel += binding.nav.clicks().subscribe {
            val gmmIntentUri =
                Uri.parse("google.navigation:q=" + viewModel.selected.get()!!.lat + "," + viewModel.selected.get()!!.lang + "&mode=d")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        viewModel += binding.moreDetail.clicks().subscribe {
            openMissionActivity()
        }
    }


    private fun openMissionActivity() {
        launchActivity<MissionActivity> {}
        Bungee.fade(this)
        finish()
    }

    override fun onMapReady(googleMap: GoogleMap?) {

        if (googleMap != null) {
            map = googleMap
        }

        try {
            val success = googleMap!!.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.style_map
                )
            )

            if (!success) {
                AppLogger.i("Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            AppLogger.i("Can't find style. Error: $e")
        }

        with(googleMap!!.uiSettings) {
            isTiltGesturesEnabled = false
            isRotateGesturesEnabled = false
        }

        locationReadyWithPermissionCheck()

    }

    private fun getMarker() {
        viewModel.getMarkers { markers ->
            list = markers
            markers.map { MarkerOptions() }

            map.clear()

            markers.forEach { marker ->
                val m =
                    MarkerOptions()
                        .position(LatLng(marker.lat, marker.lang))
                        .title(marker.name)
                map.addMarker(m)
            }

        }
    }


    fun initialize() {
        map.setOnMapClickListener {
            viewModel.selected.set(null)
        }

        map.setOnMarkerClickListener { marker ->
            val position = marker.position
            val selected = viewModel.selected.get()
            if (selected != null) {
                val sLatLng = LatLng(selected.lat, selected.lang)
                if (sLatLng == position) {
                    viewModel.selected.set(null)
                    return@setOnMarkerClickListener true
                }
            }
            val find = list.find {
                val s: LatLng = LatLng(it.lat, it.lang)
                it.lang
                s == position
            }!!

            viewModel.selected.set(find)

            marker.hideInfoWindow()

/*
            for (int i = 0; i < list.size(); i++) {
            if (marker.getPosition().latitude == Double.parseDouble(list.get(i).getLon()) && marker.getPosition().longitude == Double.parseDouble(
                    list.get(i).getLat()
                )
            ) {
                title.setText(list.get(i).getStoreName());
                address.setText(list.get(i).getAddress());
                selectedId = list.get(i).getId();
                break;
            }
*/
            return@setOnMarkerClickListener false
        }


    }

    private fun setCameraWithCoordinationBounds(southwest: LatLng, northeast: LatLng) {
        val bounds = LatLngBounds(southwest, northeast)
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }

    private fun getDeviceLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        try {
            //permission granted
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { currentLocation ->

                if (currentLocation != null) {
                    moveCamera(LatLng(currentLocation.latitude, currentLocation.longitude), 13.75f)
                } else {
//                    showSettingDialog();
                }

            }

        } catch (e: SecurityException) {
            Log.e("test", "securityException : " + e.message)
        }

    }


    private fun moveCamera(latLng: LatLng, zoom: Float) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))

    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun locationReady() {

        getDeviceLocation()

        getMarker()

        initialize()

        map.isMyLocationEnabled = true
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = true

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //     NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }


    /*

    public static double x;
    public static double y;

    private Marker mSelectedMarker = null;
    private CardView card;
    private TextView title;
    private TextView address;
    private String selectedId;
    private AppCompatButton moreDetail;
    private AppCompatButton nav;
    private List<MarkerBrief> list;


    void init() {
        mMap.setOnMapClickListener(latLng -> {
            mSelectedMarker = null;
            card.setVisibility(View.GONE);
        });

        mMap.setOnMarkerClickListener(marker -> {
            if (marker.equals(mSelectedMarker)) {
                mSelectedMarker = null;
                card.setVisibility(View.GONE);
                return true;
            }
            mSelectedMarker = marker;
            card.setVisibility(View.VISIBLE);
            marker.hideInfoWindow();

            for (int i = 0; i < list.size(); i++) {
                if (marker.getPosition().latitude == Double.parseDouble(list.get(i).getLon()) && marker.getPosition().longitude == Double.parseDouble(list.get(i).getLat())) {
                    title.setText(list.get(i).getStoreName());
                    address.setText(list.get(i).getAddress());
                    selectedId = list.get(i).getId();
                    break;
                }
            }

            return false;
        });
    }


    private void getDeviceLocation() {

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            //permission granted
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, currentLocation -> {

                if (currentLocation != null) {
                    moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 13.75f);
                } else {
                    showSettingDialog();
                }

            });

        } catch (SecurityException e) {
            Log.e("test", "securityException : " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_maps_test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initGoogleAPIClient();

        card = findViewById(R.id.card);
        title = findViewById(R.id.title);
        address = findViewById(R.id.address);
        moreDetail = findViewById(R.id.more_detail);

        moreDetail.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, DetailActivity.class);
            intent.putExtra("id", selectedId);
            startActivity(intent);
        });

        card.setVisibility(View.GONE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    void getMarker() {
        Intent intentExtra = getIntent();
        int catId = intentExtra.getIntExtra("catId", 0);
        int icon = intentExtra.getIntExtra("icon", 0);

        dataManager.loadMarkers(catId).observe(this, markers -> {
            list = markers;
            int size = 20;
            Bitmap bitmap = resizeImage(MapsActivity.this, icon, size, size);
            for (int i = 0; i < markers.size(); i++)
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(markers.get(i).getLon()), Double.parseDouble(markers.get(i).getLat())))
                        .title(markers.get(i).getStoreName())
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
        });

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        init();
        getDeviceLocation();

        getMarker();

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    /**
     * simply resizes a given drawable resource to the given width and height
     */
    public static Bitmap resizeImage(Context ctx, int resId, int iconWidth,
                                     int iconHeight) {
        iconHeight = Tools.dpToPx(ctx, iconHeight);
        iconWidth = Tools.dpToPx(ctx, iconWidth);

        // load the origial Bitmap
        Bitmap BitmapOrg = BitmapFactory.decodeResource(ctx.getResources(),
                resId);

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = iconWidth;
        int newHeight = iconHeight;

        // calculate the scale
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);

        // make a Drawable from Bitmap to allow to set the Bitmap
        // to the ImageView, ImageButton or what ever
        return resizedBitmap;
    }

    private static GoogleApiClient mGoogleApiClient;

    /* Initiate Google API Client  */
    private void initGoogleAPIClient() {
        //Without Google API Client Auto Location Dialog will not work
        mGoogleApiClient = new GoogleApiClient.Builder(MapsActivity.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void showSettingDialog() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Setting priotity of Location request to high
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);//5 sec Time interval for location update
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            final LocationSettingsStates state = result1.getLocationSettingsStates();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    // All location settings are satisfied. The client can initialize location
                    // requests here.

                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        status.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                        // Ignore the error.
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    // Location settings are not satisfied. However, we have no way to fix the
                    // settings so we won't show the dialog.
                    break;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));//Register broadcast receiver to check the status of GPS
    }

    @Override
    protected void onStop() {
        unregisterReceiver(gpsLocationReceiver);
        super.onStop();
    }

    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";

    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //If Action is Location
            if (intent.getAction().matches(BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //Check if GPS is turned ON or OFF
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e("About GPS", "GPS is Enabled in your device");
                    getDeviceLocation();
                    new Handler().postDelayed(() -> getDeviceLocation(), 1000);
                } else {
                    //If GPS turned OFF show Location Dialog
                    new Handler().postDelayed(sendUpdatesToUI, 10);
                    // showSettingDialog();
//                    updateGPSStatus("GPS is Disabled in your device");
                    Log.e("About GPS", "GPS is Disabled in your device");
                }

            }
        }
    };

    //Run on UI
    private Runnable sendUpdatesToUI = this::showSettingDialog;

}






    * */


}




