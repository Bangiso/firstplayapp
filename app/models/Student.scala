package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats.doubleFormat
import play.api.libs.json._

case class Student(
                    id: Option[Long],
                    name: String,
                    gpa: Double
                  )

object Student{
  implicit val formatJson = Json.format[Student]

  val studentForm = Form(
    mapping(
      "id"  -> optional(longNumber),
      "name" -> text,
      "gpa" ->  of(doubleFormat)
    )(Student.apply)(Student.unapply)
  )
}
