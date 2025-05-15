# 🎲 Snakes & Ladders - JavaFX Pixel Edition

> A nostalgic pixel-style implementation of the classic **Snakes and Ladders** game using JavaFX. Featuring animated tokens, a login/register system, and hidden Easter eggs!

---

## 📦 Features

- 🎮 Turn-based 1P/2P gameplay with animated per-step token jumps
- 🧑‍💻 Login & Register system with basic user management
- 🎲 Dice roll animations with alternating turn display
- 🐍 Snakes and ladders visually span across tiles with pixel art assets
- 🌠 Easter egg: a “42” appears if dice is not clicked within 10s on a snake head
- 🎉 Victory triggers a custom confetti animation pane

---

## 🗂️ Project Structure

| File | Description |
|------|-------------|
| `Main.java` | Entry point |
| `GameBoardGUI.java` | Core board UI and dice/token display |
| `Gameengine.java` | Player movement and game logic |
| `TokenAnimator.java` | Handles per-cell movement animation |
| `PlayerToken.java` | Player token image + coordinates |
| `LoginScene.java`, `RegisterScene.java`, `SignInScene.java` | Login & Register scenes |
| `ModeSelectScene.java` | Game mode selection UI |
| `ConfettiPane.java` | Confetti visual on win |
| `UserManager.java` | Handles username-password logic |
| `LogoScene.java` | Splash screen with logo |

---

## 🎨 Assets Overview

> All images are located in: `resources/assets/`

| File | Purpose |
|------|--------|
| `board.png` | Main game board |
| `pieceRed.png` | Red player token |
| `snake3.png` | Snake art |
| `logo.png` | Logo screen |
| `easter.png` | Hidden 42 image |
| `mode_bg.png` | Mode select background |
| `login_bg.png` | Login/register background |
| `option_background.png` | Option window frame |

> ⚠️ Assets are loaded using `getClass().getResource("/assets/filename")`, so folder structure **must be preserved**.

---

## 🪄 Easter Egg - “The Answer to Everything”

If a player lands on a snake's head and doesn't click the dice for 10 seconds:
- A **“42”** image fades in
- Upon returning to the mode select screen, background becomes **galaxy-themed**

---

## 🛠 How to Run

### ☕ Requirements

- Java **11 or higher** (tested with Java 17)
- JavaFX SDK **17 or later**

---

### 🚀 IntelliJ IDEA Setup

1. Open the project in IntelliJ.
2. Go to:  
   `File → Project Structure → Libraries → + → Java`  
   and select your JavaFX SDK folder (`lib` directory).
3. In **Run/Debug Configurations** for `Main.java`, add VM options:

```bash
--module-path /path/to/javafx-sdk-17/lib --add-modules javafx.controls,javafx.fxml
Replace /path/to/javafx-sdk-17/ with your actual SDK path.
```

### 💻 VS Code Setup

Install the Java Extension Pack from Microsoft.

Create a .vscode/launch.json with the following:

{
  "type": "java",
  "name": "Launch Game",
  "request": "launch",
  "mainClass": "gui.Main",
  "vmArgs": "--module-path /path/to/javafx-sdk-17/lib --add-modules javafx.controls,javafx.fxml"
}
Replace the --module-path with your JavaFX SDK path.

### 📸 Screenshots

(Add image previews if hosted online or in your repo)

### ✨ Possible Future Features

Add sound effects and background music with mute toggle

Single player mode vs AI

Online multiplayer support

Custom board or level editor

### 👨‍💻 Authors

**Rand (Pengyu LI)**

**Yunqi ZHONG**

**Built at Southern University of Science and Technology (SUSTech) as part of a Java GUI course project.**

