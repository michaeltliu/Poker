import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Integer> cardsInDeck;
    private ArrayList<Integer> visibleCards;
    private ArrayList<ArrayList<Integer>> playerCards;

    public Deck() {
        cardsInDeck = new ArrayList<>();
        for (int i = 0; i < 52; i ++) cardsInDeck.add(i);
        visibleCards = new ArrayList<>();
        playerCards = new ArrayList<>();
    }

    public void shuffleDeck() {
        Collections.shuffle(cardsInDeck);
    }

    public void dealCardsToPlayers(ArrayList<Npc> n, Human h) {
        for (Npc npc : n) {
            npc.receiveCards(cardsInDeck.remove(0), cardsInDeck.remove(0));
        }
        h.receiveCards(cardsInDeck.remove(0), cardsInDeck.remove(0));
    }

    public void placePublicCard() {
        visibleCards.add(cardsInDeck.remove(0));
    }

    /**
     * Does not remove the cards from cardsOwned arraylist of npc and human
     */
    public void returnCardsToDeck() {
        cardsInDeck.clear();
        for (int i = 0; i < 52; i ++) cardsInDeck.add(i);
        visibleCards.clear();
        playerCards.clear();
    }

    public ArrayList<Integer> getCardsInDeck() {
        return this.cardsInDeck;
    }

    public ArrayList<Integer> getVisibleCards() {
        return this.visibleCards;
    }

    public ArrayList<ArrayList<Integer>> getPlayerCards() {
        return this.playerCards;
    }
}
