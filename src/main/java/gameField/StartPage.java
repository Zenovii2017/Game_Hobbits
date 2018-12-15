package gameField;

import org.json.simple.JSONObject;
import saves.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.exit;

public class StartPage extends JFrame {
    private JTextField inputWidth = new JTextField("", 10);
    private JTextField inputHeight = new JTextField("", 10);
    private JTextField inputName = new JTextField("", 10);
    private JTextField inputType = new JTextField("", 10);

    private void addLabel(String sentence, Container container) {
        JLabel label = new JLabel(sentence);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(label);
    }

    private void addButton(String name, Container container, ActionListener actionListener) {
        JButton jButton = new JButton(name);
        jButton.addActionListener(actionListener);
        container.add(jButton);
    }

    public StartPage() {
        super("START");

        JsonReader jsonReader = new JsonReader();
        JSONObject jsonObject = jsonReader.read("src/main/resources/save.txt");

        inputHeight.setText((String) jsonObject.get("height"));
        inputWidth.setText((String) jsonObject.get("width"));
        inputName.setText((String) jsonObject.get("name"));
        inputType.setText((String) jsonObject.get("type"));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        this.setBounds((screenWidth / 2) - 450, (screenHeight / 2) - 75, 900, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 4, 2, 2));
        addLabel("Input width of your screen:", container);
        container.add(inputWidth);
        addLabel("Input height of your screen:", container);
        container.add(inputHeight);
        addLabel("Input name of your charcater:", container);
        container.add(inputName);
        addLabel("Input type of your character:", container);
        container.add(inputType);

        addButton("Exit", container, new ButtonExitEventListener());
        addButton("Check setup", container, new ButtonSetupEventListener());
        addButton("Game info", container, new ButtonInfoEventListener());
        addButton("Let's play", container, new ButtonPlayEventListener());
    }

    static class ButtonExitEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            exit(0);
        }
    }

    class ButtonSetupEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = "" + "You have entered:\n"
                    + "Width is " + inputWidth.getText() + "\n"
                    + "Height is " + inputHeight.getText() + "\n"
                    + "Name is " + inputName.getText() + "\n"
                    + "Type is " + inputType.getText();
            JOptionPane.showMessageDialog(null,
                    message,
                    "Output",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    class ButtonInfoEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = "This is a heroes game. You can play for:\n" +
                    " - Hobbit with power 0 and hp 3, his kick is start crying\n" +
                    " - Elf with power 10 and hp 10, he kill everybody which\n\t\t" +
                    "   is weaker than him, otherwise decrease power\n\t\t" +
                    "   of opponent by 1\n" +
                    " - King with power 5-15 and hp 5-15, decrease number of\n\t\t" +
                    "   hp by random number which will be in range of his power\n\t" +
                    " - Knight with power 2-12 and hp 2-12, like King";

            JOptionPane.showMessageDialog(null,
                    message,
                    "Game Info",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    class ButtonPlayEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int error = -1;
            try {
                if (Integer.valueOf(inputWidth.getText()) <= 500 || Integer.valueOf(inputHeight.getText()) <= 500) {
                    error = 0;
                }
                if (Integer.valueOf(inputWidth.getText()) >= 2000 || Integer.valueOf(inputHeight.getText()) >= 1100) {
                    error = 0;
                }
                if (inputName.getText().equals("")) {
                    error = 1;
                }
                ArrayList<String> types = new ArrayList<>();
                types.add("Hobbit");
                types.add("Elf");
                types.add("King");
                types.add("Knight");
                if (!types.contains(inputType.getText())) {
                    error = 2;
                }
                if (error != -1) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException eror) {
                if (error == -1) {
                    error = 0;
                }
                StringBuilder message = new StringBuilder("You inputted the wrong setup! Try again! Namely:\n");
                switch (error) {
                    case 0:
                        message.append("You input wrong height or width!Minimum is 500x500, maximum 1920x1080!\n");
                        break;
                    case 1:
                        message.append("You input wrong name of your character!\n");
                        break;
                    case 2:
                        message.append("You input wrong type of your character!\n");
                        break;
                }
                JOptionPane.showMessageDialog(null,
                        message,
                        "Error",
                        JOptionPane.PLAIN_MESSAGE);
            }
            if (error == -1) {
                JsonReader jsonReader = new JsonReader();
                JSONObject jsonObject = jsonReader.read("src/main/resources/save.txt");
                setVisible(false);
                jsonReader.write("src/main/resources/save.txt", inputHeight.getText(),
                        inputWidth.getText(), inputType.getText(), inputName.getText());
                GameField app = null;
                try {
                    app = new GameField(Integer.valueOf(inputWidth.getText()), Integer.valueOf(inputHeight.getText()),
                            inputType.getText(), inputName.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                }
                app.setVisible(true);
                app.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }

        }
    }

}
