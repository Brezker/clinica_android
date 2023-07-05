package com.brezker.myapplication

class EnvUrl {
    companion object{
        val UrlVal : String = "192.168.43.228"
    }
}
fun main(){
    println(EnvUrl.UrlVal)
}