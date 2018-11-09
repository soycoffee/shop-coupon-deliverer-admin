package models

import play.api.libs.json.{Json, Reads}

case class CreatedCoupon(
                          id: String,
                          page: Int,
                        )

object CreatedCoupon {

  implicit val reads: Reads[CreatedCoupon] = Json.reads[CreatedCoupon]

}


