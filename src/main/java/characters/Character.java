package characters;

import lombok.*;

@AllArgsConstructor
public class Character {
    @Setter
    @Getter
    private int power;
    @Getter
    private int hp;
    @Setter
    @Getter
    private int receivedDamage;
    @Getter
    private String image;

    public void setHp(int hp) {
        if (hp < 0) {
            this.hp = 0;
        }
        this.hp = hp;
    }

    public void kick(Character character) {
    }

    public boolean isAlive() {
        return hp > 0;
    }
}
