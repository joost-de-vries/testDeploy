package models

import java.sql.Date
import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

import scala.concurrent.Future

case class User(id: Option[Long] = None, name: String, firstname: String,    email: String,
                password: String, birthday: Option[Date] = None,
                signDate: Option[Date] = Some(new Date(System.currentTimeMillis)))

class Users(tag: Tag) extends Table[User](tag, "user") {


  type S = String
  type D = Date
  type L = Long
  // attribute name in table database
  def id = column[L]("ID", O.AutoInc, O.PrimaryKey)

  def name = column[S]("NAME")

  def password = column[S]("PASSWORD")

  def firstname = column[S]("FIRSTNAME")

  def email = column[S]("EMAIL")

  def birthday = column[D]("BIRTHDAY")

  def signDate = column[D]("SIGN_DATE")

  override def * = (id.?, name, firstname, email, password, birthday.?, signDate.?) <> ((User.apply _)
    .tupled, User.unapply _)
}

  class UserDAO @Inject()(dbConfigProvider: DatabaseConfigProvider)  {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  // TODO why this strange import, couldn't his be  regular import ?
  import dbConfig.driver.api._

  private val users = TableQuery[Users]

  def all: Future[Seq[User]] = dbConfig.db.run(users.result)
}