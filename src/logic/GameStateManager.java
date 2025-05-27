package logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameStateManager {

    private static final String GAME_STATE_FILE_PATH = "gamestates.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Map<String, GameSaveState> activeGameStates = new ConcurrentHashMap<>();

    static {
        loadGameStatesFromFile();
    }

    public static class GameSaveState {
        int player1Position;
        int player2Position;
        boolean isPlayerOneTurn;
        boolean twoPlayersMode;


        public GameSaveState(int p1Pos, int p2Pos, boolean isP1Turn, boolean isTwoPlayersMode) {
            this.player1Position = p1Pos;
            this.player2Position = p2Pos;
            this.isPlayerOneTurn = isP1Turn;
            this.twoPlayersMode = isTwoPlayersMode;
        }

        public int getPlayer1Position() {
            return player1Position;
        }

        public int getPlayer2Position() {
            return player2Position;
        }

        public boolean isPlayerOneTurn() {
            return isPlayerOneTurn;
        }

        public boolean isTwoPlayersMode() {
            return twoPlayersMode;
        }
    }

    private static String generateKey(String username, boolean isTwoPlayerMode) {
        if (username == null || username.trim().isEmpty()) {
            username = "guest";
        }
        return username + "_" + (isTwoPlayerMode ? "multi" : "single");
    }

    private static synchronized void loadGameStatesFromFile() {
        try {
            File file = new File(GAME_STATE_FILE_PATH);
            if (file.exists() && !file.isDirectory() && file.length() > 0) {
                Reader reader = Files.newBufferedReader(Paths.get(GAME_STATE_FILE_PATH));
                Type type = new TypeToken<ConcurrentHashMap<String, GameSaveState>>() {}.getType();
                activeGameStates = gson.fromJson(reader, type);
                if (activeGameStates == null) {
                    activeGameStates = new ConcurrentHashMap<>();
                }
                activeGameStates.keySet().removeIf(key -> key.toLowerCase().startsWith("guest_"));
                reader.close();
                System.out.println("Game states loaded from " + GAME_STATE_FILE_PATH);
            } else {
                activeGameStates = new ConcurrentHashMap<>();
            }
        } catch (IOException e) {
            activeGameStates = new ConcurrentHashMap<>();
        }
    }

    public static synchronized void saveGameStatesToFile() {
        try (Writer writer = Files.newBufferedWriter(Paths.get(GAME_STATE_FILE_PATH))) {
            gson.toJson(activeGameStates, writer);
        } catch (IOException e) {
            System.err.println("Failed to save game states to file: " + e.getMessage());
        }
    }

    public static synchronized void saveCurrentGameState(String username, int p1Position, int p2Position, boolean isPlayerOneTurnNext, boolean isTwoPlayerMode) {
        String key = generateKey(username, isTwoPlayerMode);
        GameSaveState state = new GameSaveState(p1Position, p2Position, isPlayerOneTurnNext, isTwoPlayerMode);
        activeGameStates.put(key, state);
        saveGameStatesToFile();
        System.out.println("Game state saved for " + key + ". P1 Pos: " + p1Position + ", P2 Pos: " + p2Position + ", P1 Turn Next: " + isPlayerOneTurnNext);
    }

    public static synchronized GameSaveState loadGameStateForUser(String username, boolean isTwoPlayerMode) {
        String key = generateKey(username, isTwoPlayerMode);
        GameSaveState loadedState = activeGameStates.get(key);
        if (loadedState != null) {
            // Ensure the loaded state matches the requested game mode
            if (loadedState.isTwoPlayersMode() == isTwoPlayerMode) {
                System.out.println("Game state loaded for " + key);
                return loadedState;
            } else {
                System.out.println("Saved game state for " + key + " is for a different mode. Not loading.");
                return null;
            }
        } else {
            System.out.println("No saved game state found for " + key);
            return null;
        }
    }

    public static synchronized void clearGameStateForUser(String username, boolean isTwoPlayerMode) {
        String key = generateKey(username, isTwoPlayerMode);
        if (activeGameStates.containsKey(key)) {
            activeGameStates.remove(key);
            new Thread(() -> saveGameStatesToFile()).start();
            System.out.println("Game state cleared for " + key);
        } else {
            System.out.println("No game state to clear for " + key);
        }
    }
}