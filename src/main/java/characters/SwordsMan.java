package characters;

import behavior.KickBehavior;
import behavior.SwordsManBehavior;
import configure.Config;

public class SwordsMan extends Character {
    public SwordsMan(int min, int max, String image) {
        super(Config.generateRandom(min, max), Config.generateRandom(min, max), 0, image);
    }

    public void kick(Character c) {
        KickBehavior kickBehavior = new SwordsManBehavior();
        kickBehavior.kick(this, c);
    }
}
