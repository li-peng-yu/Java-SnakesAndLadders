package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gameengine {
    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex;
    private boolean gameOver;
    private Player winner;
    private Random random = new Random();
    private boolean singlePlayerMode;
    public Player getPlayerByName(String name) {
        for (Player p : players) {
            if (p.name.equals(name)) {
                return p;
            }
        }
        System.err.println("Player not found by name: " + name);
        return null;
    }

    // 玩家内部类
    public class Player {
        public String name;       // 玩家名称
        public int position;      // 当前位置
        public boolean isGuest;   // 是否为游客

        Player(String name, boolean isGuest) {
            this.name = name;
            this.position = 1;  // 初始位置0表示未开始
            this.isGuest = isGuest;
        }


    }

    // 特殊格子定义
    public static final int[][] LADDERS_CELLS = {
            {43,83}, {72,91}, {27,67}, {3,40},
    };
    public static final int[][] SNAKES_CELLS = {
            {61,81}, {23,45}, {8,31}, {68,96},
    };

    public Gameengine(List<String> playerNames) {
        for (String name : playerNames) {
            players.add(new Player(name, name.equals("Guest")));
        }
        currentPlayerIndex = 0;
        singlePlayerMode = (playerNames.size() == 1);
    }

    public int rollDice() {
        if (gameOver) return -1;

        int steps = random.nextInt(6) + 1;
        movePlayer(steps);
        if (!singlePlayerMode) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
        return steps;
    }

    private void movePlayer(int steps) {
        Player p = players.get(currentPlayerIndex);
        int newPos = p.position + steps;

        if (newPos > 100) {
            newPos = 100 - (newPos - 100);
        }

        for (int[] cell : LADDERS_CELLS) {
            if (cell[0] == newPos) {
                newPos = cell[1];
                break;
            }
        }
         for (int[] cell : SNAKES_CELLS) {
         if (cell[1] == newPos) {
         newPos = cell[0];
         break;
         }
         }

        p.position = newPos;

        if (newPos == 100) {
            gameOver = true;
            winner = p;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        return winner;
    }

}