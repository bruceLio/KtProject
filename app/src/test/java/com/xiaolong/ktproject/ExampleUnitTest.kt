package com.xiaolong.ktproject

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var a = 1
// 模板中的简单名称：
        val s1 = "a is $a"
        a = 2
// 模板中的任意表达式：
        val s2 = "${s1.replace("is", "was")}, but now is $a"
        print(s2)
    }
}
