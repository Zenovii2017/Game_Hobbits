package behavior;

import characters.Character;

public class CryBehavior implements KickBehavior {
    @Override
    public void kick(Character c1, Character c2) {
        System.out.println("Crying!");
    }
}
