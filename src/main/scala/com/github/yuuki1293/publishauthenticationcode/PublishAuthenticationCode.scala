package com.github.yuuki1293.publishauthenticationcode

import org.bukkit.event.player.{PlayerJoinEvent, PlayerLoginEvent}
import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.plugin.java.JavaPlugin

import scala.util.Random

class PublishAuthenticationCode extends JavaPlugin with Listener{
  override def onEnable(): Unit = {
    super.onEnable()
    getServer.getPluginManager.registerEvents(this ,this)
  }

  override def onDisable(): Unit = {
    super.onDisable()
  }

  @EventHandler
  def onPlayerJoin(e: PlayerJoinEvent): Unit ={
    e.getPlayer.kickPlayer(new Random().nextInt(99999).toString)
  }
}