package co.ronn.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object ScalaHttpApi {

  def main(args: Array[String]): Unit = {
    val appConfig = ConfigFactory.load("application")
    val serverConfig = ConfigFactory.load("http-server")

    val actorSystemName  = appConfig.getString("main-actor-system.name")
    val host = serverConfig.getString("http-server.host")
    val port = serverConfig.getInt("http-server.port")

    implicit  val system: ActorSystem = ActorSystem(actorSystemName)
    implicit  val materializer: ActorMaterializer = ActorMaterializer()
    implicit  val executionContext: ExecutionContextExecutor = system.dispatcher

    val bindingFuture = Http().bindAndHandle(Routes.all, host, port)

    println(s"Server online by Ronnie  at http: //$host:$port/\nPress Return to Stop madafaka")
    StdIn.readLine

    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}