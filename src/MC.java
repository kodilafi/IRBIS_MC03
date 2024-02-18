import WinIS.IRBIS;

public class MC {
    public MC() {
        IRBIS IB = new IRBIS("Calculator v1.0");
        IB.addDefa();
    }

    public static void main(String[] args) {
        new MC();
    }
}