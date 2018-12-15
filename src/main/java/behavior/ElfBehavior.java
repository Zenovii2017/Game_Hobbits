package behavior;

import characters.Character;

public class ElfBehavior implements KickBehavior {
    @Override
    public void kick(Character c1, Character c2) {
        if (c1.getPower() > c2.getPower()) {
            c2.setReceivedDamage(c2.getHp());
            c2.setHp(0);
        } else {
            c2.setPower(c2.getPower() - 1);
        }
    }
}
