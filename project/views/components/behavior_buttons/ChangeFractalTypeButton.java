package project.views.components.behavior_buttons;

import project.models.fractals.ModelledFractal;
import project.views.Vue;
import project.views.components.CustomActionButton;

import javax.swing.*;

public class ChangeFractalTypeButton extends CustomActionButton {
    public ChangeFractalTypeButton(Vue context, JComponent container, String name, int x, int y, int width, int height, ModelledFractal model) {
        super(container, name,
                arg0 -> {
                    context.controller.instanciateFractal(model);
                    context.controller.refresh();
                    context.setIterations.setText(Integer.toString(context.controller.getIterations()));
                }, x, y, width, height);
    }
}
