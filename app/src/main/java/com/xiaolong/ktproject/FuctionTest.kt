package com.xiaolong.ktproject

/**
 * @author lixiaolong
 * @date 2019-11-21
 */
//函数的3种定义
fun main() {
    val fuA = { a: Int, b: Int -> a + b }
    var fuB: (Int, Int) -> Int = { a: Int, b: Int -> a + b }
    print(add(3, 4))
}

fun add(a: Int, b: Int): Int {
    return a + b
}