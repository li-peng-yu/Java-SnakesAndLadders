package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 游戏引擎核心类，整合玩家管理、骰子、游戏逻辑和道具系统
 */
public class Gameengine {
    // 玩家列表
    private List<Player> players = new ArrayList<>();
    // 当前玩家索引
    private int currentPlayerIndex;
    // 游戏是否结束标志
    private boolean gameOver;
    // 胜利者
    private Player winner;
    // 随机数生成器（用于骰子）
    private Random random = new Random();
    // 游戏模式标志 (true为单人模式，false为双人模式)
    private boolean singlePlayerMode;

    // Add to Gameengine.java
    public Player getPlayerByName(String name) {
        for (Player p : players) {
            if (p.name.equals(name)) {
                return p;
            }
        }
        System.err.println("Player not found by name: " + name);
        return null;
    }

    // 道具枚举
    public enum Item {
        FROZEN_TRAP("冰冻陷阱", "冻结对手一回合") {
            @Override
            public boolean use(Gameengine engine, Player user, Player target) {
                if (target.isFrozen) {
                    engine.addGameMessage(target.name + "已经被冻结了！");
                    return false;
                }
                target.isFrozen = true;
                engine.addGameMessage(user.name + "对" + target.name + "使用了冰冻陷阱！");
                return true;
            }
        },
        POSITION_SWAP("位置交换器", "立即与对手交换位置") {
            @Override
            public boolean use(Gameengine engine, Player user, Player target) {
                int temp = user.position;
                user.position = target.position;
                target.position = temp;
                engine.addGameMessage(user.name + "和" + target.name + "交换了位置！");
                return true;
            }
        };

        private final String name;
        private final String description;

        Item(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        // 抽象方法，每个道具需要实现自己的使用逻辑
        public abstract boolean use(Gameengine engine, Player user, Player target);
    }

    // 玩家内部类
    public class Player {
        public String name;       // 玩家名称
        public int position;      // 当前位置
        public boolean isGuest;   // 是否为游客
        public boolean isFrozen; // 是否被冻结
        public List<Item> items = new ArrayList<>(); // 玩家拥有的道具

        Player(String name, boolean isGuest) {
            this.name = name;
            this.position = 1;  // 初始位置0表示未开始
            this.isGuest = isGuest;
            this.isFrozen = false;

            // 初始给每个玩家随机分配1-2个道具
            int itemCount = random.nextInt(2) + 1;
            for (int i = 0; i < itemCount; i++) {
                items.add(random.nextBoolean() ? Item.FROZEN_TRAP : Item.POSITION_SWAP);
            }
        }

        // 检查是否拥有特定道具
        boolean hasItem(Item item) {
            return items.contains(item);
        }

        // 使用道具
        boolean useItem(Item item, Gameengine engine, Player target) {
            if (!hasItem(item)) {
                engine.addGameMessage(name + "没有" + item.getName() + "道具！");
                return false;
            }

            if (item.use(engine, this, target)) {
                items.remove(item);
                return true;
            }
            return false;
        }

    }
    public int getCurrentPlayerPosition() {
        return getCurrentPlayer().position;
    }
    // 特殊格子定义
    public static final int[][] LADDERS_CELLS = {
            {43,83}, {72,91}, {27,67}, {3,40},
    };
    public static final int[][] SNAKES_CELLS = {
            {61,81}, {23,45}, {8,31}, {68,96},
    };



    /**
     * 构造函数初始化游戏
     * @param playerNames 玩家名称列表
     */
    public Gameengine(List<String> playerNames) {
        for (String name : playerNames) {
            players.add(new Player(name, name.equals("Guest")));
        }
        currentPlayerIndex = 0;
        singlePlayerMode = (playerNames.size() == 1);
    }

    /**
     * 使用道具方法
     * @param item 要使用的道具
     * @param targetPlayerIndex 目标玩家索引
     * @return 是否使用成功
     */
    public boolean useItem(Item item, int targetPlayerIndex) {
        if (gameOver || targetPlayerIndex < 0 || targetPlayerIndex >= players.size()) {
            return false;
        }

        Player current = getCurrentPlayer();
        Player target = players.get(targetPlayerIndex);

        if (current == target) {
            addGameMessage("不能对自己使用道具！");
            return false;
        }

        return current.useItem(item, this, target);
    }

    /**
     * 获取当前玩家可用的道具列表
     * @return 道具列表
     */
    public List<Item> getCurrentPlayerItems() {
        return new ArrayList<>(getCurrentPlayer().items);
    }

    /**
     * 单人模式下AI自动掷骰子
     * @return 骰子点数（1-6），-1表示游戏已结束
     */
    public int aiRollDice() {
        if (!singlePlayerMode || gameOver) return -1;

        // AI简单逻辑：随机掷骰子
        int steps = random.nextInt(6) + 1;
        movePlayer(steps);

        // AI有30%概率使用道具
        if (random.nextDouble() < 0.3 && !players.get(1).items.isEmpty()) {
            Item item = players.get(1).items.get(0); // 使用第一个道具
            useItem(item, 0); // 对人类玩家使用
        }

        if (!gameOver) {
            currentPlayerIndex = 0; // 切换回人类玩家
        }
        return steps;
    }

    /**
     * 掷骰子并移动当前玩家
     * @return 骰子点数（1-6），-1表示游戏已结束
     */
    public int rollDice() {
        if (gameOver) return -1;

        Player current = getCurrentPlayer();
        if (current.isFrozen) {
            current.isFrozen = false; // 为下一轮解冻
            addGameMessage(current.name + "被冻结，跳过一回合！");
            // 不在此处切换 currentPlayerIndex
            return -2; // 特殊返回值表示被冻结
        }

        int steps = random.nextInt(6) + 1;
        movePlayer(steps); // movePlayer 会更新 players.get(currentPlayerIndex).position
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

    // 游戏消息处理
    private void addGameMessage(String message) {
        System.out.println("[游戏消息] " + message);
    }

    /** 获取当前玩家 */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /** 检查游戏是否结束 */
    public boolean isGameOver() {
        return gameOver;
    }

    /** 获取胜利者 */
    public Player getWinner() {
        return winner;
    }

    /** 重新开始游戏 */
    public void restart() {
        players.forEach(p -> {
            p.position = 0;
            p.isFrozen = false;
            p.items.clear();
            // 重新分配道具
            int itemCount = random.nextInt(2) + 1;
            for (int i = 0; i < itemCount; i++) {
                p.items.add(random.nextBoolean() ? Item.FROZEN_TRAP : Item.POSITION_SWAP);
            }
        });
        currentPlayerIndex = 0;
        gameOver = false;
        winner = null;
    }
}