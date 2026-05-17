# Plane Shot — 飞机移动抢红包游戏

> 2D plane-collector desktop game built with Java 17 / Swing.  
> This is an early Java desktop practice project, kept as a record of GUI and packaging experiments.

<img width="494" height="492" alt="游戏截图" src="https://github.com/user-attachments/assets/0b15012f-722c-4092-a7ee-d4ae44ef4557" />
<img width="1235" height="734" alt="局部截取_20260207_175554" src="https://github.com/user-attachments/assets/336d448c-ab48-4316-8d3f-a88d749043cf" />

## Engineering Notes

- Java 17 desktop GUI application with Swing / AWT
- Login/register flow with local file persistence (`userDatabase.txt`)
- Level selection and game loop separation
- Packaged with jpackage for portable distribution (no JRE required)
- Single-file zip distribution, no registry writes

## How to Run

1. Download `PlaneShot_v1.0.zip` from [Releases](https://github.com/2002yy/Plane-Shot/releases)
2. Extract and double-click `game.jar` (requires Java 17+)

Or build from source:
```bash
javac *.java
jar cvfe game.jar Main *.class
jpackage --type app-image --name "PlaneShot" --input . --main-jar game.jar --main-class Main
```

## Controls

| Key | Action |
|-----|--------|
| Arrow keys ↑↓←→ | Move plane |

Default account: `admin` / `2002`. New accounts can be registered in-game.

## Project Structure

```
├── Main.java                 # Entry point, launches login
├── GameWindow.java           # Game window, rendering, controls
├── LoginWindow.java          # Login / register UI
├── LevelSelectionWindow.java # Level selection
├── UserManager.java          # Account persistence (userDatabase.txt)
└── assets/                   # Game images and icon
```

## License

MIT License. Game assets are not original — ensure you have the right to use them before redistributing.
