import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerRandom extends Player {

    @Override
    public Set<Integer> chooseFlips(Set<Set<Integer>> validChoices, GameState gameState) {
        List<Set<Integer>> validChoicesList = new ArrayList<>(validChoices);
        return validChoicesList.get(ThreadLocalRandom.current().nextInt(validChoicesList.size()));
    }

}
