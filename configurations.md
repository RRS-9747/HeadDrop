---
description: >-
  HeadDrop will work fine with the default config, but if you want to customize
  it, here are the configuration options.
---

# Configurations

### Table of Contents

* Main Configuration
* Weapon Requirements
* Database Settings
* Web Leaderboard
* Discord Bot
* Special Effects
* Item Lore
* Mob Settings

### Main Configuration

```yaml
Config:
  Version: ${project.version}
  Require-Killer-Player: true
  Enable-Looting: false
  Baby-HeadDrop: false 
  Enable-Perm-Chance: false
  Nerf-Spawner: false
  Bounties: false
  Disable-Worlds:
    - demoWorld
    - demoWorld2
```

| Option                | Description                                                |
| --------------------- | ---------------------------------------------------------- |
| Version               | **DO NOT MODIFY** - Auto-generated config version          |
| Require-Killer-Player | Requires player kill credit for head drops                 |
| Enable-Looting        | Enables Looting enchantment bonus                          |
| Baby-HeadDrop         | Allows baby mobs to drop heads                             |
| Enable-Perm-Chance    | Enable permission-based chance boost (`headdrop.chance.X`) |
| Nerf-Spawner          | Disables head drops from spawner-spawned mobs              |
| Bounties              | (Premium only) Enable bounty system                        |
| Disable-Worlds        | Worlds where head drops are disabled                       |

### Weapon Requirements

```
Require-Weapon:
  Enable: false
  Weapons:
    - Diamond_Axe
```

* When enabled, only specified weapons can trigger head drops
* Use Minecraft material names
* Requires exact weapon type match

### Database Settings

```
Database:
  Enable: false
  Online: true
  URL: "jdbc:sqlite:HeadDrop.sqlite"
  User:
  Password: 
  Cleanup: 30
```

| Option  | Description                               |
| ------- | ----------------------------------------- |
| Enable  | Activate database tracking                |
| Online  | Set false for offline mode servers        |
| URL     | Database connection string                |
| Cleanup | Days until inactive player data is purged |

### Web Leaderboard

```
Web:
  Enable: false
  Port: 8080
  Endpoint: "leaderboard"
```

* Access leaderboard at: `http://[server-ip]:[port]/[endpoint]`
* Requires database to be enabled

### Discord Bot

```
Bot:
  Enable: false
  Channel-ID: ""
  Title: "HeadDrop"
  Description: "{KILLER} killed {MOB} and got a head!"
  Footer: "HeadDrop by RRS"
```

* Requires [DiscordUtils](https://www.spigotmc.org/resources/107771/) plugin
* Placeholders: `{KILLER}`, `{MOB}`
* Supports standard Discord markdown formatting

### Special Effects (Premium)

```
Effects:
  Enabled: true
  Particle: FLAME
  Count: 10
  OffsetX: 0.5
  OffsetY: 0.5
  OffsetZ: 0.5
  Speed: 0.1
  Sound: ENTITY_PLAYER_LEVELUP
  Volume: 1.0
  Pitch: 1.0
```

| Option       | Description             |
| ------------ | ----------------------- |
| Particle     | Minecraft particle type |
| Sound        | Minecraft sound name    |
| Offset       | Particle spread area    |
| Volume/Pitch | Sound effect parameters |

**Premium Feature**: Some effects require premium version

### Item Lore

```
Lores:
  - "Killed by {KILLER}"
  - "Killed on {DATE}"
```

* Customize head item lore
* Leave blank to disable
* Available placeholders:
  * `{KILLER}` - Killer's name
  * `{MOB}` - Mob type
  * `{DATE}` - Drop timestamp

### Mob Settings

```
# Example configuration
PLAYER:
  Drop: true
  Chance: 100.00
  Point: 1
```

**All mob types support these options:**

* `Drop`: Enable/disable head drops
* `Chance`: Drop percentage (0.00-100.00)
* `Sound`: Note block sound (use "default" for mob-specific)
* `Point`: Leaderboard points per drop

> **Note:** Some features require premium version. All percentage values use decimal format (e.g., 25.50 = 25.5% chance).
