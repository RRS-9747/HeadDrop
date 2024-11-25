# Configurations

HeadDrop will work fine on the default config. but if you want to customize the default config, here is the articles for you



<details>

<summary>Config</summary>

* **Version** -> You will see the plugin version here. you shouldn't touch it

- **Update-Notify** -> If the value is "true", it will send an update notification to players who have "headdrop.notif&#x79;**"** permission

* **Require-Killer-Player**: If set to "true", the plugin will verify that a mob was killed by a player before dropping its head.

- **Require-Charged-Creeper** -> If the value is "true", the plugin will check if the mob is killed by a charged creeper or not before dropping head

* **Killer-Require-Permission** -> If the value is "true", the plugin will check if the mob killer is a player and that killer player has "headdrop.killer" permission

- **Enable-Looting** -> If the value is "true", plugin will add extra drop chance on mob killed by a player

* **Baby-HeadDrop** -> If the value is "true", baby mob will drop heads too

- **Enable-Perm-Chance** ->  If the value is "true", it will check if killer player have "headdrop.chance.{NUMBER}" permission and add that number of chance with default drop chance. make sure the number is an integer number and its from 1-100

* **Disable-Worlds** -> Mobs wouldn't drop head on listed worlds

</details>

<details>

<summary>Database</summary>

* **Online** -> If your server/network is in online mode, set this to "true" otherwise set this to "false"

- **URL** -> 3 types of Database is supported\
  SQLite -> jdbc:sqlite:{FILENAME}\
  MySQL -> jdbc:mysql://{HOST}:{PORT}/{DATABASE}?autoReconnect=true\&useSSL=false\
  PostgreSQL -> jdbc:postgresql://{HOST}:{PORT}/{DATABASE}

* **User** -> Username of the database. if you are using SQLite, you can set this to null

- **Password** -> Password of the database. if you are using SQLite, you can set this to null\


</details>

<details>

<summary>Web</summary>

* **Enable** -> If the value is "true", the plugin will host a leaderboard database

- **Port** -> Set a port to host the website

* **Endpoint** -> endpoint is the context of the website. if you set this to "leaderboard", you will find the website on [https://{IP}:{PORT}/leaderboard](https://localhost:8080/leaderboard)

</details>

<details>

<summary>Bot</summary>

* **Enable** -> If the value is "true", the plugin will send webhook message when a head is dropped by a mob or players

- **WebHook** -> WebHook URL. you can create a discord webhook URL from channel settings to Integrations

* **Title** -> Title of the discord embed (PlaceholderAPI supported) (Default placeholder {KILLER} & {MOB} which return killer name & mob name)

- **Description** -> Description of the discord embed (PlaceholderAPI supported) (Default placeholder {KILLER} & {MOB} which return killer name & mob name)

* **Footer** -> Footer of the discord embed (PlaceholderAPI supported) (Default placeholder {KILLER} & {MOB} which return killer name & mob name)

</details>

<details>

<summary>Drop Configurations</summary>

* **Name** -> Set name of the mob's head (Colorcode supported)

- **Drop** -> If the value is "true", that mod will attempt to drop head

* **Chance** -> Drop chance for the mob's head (0.01 to 100.0)

- **Lore** -> A list of lores that will be on the head (Lores are not permanent & lores are color code supported) (Default placeholders are {KILLER} & {DATE} which will return killer name & killed time)\


Note: You may find some mobs without a name section. those mobs head name isn't supported & you will find a extra option in PLAYER which is "Require-Permission" That means the player has "headdrop.player" permission, he/she will drop head on death

</details>

