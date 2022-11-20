import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    /** Path of output file to write to */
    public static final String FILE_OUTPUT_PATH = "src/output/";
    /** Name of output file to write to */
    public static final String FILE_OUTPUT_NAME = "shutTheBox";

    private static final int RUNS = 10;
    private static final int GAMES_PER_RUN = 1000000;

    public static void main(String[] args) throws IOException {
        GameState gameState = new GameState(12, 2, 6);
        List<Player> players = new ArrayList<>();
        // ADD PLAYERS HERE
        players.add(new PlayerMinLowestTiles());
        players.add(new PlayerRandom());
        // ----------------
        // Key: player index, Value: list of win counts per run
        Map<Integer, List<Integer>> playerWinCounts = new HashMap<>();
        for (int i = 0; i < players.size(); i++) {
            playerWinCounts.put(i, new ArrayList<>(RUNS));
        }
        for (int run = 0; run < RUNS; run++) {
            // UNCOMMENT FOR CSV FILE GENERATION (FOR CONVERGENCE PLOTTING)
            //File outputFile = new File(FILE_OUTPUT_PATH + FILE_OUTPUT_NAME + run + ".csv");
            //FileWriter writer = new FileWriter(outputFile);
            //BufferedWriter buffer = new BufferedWriter(writer);
            //buffer.write("Game,Proportion\n");

            for (Player player : players) {
                player.resetWinCount();
            }
            for (int game = 0; game < GAMES_PER_RUN; game++) {
                int minScore;
                int minPlayer;
                boolean tiedWin;
                // If multiple players tie for the winning score, they start over
                do {
                    minScore = gameState.maxScore();
                    minPlayer = -1;
                    tiedWin = false;
                    gameState.resetTiles();
                    for (Player player : players) {
                        player.resetScore();
                    }
                    for (int i = 0; i < players.size(); i++) {
                        players.get(i).takeTurn(gameState);
                        if (players.get(i).getScore() == minScore) {
                            tiedWin = true;
                        } else if (players.get(i).getScore() < minScore) {
                            minScore = players.get(i).getScore();
                            minPlayer = i;
                            tiedWin = false;
                        }
                    }
                } while (tiedWin);
                Player winner = players.get(minPlayer);
                winner.addWinCount();
                // UNCOMMENT FOR CSV FILE GENERATION (FOR CONVERGENCE PLOTTING)
                //buffer.write((game + 1) + "," + ((float) players.get(0).getWinCount())/(game + 1) + "\n");
            }
            for (int i = 0; i < players.size(); i++) {
                playerWinCounts.get(i).add(players.get(i).getWinCount());
            }
            System.out.println("RUN " + run + " COMPLETE");

            // UNCOMMENT FOR CSV FILE GENERATION (FOR CONVERGENCE PLOTTING)
            //buffer.close();
            //writer.close();
        }

        List<Float> player1WinProportions = new ArrayList<>(RUNS);
        float minProportion = 1.0f;
        float maxProportion = 0.0f;
        for (int winCount : playerWinCounts.get(0)) {
            float proportion = ((float) winCount) / GAMES_PER_RUN;
            player1WinProportions.add(proportion);
            if (proportion < minProportion) {
                minProportion = proportion;
            }
            if (proportion > maxProportion) {
                maxProportion = proportion;
            }
        }
        System.out.println("Player 1 win proportions:");
        System.out.println(player1WinProportions);
        System.out.println("Min proportion: " + minProportion);
        System.out.println("Max proportion: " + maxProportion);
    }

}