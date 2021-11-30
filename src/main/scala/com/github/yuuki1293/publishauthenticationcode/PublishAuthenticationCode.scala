package com.github.yuuki1293.publishauthenticationcode

import com.github.yuuki1293.publishauthenticationcode.Config.loadConfig
import com.github.yuuki1293.publishauthenticationcode.PublishAuthenticationCode.appConf
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.plugin.java.JavaPlugin

import scala.annotation.tailrec
import scala.util.Random

class PublishAuthenticationCode extends JavaPlugin with Listener {
  override def onEnable(): Unit = {
    super.onEnable()
    appConf = loadConfig(this)
    getServer.getPluginManager.registerEvents(this, this)
  }

  override def onDisable(): Unit = {
    super.onDisable()
  }

  @EventHandler
  def onPlayerJoin(e: PlayerJoinEvent): Unit = {
    val tokenList = ConnectDB.GetTokenList()

    if (tokenList.sizeIs >= appConf.getTokenLength) return

    @tailrec
    def getToken: Int = {
      Random.nextInt(appConf.getTokenLength) match {
        case x if tokenList contains x => getToken
        case x => x
      }
    }

    val token = getToken
    ConnectDB.WhiteToken(token, e.getPlayer, this) match {
      case Some(_) => e.getPlayer.kickPlayer(token.toString)
      case None => e.getPlayer.kickPlayer("エラーが発生しました。")
    }
  }
}

object PublishAuthenticationCode {
  var appConf: Config = _
}