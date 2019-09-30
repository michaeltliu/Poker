import java.util.ArrayList;

public class Player {
    private Deck deck;
    private int money;
    private ArrayList<Integer> cardsOwned;

    public Player(Game g, int startingMoney) {
        deck = g.getGameDeck();
        money = startingMoney;
        cardsOwned = new ArrayList<>();
    }

    public void receiveCards(int a, int b) {
        cardsOwned.add(a);
        cardsOwned.add(b);
    }

    public void returnCards() {
        cardsOwned.clear();
    }

    public void check() {

    }

    public void call() {

    }

    public void raise() {

    }

    public void fold() {

    }

    public int getMoney() {
        return money;
    }

    public ArrayList<Integer> getCardsOwned() {
        return cardsOwned;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setMoney(int money) {
        this.money = money;
    }

}
