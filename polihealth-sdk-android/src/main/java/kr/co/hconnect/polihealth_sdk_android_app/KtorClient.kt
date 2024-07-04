package kr.co.hconnect.polihealth_sdk_android_app

import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpSendPipeline
import io.ktor.client.request.header
import io.ktor.client.statement.HttpReceivePipeline
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.json.JSONException
import org.json.JSONObject


object KtorClient {
    val client = HttpClient(Java) {

        defaultRequest {
            header("accept", "application/json")
            header("content-type", "application/json")
            header("ClientId", "3270e7da-55b1-4dd4-abb9-5c71295b849b")
            header(
                "ClientSecret",
                "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJpbmZyYSI6IkhlYWx0aE9uLVN0YWdpbmciLCJjbGllbnQtaWQiOiIzMjcwZTdkYS01NWIxLTRkZDQtYWJiOS01YzcxMjk1Yjg0OWIifQ.u0rBK-2t3l4RZ113EzudZsKb0Us9PEtiPcFDBv--gYdJf9yZJQOpo41XqzbgSdDa6Z1VDrgZXiOkIZOTeeaEYA"
            )
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        engine {
            pipelining = true
        }
    }

    init {

        client.sendPipeline.intercept(HttpSendPipeline.Before) {
            println()
            println("[Request]")
            println("Method: ${this.context.method.value}")
            println("URL: ${this.context.url}")
            println("Header: ${this.context.headers.entries()}")
            println("Body: ${context.body}")
            proceedWith(this.subject)
        }
        
        client.receivePipeline.intercept(HttpReceivePipeline.State) { response ->
            println()
            println("[Response]")
            println("Status: ${response.status}")
            println("Method: ${response.call.request.method.value}")
            println("URL: ${response.call.request.url}")
            println("Header ${response.headers.entries()}")
            println("Body: ${prettyPrintJson(response.bodyAsText())}")
        }
    }

    fun prettyPrintJson(jsonString: String): String {
        var indentLevel = 0
        val indentSpace = 4
        val prettyJson = StringBuilder()
        var inQuotes = false

        for (char in jsonString) {
            when (char) {
                '{', '[' -> {
                    prettyJson.append(char)
                    if (!inQuotes) {
                        prettyJson.append('\n')
                        indentLevel++
                        prettyJson.append(" ".repeat(indentLevel * indentSpace))
                    }
                }

                '}', ']' -> {
                    if (!inQuotes) {
                        prettyJson.append('\n')
                        indentLevel--
                        prettyJson.append(" ".repeat(indentLevel * indentSpace))
                    }
                    prettyJson.append(char)
                }

                ',' -> {
                    prettyJson.append(char)
                    if (!inQuotes) {
                        prettyJson.append('\n')
                        prettyJson.append(" ".repeat(indentLevel * indentSpace))
                    }
                }

                '"' -> {
                    prettyJson.append(char)
                    if (char == '"') {
                        inQuotes = !inQuotes
                    }
                }

                else -> {
                    prettyJson.append(char)
                }
            }
        }
        return prettyJson.toString()
    }


}