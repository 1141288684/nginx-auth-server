package com.example.plugins

import com.github.benmanes.caffeine.cache.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.Duration
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

fun buildLoaderAsync(k:String) :CompletableFuture<String>{
    return CompletableFuture.supplyAsync { "$k+buildLoaderAsync" }
}

fun Application.configureRouting() {
    //记录token对应的用户信息————json格式
    val tokenUsers: Cache<String, String> = Caffeine.newBuilder()
        .maximumSize(10000)
        .expireAfterWrite(Duration.ofMinutes(15))
        .refreshAfterWrite(Duration.ofMinutes(1))
        .softValues()
        .build(object : CacheLoader<String, String> {
            override fun load(key: String): String? {
                return null
            }

            override fun reload(key: String, oldValue: String?): String? {
                return oldValue
            }
        })
    // Starting point for a Ktor app:
    routing {
        get("/auth"){
            call.request.headers.entries().forEach {
                println(it.key)
                println(it.value)
            }
            if (call.request.headers["X-Original-URI"]!!.contains("/login")||call.request.headers["Token"]=="123456"){

                var user=tokenUsers["123456"]
                if (user==null){
                    user="654321"
                    tokenUsers["123456"]=user
                }
                call.response.headers.append("Access-Token",user)
                call.respondText("auth passed")
            }
            call.respond(HttpStatusCode.Forbidden)
        }
    }
}

operator fun Cache<String,String>.get(key: String):String? {
    return this.getIfPresent(key)
}
operator fun Cache<String,String>.set(key: String,value:String) {
    this.put(key, value)
}

