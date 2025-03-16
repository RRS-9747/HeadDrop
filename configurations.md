# Configurations

## Configurations

HeadDrop will work fine with the default config, but if you want to customize it, here are the configuration options.

<details>

<summary>Config</summary>

* **Version**:\
  The plugin version. You shouldn't modify this value as it is automatically updated by the plugin.
* **Update-Notify**:\
  If set to "true", it will send an update notification to players with the `headdrop.notif` permission, informing them of new updates.
* **Require-Killer-Player**:\
  If set to "true", the plugin will ensure that mobs are only killed by players before attempting to drop a head. If the mob is killed by something else (like fire), no head will drop.
* **Killer-Require-Permission**:\
  If set to "true", the plugin checks if the player who killed the mob has the `headdrop.killer` permission. This allows you to restrict head drops to certain players.
* **Enable-Looting**:\
  If set to "true", extra drop chances are enabled for mobs killed by players. This allows the plugin to apply additional drop chance based on certain factors, like the player's equipment or abilities.
* **Baby-HeadDrop**:\
  If set to "true", baby mobs will also drop heads when killed. If you don’t want baby mobs to drop heads, set this to "false".
* **Enable-Perm-Chance**:\
  If set to "true", the plugin will check if the killer player has a permission like `headdrop.chance.{NUMBER}` (e.g., `headdrop.chance.20`). This permission will add extra chance to the default drop chance, allowing players with higher permissions to get better chances for head drops.
* **Nerf-Spawner**:\
  If set to "true", mobs spawned from spawners will not drop heads. This is useful for controlling head drops in farms or areas with large spawner setups.
* **Bounties**:\
  Enables the bounties feature, allowing players to place bounties on mobs and players, which could be linked to head drops in a more advanced setup.
* **Disable-Worlds**:\
  Lists the worlds where mobs will not drop heads. Mobs in these worlds will not drop any heads, regardless of the kill conditions.

</details>

<details>

<summary>Database</summary>

* **Online**:\
  Set this to "true" if your server is in online mode. If your server is offline mode (e.g., in a cracked environment), set this to "false".
* **URL**:\
  Defines the database URL. You can choose from three types:
  * SQLite: `jdbc:sqlite:{FILENAME}`
  * MySQL: `jdbc:mysql://{HOST}:{PORT}/{DATABASE}?autoReconnect=true&useSSL=false`    \
    This URL connects the plugin to the database where the plugin will store data like head drops.
* **User**:\
  The database username for MySQL. If you’re using SQLite, this can be left as `null`.
* **Password**:\
  The database password for MySQL. If you’re using SQLite, leave this as `null`.

</details>

<details>

<summary>Web</summary>

* **Enable**:\
  If set to "true", the plugin will host a leaderboard database for the website. This allows you to display a leaderboard of the top players who have collected the most heads.
* **Port**:\
  Specifies the port number on which the website will be hosted. You can choose any available port.
* **Endpoint**:\
  This defines the endpoint context for the website. For example, setting it to "leaderboard" will make the website accessible at `[https://{IP}:{PORT}/leaderboard](https://localhost:8080/leaderboard)`.

</details>

<details>

<summary>Bot</summary>

* **Enable**:\
  If set to "true", the plugin will send a webhook message to Discord every time a head is dropped by a mob or player. This can be useful for creating a notification system.
* **WebHook**:\
  The Discord webhook URL where messages will be sent. You can create a webhook URL from Discord channel settings under Integrations.
* **Title**:\
  The title of the Discord embed message. It supports PlaceholderAPI, so you can use placeholders like `{KILLER}` and `{MOB}` to show the killer’s name and the mob’s name.
* **Description**:\
  The description of the Discord embed message. This supports PlaceholderAPI as well. The default message is "{KILLER} killed {MOB} and got a head!".
* **Footer**:\
  The footer of the Discord embed message. This supports PlaceholderAPI too. The default footer message is "HeadDrop by RRS".

</details>

<details>

<summary>Drop Configurations</summary>

* **Name**:\
  The name given to the mob's head (supports color codes). You can customize the name for different mobs' heads.
* **Drop**:\
  If set to "true", the mob will attempt to drop a head upon being killed. If set to "false", it will not drop a head.
* **Chance**:\
  The chance for the mob to drop a head, ranging from 0.01 to 100.0. This allows you to adjust the drop rate for specific mobs.
* **Lore**:\
  A list of lores that will be applied to the mob's head (lore supports color codes). The default placeholders `{KILLER}` and `{DATE}` will show the killer’s name and the time the mob was killed.

**Note**: Some mobs may not have a name section. For these mobs, you will find an extra option in the `PLAYER` section called `Require-Permission`. If a player has the `headdrop.player` permission, they will drop a head upon death.

</details>
