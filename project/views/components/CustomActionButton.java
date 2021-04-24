package project.views.components;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CustomActionButton extends CustomButton {
    public CustomActionButton(JComponent container, String name, ActionListener action, int x, int y, int width, int height) {
        super(container, name, x, y, width, height);
        this.button.addActionListener(action);
    }
}
