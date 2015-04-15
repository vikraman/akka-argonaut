package akka.http.marshallers.argonaut

import scalaz._
import argonaut._, Argonaut._
import akka.http.model.{ ContentTypes, HttpCharsets }
import akka.http.model.MediaTypes.`application/json`
import akka.http.marshalling.{ ToEntityMarshaller, Marshaller }
import akka.http.unmarshalling.{ FromEntityUnmarshaller, Unmarshaller }
import akka.stream.FlowMaterializer

import scala.concurrent.ExecutionContext
import scala.language.implicitConversions

trait ArgonautSupport {

  implicit def argonautUnmarshallerConverter[T](e: DecodeJson[T])(implicit ec: ExecutionContext, mat: FlowMaterializer): FromEntityUnmarshaller[T] =
    argonautUnmarshaller(e, ec, mat)

  implicit def argonautUnmarshaller[T](implicit e: DecodeJson[T], ec: ExecutionContext, mat: FlowMaterializer): FromEntityUnmarshaller[T] =
    argonautJsonUnmarshaller.map(e.decodeJson(_))

  implicit def argonautJsonUnmarshaller(implicit ec: ExecutionContext, mat: FlowMaterializer): FromEntityUnmarshaller[Json] =
    Unmarshaller.byteStringUnmarshaller.forContentTypes(`application/json`).mapWithCharset { (data, charset) ⇒
      val input =
        if (charset == HttpCharsets.`UTF-8`) data.utf8String
        else data.decodeString(charset.nioCharset.name)
      Parse.parse(input)
    }

  implicit def argonautMarshallerConverter[T](e: EncodeJson[T])(implicit ec: ExecutionContext, p: Pretty = prettifier): ToEntityMarshaller[T] =
    argonautMarshaller[T](e, ec, p)

  implicit def argonautMarshaller[T](implicit e: EncodeJson[T], ec: ExecutionContext, p: Pretty = prettifier): ToEntityMarshaller[T] =
    argonautJsonMarshaller[T].compose(e.encode)

  implicit def argonautJsonMarshaller[T](implicit e: EncodeJson[T], ec: ExecutionContext, p: Pretty = prettifier): ToEntityMarshaller[Json] =
    Marshaller.StringMarshaller.wrap(ContentTypes.`application/json`)(p)

  private implicit def collapseDecodeResult[T](d: DecodeResult[T]): T = d.result match {
    case -\/((msg, _)) => throw new IllegalArgumentException(msg)
    case \/-(t)        => t
  }

  private implicit def collapseDisjunction[T](d: String \/ T): T = d match {
    case -\/(msg) => throw new IllegalArgumentException(msg)
    case \/-(t)   => t
  }

  type Pretty = Json => String
  protected val prettifier: Pretty = _.nospaces

}

object ArgonautSupport extends ArgonautSupport
