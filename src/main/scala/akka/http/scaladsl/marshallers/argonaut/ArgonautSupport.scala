package akka.http.scaladsl.marshallers.argonaut

import argonaut._, Argonaut._
import akka.http.scaladsl.model.{ HttpCharsets, MediaTypes }
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.marshalling.{ ToEntityMarshaller, Marshaller }
import akka.http.scaladsl.unmarshalling.{ FromEntityUnmarshaller, Unmarshaller }

import scala.language.implicitConversions

trait ArgonautSupport {

  implicit def argonautUnmarshaller[T](implicit e: DecodeJson[T]): FromEntityUnmarshaller[T] =
    argonautJsonUnmarshaller.map(e.decodeJson(_))

  implicit val argonautJsonUnmarshaller: FromEntityUnmarshaller[Json] =
    Unmarshaller.byteStringUnmarshaller.forContentTypes(`application/json`).mapWithCharset { (data, charset) ⇒
      val input =
        if (charset == HttpCharsets.`UTF-8`) data.utf8String
        else data.decodeString(charset.nioCharset.name)
      Parse.parse(input)
    }

  implicit def argonautMarshaller[T](implicit e: EncodeJson[T], p: Pretty = prettifier): ToEntityMarshaller[T] =
    argonautJsonMarshaller.compose(e.encode)

  implicit def argonautJsonMarshaller(implicit p: Pretty = prettifier): ToEntityMarshaller[Json] =
    Marshaller.StringMarshaller.wrap(MediaTypes.`application/json`)(p)

  private implicit def collapseDecodeResult[T](d: DecodeResult[T]): T = d.result match {
    case Left((msg, _)) => throw new IllegalArgumentException(msg)
    case Right(t)       => t
  }

  private implicit def collapseEither[T](d: Either[String, T]): T = d match {
    case Left(msg) => throw new IllegalArgumentException(msg)
    case Right(t)  => t
  }

  type Pretty = Json => String
  protected val prettifier: Pretty = _.nospaces

}

object ArgonautSupport extends ArgonautSupport
