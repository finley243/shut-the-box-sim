import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This player will maximize the sum of squares of the reverse of the values of the selected tiles
 * (tile count + 1 - tile value).
 */
public class PlayerLowestTiles extends Player {

    @Override
    public Set<Integer> chooseFlips(Set<Set<Integer>> validChoices, GameState gameState) {
        List<Set<Integer>> bestChoices = new ArrayList<>();
        int maxSum = 0;
        for (Set<Integer> currentChoice : validChoices) {
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
