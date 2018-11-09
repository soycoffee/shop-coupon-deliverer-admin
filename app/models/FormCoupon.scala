package models

import play.api.libs.json.{Json, OWrites}

case class FormCoupon(
                       title: String,
                       description: String,
                       image: String,
                       qr_code_image: String,
                     )

object FormCoupon {

  implicit val writes: OWrites[FormCoupon] = Json.writes[FormCoupon]

}

