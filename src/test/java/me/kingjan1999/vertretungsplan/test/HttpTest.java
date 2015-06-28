package me.kingjan1999.vertretungsplan.test;

import me.kingjan1999.vertretungsplan.Vertretungsplan;
import me.kingjan1999.vertretungsplan.net.HttpManager;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by jan on 26.06.15.
 * Test Class for HttpManager
 *
 */
public class HttpTest {

    /**
     * Some URLS
     */

    private String heute_url = "http://ohgspringe.de/phocadownload/plan/subst_001.htm";
    private String morgen_url = "http://ohgspringe.de/phocadownload/plan/subst_002.htm";

    private final String anotherUrl = "http://vertretungsplan.gymnasium-eversten.de/subst_002.htm";

    /**
     * Test download of the Vertretungsplan
     */
    @Test
    public void testDownload() {
        //HttpManager heute = new HttpManager(heute_url);
        try {
            HttpManager heute = new HttpManager(anotherUrl);
            heute.fetchPlan();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Test for the downloaded testPlan1.html
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void testPlan1() throws IOException, URISyntaxException {
        URL url = this.getClass().getResource("/testPlan1.html");
        HttpManager hp1;
        Vertretungsplan p1 = null;
        try {
            hp1 = new HttpManager(new File(url.getFile()));
            p1 = hp1.fetchPlan();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        //Test Meta
        assertEquals("Untis 2015", p1.getMeta().getUntis_ver());
        assertEquals("28.06.2015 12:11", p1.getMeta().getLastUpdated());
        assertEquals("30.6.2015", p1.getMeta().getTag());

        //Test Infos
        assertEquals(4, p1.getInfos().length);

        //Test some Vertretungen
        assertEquals("3", p1.getVertretungen().get(2).get("Stunde"));
        assertEquals("R01", p1.getVertretungen().get(8).get("Raum"));
        assertEquals("De", p1.getVertretungen().get(15).get("(Fach)"));
    }

    /**
     * Test for the downloaded testPlan2.html
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void testPlan2() throws IOException, URISyntaxException {
        URL url = this.getClass().getResource("/testPlan2.html");
        HttpManager hp2;
        Vertretungsplan p2 = null;
        try {
            hp2 = new HttpManager(new File(url.getFile()));
            p2 = hp2.fetchPlan();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        //Test Meta
        assertEquals("Untis 2016", p2.getMeta().getUntis_ver());
        assertEquals("26.06.2015 16:22", p2.getMeta().getLastUpdated());
        assertEquals("29.6.2015", p2.getMeta().getTag());
        assertEquals(1, p2.getMeta().getCurrent());
        assertEquals(2, p2.getMeta().getMax());

        //Test Infos
        assertEquals(1, p2.getInfos().length);

        //Test some Vertretungen
        assertEquals("2", p2.getVertretungen().get(2).get("Stunde"));
        assertEquals("---", p2.getVertretungen().get(8).get("Raum"));
        assertEquals("WN", p2.getVertretungen().get(15).get("(Fach)"));
    }
}
