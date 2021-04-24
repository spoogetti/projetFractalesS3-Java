package project.views;

import project.models.Model;

public class PositioningConstants {
    public static int M_1 = 1;
    public static int M_2 = 2;
    public static int M_3 = 3;
    public static int M_10 = 10;

    public static int line(double line) {
        return (int) ((Model.screenHeight / 12) * line);
    }

    public static int col(double col) {
        return (int) ((Model.screenWidth / 12) * col);
    }

    public static int BUTTON_WIDTH = col(1);
    public static int BUTTON_HEIGHT = line(0.5);

}
