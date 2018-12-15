package characters;

import behavior.CryBehavior;
import behavior.KickBehavior;

public class Hobbit extends Character {
    public Hobbit() {
        super(10, 10, 0, "src/main/resources/hobbit.png");
    }

    public void kick(Character c) {
        KickBehavior kickBehavior = new CryBehavior();
        kickBehavior.kick(this, c);
    }
}
