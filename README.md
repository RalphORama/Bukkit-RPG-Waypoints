# RPG Waypoints

Hi. This plugin is under development. It's not ready yet.


## Design Doc

I'm writing this design doc before I start working on the plugin so I don't get into feature creep.  I will add more
features once the features specified here are finished.

RPG Waypoints' intended functionality is similar to fast traveling in a similar manner to The Elder Scrolls or Fallout
games.

Across the map, waypoints are created by players or admins.  They consist of a golden pressure plate and a sign with a
`[header]`.  As players explore your map, they unlock the ability to teleport to these locations by standing on the
pressure plate.


### Sign Functionality

Signs would be formatted as follows:

```
    [header]
 [name of warp]
[optional price]
```

Instead of using a command, we should hook the `BlockPlaceEvent` and parse the sign. If the player doesn't have 
`waypoints.create`, it breaks the sign, drops the item, and gives the player an error message.

Also hook `BlockBreakEvent`, parse signs, and only allow the player to break the sign if they own it or have the
`rpg.break.others` permission.

`[header]` should have a config option to set exactly what `header` is (i.e. `header: Waypoint`).

`[optional price]` should be taken as a number, e.g. 3000 or 20.15. We might have to check if the economy supports
fractional prices, fail or round (**to be decided**), add the money symbol (e.g. `$`) before or after the number.

Economy functionality will be implemented last. 

I'd like to design this project ORM-first, as I believe the SQL structure would be good for this.  Consider:

| Table Name | Structure                                  |
|------------|--------------------------------------------|
| players    | id, uuid, found_waypoints                  |
| waypoints  | id, x, y, z, owner(players), cost          |

This isn't a finalized representation of what the data will look like.

**Stretch Goals:**

  - Add price prefix (color, formatting) option in config.
  - A fourth line showing how many players have found the waypoint or the owner of the waypoint.
  - Commands use tellraw and are interactive (fill/run commands)


### Commands

- `/wp`
  - Prints usage
  - `waypoints.wp`
  - Default: `true`
- `/wp list [page]`
  - Shows all available waypoints
  - Optional argument: Page of paginated results
  - `waypoints.list`
  - Default: `true`
- `/wp tp <name>`
  - Teleports the user to the waypoint after an optional cooldown.
  - `waypoints.tp`
  - Default: `true`
- `/wp tp [name] [username]`
  - Teleports the specified user to the waypoint.
  - `waypoints.tp.others`
  - Default: `OP`
- `/wp owner`
  - Gets the owner of the sign the invoker is looking at.
  - `waypoints.owner`
  - Default: `OP`
- `/wp reload`
  - Reloads the plugin config.
  - `waypoints.reload`
  - Default: `OP`


### Standalone Permissions

- `waypoints.create`
  - Allows placing of `[waypoint]` signs
  - Default: `OP`
- `waypoints.create.unlimited`
  - Allows user to create unlimited waypoints
  - Default: `OP`
- `waypoints.remove`
  - Allows user to remove their own waypoints
  - Default: `true`
- `waypoints.remove.others`
  - Allows the user to remove other players' waypoints
  - Default: `OP`
- `waypoints.tp.nocooldown`
  - Skips the cooldown when teleporting
  - Default: `OP`
- `waypoints.admin`
  - `waypoints.create`
  - `waypoints.tp.others`
  - `waypoints.owner`
  - `waypoints.reload`
  - `waypoints.remove.others`
  - `waypoints.tp.nocooldown`
  - Default: `OP`

### Config Options

See src/main/resources/config.yml