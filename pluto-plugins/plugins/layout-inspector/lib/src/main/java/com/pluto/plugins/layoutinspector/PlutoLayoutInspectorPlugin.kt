package com.pluto.plugins.layoutinspector

import androidx.fragment.app.Fragment
import com.pluto.plugin.DeveloperDetails
import com.pluto.plugin.Plugin
import com.pluto.plugin.PluginConfiguration
import com.pluto.plugins.layoutinspector.internal.ActivityLifecycle

class PlutoLayoutInspectorPlugin(identifier: String) : Plugin(identifier) {
    override fun getConfig() = PluginConfiguration(
        name = context.getString(R.string.pluto_li___plugin_name),
        icon = R.drawable.pluto_li___ic_plugin_logo,
        version = BuildConfig.VERSION_NAME
    )

    override fun getView(): Fragment = BaseFragment()

    override fun getDeveloperDetails(): DeveloperDetails {
        return DeveloperDetails(
            website = "https://plutolib.com",
            vcsLink = "https://github.com/plutolib/pluto",
            twitter = "https://twitter.com/pluto_lib"
        )
    }

    override fun onPluginDataCleared() {
    }

    override fun onPluginInstalled() {
        application.registerActivityLifecycleCallbacks(ActivityLifecycle())
    }
}
