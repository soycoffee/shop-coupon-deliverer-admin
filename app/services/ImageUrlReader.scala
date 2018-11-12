package services

import javax.inject.{Inject, Singleton}
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ImageUrlReader @Inject()(
                           wsClient: WSClient,
                         )(implicit ec: ExecutionContext) {

  def apply(url: String): Future[String] =
    wsClient.url(url).get() map { response =>
      s"data:${response.header("Content-Type").get};base64,${response.body}"
    }

}
