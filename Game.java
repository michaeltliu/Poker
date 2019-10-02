import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Deck gameDeck;
    private ArrayList<Npc> npcs;
    private ArrayList<Integer> activePlayers;
    private int smallBlind;
    private int bigBlind;

    private int turn;
    private int round;

    public Game(Deck d, int numNPC, int startingMoney, int sBlind) {
        gameDeck = d;
        // activePlayers must initialize before the npcs.add() call
        activePlayers = new ArrayList<>();
        for (int i = 0; i < numNPC + 1; i ++) activePlayers.add(1);
        npcs = new ArrayList<>();
        for (int i = 0; i < numNPC; i ++) {
            npcs.add(new Npc(this, startingMoney));
        }
        smallBlind = sBlind;
        bigBlind = sBlind * 2;

        turn = 0;
        round = 0;
    }

    public Deck getGameDeck() {
        return gameDeck;
    }

    public ArrayList<Npc> getNpcs() {
        return npcs;
    }

    public ArrayList<Integer> getActivePlayers() {
        return activePlayers;
    }

    public int getRound() {
        return round;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many NPC's would you like to play against? ");
        int numNPC = scanner.nextInt();
        System.out.println("How much starting money should everyone start with? ");
        int startingMoney = scanner.nextInt();
        System.out.println("How much should the small blind be? Big blind will be automatically double. ");
        int smallBlind = scanner.nextInt();

        Deck gameDeck = new Deck();
        Game game = new Game(gameDeck, numNPC, startingMoney, smallBlind);


    }
}
