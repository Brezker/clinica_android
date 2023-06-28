package com.brezker.myapplication

class EnvUrl {
    companion object{
        val UrlVal : String = "192.168.0.14"
    }
}
fun main(){
    println(EnvUrl.UrlVal)
}