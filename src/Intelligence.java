import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Intelligence {
    private Game game;
    private Deck deck;
    private Npc npc;
    private double[] deckProbabilities; //probably useless very soon
    private ArrayList<Integer> visibleCards;
    private ArrayList<Integer> personalCards;
    private ArrayList<Integer> usableCards;
    private int deckSize;
    private int numNPC;
    private int otherActives;

    public Intelligence(Game g, Npc npc) {
        game = g;
        deck = game.getGameDeck();
        this.npc = npc;
        numNPC = game.getNpcs().size();

        refreshData();
    }

    private void refreshData() {
        deckProbabilities = new double[52];
        for (int i = 0; i < 52; i ++) { deckProbabilities[i] = 1; }

        visibleCards = new ArrayList<>(deck.getVisibleCards());
        personalCards = new ArrayList<>(npc.getCardsOwned());

        usableCards = new ArrayList<>();
        usableCards.addAll(visibleCards);
        usableCards.addAll(personalCards);

        for (Integer i : usableCards) { deckProbabilities[i] = 0; }
        deckSize = deck.getCardsInDeck().size();
        for (int i = 0; i < 52; i ++) { deckProbabilities[i] *= deckSize/52.0; }

        otherActives = 0;
        for (Integer i : game.getActivePlayers()) {
            if (i == 1) otherActives ++;
        }
    }

    private long choose(int a, int b) {
        long product = 1;
        for (int i = a; i > b; i --) {
            product *= i;
            product /= 1 + a - i;
        }
        return product;
    }

    public double pHighCard() {
        return 0;
    }

    private double pPairHelper(int target, HashMap<Integer,Integer> counts) {
        if (counts.get(target) >= 2)
            return 1.0;
        else if (counts.get(target) <= 1) {
            double[] probRemaining = new double[4];
            long denom = choose(deckSize, 2 * numNPC);
            probRemaining[0] = (choose(deckSize - 3, 2 * numNPC - 3) + 0.0)/ denom;
            probRemaining[1] = 3.0 * (choose(deckSize - 3, 2 * numNPC - 2))/ denom;
            probRemaining[2] = 3.0 * (choose(deckSize - 3, 2 * numNPC - 1))/ denom;
            probRemaining[3] = (choose(deckSize - 3, 2 * numNPC) + 0.0)/ denom;

            int c;
            if (game.getRound() == 0) { c = 5;}
            else if (game.getRound() == 1) { c = 2; }
            else if (game.getRound() == 2) { c = 1; }
            else { c = 0; }

            double p = 0;
            if (counts.get(target) == 1) {
                for (int i = 0; i < 4; i++) {
                    p += probRemaining[i] * (1 - Math.pow((deckSize - i) / (deckSize + 0.0), c));
                }
                return p;
            }
            // this else if will never be reached by pPair()
            // but may be needed by other helper methods e.g. pTwoPairHelper()
            else if (counts.get(target) == 0) {
                for (int i = 0; i < 4; i++) {
                    p += probRemaining[i] * (1 - Math.pow((deckSize - i) / (deckSize + 0.0), c) -
                            c * Math.pow((deckSize - i)/(deckSize + 0.0), c-1) * i/(deckSize + 0.0));
                }
                return p;
            }
        }
        return 0;
    }
    public HashMap<Integer, Double> pPair() {
        refreshData();

        HashSet<Integer> keys = new HashSet<>();
        HashMap<Integer, Integer> counts = new HashMap<>();
        HashMap<Integer, Double> ret = new HashMap<>();

        for (int i : usableCards) {
            int rank = i % 13;
            keys.add(rank);
            counts.put(rank, counts.getOrDefault(rank, 0) + 1);
        }

        for (Integer key : keys) {
            ret.put(key, pPairHelper(key, counts));
        }
        return ret;
    }

    private double pTwoPairHelper(int[] pair, HashMap<Integer,Integer> counts) {
        if (counts.get(pair[0]) >= 2) {
            return pPairHelper(pair[1], counts);
        }
        else if ((counts.get(pair[1])) >= 2) {
            return pPairHelper(pair[0], counts);
        }
        else if (counts.get(pair[0]) == 1 && counts.get(pair[1]) == 1) {
            long denom = choose(deckSize, 2 * numNPC);
            double[] probRemaining = new double[4];
            probRemaining[0] = (choose(deckSize - 3, 2 * numNPC - 3) + 0.0)/ denom;
            probRemaining[1] = 3.0 * (choose(deckSize - 3, 2 * numNPC - 2))/ denom;
            probRemaining[2] = 3.0 * (choose(deckSize - 3, 2 * numNPC - 1))/ denom;
            probRemaining[3] = (choose(deckSize - 3, 2 * numNPC) + 0.0)/ denom;

            int c;
            if (game.getRound() == 0) { c = 5;}
            else if (game.getRound() == 1) { c = 2; }
            else if (game.getRound() == 2) { c = 1; }
            else { c = 0; }

            double p = 0;
            for (int i = 0; i < 4; i ++) {
                for (int j = 0; j < c - i; j ++) {
                    if (j > 3) {
                        break;
                    }
                    p += probRemaining[i]
                }
            }
        }
        return 0;
    }

    public HashMap<int[], Double> pTwoPair() {
        refreshData();

        HashSet<Integer> keys = new HashSet<>();
        HashMap<Integer, Integer> counts = new HashMap<>();
        HashMap<int[], Double> ret = new HashMap<>();

        for (int i : usableCards) {
            int rank = i % 13;
            keys.add(rank);
            counts.put(rank, counts.getOrDefault(rank, 0) + 1);
        }
        ArrayList<Integer> keyList = new ArrayList<>(keys);
        for (int i = 0; i < keyList.size() - 1; i++) {
            for (int j = i + 1; j < keyList.size(); j ++) {
                int[] pair = new int[] {keyList.get(i), keyList.get(j)};
                ret.put(pair, pTwoPairHelper(pair, counts));
            }
        }

        return ret;
    }

    // just pPair with slight modifications
    public HashMap<Integer, Double> pThreeOfAKind() {
        HashSet<Integer> keys = new HashSet<>();
        HashMap<Integer, Integer> counts = new HashMap<>();
        HashMap<Integer, Double> ret = new HashMap<>();

        for (int i : usableCards) {
            int rank = i % 13;
            keys.add(rank);
            counts.put(rank, counts.getOrDefault(rank, 0) + 1);
        }

        for (Integer key : keys) {
            double remainingCount = 0;
            for (int i = key; i < 52; i += 13) { remainingCount += deckProbabilities[i]; }

            if (counts.get(key) >= 3)
                ret.put(key, 1.0);
            else if (counts.get(key) == 2) {
                if (game.getRound() == 0) {
                    double p = 1 - Math.pow(1 - (remainingCount+0.0)/deck.getCardsInDeck().size(), 5);
                    ret.put(key, p);
                }
                else if (game.getRound() == 1) {
                    double p = 1 - Math.pow(1 - (remainingCount+0.0)/deck.getCardsInDeck().size(), 2);
                    ret.put(key, p);
                }
                else if (game.getRound() == 2) {
                    double p = 1 - Math.pow(1 - (remainingCount+0.0)/deck.getCardsInDeck().size(), 1);
                    ret.put(key, p);
                }
            }
            else if (counts.get(key) == 1) {
                if (game.getRound() == 0) {
                    double p = 1;
                }
            }
        }
        return ret;
    }
/**
    public double pStraight() {

    }
*/
    public double pFlush() {
        return 0;
    }
/**
    public double pFullHouse() {

    }

    public double pFourOfAKind() {

    }

    public double pStraightFlush() {

    }

    public double pRoyalFlush() {

    }
*/
    public double[] getDeckProbabilities() {
        return deckProbabilities;
    }
}
