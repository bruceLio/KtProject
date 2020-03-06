package com.xiaolong.ktproject

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = getInstrumentation().targetContext
        val name = MainActivity::class.java.name
        val newActivity = getInstrumentation().newActivity(appContext.classLoader, name, Intent())
        getInstrumentation().callActivityOnCreate(newActivity, null)
        assertEquals("com.xiaolong.ktproject", appContext.packageName)
    }
}
