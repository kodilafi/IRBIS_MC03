package WinIS;

public class isMa {
    protected static int MOrP(int usi, boolean a2, float mns, float pls) {
        float abm = 0 - usi / mns;
        float abp = (250 - usi) / pls;
        return (int) (a2 ? Math.ceil(Math.min(usi + abp, 250))
                : Math.floor(Math.max(usi + abm, 0)));
    }

    protected static float In(float usi) {
        return usi * (IRBIS.fpc/10);
    }
    protected static int NToN(int usi, int ust, float pls) {
        if (usi == ust) {
            return usi;
        } else {
            if (usi < ust) {
                return (int)  Math.ceil(usi + (ust - usi) / pls);
            } else {
                return (int) Math.floor(usi + (ust - usi) / pls);
            }
        }
    }
}