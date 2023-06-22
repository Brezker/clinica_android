package com.brezker.myapplication

class EnvUrl {
    companion object{
        val UrlVal : String = "192.168.100.21"
    }
}
fun main(){
    println(EnvUrl.UrlVal)
}