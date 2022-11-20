import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This player will maximize the number of selected tiles, then maximize the sum of squares of the reverse of the
 * values of the selected tiles (tile count + 1 - tile value).
 */
public class PlayerMaxLowestTiles extends Player {

    @Override
    public Set<Integer> chooseFlips(Set<Set<Integer>> validChoices, GameState gameState) {
        List<Set<Integer>> maxTileChoices = new ArrayList<>();
        int maxTiles = 0;
        for (Set<Integer> currentChoice : validChoices) {
            if (currentChoice.size() > maxTiles) {
                maxTiles = currentChoice.size();
                maxTileChoices.clear();
                maxTileChoices.add(currentChoice);
            } else if (currentChoice.size() == maxTiles) {
                maxTileChoices.add(currentChoice);
            }
        }
        if (maxTileChoices.size() == 1) {
            return maxTileChoices.get(0);
        }
        List<Set<Integer>> bestChoices = new ArrayList<>();
        int maxSum = 0;
        for (Set<Integer> currentChoice : maxTileChoices) {
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
