package project.ui.osm

import android.os.Bundle
import androidx.activity.viewModels
import project.ui.base.BaseActivity
import javax.inject.Inject

import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.BuildConfig
import ir.sinapp.sarnakh.databinding.ActivityOsmBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import androidx.preference.PreferenceManager
import org.osmdroid.views.CustomZoomButtonsController
import dagger.hilt.android.AndroidEntryPoint
import ir.sinapp.sarnakh.databinding.ActivityLoginBinding


@AndroidEntryPoint
class OsmActivity: BaseActivity<ActivityOsmBinding, OsmViewModel>(ActivityOsmBinding::class.java), OsmNavigator {


    override val viewModel: OsmViewModel by viewModels { this.defaultViewModelProviderFactory }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(ActivityOsmBinding.inflate(layoutInflater))
        viewModel.navigator = this

        //load/initialize the osmdroid configuration, this can be done
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID

        binding.map.setTileSource(TileSourceFactory.MAPNIK);

        binding.map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        binding.map.setMultiTouchControls(true);
        binding.map.minZoomLevel = 6.0
        binding.map.isFlingEnabled = false
    }





    public override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        binding.map.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    public override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        binding.map.onPause()  //needed for compass, my location overlays, v6.0.0 and up
    }

}




