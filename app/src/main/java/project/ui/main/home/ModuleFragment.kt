package project.ui.main.home

import android.content.Context
import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import project.utils.GlideImageLoadingService


@Module
@InstallIn(FragmentComponent::class)
class HomeFragmentModule {

    @Provides
    fun provideGlideImageLoader(@ActivityContext context: Context) = GlideImageLoadingService(context)
}




