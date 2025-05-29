# 🐱 BOOP – A JavaFX Implementation of the Cat-Pushing Strategy Game

**BOOP** is a two-player abstract strategy game brought to life with JavaFX. Place kittens on a cozy quilted bed and watch as they *boop* adjacent pieces around! Graduate kittens to cats and aim to line up three of your feline friends to win. Simple to learn, yet delightfully chaotic to master.

---

## 🧩 Game Overview
- 2 Player
- Turn-based
- Strategy game

---

## 🎯 How to Win

- **Primary Win Condition**: Line up **3 Cats** in a row (horizontal, vertical, or diagonal).
- **Alternative Win Condition**: Have **all 8 Cats** on the board at the end of your turn.

---

## 🐾 Game Components

Each player starts with:
- 🐱 **8 Kittens** (active pieces)
- 🐈 **8 Cats** (in reserve, earned through graduation)

---

## 📋 Basic Rules

### Setup
- Each player starts with **8 Kittens**.
- **8 Cats** begin in reserve, out of play. These will only appear in the pool when kittens are graduated to cats
- Players alternate placing one piece per turn.

### Placing Pieces
- **Place a Kitten** on any open square.
- **Boop happens**: All adjacent pieces (including diagonals) are pushed one space away.
- **Chain reactions do NOT occur** — booped pieces don’t cause additional boops.

### Booping Rules
- Pieces can be booped **off the board** and return to the owner's pool.
- Two pieces in a line **cannot be booped** by a third piece in that same line.
- **Diagonal booping is allowed**.
- Sound effects encouraged: "boop" and "meow" are welcome. 🐾

---

## 🐈 Graduating Kittens to Cats

- Line up **3 Kittens** → remove them and gain **3 Cats** in your pool.
- You always have **8 active pieces total** on the board or in-hand.

### Special Cases
- If more than 3 in a row: choose a group of 3 to graduate.
- If you have 8 pieces on the board: you may graduate a **single Kitten**.
- Mixed lines (Cats + Kittens) still trigger graduation **for Kittens**.

---

## 🐾 Playing with Cats

- On your turn, place a **Cat OR a Kitten**.
- **Cats behave like Kittens** with one big exception:
  - **Cats cannot be booped by Kittens**
  - **Cats can boop other Cats and Kittens**

---

## 🧠 Strategy Tips

### Positioning
- **Center squares** are powerful: less likely to be booped off.
- Losing a piece isn’t the end — it returns to your pool.

### Tactics
- Defend lines of two to prevent the opponent’s win.
- Use L-shaped setups to boop into a 3-in-a-row.
- Place Cats strategically: they’re immune to Kitten boops.

---

## 🛠️ Tech Stack

- Java 23
- JavaFX 23.0.1
- Maven
- ControlsFX 11.2.1

---

## 🚀 How to Run the App

### ✅ Prerequisites
- Java 21 or newer
- Maven installed

### 🧪 Run Instructions

```bash
git clone https://github.com/garethmunnings/BOOP.git
cd BOOP
mvn clean javafx:run
