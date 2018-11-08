package services

import javax.inject.{Inject, Singleton}
import models.Coupon
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import play.api.{Configuration, Logger}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ApiClient @Inject()(
                           wsClient: WSClient,
                           configuration: Configuration,
                         )(implicit ec: ExecutionContext) {

  import Coupon.reads

  private val logger = Logger(this.getClass)
  private val apiUrl = configuration.get[String]("shopCouponDeliverer.apiUrl")
  private val apiKey = configuration.get[String]("shopCouponDeliverer.apiKey")

  def queryCoupons(page: Int): Future[(Seq[Coupon], Int)] =
    access {
      wsClient.url(apiUrl)
        .addQueryStringParameters("page" -> page.toString)
    } map { response =>
      (response.json.as[Seq[Coupon]], response.header("last-page").get.toInt)
    }

  private def access(block: => WSRequest): Future[WSResponse] = {
    val request = block
    logger.info(s"request: ${request.method} ${request.uri} ${request.headers}")
    request.addHttpHeaders("x-api-key" -> apiKey).execute() map { response =>
      if (Seq(4, 5) contains response.status / 100) {
        logger.info(s"error_response: ${response.status} ${response.headers}")
      }
      response
    }
  }

}
