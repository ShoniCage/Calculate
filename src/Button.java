import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Button extends JButton {
    public Button (String name, Container container, ActionListener listener, int r, int g, int b) {
        JButton button = new JButton(name);
        button.setBackground(new Color(r, g, b));
        Font font = new Font("Mono821BT", Font.BOLD, 30);
        button.setFont(font);
        button.setForeground(new Color(211,236, 243));
        container.add(button);
        button.addActionListener(listener);
    }
}
