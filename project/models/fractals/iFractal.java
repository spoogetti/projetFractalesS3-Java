package project.models.fractals;

import java.awt.image.BufferedImage;

public interface iFractal {
    BufferedImage render(double factor, int offsetX, int offsetY, int iterations);
    int getIterations();
    void setIterations(int iterations);
}
