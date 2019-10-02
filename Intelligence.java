import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Intelligence {
    private Game game;
    private Deck deck;
    private Npc npc;
    private double[] deckProbabilities;
    private ArrayList<Integer> visibleCards;
    private ArrayList<Integer> personalCards;
    private ArrayList<Integer> usableCards;
    private int deckSize;
    private int otherActives;

    public Intelligence(Game g, Npc npc) {
        game = g;
        deck = game.getGameDeck();
        this.npc = npc;

        // Copied over from refreshData(). Must avoid calling refreshData() before Intelligence
        // instance is fully initialized
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

    public void refreshData() {
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
/**
    public double pHighCard() {

    }

    public HashMap<Integer, Double> pPair() {
        HashSet<Integer> keys = new HashSet<>();
        HashMap<Integer, Integer> counts = new HashMap<>();
        HashMap<Integer, Double> ret = new HashMap<>();

        for (int i : usableCards) {
            int rank = i % 13;
            keys.add(rank);
            counts.put(rank, counts.getOrDefault(counts.get(rank), 0) + 1);
        }

        for (Integer key : keys) {
            if (counts.get(key) >= 2)
                ret.put(key, 1.0);
            else if (counts.get(key) == 1) {
                double remainingCount = 0;
                for (int i = key; i < 52; i += 13) { remainingCount += deckProbabilities[i]; }
                if (game.getRound() == 0) {
                    double p = 1 - Math.pow(1 - remainingCount/deck.getCardsInDeck().size(), 5);
                }
                else if ()
            }
        }

    }

    public double pTwoPair() {

    }

    public double pThreeOfAKind() {

    }

    public double pStraight() {

    }

    public double pFlush() {

    }

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
