# Commands

## HeadDrop Commands

Manage plugin functionality and access features through these commands.

### Command Overview

| Command     | Description         | Permission      | Aliases        |
| ----------- | ------------------- | --------------- | -------------- |
| `/headdrop` | Main plugin command | None            | hd             |
| `/head`     | Get player heads    | `headdrop.head` | gethead, skull |

### Main Command: `/headdrop`

```bash
/headdrop [help|reload|leaderboard|debug|gui]
```

#### Subcommands

| Subcommand  | Description                | Permission                  |
| ----------- | -------------------------- | --------------------------- |
| help        | Show command help          | None                        |
| reload      | Reload configuration files | `headdrop.reload`           |
| leaderboard | Display head drop rankings | `headdrop.view.leaderboard` |
| debug       | Debug info for developer   | None                        |
| gui         | Open HeadDrop interface    | `headdrop.gui.view`         |

**Usage Examples:**

```
/headdrop reload  # Reload config
/headdrop gui     # Open GUI interface
/headdrop help    # Show command help
```

### Player Head Command: `/head`

```
/head [player]
```

| Parameter  | Description                   |
| ---------- | ----------------------------- |
| `[player]` | (Optional) Target player name |

**Features:**

* Shows your own head if no player specified
* Works with offline players
* Preserves custom player textures

**Examples:**

bashCopy

```
/head           # Get your own head
/head Notch     # Get Notch's head
```

### Permission Messages

* `headdrop.head` required for `/head` command
* `headdrop.reload` required for config reloads
* `headdrop.admin` required for debug information
* Failed commands show: _"You do not have permission to use this command"_

### Important Notes

1. Leaderboard requires database to be enabled
2. GUI access requires `headdrop.gui.view` permission
3. Player heads use persistent UUID-based textures
4. Reload command may require restart for some changes

> **Tip:** Use TAB completion (1.13+) to see available subcommands!

For permission requirements, see [Permissions Documentation](https://permissions-page-link/).
