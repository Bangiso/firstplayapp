package services

import com.google.inject.ImplementedBy
import daos.StudentsDao
import models.Student

@ImplementedBy(classOf[StudentsDao])
trait StudentsService {

  def fetchStudents(): List[Student]

  def fetchStudentById(id: Long): Option[Student]

  def saveStudent(student: Student): Unit

  def updateStudent(student: Student): Unit

  def deleteStudent(id: Long): Unit

  def healthCheck(): Unit

}
