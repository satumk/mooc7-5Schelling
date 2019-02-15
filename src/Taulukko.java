
import java.util.HashMap;

public class Taulukko {

    private HashMap<Integer, HashMap<Integer, Integer>> taulukko;
    private int leveys;
    private int korkeus;

    public Taulukko(int leveys, int korkeus) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        this.taulukko = new HashMap<>();
    }

    public int getLeveys() {
        return leveys;
    }

    public int getKorkeus() {
        return korkeus;
    }

    public void aseta(int x, int y, int arvo) {
        if (x < 0 || x >= this.leveys || y < 0 || y >= this.korkeus) {
            return;
        }

        this.taulukko.putIfAbsent(x, new HashMap<>());
        this.taulukko.get(x).put(y, arvo);
    }

    public int hae(int x, int y) {
        if (!this.taulukko.containsKey(x)) {
            return 0;
        }

        if (!this.taulukko.get(x).containsKey(y)) {
            return 0;
        }

        return this.taulukko.get(x).get(y);
    }

}
