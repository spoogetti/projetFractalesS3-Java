package project.views.components;
import javax.swing.*;

public class CustomButton {
    protected JButton button;

    public CustomButton(JComponent container, String name, int x, int y, int width, int height) {
        this.button = new JButton(name);
        this.button.setBounds(x, y, width, height);
        container.add(this.button);
    }
}
