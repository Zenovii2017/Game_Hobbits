package buttonEventListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.System.exit;

public class ButtonExitEventListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        exit(0);
    }
}
