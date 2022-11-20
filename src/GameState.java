import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameState {

    private final int tileCount;
    private final int diceCount;
    private final int diceSides;
    // Key: sum, Value: set of subsets
    private final Map<Integer, Set<Set<Integer>>> validSubsets;

    // False = not flipped, True = flipped
    private final boolean[] tileStates;

    public GameState(int tileCount, int diceCount, int diceSides) {
        this.tileCount = tileCount;
        this.diceCount = diceCount;
        this.diceSides = diceSides;
        tileStates = new boolean[tileCount];
        this.validSubsets = getValidSubsets();
    }

    public int roll() {
        int diceSum = 0;
        for (int i = 0; i < diceCount; i++) {
            diceSum += ThreadLocalRandom.current().nextInt(1, diceSides + 1);
        }
        return diceSum;
    }

    public Set<Set<Integer>> getPossibleFlips(int rolledSum) {
        Set<Set<Integer>> possibleSubsets = validSubsets.get(rolledSum);
        if (possibleSubsets == null) {
            return new HashSet<>();
        } else {
            Set<Set<Integer>> validChoices = new HashSet<>();
            for (Set<Integer> subset : possibleSubsets) {
                boolean isValid = true;
                for (int value : subset) {
                    if (tileStates[value - 1]) {
                        isValid = false;
                        break;
                    }
                }
                if (isValid) {
                    validChoices.add(subset);
                }
            }
            return validChoices;
        }
    }

    public void applyChosenFlips(Set<Integer> chosenFlips) {
        for (int value : chosenFlips) {
            if (tileStates[value - 1]) throw new IllegalArgumentException("Invalid chosen tile set " + chosenFlips.toString() + " for state " + Arrays.toString(tileStates));
            tileStates[value - 1] = true;
        }
    }

    public int getUnflippedTileSum() {
        int sum = 0;
        for (int i = 1; i <= tileCount; i++) {
            if (!tileStates[i - 1]) {
                sum += i;
            }
        }
        return sum;
    }

    public void resetTiles() {
        for (int i = 0; i < tileCount; i++) {
            tileStates[i] = false;
        }
    }

    public int maxScore() {
        return (tileCount * (tileCount + 1)) / 2;
    }

    public int getTileCount() {
        return tileCount;
    }

    private Map<Integer, Set<Set<Integer>>> getValidSubsets() {
        Map<Integer, Set<Set<Integer>>> subsetMap = new HashMap<>();
        Set<Set<Integer>> allSubsets = expandSubset(new HashSet<>(), new HashSet<>(), 1);
        Iterator<Set<Integer>> subsetItr = allSubsets.iterator();
        while (subsetItr.hasNext()) {
            Set<Integer> currentSubset = subsetItr.next();
            int sum = 0;
            for (Integer value : currentSubset) {
                sum += value;
            }
            if (sum > diceCount * diceSides) {
                // Remove subsets that cannot be reached by the highest possible roll
                subsetItr.remove();
            } else {
                if (!subsetMap.containsKey(sum)) {
                    subsetMap.put(sum, new HashSet<>());
                }
                // Adds subset to map under the key of its sum
                subsetMap.get(sum).add(currentSubset);
            }
        }
        return subsetMap;
    }

    private Set<Set<Integer>> expandSubset(Set<Set<Integer>> subsets, Set<Integer> currentSubset, int start) {
        if (start > tileCount) {
            return subsets;
        }
        for (int i = start; i <= tileCount; i++) {
            Set<Integer> newSubset = new HashSet<>(currentSubset);
            newSubset.add(i);
            subsets.add(newSubset);
            subsets = expandSubset(subsets, newSubset, i + 1);
        }
        return subsets;
    }

}
