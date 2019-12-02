import java.util.ArrayList;

public class Testing {
    public static void printArray(double[] arr) {
        for (double d : arr) {
            System.out.print(d + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Deck d = new Deck();
        d.shuffleDeck();
        Game g = new Game(d, 2, 100, 1);
        Human h = new Human(g, 100);
        Intelligence intel = new Intelligence(g, g.getNpcs().get(0));
        printArray(intel.getDeckProbabilities());
        d.dealCardsToPlayers(g.getNpcs(), h);
        intel.refreshData();
        printArray(intel.getDeckProbabilities());
        for (int i = 0; i < 3; i ++) {
            d.placePublicCard();
        }
        g.nextRound();
        System.out.println(d.getVisibleCards());
        intel.refreshData();
        printArray(intel.getDeckProbabilities());
        System.out.println(g.getNpcs().get(0).getCardsOwned());
        System.out.println(intel.pPair());
    }

}
