# 🎲 Snakes & Ladders - JavaFX Pixel Edition

> A vibrant, nostalgic pixel-style remake of the classic **Snakes and Ladders** game using JavaFX, featuring smooth animations, a login system, and hidden Easter eggs.

---

## 📦 Features

- 🎮 **Turn-based gameplay** for one or two players
- 🧩 **Pixel-art UI** with colorful grid and retro design
- 🧑‍💻 **Login & Register system** (with `UserManager.java`)
- 🎲 **Dice roll animation** and per-step token jumping (`TokenAnimator`)
- 🐍 **Snakes and ladders** visually span across multiple tiles
- 🎉 **Winning confetti animation** using a custom `ConfettiPane`
- 🌌 **Easter egg**: if a player lands on a snake head and waits 10s, a "42" image fades in; mode screen background then becomes galaxy-themed

---

## 🗂️ Project Structure

| File | Purpose |
|------|---------|
| `Main.java` | Entry point |
| `GameBoardGUI.java` | Main game UI and board logic |
| `Gameengine.java` | Game state, player turn, snake/ladder logic |
| `PlayerToken.java` | Token display and positioning |
| `TokenAnimator.java` | Handles per-cell token movement animation |
| `ConfettiPane.java` | Confetti celebration pane |
| `LoginScene.java`, `RegisterScene.java`, `SignInScene.java` | UI for account management |
| `ModeSelectScene.java` | Game mode selection |
| `UserManager.java` | Handles registration/login records |
| `LogoScene.java` | Logo entrance screen |

---

## 🎨 Image Assets

| Filename | Usage |
|----------|-------|
| `board.png` | Main board with numbers and colors |
| `pieceRed.png` | Red player token |
| `snake3.png` | Purple snake art |
| `easter.png` | "42" hidden Easter egg image |
| `logo.png` | Game logo on startup screen |
| `mode_bg.png` | Background of mode select screen |
| `login_bg.png` | Background for login/register pages |
| `option_background.png` | Option/restart UI window |

> ⚠️ Place all assets under `resources/assets/` to ensure JavaFX loads them via `getClass().getResource("/assets/filename")`.

---

## 🪄 Easter Egg: The Meaning of Life

When a player lands on a snake's head and does not roll the dice for **10 seconds**, the number **“42”** (a tribute to *The Hitchhiker’s Guide to the Galaxy*) will fade in. After returning to the mode select screen, its background changes to a **cosmic galaxy theme**.

---

## 🛠 How to Run

1. Install **Java 11+** and ensure **JavaFX** is properly set up in your IDE.
2. Clone or download this repo.
3. Open the project in **IntelliJ IDEA** or **VS Code**.
4. Run `Main.java`.

---

## 📸 Previews

- ![Board](./assets/board.png)
- ![Easter Egg](./assets/easter.png)
- ![Logo](./assets/logo.png)

---

## ✨ Future Ideas

- Add sound effects and BGM with mute toggle
- Network multiplayer
- Single-player mode with AI
- Snake/ladder editor or random generator

---

## 👨‍💻 Authors

Developed by:

- **Rand (Pengyu LI)**  
- **Yunqi ZHONG**

Proudly created as part of the Snakes and Ladders JavaFX project at SUSTech.

---
