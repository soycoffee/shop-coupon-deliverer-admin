package controllers

import javax.inject._
import play.api.mvc._
import services.ApiClient

import scala.concurrent.ExecutionContext

@Singleton
class HomeController @Inject()(
                                cc: ControllerComponents,
                                apiClient: ApiClient,
                              ) extends AbstractController(cc) {

  private implicit val ec: ExecutionContext = defaultExecutionContext

  def index() = Action.async { implicit request: Request[AnyContent] =>
    apiClient.queryCoupons()
      .map(views.html.index(_))
      .map(Ok(_))
  }
}
