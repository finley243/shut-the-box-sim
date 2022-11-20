import java.util.Set;

public abstract class Player {

    private int score;
    private int winCount;

    public Player() {

    }

    public int getScore() {
        return score;
    }

    public int getWinCount() {
        return winCount;
    }

    public void resetScore() {
        score = 0;
    }

    public void resetWinCount() {
        winCount = 0;
    }

    public void addWinCount() {
        winCount += 1;
    }

    public void takeTurn(GameState gameState) {
        while (true) {
            int roll = gameState.roll();
            Set<Set<Integer>> validChoices = gameState.getPossibleFlips(roll);
            if (validChoices.isEmpty()) {
                break;
            }
            Set<Integer> chosenFlips = chooseFlips(validChoices, gameState);
            gameState.applyChosenFlips(chosenFlips);
        }
        score += gameState.getUnflippedTileSum();
        gameState.resetTiles();
    }

    public abstract Set<Integer> chooseFlips(Set<Set<Integer>> validChoices, GameState gameState);

}
