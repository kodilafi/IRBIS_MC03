package WinIS;

import java.awt.geom.RoundRectangle2D;

public class isWindow {
    private static int LeRE = 0;
    //16 << Left, 8 << Right, 0 << Exit/Enter.



    public static int Wi, He, Ro = 16, Br = 2, Sh = 2;
    //wW wH - размер окна, wR - округление,
    //wB - толщина рамок, wS - толщина тени.



    //Getter's
    public static int gtLe() { return LeRE >> 16 & 0xff; }
    public static int gtRi() { return LeRE >> 8 & 0xff; }
    public static int gtEn() { return LeRE & 0xff; }
    public static RoundRectangle2D gtRr(int usi) {
        return new RoundRectangle2D.Float((Sh - usi), (Sh - usi), (Wi + usi*2), (He + usi*2),
                (Ro + usi*2), (Ro + usi*2));
    }
    //Multiplication's
    public static int muLe(int i) {
        return Math.round(i / 250f * gtLe());
    }
    public static int muRi(int i) {
        return Math.round(i / 250f * gtRi());
    }
    public static int muEn(int i) {
        return Math.round(i / 250f * gtEn());
    }
    public static float muLe(float f) {
        return f / 250f * gtLe();
    }
    public static float muRi(float f) {
        return f / 250f * gtRi();
    }
    public static float muEn(float f) {
        return f / 250f * gtEn();
    }
}
