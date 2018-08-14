package co.ronn.api

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}


final class ScalaHTTPApiTest extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  private val routesWithDefin1edResponses: Route =
    get {
      path("status"){
        complete(HttpEntity(ContentTypes.`application/json`, """{"status": "ok"}"""))
      }
    }

  private val greetingRoute: Route = {
    get{
      path("saludo"){
        complete("Hola, putos!")
      }
    }
  }

  "ScalaHTTPApiTest" should{

    "respond successfuly while requesting its  status" in {

      Get("/status") ~> routesWithDefin1edResponses ~> check{
        status shouldBe StatusCodes.OK
        contentType shouldBe ContentTypes.`application/json`
        entityAs[String] shouldBe """{"status": "ok"}"""
      }

      Get("/saludo") ~> greetingRoute ~> check{
        status shouldBe StatusCodes.OK
        entityAs[String] shouldBe "Hola, putos!"
        contentType shouldBe ContentTypes.`text/plain(UTF-8)`
      }
    }
  }
}