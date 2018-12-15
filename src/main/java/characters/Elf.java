package characters;

import behavior.ElfBehavior;
import behavior.KickBehavior;

public class Elf extends Character {
    public Elf() {
        super(15, 15, 0, "src/main/resources/elf.png");
    }

    public void kick(Character c) {
        KickBehavior kickBehavior = new ElfBehavior();
        kickBehavior.kick(this, c);
    }
}
