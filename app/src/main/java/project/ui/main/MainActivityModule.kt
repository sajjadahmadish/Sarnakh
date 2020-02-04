package project.ui.main

import androidx.lifecycle.ViewModelProviders
import com.mikepenz.materialdrawer.DrawerBuilder
import dagger.Module
import dagger.Provides
import project.ViewModelProviderFactory


@Module
class MainActivityModule {

    @Provides
    fun provideMainViewModel(
        factory: ViewModelProviderFactory,
        activity: MainActivity
    ): MainViewModel =
        ViewModelProviders.of(activity, factory)[MainViewModel::class.java]


    @Provides
    fun provideMainPagerAdapter(activity: MainActivity): MainPagerAdapter {
        return MainPagerAdapter(activity.supportFragmentManager)
    }

    @Provides
    fun provideDrawer(activity: MainActivity) = DrawerBuilder().withActivity(activity)!!


}




