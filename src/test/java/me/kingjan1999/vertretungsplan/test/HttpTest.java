package me.kingjan1999.vertretungsplan.test;

import me.kingjan1999.vertretungsplan.Vertretungsplan;
import me.kingjan1999.vertretungsplan.net.HttpManager;
import org.apache.commons.beanutils.DynaBean;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by jan on 26.06.15.
 * Test Class for HttpManager
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
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Test for the downloaded testPlan1.html
     **/
    @Test
    public void testPlan1() {
        Vertretungsplan p1 = null;
        try {
            p1 = fetchFromFile("/testPlan1.html");
        } catch (IOException | IllegalAccessException | InstantiationException e) {
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
     */
    @Test
    public void testPlan2() {
        Vertretungsplan p2 = null;
        try {
            p2 = fetchFromFile("/testPlan2.html");
        } catch (IOException | IllegalAccessException | InstantiationException e) {
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

    @Test
    public void testFilter() {
        Vertretungsplan p1 = null;
        try {
            p1 = fetchFromFile("/testPlan1.html");
        } catch (IOException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            fail();
        }

        List<DynaBean> found = p1.getVertretungenForCourse("5a", 0);

        assertEquals(1, found.size());

        found = p1.getVertretungenForCourse("10B");

        assertEquals(4, found.size());

        found = p1.getVertretungenForCourse("10");

        assertEquals(9, found.size());
    }


    private Vertretungsplan fetchFromFile(String fileName) throws IllegalAccessException, IOException, InstantiationException {
        URL url = this.getClass().getResource(fileName);
        HttpManager hp = new HttpManager(new File(url.getFile()));
        return hp.fetchPlan();
    }
}
