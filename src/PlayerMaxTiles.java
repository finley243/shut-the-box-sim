import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This player will maximize the number of selected tiles.
 */
public class PlayerMaxTiles extends Player {

    @Override
    public Set<Integer> chooseFlips(Set<Set<Integer>> validChoices, GameState gameState) {
        List<Set<Integer>> bestChoices = new ArrayList<>();
        int maxTiles = 0;
        for (Set<Integer> currentChoice : validChoices) {
            if (currentChoice.size() > maxTiles) {
                maxTiles = currentChoice.size();
                bestChoices.clear();
                bestChoices.add(currentChoice);
            } else if (currentChoice.size() == maxTiles) {
                bestChoices.add(currentChoice);
            }
        }
        return bestChoices.get(ThreadLocalRandom.current().nextInt(bestChoices.size()));
    }

}
