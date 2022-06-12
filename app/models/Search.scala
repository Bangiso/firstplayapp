package models

import play.api.data.Form
import play.api.data.Forms.{text, mapping}

case class Search(
                   keyword: String
                 )
object Search{
  val searchForm = Form(
    mapping(
      "keyword" -> text,
    )(Search.apply)(Search.unapply)
  )
}
