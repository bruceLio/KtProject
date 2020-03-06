package com.xiaolong.ktproject

/**
 * @author lixiaolong
 * @date 2019-11-21
 */
fun main() {
    val range = 1..10
    val range2 = 1 until 10
    for (b in range) {
        print("${b},")
    }
    println()
    for (b in range2) {
        print("${b},")
    }
    println()
    for ((i, e) in range.withIndex()) {
        print("${i}-${e}   ")
    }
    println()
    println(whenTest("c"))
    forEachTest()

}

fun whenTest(body: String): Int {
    return when (body) {
        "a" -> 1
        "b" -> 2
        "c" -> 3
        else -> -1
    }
}
fun  forEachTest(){
    val list = listOf("aaaa","bbbb","ccccc","dddddddddd")
    list.filter { !it.startsWith("a") }
        .sortedBy {it.length }
        .asReversed()
        .map { it.toUpperCase() }
        .forEach { println(it) }
}
