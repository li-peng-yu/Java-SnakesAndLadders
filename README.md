# 🎲 Snakes & Ladders - JavaFX Pixel Edition

> A nostalgic pixel-style implementation of the classic **Snakes and Ladders** game using JavaFX. Featuring animated tokens, a login/register system, and hidden Easter eggs!

---

## 📦 Features

- 🎨 **JavaFX GUI** with a pixel-art theme and custom animations.
- 👥 **User system** with registration, login, and guest mode support.
- 💾 **Game state auto-save and resume**, with separate save files for each user.
- 🧠 **Single-player and Two-player mode**.
- 🐍🪜 **Dynamic snakes and ladders** rendered across multiple tiles.
- 🎉 **Victory animation** using confetti effects.
- 🛸 **Easter Egg**: If a player lands on a snake head and is idle for 15 seconds, the number `42` appears, and the background changes to a galaxy theme.
- 🔄 Restart and mode selection from the main interface.
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

## 💻 How to Run

### 🧠 Option 1: IntelliJ IDEA

1. Open project in IntelliJ IDEA.

2. Go to:
File > Project Structure > Libraries > + > Java
and add:
- `javafx-sdk-XX/lib`
- `gson-2.10.1.jar`

3. Configure VM options:--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
4. Right-click `Main.java` and select **Run**.

✅ Done! IntelliJ will handle the rest.

---

### 🧠 Option 2: VS Code

1. Install extensions:
- **Java Extension Pack**
- **JavaFX Support for VS Code**

2. Download JavaFX and Gson as above.

3. In `.vscode/launch.json`, set up:

```json
{
"type": "java",
"name": "Launch Main",
"request": "launch",
"mainClass": "Main",
"vmArgs": "--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml",
"classpath": [
 "src",
 "gson-2.10.1.jar"
]
}
```
4.Compile and run from Run & Debug panel.

Tip: You may also use tasks to compile JavaFX+Gson manually if not using the extension pack.


## 🌌 Easter Egg
If a player lands on a snake and does not move for 15 seconds, the number 42 (a reference to The Hitchhiker’s Guide to the Galaxy) appears. Afterward, the background changes to a galaxy-themed scene.

## 🧠 Save System
Users stored in users.json

Each player's game state saved to gamestates.json

Automatically loaded on login

Guest mode does not trigger save

Restarting the game clears current save


## 👤 Contributors
Rand – UI design, logic, architecture

Add more contributors if needed

## 📄 License
This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Authors

**Rand (Pengyu LI)**

**Yunqi ZHONG**

**Built at Southern University of Science and Technology (SUSTech) as part of a Java GUI course project.**

