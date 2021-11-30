package com.github.yuuki1293.publishauthenticationcode

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin
import slick.jdbc.MySQLProfile.api._

object Config{
  def loadConfig(plugin: Plugin): Config ={
    plugin.saveDefaultConfig()
    plugin.reloadConfig()
    new Config(plugin.getConfig)
  }
}

final class Config(config: FileConfiguration) {
  def getMysqlUrl: String = config.getString("mysql.url")

  def getMysqlPort: String = config.getString("mysql.port")

  def getMysqlDB: String = config.getString("mysql.db")

  def getMysqlUser: String = config.getString("mysql.user")

  def getMysqlPassword: String = config.getString("mysql.password")

  def getTokenLength: Int = config.getInt("tokenLength")
}
