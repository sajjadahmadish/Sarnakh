package project.ui.main

import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commitNow
import com.crashlytics.android.Crashlytics
import com.google.android.material.tabs.TabLayout
import com.google.firebase.iid.FirebaseInstanceId
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.visibility
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SwitchDrawerItem
import io.github.inflationx.calligraphy3.CalligraphyUtils
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.R
import ir.sinapp.sarnakh.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import org.greenrobot.eventbus.Subscribe
import project.data.DataManager
import project.ui.ar.ARActivity
import project.ui.base.BaseActivity
import project.ui.main.best.BestFragment
import project.ui.main.help.HelpFragment
import project.ui.main.home.HomeFragment
import project.ui.main.notification.NotificationFragment
import project.ui.main.setting.SettingFragment
import project.ui.main.wheel.WheelFragment
import project.ui.map.MapActivity
import project.ui.missionList.MissionListActivity
import project.ui.profile.ProfileActivity
import project.ui.ticket.TicketActivity
import project.utils.*
import project.utils.navDrawer.MyDrawerItem
import javax.inject.Inject


class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewModel>(ActivityMainBinding::class.java),
    MainNavigator {


    override val bindingVariable: Int get() = BR.viewModel

    @Inject
    override lateinit var viewModel: MainViewModel


    @Inject
    lateinit var drawerBuilder: DrawerBuilder


    @Inject
    lateinit var mPagerAdapter: MainPagerAdapter


    private lateinit var drawer: Drawer
    private lateinit var notificationItem: MyDrawerItem
    private lateinit var headerResult: AccountHeader
    private lateinit var itemProfile: ProfileDrawerItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigator = this

        showFragment(getString(R.string.home), 1)

        runBlocking {
            setUpText()
            setUpDrawer()
        }



        initNotify()


    }


    private fun initNotify() {

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModel.registerNotify(task.result!!.token)
            }
        }


    }

    private fun setUpText() {

        binding.textSwitcher.setFactory {
            val t = TextView(this@MainActivity)
            t.gravity = Gravity.CENTER
            t.setTextColor(resources.getColor(R.color.colorPrimary))
            t.textSize = 21f
            t.typeface = CommonUtils.typefaceFromAsset("fonts/IRANYekanMobileBold.ttf", this)
            t
        }

        val `in` = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)

        binding.textSwitcher.inAnimation = `in`
        binding.textSwitcher.outAnimation = out

        binding.textSwitcher.setCurrentText(getString(R.string.app_name))
    }


    private fun setUpDrawer() {

        itemProfile = ProfileDrawerItem()
            .withIdentifier(1)
            .withName("profile")
            .withIcon(R.drawable.sajjad)


        headerResult = AccountHeaderBuilder()
            .withActivity(this)
            .withTextColorRes(R.color.white)
            .withHeightDp(270)
            .withAccountHeader(R.layout.include_drawer_header_profile_image)
            .withSelectionListEnabledForSingleProfile(false)
            .withCloseDrawerOnProfileListClick(true)
            .withOnAccountHeaderSelectionViewClickListener { view, profile ->
                openProfileActivity()
                true
            }
            .addProfiles(itemProfile)
            .build()


        viewModel.updateHeader { firstName, lastName, username, gender ->
            Crashlytics.setUserIdentifier(username)
            Crashlytics.setUserName("$firstName $lastName")

            Crashlytics.setUserIdentifier(username)
            itemProfile.withName("$firstName $lastName")
            itemProfile.withEmail(getString(R.string.ht))
            itemProfile.withIcon(if (gender) R.drawable.sajjad else R.drawable.ic_user_woman)
            headerResult.updateProfile(itemProfile)

        }.doOnError { }.subscribe()


        val font = CommonUtils.typefaceFromAsset("fonts/IRANYekanMobileBold.ttf", this)


        val itemHome = MyDrawerItem()
            .withSelected(true)
            .withIconTintingEnabled(true)
            .withName(R.string.home)
            .withIcon(R.drawable.ic_classmatelogo)
            .withTypeface(font)


        val itemBest = MyDrawerItem()
            .withSelected(false)
            .withIconTintingEnabled(true)
            .withName(R.string.best)
            .withIcon(R.drawable.ic_calendar)
            .withTypeface(font)

        val itemWheel = MyDrawerItem()
            .withSelected(false)
            .withIconTintingEnabled(true)
            .withName(R.string.lucky)
            .withIcon(R.drawable.ic_user)
            .withTypeface(font)

//
        notificationItem = MyDrawerItem()
            .withName(R.string.notifications)
            .withSelected(false)
            .withIconTintingEnabled(true)
            .withIcon(R.drawable.ic_notification)
            .withTypeface(font)


        val itemSetting = MyDrawerItem()
            .withSelected(false)
            .withName(R.string.settings)
            .withIconTintingEnabled(true)
            .withIcon(R.drawable.ic_settings)
            .withTypeface(font)

        val itemDayNight = SwitchDrawerItem()
            .withSelected(false)
            .withName(R.string.night_mode)
            .withIconTintingEnabled(true)
            .withIcon(R.drawable.ic_moon)
            .withTypeface(font)
            .withChecked(isNightMode())
            .withSelectable(false)
            .withOnCheckedChangeListener { _, _, isChecked ->
                drawer.closeDrawer()
                if (!isChecked)
                    viewModel.setTheme(
                        AppCompatDelegate.MODE_NIGHT_NO,
                        DataManager.Theme.THEME_LIGHT
                    )
                else viewModel.setTheme(
                    AppCompatDelegate.MODE_NIGHT_YES,
                    DataManager.Theme.THEME_DARK
                )
            }


        val itemHelp = MyDrawerItem()
            .withSelected(false)
            .withIconTintingEnabled(true)
            .withName(R.string.help)
            .withIcon(R.drawable.ic_help)
            .withTypeface(font)

        val itemInvite = MyDrawerItem()
            .withSelected(false)
            .withIconTintingEnabled(true)
            .withName(R.string.invite)
            .withIcon(R.drawable.ic_share)
            .withTypeface(font)

        drawer = drawerBuilder
            .withOnDrawerListener(object : Drawer.OnDrawerListener {
                override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
                    binding.content.translationX =
                        slideOffset * drawerView!!.width * if (isRtl()) -1 else 1
                }

                override fun onDrawerClosed(drawerView: View?) {
                }

                override fun onDrawerOpened(drawerView: View?) {
                }

            })
            .withAccountHeader(headerResult)
            .addDrawerItems(
                itemHome,
                itemBest,
                itemWheel,
                DividerDrawerItem().withSelectable(false),
                notificationItem,
                itemSetting,
                itemHelp
            )
            .addStickyDrawerItems(
                itemDayNight
            )
            .withOnDrawerItemClickListener { _, position, drawerItem ->
                runBlocking {
                    if (viewModel.tab.get() != position && drawerItem != itemDayNight) {
                        drawerItem as MyDrawerItem
                        showFragment(drawerItem.name.getText(this@MainActivity), position)
                    }
                }
                if (drawerItem != itemDayNight)
                    return@withOnDrawerItemClickListener false
                true
            }
            .withToolbar(binding.toolbar)
            .withActionBarDrawerToggleAnimated(true)
            .build()


        val f = Tools.getMode(this)
        if (f != Configuration.UI_MODE_NIGHT_YES)
            Tools.setSystemBarLight(this)
