package projetFractales;

import java.awt.image.BufferedImage;

public interface iFractale {
    BufferedImage modelisation(double facteur, int offsetX, int offsetY, int iterations);
    int getIterations();
    void setIterations(int iterations);
}
