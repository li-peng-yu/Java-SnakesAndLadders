import java.io.*;

public class readfile {
    private static final String SAVE_FILE = "game_save.txt";

    /**
     * 保存游戏状态
     */
    public static void saveGame(int position, int rounds, int players) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE));
        writer.write(position + "\n");
        writer.write(rounds + "\n");
        writer.write(players + "\n");
        writer.close();
    }

    /**
     * 读取游戏状态
     */
    public static int[] loadGame() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE));
        int position = Integer.parseInt(reader.readLine());
        int rounds = Integer.parseInt(reader.readLine());
        int players = Integer.parseInt(reader.readLine());
        reader.close();
        return new int[]{position, rounds, players};
    }

    /**
     * 检查存档是否存在
     */
    public static boolean hasSaveFile() {
        return new File(SAVE_FILE).exists();
    }
}
