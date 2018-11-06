package models

import play.api.libs.json._

case class Coupon(
                   id: String,
                   title: String,
                   description: String,
                   image_url: String,
                   qr_code_image_url: String,
                 )

object Coupon {

  implicit val reads: Reads[Coupon] = Json.reads[Coupon]

}