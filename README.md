# Shadow Blocks
===============

Repository for my SWEN20003 project.

`Block`, `Tile`, `Effect` and `Unit` all describe the functionality of their respective
types but are not used as instances in the world. For that the implementation classes of `BlockInstance`,
`TileInstance`, `EffectInstance` and `UnitInstance` are used respectively.

In the `World`, a geometrical instance of a `Block` is defined and distinguished by its `Position` and
its `IBlockState` (which includes the `Block` type definition). Similar techniques are done for `Tile`, `Unit` and `Effect`.
This just abstracts away implementation and rendering details from the description classes.

The four types of _game objects_ (`Block`, `Tile`, `Unit`, `Effect`) could have been tied together in an inheritance tree. Example:
```java
class Block extends Tile { ... }
class Tile extends GameObject { ... }
class Unit extends GameObject { ... }
class Effect extends GameObject { ... }
```

It was a conscious choice to not implement a hierarchy like this (despite it making some tasks in `World` and `ObjectRegistry`)
significantly easier and less repetitive, I wanted these 4 types to be completely distinguishable from one another and have their owm
development history (i.e. Can completely change how Tile works without having to worry about a `GameObject` contract change effecting
`Block`s).  