package project;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import project.data.DataManager;
import project.ui.ar.ARViewModel;
import project.ui.intro.IntroViewModel;
import project.ui.lucky.LuckyViewModel;
import project.ui.main.MainViewModel;
import project.ui.main.best.BestViewModel;
import project.ui.main.help.HelpViewModel;
import project.ui.main.home.HomeViewModel;
import project.ui.main.notification.NotificationViewModel;
import project.ui.main.setting.SettingViewModel;
import project.ui.map.MapViewModel;
import project.ui.mission.MissionViewModel;
import project.ui.missionList.MissionListViewModel;
import project.ui.osm.OsmViewModel;
import project.ui.profile.ProfileViewModel;
import project.ui.splash.SplashViewModel;
import project.utils.rx.SchedulerProvider;


@Singleton
public class ViewModelProviderFactory implements ViewModelProvider.Factory {


    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public ViewModelProviderFactory(DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(HelpViewModel.class)) {
            return (T) new HelpViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(SettingViewModel.class)) {
            return (T) new SettingViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(NotificationViewModel.class)) {
            return (T) new NotificationViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(OsmViewModel.class)) {
            return (T) new OsmViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(IntroViewModel.class)) {
            return (T) new IntroViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(BestViewModel.class)) {
            return (T) new BestViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(ARViewModel.class)) {
            return (T) new ARViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(LuckyViewModel.class)) {
            return (T) new LuckyViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(MissionViewModel.class)) {
            return (T) new MissionViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(MissionListViewModel.class)) {
            return (T) new MissionListViewModel(dataManager, schedulerProvider);
        }


        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

}
