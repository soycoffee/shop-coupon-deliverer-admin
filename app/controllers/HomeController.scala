package controllers

import javax.inject._
import play.api.mvc._
import services.ApiClient

import scala.concurrent.ExecutionContext
import Function.tupled

@Singleton
class HomeController @Inject()(
                                cc: ControllerComponents,
                                apiClient: ApiClient,
                              ) extends AbstractController(cc) {

  private implicit val ec: ExecutionContext = defaultExecutionContext

  def index(page: Int): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    apiClient.queryCoupons(page)
      .map(tupled(views.html.index(_, page, _)))
      .map(Ok(_))
  }
}
