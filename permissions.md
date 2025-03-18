---
description: Manage plugin access and features through these permissions nodes.
---

# Permissions

### Table of Contents

* All Permissions
* Permission Hierarchy
* Usage Examples

### All Permissions

| Permission Node             | Description                 | Default | Notes                                    |
| --------------------------- | --------------------------- | ------- | ---------------------------------------- |
| `headdrop.reload`           | Reload plugin configuration | op      | Requires server restart for some changes |
| `headdrop.view.leaderboard` | View head drop leaderboard  | true    | Requires database enabled                |
| `headdrop.gui.view`         | Access HeadDrop GUI         | true    | Includes view-only access                |
| `headdrop.head`             | Obtain player heads         | op      | Use `/headdrop head [player]`            |
| `headdrop.killer`           | Trigger mob head drops      | true    | Combined with game mechanics             |
| `headdrop.player`           | Drop own head on death      | true    | Requires player head drops enabled       |
| `headdrop.gui.move`         | Modify GUI items            | op      | Dangerous in live inventories            |
| `headdrop.notify`           | Receive admin notifications | op      | Update alerts and warnings               |
| `headdrop.admin`            | Full admin access           | op      | Includes child permissions               |

### Permission Hierarchy

#### Admin Override (`headdrop.admin`)

This parent permission automatically grants:

* `headdrop.reload`
* `headdrop.head`
* `headdrop.gui.move`
* `headdrop.notify`

```yaml
headdrop.admin:
  children:
    headdrop.reload: true
    headdrop.head: true
    headdrop.gui.move: true
    headdrop.notify: true
```

### Usage Examples

#### Basic Player Setup

```
user-permissions:
  - headdrop.view.leaderboard
  - headdrop.gui.view
  - headdrop.killer
  - headdrop.player
```

#### Staff Permissions

```
moderator-permissions:
  - headdrop.view.leaderboard
  - headdrop.gui.view
  - headdrop.killer
  - headdrop.player
  - headdrop.notify
```

#### Admin Permissions

```
admin-permissions:
  - headdrop.admin
```

### Important Notes

1. Default values:
   * `op`: Only operators have by default
   * `true`: All players have by default
2. Use permission managers like LuckPerms for advanced control
3. Permission hierarchy must be explicitly set in your permissions plugin
4. Some features require corresponding configuration settings to work

> **Warning:** `headdrop.gui.move` should only be granted to trusted staff as it allows inventory modification.

For permission management help, refer to your permissions plugin documentation.
