@file:Suppress("unused")

package project.di.bulder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import project.ui.ar.ARActivity
import project.ui.ar.ARActivityModule
import project.ui.intro.IntroActivity
import project.ui.intro.IntroActivityModule
import project.ui.login.LoginActivity
import project.ui.login.LoginActivityModule
import project.ui.lucky.LuckyActivity
import project.ui.lucky.LuckyActivityModule
import project.ui.main.MainActivity
import project.ui.main.MainActivityModule
import project.ui.main.best.BestFragmentProvider
import project.ui.main.help.HelpFragmentProvider
import project.ui.main.home.HomeFragmentProvider
import project.ui.main.notification.NotificationFragmentProvider
import project.ui.main.setting.SettingFragmentProvider
import project.ui.map.MapActivity
import project.ui.map.MapActivityModule
import project.ui.mission.MissionActivity
import project.ui.mission.MissionActivityModule
import project.ui.missionList.MissionListActivity
import project.ui.missionList.MissionListActivityModule
import project.ui.osm.OsmActivity
import project.ui.osm.OsmActivityModule
import project.ui.profile.ProfileActivity
import project.ui.profile.ProfileActivityModule
import project.ui.splash.SplashActivity
import project.ui.splash.SplashActivityModule
import project.ui.ticket.TicketActivity
import project.ui.ticket.TicketActivityModule
import project.utils.notify.MessagingService
import project.utils.notify.ServiceModule

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(
        modules = [(MainActivityModule::class),
            (HomeFragmentProvider::class),
            (BestFragmentProvider::class),
            (HelpFragmentProvider::class),
            (SettingFragmentProvider::class),
            (NotificationFragmentProvider::class)]
    )
    abstract fun bindMain(): MainActivity

    @ContributesAndroidInjector(modules = [(LoginActivityModule::class)])
    abstract fun bindLogin(): LoginActivity

    @ContributesAndroidInjector(modules = [(SplashActivityModule::class)])
    abstract fun bindSplash(): SplashActivity


    @ContributesAndroidInjector(modules = [(ServiceModule::class)])
    abstract fun bindService(): MessagingService

    @ContributesAndroidInjector(modules = [(MapActivityModule::class)])
    abstract fun bindMap(): MapActivity

    @ContributesAndroidInjector(modules = [(OsmActivityModule::class)])
    abstract fun bindOsm(): OsmActivity

    @ContributesAndroidInjector(modules = [(IntroActivityModule::class)])
    abstract fun bindIntro(): IntroActivity

    @ContributesAndroidInjector(modules = [(ProfileActivityModule::class)])
    abstract fun bindProfile(): ProfileActivity

    @ContributesAndroidInjector(modules = [(ARActivityModule::class)])
    abstract fun bindAR(): ARActivity

    @ContributesAndroidInjector(modules = [(LuckyActivityModule::class)])
    abstract fun bindLucky(): LuckyActivity

    @ContributesAndroidInjector(modules = [(MissionActivityModule::class)])
    abstract fun bindMission(): MissionActivity

    @ContributesAndroidInjector(modules = [(TicketActivityModule::class)])
    abstract fun bindTicket(): TicketActivity

    @ContributesAndroidInjector(modules = [(MissionListActivityModule::class)])
    abstract fun bindMissionList(): MissionListActivity

    //    @ContributesAndroidInjector(modules = [(&{activityName}ActivityModule::class)])
//    abstract fun bind&{activityName}(): &{activityName}Activity


}