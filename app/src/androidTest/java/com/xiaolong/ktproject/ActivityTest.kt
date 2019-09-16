package com.xiaolong.ktproject

import androidx.test.rule.ActivityTestRule
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Test


/**
 * @author lixiaolong
 * @date 2019-09-12
 */
class ActivityTest : ActivityTestRule<MainActivity>(MainActivity::class.java) {
    init {
        launchActivity(activityIntent)
    }

    @Test
    fun testButtonClick() {
        runOnUiThread {
            activity.button.performClick()
        }
    }
}
