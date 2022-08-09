package com.example.kotlinbasics

import java.math.BigInteger
import java.util.*
import kotlin.collections.ArrayList


fun main() {
    //Reverse Counting
//    var arrg = arrayOf(4,7,6,5,4,3,2,1)
//    reverseArray(arrg) //12345
//    findArray(arrg) // index@2
//    sumArray(arrg) //15

//    //var array = arrayOf(1, 6, 22, 25, 5, -1, 8, 10)
//    // var array = arrayOf(6, 1, 2, 25, 5, -1, 8, 10)
//    var array = arrayOf(15, 1, 22, 25, 6, -1, 8, 10)
//    var sequence = arrayOf(5, 1, 22, 25, 6, -1, 8, 10, 10)
//    // 1,0,5,7
//    //println(array.contains(9))
//    println(yes(array, sequence))

    //print(isPalindrome(121))
//    print(isValid("([{}])"))
//
//    "([)]"

//    var gg = intArrayOf(9, 9)
//    println(plusOne(gg).toList())

    var cal: Calendar = Calendar.getInstance()
    cal.time = Date()

    val formattedDate =
        "${cal.get(Calendar.MONTH)}/${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.YEAR)}"
    println(cal.get(Calendar.MONTH))
    println(cal.get(Calendar.MONTH) +1)


    println("This me test".capitalize())

}

fun plusOne(digits: IntArray): IntArray {
    for (i in digits.lastIndex downTo 0) {
        digits[i] += 1
        if (digits[i] <= 9) return digits
        digits[i] = 0
    }
    val arr = IntArray(digits.size + 1)
    arr[0] = 1

    return arr
}

fun lengthOfLastWord(s: String): Int {
    var count = 0;
    for (i in s.lastIndex downTo 0) {
        if (s[i].isLetter()) {
            count++
        } else {
            if (count > 0) {
                break
            }
        }
    }
    return count
}

fun searchInsert(nums: IntArray, target: Int): Int {
    if (nums.isEmpty()) return 0
    return if (nums.contains(target)) {
        nums.indexOf(target)
    } else {
        var newArray = ArrayList<Int>()

        for (i in nums) {
            newArray.add(i)
        }
        newArray.add(target)
        newArray.sort()
        newArray.indexOf(target)
    }
}

fun strStr(haystack: String, needle: String): Int {
    if (!haystack.contains(needle)) return -1
    if (needle.isEmpty()) return 0

    var found = false

    var firstString = needle[0]
    for (i in haystack.indices) {
        if ((haystack.length - needle.length) < i) break

        if (found) {
            found = false
            continue
        }

        if (haystack[i] == firstString) {
            val fullStr = haystack.substring(i, i + needle.length)
            if (fullStr == needle) {
                return i
            }
        }
    }
    return -1
}

fun removeElement(nums: IntArray, val2: Int): Int {
    var sum = 0
    for (i in nums) {
        if (i == val2) {
            sum++
        }
    }
    return nums.size - sum
}

fun isValid(s: String): Boolean {
    if (s.length % 2 != 0) return false

    var array = ArrayList<Int>();

    for (i in s.indices) {
        var str = s[i].toString()
        for (y in i..s.lastIndex) {
            if (y == s.lastIndex) continue

            if (str == "{") {
                if (s[y + 1].toString() == "}") {
                    array.add(y + 1)
                    break
                }
            }
            if (str == "[") {
                if (s[y + 1].toString() == "]") {
                    array.add(y + 1)
                    break
                }
            }
            if (str == "(") {
                if (s[y + 1].toString() == ")") {
                    array.add(y + 1)
                    break
                }
            }
        }
    }

    return (s.length / 2) == array.size
}

