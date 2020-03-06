package com.xiaolong.ktproject

import android.os.Bundle
import android.util.Log
import com.xiaolong.ktproject.common.BaseActivity
import com.xiaolong.ktproject.common.logE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    var s = "a"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            Log.e("xiaolong", "buttonClick")
        }
        "xiaolong".logE()
        this.s = null.toString()
        test(s)
    }

    fun test(s: String) {
        s.logE()
    }

}
