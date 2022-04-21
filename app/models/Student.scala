package models

import anorm._
import play.api.libs.json._

case class Student(
                    id: Long,
                    name: String,
                    gpa: Double
                  )

object Student{
  implicit val formatJson = Json.format[Student]
}
