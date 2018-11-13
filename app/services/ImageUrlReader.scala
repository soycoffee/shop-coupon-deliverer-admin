package services

import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ImageUrlReader @Inject()(
                           wsClient: WSClient,
                         )(implicit ec: ExecutionContext) {

  private val logger = Logger(this.getClass)

  def apply(url: String): Future[String] = {
    logger.info(s"request: $url")
    wsClient.url(url).addHttpHeaders("Accept" -> "image/*").get() map { response =>
      val contentType = response.header("Content-Type").get
      val encodedBody = java.util.Base64.getEncoder.encodeToString(response.body[Array[Byte]])
      s"data:$contentType;base64,$encodedBody"
    }
  }

}
