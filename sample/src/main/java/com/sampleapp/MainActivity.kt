package com.sampleapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.pluto.Pluto
import com.pluto.plugins.datastore.pref.PlutoDataStoreWatcher
import com.sampleapp.databinding.ActivityMainBinding
import com.sampleapp.list.PluginListAdapter
import com.sampleapp.list.PluginListItem
import com.sampleapp.plugins.SupportedPlugins
import com.sampleapp.utils.DiffAdapter
import com.sampleapp.utils.DiffAwareHolder
import com.sampleapp.utils.ListAdapter
import com.sampleapp.utils.ListItem
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val pluginAdapter: ListAdapter by lazy { PluginListAdapter(onActionListener) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val javaTest = JavaTest()
        setContentView(binding.root)

        binding.crashList.apply {
            adapter = pluginAdapter
        }
        pluginAdapter.list = SupportedPlugins.get()
        binding.version.text = "ver ${BuildConfig.VERSION_NAME}"

//        binding.showNotch.setOnClickListener {
//            if (IS_TESTING_JAVA) {
//                javaTest.showNotch(true)
//            } else {
//                Pluto.showNotch(true)
//            }
//        }
//
//        binding.hideNotch.setOnClickListener {
//            if (IS_TESTING_JAVA) {
//                javaTest.showNotch(false)
//            } else {
//                Pluto.showNotch(false)
//            }
//        }
//
        binding.open.setOnClickListener {
            if (IS_TESTING_JAVA) {
                javaTest.open()
            } else {
                Pluto.open()
            }
        }
//
//        binding.openDemoPlugin.setOnClickListener {
//            if (IS_TESTING_JAVA) {
//                javaTest.open(DEMO_PLUGIN_ID)
//            } else {
//                Pluto.open(DEMO_PLUGIN_ID)
//            }
//        }
        initDataForDataStoreSample()
    }

    private val onActionListener = object : DiffAdapter.OnActionListener {
        override fun onAction(action: String, data: ListItem, holder: DiffAwareHolder?) {
            if (data is PluginListItem) {
                SupportedPlugins.openPlugin(this@MainActivity, data)
            }
        }
    }

    private fun initDataForDataStoreSample() {
        PlutoDataStoreWatcher.watch("prefrence name", dataStore)
        PlutoDataStoreWatcher.watch("user_info", dataStore2)
        lifecycleScope.launch {
            dataStore2.edit {
                it[booleanPreferencesKey("isLoggedIn")] = true
                it[stringPreferencesKey("auth_token")] = "asljknva38uv972gv"
                it[stringPreferencesKey("refresh_token")] = "iuch21d2c1acbkufh2918hcb1837bc1a"
            }
            dataStore.edit {
                it[booleanPreferencesKey("random_boolean")] = false
                it[stringPreferencesKey("random_string")] = "random string value"
                it[longPreferencesKey("random_long")] = RANDOM_LONG
                it[floatPreferencesKey("random_float")] = PI_VALUE
            }
        }
    }

    companion object {
        const val RANDOM_LONG = 13_101_993L
        const val PI_VALUE = 3.141592653589793238462643383279502884197f
        const val IS_TESTING_JAVA = true
    }
}

private val Context.dataStore by preferencesDataStore(
    name = "prefrence name"
)
private val Context.dataStore2 by preferencesDataStore(
    name = "user_info"
)
