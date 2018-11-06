package services

import javax.inject.Inject
import play.api.{Configuration, Logger}
import play.api.libs.json.JsValue
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}

import scala.concurrent.Future

class ApiClient @Inject()(wsClient: WSClient, configuration: Configuration) {

  private val logger = Logger(this.getClass)
  private val apiUrl = configuration.get[String]("shopCouponDeliverer.apiUrl")
  private val apiKey = configuration.get[String]("shopCouponDeliverer.apiKey")

  def queryCoupons(): Future[JsValue] =
    access(wsClient.url(apiUrl)).map(_.json)

  private def access(block: => WSRequest): Future[WSResponse] = {
    val request = block
    logger.info(s"access: ${request.method} ${request.uri} ${request.headers}")
    request.addHttpHeaders("x-api-key" -> apiKey).execute()
  }

}
