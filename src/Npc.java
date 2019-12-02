import java.util.ArrayList;

public class Npc extends Player{
    private Intelligence intel;

    public Npc(Game g, int startingMoney) {
        super(g, startingMoney);
        intel = new Intelligence(g, this);
    }

    public void move() {

    }

}
