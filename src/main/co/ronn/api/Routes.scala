package co.ronn.api

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route

object Routes {

  val all: Route = get {
    path("status"){
      complete(HttpEntity(ContentTypes.`application/json`, """{"status": "ok"}"""))
    }
  }
}