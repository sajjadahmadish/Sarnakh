/*package ${packageName}

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class ${fragmentName}FragmentModule {

    @Provides
    fun provide${fragmentName}ViewModel(
        factory: ViewModelProviderFactory,
        fragment: ${fragmentName}Fragment
    ): ${fragmentName}ViewModel =
        ViewModelProviders.of(fragment, factory)[${fragmentName}ViewModel::class.java]

}
*/




