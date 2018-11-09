package services

import javax.inject.{Inject, Singleton}
import models.{Coupon, FormCoupon, CreatedCoupon}
import play.api.libs.json.Json
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import play.api.{Configuration, Logger}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ApiClient @Inject()(
                           configuration: Configuration,
                           wsClient: WSClient,
                         )(implicit ec: ExecutionContext) {

  import Coupon.reads
  import FormCoupon.writes
  import CreatedCoupon.reads

  private val logger = Logger(this.getClass)
  private val apiUrl = configuration.get[String]("shopCouponDeliverer.apiUrl")
  private val apiKey = configuration.get[String]("shopCouponDeliverer.apiKey")

  def createCoupon(formCoupon: FormCoupon): Future[CreatedCoupon] =
    access {
      wsClient.url(apiUrl)
        .withMethod("POST")
        .withBody(Json.toJson(formCoupon))
    } map { response =>
      response.json.as[CreatedCoupon]
    }

  def queryCoupons(page: Int): Future[(Seq[Coupon], Int)] =
    access {
      wsClient.url(apiUrl)
        .withMethod("GET")
        .addQueryStringParameters("page" -> page.toString)
    } map { response =>
      (response.json.as[Seq[Coupon]], response.header("last-page").get.toInt)
    }

  private def access(block: => WSRequest): Future[WSResponse] = {
    val request = block
    logger.info(s"request: ${request.method} ${request.uri} ${request.headers}")
    request.addHttpHeaders("x-api-key" -> apiKey).execute() map { response =>
      response.status / 100 match {
        case 4 | 5 => logger.info(s"error_response: ${response.status} ${response.headers} ${response.body}")
        case _ =>
      }
      response
    }
  }

}
