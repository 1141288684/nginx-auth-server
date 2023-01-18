package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(){
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module2)
        .start(wait = true)
}
fun Application.module2(){
    routing {
        get("/"){
            val user  = call.request.headers["Authorization"]!!
            call.respondText(user)
        }
        post("/login"){
            println(call.parameters["username"]+call.parameters["password"])
            call.response.headers.append("Access-Token","123456")
            call.respondText("login!")
        }
    }
}