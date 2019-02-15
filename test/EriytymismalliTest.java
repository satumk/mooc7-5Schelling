
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class EriytymismalliTest {

    private Eriytymismalli malli;

    @Before
    public void setup() {
        this.malli = new Eriytymismalli(10, 10, 5, 2, 70.0);
    }

    @Test
    @Points("07-05.1")
    public void asetaKaikkiTyhjaksiAsettaaTaulukonArvotNolliksi() {
        asetaArvo(malli, 0, 0, 1);

        malli.tyhjenna();
        if (annaArvo(malli, 0, 0) == 1) {
            fail("Kun kutsuaan metodia asetaKaikkiTyhjiksi, tulee kaikki taulukon arvot asettaa nolliksi.");
        }

        asetaArvo(malli, 7, 5, 1);
        asetaArvo(malli, 5, 7, 1);
        asetaArvo(malli, 1, 2, 1);

        malli.tyhjenna();

        if (annaArvo(malli, 7, 5) == 1) {
            fail("Kun kutsuaan metodia asetaKaikkiTyhjiksi, tulee kaikki taulukon arvot asettaa nolliksi.");
        }
        if (annaArvo(malli, 5, 7) == 1) {
            fail("Kun kutsuaan metodia asetaKaikkiTyhjiksi, tulee kaikki taulukon arvot asettaa nolliksi.");
        }
        if (annaArvo(malli, 1, 2) == 1) {
            fail("Kun kutsuaan metodia asetaKaikkiTyhjiksi, tulee kaikki taulukon arvot asettaa nolliksi.");
        }
    }

    @Test
    @Points("07-05.1")
    public void tyhjatPaikatPalauttaaKaikkiArvotJosAsetettuTyhjaksi() {
        asetaArvo(malli, 0, 0, 1);

        malli.tyhjenna();

        assertTrue("Kun luodaan malli, jonka leveys on 10 ja korkeus on 10, ja kaikki kohdat alustetaan tyhjäksi, tulee metodin tyhjatPaikat palauttaa 100 tyhjää paikkaa. Nyt tyhjiä paikkoja palautettiin: " + malli.tyhjatPaikat().size(), malli.tyhjatPaikat().size() == 100);

        asetaArvo(malli, 0, 0, 1);

        assertTrue("Kun luodaan malli, jonka leveys on 10 ja korkeus on 10, ja jossa yksi paikka ei ole tyhjä, tulee metodin tyhjatPaikat palauttaa 99 tyhjää paikkaa. Nyt tyhjiä paikkoja palautettiin: " + malli.tyhjatPaikat().size(), malli.tyhjatPaikat().size() == 99);

        for (Piste tyhja : malli.tyhjatPaikat()) {
            assertFalse("Kun mallin kohta 0, 0 on asetettu ei-tyhjäksi, ei se saa olla tyhjien paikkojen listalla.", tyhja.getX() == 0 && tyhja.getY() == 0);
        }
    }

    @Test
    @Points("07-05.1")
    public void satunnaisetPaikatJaTyhjat() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            ArrayList<Piste> pisteet = new ArrayList<>();

            for (int j = 0; j < 5 + random.nextInt(); j++) {
                Piste p = new Piste(random.nextInt(10), random.nextInt(10));

                if (pisteet.contains(p)) {
                    continue;
                }

                pisteet.add(p);
            }

            pisteet.stream().forEach(p -> {
                asetaArvo(malli, p.getX(), p.getY(), 1);
            });

            malli.tyhjenna();

            assertTrue("Kun luodaan malli, jonka leveys on 10 ja korkeus on 10, ja kaikki kohdat alustetaan tyhjäksi, tulee metodin tyhjatPaikat palauttaa 100 tyhjää paikkaa. Nyt tyhjiä paikkoja palautettiin: " + malli.tyhjatPaikat().size(), malli.tyhjatPaikat().size() == 100);

            pisteet.stream().forEach(p -> {
                asetaArvo(malli, p.getX(), p.getY(), 1);
            });

            assertTrue("Kun luodaan malli, jonka leveys on 10 ja korkeus on 10, ja jossa on " + pisteet.size() + " ei tyhjää paikkaa, tulee metodin tyhjatPaikat palauttaa " + (100 - pisteet.size()) + " tyhjää paikkaa. Nyt tyhjiä paikkoja palautettiin: " + malli.tyhjatPaikat().size(), malli.tyhjatPaikat().size() == 100 - pisteet.size());

            pisteet.stream().forEach(p -> {
                assertFalse("Kun mallin kohta " + p.getX() + ", " + p.getY() + " on asetettu ei-tyhjäksi, ei se saa olla tyhjien paikkojen listalla.", malli.tyhjatPaikat().contains(p));
            });
        }
    }

    @Test
    @Points("07-05.2")
    public void haeTyytymattomat() {
        malli.tyhjenna();

        malli.asetaTyytyvaisyysRaja(5);

        asetaArvo(malli, 0, 0, 1);
        asetaArvo(malli, 1, 0, 1);
        asetaArvo(malli, 0, 1, 1);
        asetaArvo(malli, 1, 1, 1);

        asetaArvo(malli, 1, 2, 2);
        asetaArvo(malli, 2, 1, 2);
        asetaArvo(malli, 2, 2, 2);
        asetaArvo(malli, 3, 2, 2);
        asetaArvo(malli, 2, 3, 2);
        asetaArvo(malli, 3, 3, 2);
        asetaArvo(malli, 2, 0, 2);
        asetaArvo(malli, 0, 2, 2);

        assertTrue("Jos tyytyväisyysraja on 5 ja pisteellä on kolme samantyyppisiä naapuria, sen pitäisi olla tyytymätön.\n" + raportti(0, 0), malli.haeTyytymattomat().contains(new Piste(0, 0)));
        assertTrue("Jos tyytyväisyysraja on 5 ja henkilöllä on kolme samantyyppistä naapuria, pitäisi henkilön olla tyytymätön.\n" + raportti(1, 1), malli.haeTyytymattomat().contains(new Piste(1, 1)));
        assertFalse("Jos tyytyväisyysraja on 5 ja henkilöllä on viisi samantyyppistä naapuria, ei pisteen pitäisi olla tyytymätön.\n" + raportti(2, 2), malli.haeTyytymattomat().contains(new Piste(2, 2)));
    }

    @Test
    @Points("07-05.2")
    public void haeTyytymattomat2() {
        malli.tyhjenna();

        malli.asetaTyytyvaisyysRaja(3);

        asetaArvo(malli, 0, 0, 1);
        asetaArvo(malli, 1, 0, 1);
        asetaArvo(malli, 0, 1, 1);
        asetaArvo(malli, 1, 1, 1);
        asetaArvo(malli, 2, 0, 1);

        asetaArvo(malli, 1, 2, 2);
        asetaArvo(malli, 2, 1, 2);
        asetaArvo(malli, 2, 2, 2);
        asetaArvo(malli, 3, 2, 2);
        asetaArvo(malli, 2, 3, 2);
        asetaArvo(malli, 3, 3, 2);

        assertFalse("Jos tyytyväisyysraja on 3 ja henkilöllä on kolme samantyyppisiä naapuria, ei sen pitäisi olla tyytymätön.\n" + raportti(0, 0), malli.haeTyytymattomat().contains(new Piste(0, 0)));
        assertTrue("Jos tyytyväisyysraja on 3, ja henkilöllä on kaksi samantyyppistä naapuria, pitäisi henkilön olla tyytymätön. Älä laske itseä mukaan naapureihin.\n" + raportti(2, 0), malli.haeTyytymattomat().contains(new Piste(2, 0)));
        assertFalse("Jos tyytyväisyysraja on 3, ja henkilöllä on viisi samantyyppistä naapuria, ei pisteen pitäisi olla tyytymätön.\n" + raportti(2, 2), malli.haeTyytymattomat().contains(new Piste(2, 2)));
    }

    private String raportti(int pisteX, int pisteY) {

        String tulostus = "Haettiin kohtaa " + pisteX + ", " + pisteY + " taulukosta:\n";
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                tulostus += annaArvo(malli, x, y) + " ";
            }
            tulostus += "\n";
        }

        tulostus += "(y-koordinaatin lasku alkaa vasemmasta yläkulmasta alaspäin)\n";
        return tulostus;

    }

    private void asetaArvo(Eriytymismalli malli, int x, int y, int arvo) {
        if (malli.annaData() == null) {
            return;
        }

        malli.annaData().aseta(x, y, arvo);
    }

    private int annaArvo(Eriytymismalli malli, int x, int y) {
        if (malli.annaData() == null) {
            fail("Ennustemalli-luokan metodi annaData palautti null-viitteen. Näin ei tule käydä.");
        }

        return malli.annaData().hae(x, y);
    }
}
