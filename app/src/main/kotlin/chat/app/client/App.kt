/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package chat.app.client

import chat.app.client.env.Env
import chat.app.client.json.create.CreateRequest
import chat.app.client.json.create.CreateResponse
import chat.app.client.json.read.ReadResponse
import chat.app.client.json.update.UpdateRequest
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

val url = Env.URL.value

fun main() = runBlocking{

    val client = HttpClient(CIO){
        install(JsonFeature){
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {}
            )
        }
    }

    print("User name: ")
    val userName = readLine()!!

    println("Start")

    while (true){
        when(readLine()){
            "create" -> create(client, userName)
            "read" -> read(client)
            "update" -> update(client, userName)
            "delete" -> delete(client)
            "exit" -> break
            else -> continue
        }
        print("Next command: ")
    }
    println("Finish!")
}

suspend fun create(client: HttpClient, userName: String){

    print("text: ")

    val text = readLine()!!

    val createResponse = client.post<CreateResponse>(url){
        headers {
            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        body = CreateRequest(userName, text)
    }

    println(createResponse.toString())
}

suspend fun read(client: HttpClient){
    val readRespond = client.get<List<ReadResponse>>(url)

    readRespond.forEach {
        println(it.toString())
    }
}

suspend fun update(client: HttpClient, userName: String){

    print("id: ")
    val id = readLine()!!

    print("text: ")

    val text = readLine()!!

    val httpResponse = client.put<HttpResponse>(url){
        headers {
            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        body = UpdateRequest(id, userName, text)
    }

    if(httpResponse.status.value in 200..299){
        println("Success")
    }
    else{
        println("Error")
    }
}

suspend fun delete(client: HttpClient){
    print("Delete id: ")
    val id = readLine()!!

    val httpResponse = client.delete<HttpResponse>("$url/$id")
    if(httpResponse.status.value in 200..299){
        println("Success")
    }
    else{
        println("Error")
    }
}
