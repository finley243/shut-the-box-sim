import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This player will minimize the number of selected tiles.
 */
public class PlayerMinTiles extends Player {

    @Override
    public Set<Integer> chooseFlips(Set<Set<Integer>> validChoices, GameState gameState) {
        List<Set<Integer>> bestChoices = new ArrayList<>();
        int minTiles = gameState.getTileCount();
        for (Set<Integer> currentChoice : validChoices) {
            if (currentChoice.size() < minTiles) {
                minTiles = currentChoice.size();
                bestChoices.clear();
                bestChoices.add(currentChoice);
            } else if (currentChoice.size() == minTiles) {
                bestChoices.add(currentChoice);
            }
        }
        return bestChoices.get(ThreadLocalRandom.current().nextInt(bestChoices.size()));
    }

}
