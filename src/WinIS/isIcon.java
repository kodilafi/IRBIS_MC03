package WinIS;

import javax.swing.Timer;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class isIcon {
    private int LeRE = 0;
    //16 << Left, 8 << Right, 0 << Exit/Enter.
    public boolean Le = false, Ri = false, En = false;
    //0 - Left Pre/Rel; 1 - Right Pre/Rel; 2 - Enter/Exit;
    protected Timer TI;
    //Timer



    private int aRGB = 0;
    public int Wi;
    public isSide Si;
    public Shape[] Dr;
    public String Na;



    public isIcon (int uWd, isSide uSi, String uNa, Shape[] uSh) {
        Wi = uWd;
        Dr = uSh;
        Na = uNa;
        Si = uSi;
        TI = new Timer(Math.round(1000/IRBIS.fpc), new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                LeRE = (isMa.MOrP(gtLe(), Le, isMa.In(0.75f), isMa.In(0.375f)) << 16)
                        | (isMa.MOrP(gtRi(), Ri, isMa.In(0.75f), isMa.In(0.375f)) << 8)
                        | isMa.MOrP(gtEn(), En, isMa.In(0.75f), isMa.In(0.375f));

                boolean Chck = (gtLe() == 0 || gtLe() == 250)
                        && (gtRi() == 0 || gtRi() == 250)
                        && (gtEn() == 0 || gtEn() == 250);
                if (Chck)
                    TI.stop();
            }
        });
    }
    public void F() { }



    //Getter's
    public int gtLe() { return LeRE >> 16 & 0xff; }
    public int gtRi() { return LeRE >> 8 & 0xff; }
    public int gtEn() { return LeRE & 0xff; }
    public int gtRe() { return aRGB >> 16 & 0xff; }
    public int gtGr() { return aRGB >> 8 & 0xff; }
    public int gtBl() { return aRGB & 0xff; }
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
    public void stCol(int R, int G, int B) {
        aRGB = (R << 16) | (G << 8) | (B);
    }
    public void stLe(boolean usi) {
        if (usi) {
            if (!Le)
                Le = true;
            boTM();
        } else if (Le) {
            F();
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
            if (!En)
                En = true;
            boTM();
        } else if (En) {
            En = false;
            boTM();
        }
    }



    //Bonus
    private void boTM() {
        if (!IRBIS.TI.isRunning())
            IRBIS.TI.start();
        if (!TI.isRunning())
            TI.start();
    }
}
