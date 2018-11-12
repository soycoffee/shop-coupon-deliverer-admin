package controllers

import javax.inject._
import models.FormCoupon
import play.api.data._
import play.api.i18n.{Lang, Messages, MessagesApi}
import play.api.mvc._
import services.ApiClient

import scala.Function.tupled
import scala.concurrent.ExecutionContext
@Singleton
class CouponController @Inject()(
                                cc: ControllerComponents,
                                messagesApi: MessagesApi,
                                apiClient: ApiClient,
                              ) extends AbstractController(cc) {

  private implicit val ec: ExecutionContext = defaultExecutionContext
  private implicit val messages: Messages = messagesApi.preferred(Seq(Lang("en")))

  val form = Form(
    Forms.mapping(
      "title" -> Forms.nonEmptyText,
      "description" -> Forms.text,
      "image" -> Forms.nonEmptyText,
      "qr_code_image" -> Forms.nonEmptyText,
    )(FormCoupon.apply)(FormCoupon.unapply)
  )

  def index(pageKey: Option[String]): Action[AnyContent] = Action.async {
    apiClient.queryCoupons(pageKey) map tupled({ (coupons, nextPageKey) =>
      Ok(views.html.index(coupons, nextPageKey))
    })
  }

  def getCreate: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.getCreate(form))
  }

  def create: Action[FormCoupon] = Action.async(parse.form(form)) { request =>
    apiClient.createCoupon(request.body) map { _ =>
      Redirect(routes.CouponController.index(None))
    }
  }

}
