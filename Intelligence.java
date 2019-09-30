import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Intelligence {
    private Game game;
    private Deck deck;
    private Npc npc;
    private ArrayList<Integer> deckCards;
    private ArrayList<Integer> visibleCards;
    private ArrayList<Integer> personalCards;
    private ArrayList<Integer> usableCards;
    private int otherActives;

    public Intelligence(Game g, Npc npc) {
        game = g;
        deck = game.getGameDeck();
        this.npc = npc;
        refreshData();
    }

    /**
     * Makes copies of the arraylists every time, as to avoid potentially modifying the arraylists
     * in other classes.
     */
    public void refreshData() {
        deckCards = new ArrayList<>(deck.getCardsInDeck());
        visibleCards = new ArrayList<>(deck.getVisibleCards());
        personalCards = new ArrayList<>(npc.getCardsOwned());
        usableCards = new ArrayList<>();
        usableCards.addAll(visibleCards);
        usableCards.addAll(personalCards);
        otherActives = 0;
        for (Integer i : game.getActivePlayers()) {
            if (i == 1) otherActives ++;
        }
    }

    public double pHighCard() {

    }

    public double pPair() {
        for (int i = 0 ; i < usableCards.size(); i ++) {
            usableCards.set(i, usableCards.get(i) % 13);
        }
        HashSet<Integer> keys = new HashSet<>(usableCards);
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int i : usableCards) {
            counts.put(i, counts.getOrDefault(counts.get(i), 0) + 1);
        }
        for (Integer key : keys) {
            if (counts.get(key) >= 2)
                return 1;
            else {

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
}
