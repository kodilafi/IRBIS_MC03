package WinIS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class IRBIS extends Frame {
    private static Graphics2D G2D;
    protected static Timer TI;
    protected static float fpc = 45;
    protected static isIcon[] I = new isIcon[0];
    protected static isBar B = new isBar();
    private final String S;
    protected static int wbB () {
        return isWindow.Br + B.Br;
    }
    //скрещение толщины рамок окна и полки.
    protected static int wb3 () {
        return wbB() + isWindow.Sh;
    }
    //скрещение толщины рамок окна, полки и тени.
    protected static int wbR () {
        return isWindow.Ro - wbB();
    }
    protected static int mpX, mpY;
    //Положение курсора на экране (фактическое).
    private int Check = 0;
    public IRBIS (String title) {
        Dimension D = Toolkit.getDefaultToolkit().getScreenSize();
        isWindow.Wi = D.width/2;
        isWindow.He = D.height/2;
        S = title;

        TI = new Timer(Math.round(1000/fpc), AL);

        setUndecorated(true);
        setName(title);
        setBounds(isWindow.Wi/2 - isWindow.Sh, isWindow.He/2 - isWindow.Sh, isWindow.Wi + isWindow.Sh *2, isWindow.He + isWindow.Sh *2);
        setVisible(true);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked (MouseEvent e) {

            }
            @Override
            public void mousePressed (MouseEvent e) {
                int Lxp = wb3();
                int Rxp = isWindow.Wi + isWindow.Sh;

                for (isIcon I : I) {
                    if (I.Si == isSide.Right) Rxp -= (I.Wi + wbB());

                    if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                        RoundRectangle2D RR = new RoundRectangle2D.Float(
                                (I.Si == isSide.Left) ? Lxp : Rxp, wb3(), I.Wi, B.He, wbR(), wbR());
                        I.stLe(RR.contains(e.getX(), e.getY()));
                    }

                    if (I.Si == isSide.Left) Lxp += (I.Wi + wbB());
                }

                RoundRectangle2D Rb = new RoundRectangle2D.Float(Lxp, wb3(), Rxp - (Lxp + wbB()), B.He, wbR(), wbR());
                if (Rb.contains(e.getX(), e.getY())) {
                    mpX = e.getX();
                    mpY = e.getY();
                    B.stLe(Rb.contains(e.getX(), e.getY()));
                }
            }
            @Override
            public void mouseReleased (MouseEvent e) {
                for (isIcon I : I) if (I.Le)
                    I.stLe(false);
                B.stLe(false);
            }
            @Override
            public void mouseEntered (MouseEvent e) {

            }
            @Override
            public void mouseExited (MouseEvent e) {
                for (isIcon I : I)
                    I.stEn(false);
                B.stEn(false);
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged (MouseEvent e) {
                if (B.Le)
                    setLocation(getX() - (mpX - e.getX()), getY() - (mpY - e.getY()));
                else {
                    mpX = e.getX();
                    mpY = e.getY();
                }
            }
            @Override
            public void mouseMoved (MouseEvent e) {
                mpX = e.getX();
                mpY = e.getY();

                int Lxp = wb3();
                int Rxp = isWindow.Wi + isWindow.Sh;

                for (isIcon I : I) {
                    Rxp -= (I.Si == isSide.Right) ? (I.Wi + wbB()) : 0;

                    RoundRectangle2D Ri = new RoundRectangle2D.Float(
                            (I.Si == isSide.Left) ? Lxp : Rxp, wb3(), I.Wi, B.He, wbR(), wbR());

                    Lxp += (I.Si == isSide.Left) ? (I.Wi + wbB()) : 0;

                    I.stEn(Ri.contains(e.getX(), e.getY()));
                }

                RoundRectangle2D Rb = new RoundRectangle2D.Float(Lxp, wb3(), Rxp - (Lxp + wbB()), B.He, wbR(), wbR());
                B.stEn(Rb.contains(e.getX(), e.getY()));

                /*if (mpX) {

                }*/
            }
        });

        TI.start();
    }

    //Рисование окна
    public void paint(Graphics G) {
        G2D = (Graphics2D) G;

        G2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        G2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        G2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

        RoundRectangle2D R3 = new RoundRectangle2D.Float(
                (isWindow.Br + isWindow.Sh), (wbB()*2 + B.He + isWindow.Sh),
                (isWindow.Wi - isWindow.Br*2), (isWindow.He - (wbB()*2+ B.He + isWindow.Br)),
                (isWindow.Ro - isWindow.Br), (isWindow.Ro - isWindow.Br));
        Point2D ST = new Point2D.Float(mpX, mpY);

        //Заливка основы
        G2D.setColor(new Color(15, 15, 15, 75));
        G2D.fill(isWindow.gtRr(2));
        G2D.setColor(new Color(90, 90, 90));
        G2D.fill(isWindow.gtRr(0));
        G2D.setColor(new Color(15, 15, 15));
        G2D.fill(R3);

        //Рисовка иконок
        int Lxp = wb3();
        int Rxp = isWindow.Wi + isWindow.Sh;

        for (isIcon B : I) {
            //B.stAn();
            if (B.Si == isSide.Right)
                Rxp -= (B.Wi + wbB());
            drawIcon(B, (B.Si == isSide.Left) ? Lxp : Rxp);
            if (B.Si == isSide.Left)
                Lxp += (B.Wi + wbB());
        }
        //Пустая часть строки
        if (B.gtEn() > 0) {
            Point2D cnt = new Point2D.Float(B.X, B.Y);
            float rad = 75f + B.muEn(75) + Math.min(50, (int) (Math.abs(mpX - B.X) / 1.5));
            float[] di1 = {0.75f, 0.875f, 1};
            float[] di2 = {0.25f, 0.750f, 1};

            int gr = 105 + B.muEn(30);
            Color clr_Br1 = new Color(gr, gr, gr);
            Color clr_Br2 = new Color(105, 105, 105);
            Color[] cl1 = {clr_Br1, clr_Br2, clr_Br2};
            Color[] cl2 = {clr_Br1, clr_Br2, clr_Br2};

            G2D.setPaint(new RadialGradientPaint(cnt, rad, di1, cl1));
            G2D.fill(B.gtRr(0));
            G2D.setPaint(new RadialGradientPaint(cnt, rad, di2, cl2));
            G2D.fill(B.gtRr(-1));
        } else {
            G2D.setColor(new Color(105, 105, 105));
            G2D.fill(B.gtRr(0));
        }

        G2D.setColor(new Color(0, 0, 0));
        if (Math.abs(Check) > 0) {
            G2D.setColor(new Color(250, 0, 0, Math.abs(Check)));
            G2D.fillOval(Lxp + 4, wb3() + 4, 12, 12);
        }
    }

    ActionListener AL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setBackground(new Color(0, true));

            Check = (Check > 200) ? -250 : Check + 50;

            boolean Ch2 = true;
            for (isIcon fI : I)
                if (fI.TI.isRunning())
                    Ch2 = false;
            if (B.TI.isRunning())
                Ch2 = false;

            if (Ch2) {
                TI.stop();
            }
        }
    };
    //Процесс создания иконки.
    public void addIcon(isIcon icon) {
        isIcon[] NiA = new isIcon[I.length + 1];
        System.arraycopy(I, 0, NiA, 0, I.length);
        NiA[I.length] = icon;
        I = NiA;
    }
    public void addIcon(isIcon[] icons) {
        isIcon[] NiA = new isIcon[I.length + icons.length];
        System.arraycopy(I, 0, NiA, 0, I.length);
        if (NiA.length - I.length >= 0) System.arraycopy(icons, I.length, NiA, I.length, NiA.length - I.length);
        I = NiA;
    }
    private void drawIcon(isIcon fI, float fX) {
        int cR = fI.gtRe();
        int cG = fI.gtGr();
        int cB = fI.gtBl();
        int D = (int) ((cR+cG+cB)/8f);
        int U = (int) ((cR+cG+cB)/8f);
        int frR = (isWindow.Ro - (isWindow.Br + B.Br));

        int Dsh1 = (int) (D / 150f * (150 - Math.min(fI.gtLe(), 150)));
        Color ClR_Sh1 = new Color(Math.max(cR - Dsh1, 0), Math.max(cG - Dsh1, 0), Math.max(cB - Dsh1, 0));
        int Dsh2 = (int) (D / 150f * Math.max(fI.gtLe() - 100, 0));
        Color ClR_Sh2 = new Color(Math.max(cR - Dsh2, 0), Math.max(cG - Dsh2, 0), Math.max(cB - Dsh2, 0));
        int U2 = fI.muEn(U);
        Color ClR_2 = new Color(Math.min(cR + U2, 255), Math.min(cG + U2, 255), Math.min(cB + U2, 255));

        RoundRectangle2D RrG_1 = new RoundRectangle2D.Float(fX, wb3(), fI.Wi, B.He, frR, frR);

        if (fI.gtEn() > 0) {
            float frW = fI.Wi + fI.muEn(4);
            float frH = B.He + fI.muEn(4);
            float frX = fX + fI.Wi/2f - frW/2f;
            float frY = wb3() + B.He /2f - frH/2f;
            RoundRectangle2D RrG_2 = new RoundRectangle2D.Float(frX, frY, frW, frH, frR, frR);

            G2D.setColor(new Color(cR, cG, cB, 150 - fI.muEn(75)));
            G2D.fill(RrG_2);
        }

        G2D.setColor(ClR_2);
        G2D.fill(RrG_1);
        G2D.setClip(RrG_1);
        G2D.translate((fX + fI.Wi/2f), (wb3() + B.He/2f));
        for (Shape Sh : fI.Dr) {
            drawStR(5);
            G2D.setColor(ClR_2);
            G2D.draw(Sh);
            drawStR(1);
            G2D.setColor(ClR_Sh1);
            G2D.draw(Sh);
        }
        G2D.translate(-(fX + fI.Wi/2f), -(wb3() + B.He/2f));
        G2D.setClip(isWindow.gtRr(0));

        if (fI.gtLe() > 0) {
            RoundRectangle2D RrG_3;
            if (fI.gtLe() < 250) {
                float rW = fI.muLe(fI.Wi);
                float rH = fI.muLe(B.He);
                float rX = fX + Math.max(0, Math.min(mpX - fX, fI.Wi)) / 250f * (250-fI.gtLe());
                float rY = wb3() + Math.max(0, Math.min(mpY - wbB(), B.He)) / 250f * (250 - fI.gtLe());
                RrG_3 = new RoundRectangle2D.Float((rX + 1), (rY + 1), (rW - 2), (rH - 2), (frR - 2), (frR - 2));
            } else {
                RrG_3 = new RoundRectangle2D.Float((fX + 1), (wb3() + 1), (fI.Wi - 2), (B.He - 2), (frR - 2), (frR - 2));
            }
            G2D.setColor(ClR_Sh2);
            G2D.fill(RrG_3);
            G2D.setClip(RrG_3);
            G2D.translate((fX + fI.Wi/2f), (wb3() + B.He/2f));
            for (Shape Sh : fI.Dr) {
                drawStR(5);
                G2D.setColor(ClR_Sh2);
                G2D.draw(Sh);
                drawStR(1);
                G2D.setColor(ClR_2);
                G2D.draw(Sh);
            }
            G2D.translate(-(fX + fI.Wi/2f), -(wb3() + B.He/2f));
            G2D.setClip(isWindow.gtRr(0));
        }
    }
    public void drawStR(int usi) {
        G2D.setStroke(new BasicStroke(usi, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }
    public void addDefa() {
        //Шейпы
        Polygon Ic11 = new Polygon();
        Ic11.addPoint(-5, -5);
        Ic11.addPoint(0, 0);
        Ic11.addPoint(0, 5);
        Ic11.addPoint(-5, 5);

        Polygon Ic12 = new Polygon();
        Ic12.addPoint(5, -5);
        Ic12.addPoint(0, 0);
        Ic12.addPoint(0, 5);
        Ic12.addPoint(5, 5);

        Shape[] Ic1 = {Ic11, Ic12};

        Line2D Ic21 = new Line2D.Float(-5, -5, 5, 5);
        Line2D Ic22 = new Line2D.Float(-5, 5, 5, -5);

        Shape[] Ic2 = {Ic21, Ic22};

        Rectangle2D Ic31 = new Rectangle2D.Float(-5, -5, 10, 10);
        Rectangle2D Ic32 = new Rectangle2D.Float(-5, -5, 5, 5);

        Shape[] Ic3 = {Ic31, Ic32};

        Rectangle2D Ic41 = new Rectangle2D.Float(-5, -5, 10, 10);
        Line2D Ic42 = new Line2D.Float(-5, 5, 5, 5);

        Shape[] Ic4 = {Ic41, Ic42};

        //Программные иконки
        isIcon Icon = new isIcon(B.He, isSide.Left, "Icon", Ic1);
        Icon.stCol(240, 150, 60);

        isIcon Clos = new isIcon(B.He *2, isSide.Right, "Exit", Ic2) {
            @Override
            public void F () {
                System.exit(0);
            }
        };
        Clos.stCol(210, 120, 120);

        isIcon Full = new isIcon(B.He, isSide.Right, "Full", Ic3);
        Full.stCol(150, 150, 150);

        isIcon Pane = new isIcon(B.He, isSide.Right, "Pane", Ic4);
        Pane.stCol(150, 150, 150);

        addIcon(new isIcon[]{Icon, Clos, Full, Pane});
    }
}