package gameField;

import buttonEventListener.ButtonExitEventListener;
import characters.*;
import characters.Character;
import factory.CharacterFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameField extends JFrame {
    private static Character opponent;
    private static Character my_character;
    private static ArrayList parameters = new ArrayList();

    private void addLabel(String sentence,
                          GridBagLayout gridbag,
                          GridBagConstraints c) {
        JLabel label = new JLabel(sentence);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.parameters.add(label);
        gridbag.setConstraints(label, c);
        add(label);
    }

    private void addImage(Image image,
                          GridBagLayout gridbag,
                          GridBagConstraints c) throws IOException {
        JLabel picLabel = new JLabel(new ImageIcon(image));
        picLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.parameters.add(picLabel);
        gridbag.setConstraints(picLabel, c);
        add(picLabel);
    }

    private void addButton(String name,
                           GridBagLayout gridbag,
                           GridBagConstraints c, ActionListener actionListener) {
        JButton button = new JButton(name);
        button.addActionListener(actionListener);
        this.parameters.add(button);
        gridbag.setConstraints(button, c);
        add(button);
    }

    private void changeLabel(int index, String text) {
        JLabel jLabel = (JLabel) this.parameters.get(index);
        jLabel.setText(text);
    }

    private void changeImage(int index, String path) throws IOException {
        JLabel jLabel = (JLabel) this.parameters.get(index);
        BufferedImage myImage = ImageIO.read(new File(this.opponent.getImage()));
        ImageIcon icon = new ImageIcon(myImage.getScaledInstance(myImage.getWidth() / 3,
                myImage.getHeight() / 3, Image.SCALE_DEFAULT));
        jLabel.setIcon(icon);
    }

    public void refresh() {
        changeLabel(9, "Hp: " + String.valueOf(this.my_character.getHp()));
        changeLabel(11, "Hp: " + String.valueOf(this.opponent.getHp()));
        changeLabel(12, "Power: " + String.valueOf(this.my_character.getPower()));
        changeLabel(14, "Power: " + String.valueOf(this.opponent.getPower()));
        changeLabel(15, "Received damage: " + String.valueOf(this.my_character.getReceivedDamage()));
        changeLabel(17, "Received damage: " + String.valueOf(this.opponent.getReceivedDamage()));
    }

    private void changeOpponent(Character opponent) throws IOException {
        this.opponent = opponent;
        changeLabel(2, "Unknown " + opponent.getClass().getSimpleName());
        changeLabel(5, opponent.getClass().getSimpleName());
        changeImage(8, opponent.getImage());
        changeLabel(11, "Hp: " + String.valueOf(opponent.getHp()));
        changeLabel(14, "Power: " + String.valueOf(opponent.getPower()));
        changeLabel(17, "Received damage: " + String.valueOf(opponent.getReceivedDamage()));
    }

    public GameField(int width, int height, String type, String name) throws IOException,
            InstantiationException, IllegalAccessException {
        super("Game");

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        setFont(new Font("SansSerif", Font.PLAIN, 16));
        setLayout(gridbag);

        CharacterFactory characterFactory = new CharacterFactory();
        switch (type) {
            case "Elf":
                this.my_character = new Elf();
                break;
            case "Hobbit":
                this.my_character = new Hobbit();
                break;
            case "King":
                this.my_character = new King();
                break;
            case "Knight":
                this.my_character = new Knight();
                break;
        }
        this.opponent = characterFactory.createCharacter();

        c.fill = GridBagConstraints.BOTH;

        c.weighty = 2.0;
        c.weightx = 4.0;
        addLabel(name, gridbag, c);
        c.weightx = 2.0;
        addLabel("", gridbag, c);
        c.weightx = 4.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        addLabel("Unknown " + this.opponent.getClass().getSimpleName(), gridbag, c);

        c.gridwidth = GridBagConstraints.BOTH;
        addLabel(type, gridbag, c);
        c.weightx = 2.0;
        addLabel("", gridbag, c);
        c.weightx = 4.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        addLabel(this.opponent.getClass().getSimpleName(), gridbag, c);

        c.gridwidth = GridBagConstraints.BOTH;
        c.weighty = 5.0;
        c.weightx = 4.0;
        BufferedImage myImage = ImageIO.read(new File(this.my_character.getImage()));
        addImage(myImage.getScaledInstance(myImage.getWidth() / 3,
                myImage.getHeight() / 3, Image.SCALE_DEFAULT), gridbag, c);
        c.weighty = 2.0;
        addLabel("VS", gridbag, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        BufferedImage opponentImage = ImageIO.read(new File(this.opponent.getImage()));
        addImage(opponentImage.getScaledInstance(opponentImage.getWidth() / 3,
                opponentImage.getHeight() / 3, Image.SCALE_DEFAULT), gridbag, c);

        c.gridwidth = GridBagConstraints.BOTH;
        addLabel("Hp: " + this.my_character.getHp(), gridbag, c);
        addLabel("", gridbag, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        addLabel("Hp: " + this.opponent.getHp(), gridbag, c);

        c.gridwidth = GridBagConstraints.BOTH;
        addLabel("Power: " + this.my_character.getPower(), gridbag, c);
        addLabel("", gridbag, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        addLabel("Power: " + this.opponent.getPower(), gridbag, c);

        c.gridwidth = GridBagConstraints.BOTH;
        addLabel("Received damage: " + this.my_character.getReceivedDamage(), gridbag, c);
        addLabel("", gridbag, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        addLabel("Received damage: " + this.opponent.getReceivedDamage(), gridbag, c);

        c.gridwidth = GridBagConstraints.BOTH;
        addButton("Exit", gridbag, c, new ButtonExitEventListener());
        addButton("Fight", gridbag, c, new ButtonFightEventListener());
        addButton("Search next opponent", gridbag, c, new ButtonSearchNextOpponentEventListener());

        setSize(width, height);
    }

    class ButtonFightEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GameField.my_character.kick(GameField.opponent);
            if (GameField.my_character.getClass() == Hobbit.class || GameField.opponent.getClass() == Hobbit.class) {
                printMessage("Someone crying!!!", "...");
            }
            if (GameField.opponent.isAlive())
                GameField.opponent.kick(GameField.my_character);
            else {
                printMessage("You win!", "My greetings!");
                JButton jButton = (JButton) GameField.parameters.get(19);
                jButton.setEnabled(false);
            }
            if (!GameField.my_character.isAlive()) {
                printMessage("You lose!", "There is nothing to say!");
                JButton jButton = (JButton) GameField.parameters.get(19);
                jButton.setEnabled(false);
                jButton = (JButton) GameField.parameters.get(20);
                jButton.setEnabled(false);
            }
            String message = "You have inflicted " + GameField.opponent.getReceivedDamage()
                    + " damage!\n Your opponent have inflicted " + GameField.my_character.getReceivedDamage() +
                    " damage";
            printMessage(message, "Normal!");
            refresh();
        }
    }

    public void printMessage(String text, String title) {
        JOptionPane.showMessageDialog(null,
                text,
                title,
                JOptionPane.PLAIN_MESSAGE);
    }

    class ButtonSearchNextOpponentEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CharacterFactory characterFactory = new CharacterFactory();
            try {
                Character opponent = characterFactory.createCharacter();
                JButton jButton = (JButton) GameField.parameters.get(19);
                jButton.setEnabled(true);
                changeOpponent(opponent);
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
