package WinIS;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class isBar {
    private int LeRE = 0;
    //16 << Left, 8 << Right, 0 << Exit/Enter.
    public boolean Le = false, Ri = false, En = false;
    //0 - Left Pre/Rel; 1 - Right Pre/Rel; 2 - Enter/Exit;
    protected Timer TI;
    //Timer



    public static int He = 20, Br = 2;
    //He - Толщина полки, Br - Толщина рамок полки.
    public int X = 0, Y = 0;
    //Xp, Yp - Положение курсора на экране.



    public isBar () {
        TI = new Timer(Math.round(1000/IRBIS.fpc), new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                LeRE = (isMa.MOrP(gtLe(), Le, isMa.In(0.75f), isMa.In(0.375f)) << 16)
                        | (isMa.MOrP(gtRi(), Ri, isMa.In(0.75f), isMa.In(0.375f)) << 8)
                        | isMa.MOrP(gtEn(), En, isMa.In(0.75f), isMa.In(0.375f));
                X = isMa.NToN(X, IRBIS.mpX, 4.0f);
                Y = isMa.NToN(Y, IRBIS.mpY, 4.0f);

                boolean Chck = (gtLe() == 0 || gtLe() == 250) && (gtRi() == 0 || gtRi() == 250)
                        && (gtEn() == 0 || (gtEn() == 250 && (IRBIS.mpX == X && IRBIS.mpY == Y)));
                if (Chck)
                    TI.stop();
            }
        });
    }



    //Getter's
    public int gtLe() { return LeRE >> 16 & 0xff; }
    public int gtRi() { return LeRE >> 8 & 0xff; }
    public int gtEn() { return LeRE & 0xff; }
    public static RoundRectangle2D gtRr(int usi) {
        float Lx = IRBIS.wb3();
        float Rx = isWindow.Wi + isWindow.Sh;
        for (isIcon f1 : IRBIS.I) {
            switch (f1.Si) {
                case Left: Lx += (f1.Wi + IRBIS.wbB());  break;
                case Right: Rx -= (f1.Wi + IRBIS.wbB()); break;
            }
        }

        return new RoundRectangle2D.Float(
                (Lx - usi), (IRBIS.wb3() - usi), ((Rx - (Lx + IRBIS.wbB())) + usi*2), (He + usi*2),
                (isWindow.Ro - IRBIS.wbB() + usi*2), (isWindow.Ro - IRBIS.wbB() + usi*2));
    }
    //Multiplication's
    public int muLe(int i) {
        return Math.round(i / 250f * gtLe());
    }
    public int muRi(int i) {
        return Math.round(i / 250f * gtRi());
    }
    public int muEn(int i) {
        return Math.round(i / 250f * gtEn());
    }
    public float muLe(float f) {
        return f / 250f * gtLe();
    }
    public float muRi(float f) {
        return f / 250f * gtRi();
    }
    public float muEn(float f) {
        return f / 250f * gtEn();
    }



    //Setter's
    public void stLe(boolean usi) {
        if (usi) {
            if (!Le)
                Le = true;
            boTM();
        } else if (Le) {
            Le = false;
            boTM();
        }
    }
    public void stRi(boolean usi) {
        if (usi) {
            if (!Ri)
                Ri = true;
            boTM();
        } else if (Ri) {
            Ri = false;
            boTM();
        }

    }
    public void stEn(boolean usi) {
        if (usi) {
            if (!En) {
                En = true;
                X = IRBIS.mpX;
                Y = IRBIS.mpY;
            }
            boTM();
        } else if (En) {
            En = false;
            boTM();
        }
    }



    //Bonus
    private void boTM () {
        if (!IRBIS.TI.isRunning())
            IRBIS.TI.start();
        if (!TI.isRunning())
            TI.start();
    }
}