//        Tools.setSystemBarColor(this, R.color.colorPrimaryBackground)
        Tools.changeNavigateionIconColor(
            binding.toolbar,
            resources.getColor(R.color.colorAccent)
        )

    }


    private fun isNightMode(): Boolean {
        return when (viewModel.savedTheme) {
            DataManager.Theme.THEME_LIGHT -> false
            DataManager.Theme.THEME_DARK -> true
            DataManager.Theme.THEME_UNDEFINED -> {
                when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_NO -> false
                    Configuration.UI_MODE_NIGHT_YES -> true
                    else -> false
                }
            }
            else -> false
        }
    }


    @Subscribe
    fun event(event: EventLang) {
        setLanguage(event.l, this)
    }

    @Suppress("SameParameterValue")
    private fun changeTabsFont(tabLayout: TabLayout, fontName: String) {
        val vg = tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildCount = vgTab.childCount
            for (i in 0 until tabChildCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    CalligraphyUtils.applyFontToTextView(tabLayout.context, tabViewChild, fontName)
                }
            }
        }
    }


    @Subscribe
    fun event(event: EventAr) {
        openARActivity()
    }

    override fun openARActivity() {
        launchActivity<ARActivity> {}
        Bungee.fade(this)
    }


    @Subscribe
    fun event(event: EventWheel) {
        showDialogProductBlue()
    }

    @Subscribe
    fun event(event: EventTicket) {
        openTicketActivity()
    }

    @Subscribe
    fun event(event: EventMissionList) {
        openMissionListActivity()
    }

    override fun openTicketActivity() {
        launchActivity<TicketActivity> {}
        Bungee.fade(this)
    }

    override fun openProfileActivity() {
        launchActivity<ProfileActivity> {}
        Bungee.fade(this)
    }

    override fun openMissionListActivity() {
        launchActivity<MissionListActivity> {}
        Bungee.fade(this)
    }


    @Subscribe
    fun event(event: EventMap) {
        openMapActivity()
    }

    override fun openMapActivity() {
        launchActivity<MapActivity> {}
        Bungee.fade(this)
    }

    override fun onBackPressed() {
        when {
            drawer.isDrawerOpen -> drawer.closeDrawer()
            viewModel.tab.get() != 1 -> drawer.setSelectionAtPosition(1)
            else -> super.onBackPressed()
        }
    }


    private fun showDialogProductBlue() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_gift)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.show()
    }

    private fun showFragment(name: String, position: Int) {
        viewModel.tab.set(position)

        try {
            binding.textSwitcher?.setText(name)
        } catch (e: Exception) {
        }

        val fragment = when (name) {
            getString(R.string.home) -> HomeFragment.newInstance()
            getString(R.string.best) -> BestFragment.newInstance()
            getString(R.string.lucky) -> WheelFragment.newInstance()
            getString(R.string.notifications) -> NotificationFragment.newInstance()
            getString(R.string.settings) -> SettingFragment.newInstance()
            getString(R.string.help) -> HelpFragment.newInstance()
            else -> null
        } ?: return
        supportFragmentManager.commitNow {
            replace(R.id.fragment, fragment)
        }
    }


}


