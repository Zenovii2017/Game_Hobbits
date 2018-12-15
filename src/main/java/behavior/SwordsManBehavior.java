package behavior;

import characters.Character;
import configure.Config;

public class SwordsManBehavior implements KickBehavior {

    @Override
    public void kick(Character c1, Character c2) {
        int damage = Config.generateRandom(0, c1.getPower());
        c2.setReceivedDamage(damage);
        c2.setHp(c2.getHp() - damage);
    }
}
