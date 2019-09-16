package com.xiaolong.ktproject

import android.os.Bundle
import android.util.Log
import com.xiaolong.ktproject.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            Log.e("xiaolong", "buttonClick")
        }
    }
}
