package com.example.kotlinbasics


fun main() {
    //Reverse Counting
    var arrg = arrayOf(4,7,6,5,4,3,2,1)

    reverseArray(arrg) //12345
    findArray(arrg) // index@2
    sumArray(arrg) //15
}


fun reverseArray(arrg: Array<Int>) {
    for(i in arrg.size downTo 1){
        print("$i")
    }
    print("\n")
}

fun findArray(arrg: Array<Int>) {
    val findInt = 4
    for(i in arrg.indices){
        if(arrg[i] == findInt){
            print("@$i|")
        }
    }
    print("\n")
}

fun sumArray(arrg: Array<Int>) {
    var sum: Int = 0
    for(i in arrg){
        sum +=i
    }
    println(sum)
}
















