package daos

import anorm._
import com.google.inject.Inject
import play.api.db.{Database, NamedDatabase}
import models.Student
import services.StudentsService

import java.sql.Connection

class StudentsDao @Inject()(
                             @NamedDatabase("students") db: Database
                           )  extends StudentsService {


  def fetchStudents(): List[Student] = db
    .withConnection { implicit con =>
            StudentsDao.fetchStudents()
    }

  def fetchStudentById(id: Long): Option[Student] = db
    .withConnection { implicit con =>
      StudentsDao.fetchStudentById(id)
    }

  def saveStudent(student: Student): Unit = db
    .withConnection { implicit con =>
      StudentsDao.saveStudent(student)
    }

  def updateStudent(student: Student): Unit = db
    .withConnection { implicit con =>
      StudentsDao.updateStudent(student)
    }

  def deleteStudent(id: Long): Unit = db
  .withConnection { implicit con =>
    StudentsDao.deleteStudent(id)
  }

  def healthCheck() = db
    .withConnection { implicit con =>
      StudentsDao.healthCheck()
    }
}

object StudentsDao {
  def fetchStudents()(implicit con: Connection): List[Student] = {
    SQL"""
         SELECT id, name, gpa
         FROM  StudentDB.students
       """.as(Macro.namedParser[Student].*)
  }

  def fetchStudentById(id: Long)(implicit con: Connection): Option[Student] = {
    SQL"""
         SELECT id, name, gpa
         FROM  StudentDB.students
         WHERE id = $id
       """.as(Macro.namedParser[Student].singleOpt)
  }

  def saveStudent(student: Student)(implicit con: Connection): Unit = {
    SQL"""
         INSERT INTO StudentDB.students(id, name, gpa) values (${student.id}, ${student.name}, ${student.gpa})
       """.execute()
  }

  def updateStudent(student: Student)(implicit con: Connection): Unit = {
    SQL"""
         UPDATE StudentDB.students SET name = ${student.name}, gpa = ${student.gpa} WHERE id = ${student.id}
       """.execute()
  }

  def deleteStudent(id: Long)(implicit con: Connection): Unit = {
    SQL"""
         DELETE FROM StudentDB.students where id = $id
       """.execute()
  }

  def healthCheck()(implicit con: Connection): Unit ={
    SQL"""
       SELECT 1
       """.execute()
  }
}


