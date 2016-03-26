package akka.http.scaladsl.marshallers.argonaut

import argonaut._, Argonaut._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, HttpRequest }
import akka.http.scaladsl.model.HttpCharsets.`UTF-8`
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.unmarshalling.FromEntityUnmarshaller

import org.scalatest.{ FunSpec, BeforeAndAfterAll, ShouldMatchers }
import org.scalatest.concurrent.ScalaFutures

case class Person(name: String, age: Int)

object Person {

  implicit def PersonCodecJson: CodecJson[Person] =
    casecodec2(Person.apply, Person.unapply)("name", "age")

  val person = Person("Foo Bar", 42)
  val goodJson = person.asJson.nospaces
  val badJson = """{ "name" := "Foo Bar", "age" := 42 }"""

}

class ArgonautSupportSpec
    extends FunSpec with BeforeAndAfterAll with ArgonautSupport
    with ShouldMatchers with ScalaFutures {

  val system = ActorSystem("akka-argonaut")
  implicit val ec = system.dispatcher
  implicit val mat = ActorMaterializer()(system)

  describe("when marshalling a http request") {

    val marshallable: ToResponseMarshallable = Person.person

    whenReady(marshallable(HttpRequest())) { request =>

      val entity = request.entity

      it("should have the correct media-type") {
        entity.contentType.mediaType shouldBe `application/json`
      }

      it("should have correct body") {
        whenReady(request.entity.dataBytes
          .map(_.utf8String).runFold("")(_ + _)) {
          _ shouldBe Person.goodJson
        }
      }
    }
  }

  describe("when unmarshalling a string") {

    val unmarshaller = implicitly[FromEntityUnmarshaller[Person]]

    it("should successfully unmarshal a valid entity") {
      val goodEntity = HttpEntity(ContentTypes.`application/json`, Person.goodJson)
      whenReady(unmarshaller(goodEntity)) {
        _ shouldBe Person.person
      }
    }

    it("should fail to unmarshal an invalid entity") {
      val badEntity = HttpEntity(ContentTypes.`application/json`, Person.badJson)
      whenReady(unmarshaller(badEntity).failed) {
        _ shouldBe a[IllegalArgumentException]
      }
    }
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
    system.terminate()
  }
}
