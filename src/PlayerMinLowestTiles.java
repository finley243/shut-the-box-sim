import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This player will maximize the number of selected tiles, then maximize the sum of squares of the reverse of the
 * values of the selected tiles (tile count + 1 - tile value).
 */
public class PlayerMinLowestTiles extends Player {

    @Override
    public Set<Integer> chooseFlips(Set<Set<Integer>> validChoices, GameState gameState) {
        List<Set<Integer>> minTileChoices = new ArrayList<>();
        int minTiles = gameState.getTileCount();
        for (Set<Integer> currentChoice : validChoices) {
            if (currentChoice.size() < minTiles) {
                minTiles = currentChoice.size();
                minTileChoices.clear();
                minTileChoices.add(currentChoice);
            } else if (currentChoice.size() == minTiles) {
                minTileChoices.add(currentChoice);
            }
        }
        if (minTileChoices.size() == 1) {
            return minTileChoices.get(0);
        }
        List<Set<Integer>> bestChoices = new ArrayList<>();
        int maxSum = 0;
        for (Set<Integer> currentChoice : minTileChoices) {
            int currentSum = 0;
            for (int tile : currentChoice) {
                currentSum += Math.toIntExact(Math.round(Math.pow(gameState.getTileCount() + 1 - tile, 2)));
            }
            if (currentSum > maxSum) {
                maxSum = currentSum;
                bestChoices.clear();
                bestChoices.add(currentChoice);
            } else if (currentSum == maxSum) {
                bestChoices.add(currentChoice);
            }
        }
        return bestChoices.get(ThreadLocalRandom.current().nextInt(bestChoices.size()));
    }

}