//fun isValid(s: String): Boolean {
//    var counter = 0
//
//    while (counter < s.length) {
//        var x = s[counter].toString()
//        if (counter == s.length - 1) return false
//        when (x) {
//            "(" -> {
//                if (s[counter + 1].toString() != ")") return false
//            }
//            "[" -> {
//                if (s[counter + 1].toString() != "]") return false
//            }
//            "{" -> {
//                if (s[counter + 1].toString() != "}") return false
//            }
//        }
//        counter += 2
//    }
//    return true
//}

fun romanToInt(s: String): Int {
    var sum = 0
    var foundComb = false

    for (i in 0..s.length - 1) {
        var strComb = ""

        if (foundComb) { // skip next iteration
            foundComb = false
            continue
        }

        strComb = if (i == s.length - 1) {
            s[i].toString()
        } else {
            "${s[i]}${s[i + 1]}"
        }

        if (strComb == "IV" || strComb == "IX" || strComb == "XL" || strComb == "XC" || strComb == "CD" || strComb == "CM") {
            sum += getStrValue(strComb)
            foundComb = true
        } else {
            sum += getStrValue(s[i].toString())
            foundComb = false
        }
    }
    return sum
}

fun getStrValue(s: String): Int {
    return when (s) {
        "I" -> 1
        "IV" -> 4
        "V" -> 5
        "IX" -> 9
        "X" -> 10
        "XL" -> 40
        "L" -> 50
        "XC" -> 90
        "C" -> 100
        "CD" -> 400
        "D" -> 500
        "CM" -> 900
        "M" -> 1000
        else -> {
            0
        }
    }
}

fun isPalindrome(x: Int): Boolean {
    var str: String = x.toString()
    var int: String = str.reversed()

    if (str == int) {
        return true
    }
    return false
}

fun twoSum(nums: IntArray, target: Int): List<Int> {
//    val numbers = intArrayOf(3,2,4)
//    val target = 6
    var ret = arrayListOf(0, 1)
    var noAnswer = ArrayList<Int>()

    for (i in nums.indices) {
        for (u in nums.indices) {
            if (u == i) break
            if ((nums[i] + nums[u]) == target) {
                ret.set(0, u)
                ret.set(1, i)
                return ret
            }
        }
    }
    return noAnswer
}


fun sortArray() {
    var array = mutableListOf(-100, 1, 2, 3, 4, 5, 6)
    var newAA = ArrayList<Int>()

    var firstItem = 0
    var lastItem = array.lastIndex

    for (i in array.indices) {
        var firstElem = Math.abs(array[firstItem])
        var lastElem = Math.abs(array[lastItem])

        if (lastElem > firstElem) {
            newAA.add(0, lastElem * lastElem)
            lastItem -= 1
        } else {
            newAA.add(0, firstElem * firstElem)
            firstItem += 1
        }
    }
    print(newAA)
}


fun yes(array: Array<Int>, sequence: Array<Int>): Boolean {
    var found = 0;
    var count = 0;

    for (i in sequence) {
        for (u in found..array.lastIndex) {
            if (i == array[u]) {
                found = u + 1
                count++
                break
            }
        }
    }

    if (count == sequence.size) {
        return true
    }
    return false
}


//fun reverseArray(arrg: Array<Int>) {
//    for(i in arrg.size downTo 1){
//        print("$i")
//    }
//    print("\n")
//}
//
//fun findArray(arrg: Array<Int>) {
//    val findInt = 4
//    for(i in arrg.indices){
//        if(arrg[i] == findInt){
//            print("@$i|")
//        }
//    }
//    print("\n")
//}
//
//fun sumArray(arrg: Array<Int>) {
//    var argg = arrayOf(3, 5, -4, 8, 11, 1, -1, 6)
//    var targetSum = 10
//    var finalVal = mutableListOf<Int>()
//
//    for (i in argg.indices) {
//        println(argg.contains(5))
//        for (y in argg.indices) {
//
//            if (i == y) break
//            var total = argg[i] + (argg[y])
//
//            if (total == targetSum) {
//                finalVal.add(argg[i])
//                finalVal.add(argg[y])
//            }
//
//        }
//}

















