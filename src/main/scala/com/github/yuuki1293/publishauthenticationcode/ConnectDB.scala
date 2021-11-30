package com.github.yuuki1293.publishauthenticationcode

import com.github.yuuki1293.publishauthenticationcode.PublishAuthenticationCode.appConf
import mydb.Tables._
import mydb.Tables.profile.api._
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object ConnectDB {
  def GetTokenList(): List[Int] = {
    val url = "jdbc:mysql://localhost/mydb"
    val db = Database.forURL(url, appConf.getMysqlUser, appConf.getMysqlPassword, driver = "com.mysql.cj.jdbc.Driver")

    // Using generated code. Our Build.sbt makes sure they are generated before compilation.
    val q = Token.map(_.token)

    Await.result(db.run(q.result).map { result =>
      result.toList
    }, 60 seconds)
  }

  def WhiteToken(token: Int, player: Player, plugin: JavaPlugin): Option[Unit] = {
    val url = appConf.getMysqlUrl
    val db = Database.forURL(url, appConf.getMysqlUser, appConf.getMysqlPassword, driver = "com.mysql.cj.jdbc.Driver")

    val q = Token += TokenRow(token, player.getUniqueId.toString)
    Await.result(db.run(q)
      .map(x => Some(plugin.getLogger info x.toString))
      .recover {
        case _ => None
      }, 60 seconds)
  }
}
