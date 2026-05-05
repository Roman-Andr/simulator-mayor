# Mayor Simulator — Minecraft City Builder

A feature-rich Minecraft minigame built for the **Cristalix** network. Players manage their own city: unlock districts, place buildings block by block, hire workers, and compete on leaderboards — all on top of a custom client mod and a standalone microservice backend.

---

## Architecture

The project is a multi-module Gradle build targeting the Cristalix platform (a Russian Minecraft network running a fork of Paper called DarkPaper).

```bash
simulator-mayor/
├── common/       — shared utilities, channels, and data types
├── protocol/     — socket packet definitions (node ↔ user service)
├── node/         — main Bukkit plugin (game logic, UI, events)
├── mod/
│   └── uimod/    — custom client-side Minecraft mod
├── service/
│   └── user/     — standalone microservice: MongoDB persistence, leaderboard
└── bundler/      — custom Gradle plugin (ProGuard obfuscation, mod packaging)
```

The **node** and **user service** communicate over a binary socket protocol defined in the `protocol` module. Player data is stored in MongoDB and queried asynchronously; the service also exposes a leaderboard endpoint.

---

## Features

**City management**

- Players own and unlock multiple districts, each containing a grid of buildable cells
- Structures degrade over time and must be repaired to keep generating income
- Passive income accumulates per game tick, scaled by owned structures and active boosters

**Block-by-block construction**

- Buildings are assembled one block at a time, guided by a custom visual overlay
- Two project types: *client* (manual placement) and *worker* (automated)
- Fake blocks rendered only for the owning player via the visual driver

**Worker system**

- Workers have rarity tiers, individual speed and reliability stats, and can level up
- Workers are assignable to active projects and consume blocks from a per-structure storage

**Economy**

- In-game currency earned through completed projects, income ticks, and freelance jobs
- Showcase marketplace with dynamically refreshing prices
- Bank accounts and credit system

**Donate / ability system**

- Cosmetic tags, income boosters, booster packs, and gameplay abilities (e.g. no structure degradation)

**Quality-of-life**

- Custom scoreboard and tab list
- Animated UI driven by the Anime/Stronghold visual framework
- Leaderboard refreshed every two minutes
- Daily rewards
- Telegram bot integration for server-side error logging

---

## Tech Stack

| Layer | Technology |
| --- | --- |
| Language | Kotlin 1.8 |
| Game server | DarkPaper (Cristalix fork of Paper 1.12.2) |
| Async | Kotlin Coroutines |
| Persistence | MongoDB (via `MongoAdapter`) |
| Transport | Cristalix socket protocol |
| Client mod | Custom Minecraft mod (Anime/Func SDK) |
| Build | Gradle (Kotlin DSL), Shadow JAR, custom bundler plugin |
| Obfuscation | ProGuard (via bundler Gradle plugin) |
| Logging | kotlin-telegram-bot |

---

## Module overview

### `node`

The core Bukkit plugin. Registers all game systems on `onEnable` and drives the main game loop via Bukkit schedulers and coroutines. Key packages:

- `city` — `City`, `CityHall`, district cells, structure state machine
- `structure` — abstract `BuildingStructure` with `ClientStructure` / `WorkerStructure` subtypes
- `worker` — worker entity, rarity, level-up logic
- `player` — `User` context, data loading/saving, income calculation
- `ui` — scoreboard, banner samples, item managers, formatter
- `action` — command handlers and inventory menu actions
- `dontate` — ability and booster definitions

### `service/user`

A lightweight JVM microservice bootstrapped with `MicroserviceBootstrap`. Handles three packet types over the socket:

- `GetUserPackage` — fetch serialised player JSON from MongoDB
- `SaveUserPackage` — upsert player data with key stats (money, experience, reputation)
- `GetLeaderboardPackage` — return top-N players by a given field

### `bundler`

A custom Gradle plugin that generates `mod.properties`, runs ProGuard over the built JAR, and packages the client mod for upload.

### `protocol`

Shared data classes for all inter-service packets, with no runtime dependencies beyond the JDK.

---

## Building

Prerequisites: JDK 11+, Gradle 8, access to the private Cristalix Maven repository.

```bash
# Copy and fill in credentials
cp gradle.properties.template gradle.properties

# Build everything
./gradlew :node:jar
./gradlew :service:user:jar
```

Pre-configured IntelliJ run configurations are in the `run/` directory (build, upload, remote debug).

---

## License

[MIT](LICENSE)
