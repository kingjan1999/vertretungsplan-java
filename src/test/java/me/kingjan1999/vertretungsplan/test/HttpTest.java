package me.kingjan1999.vertretungsplan.test;

import me.kingjan1999.vertretungsplan.net.HttpManager;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

/**
 * Created by jan on 26.06.15.
 */
public class HttpTest {

    private String heute_url = "http://ohgspringe.de/phocadownload/plan/subst_001.htm";
    private String morgen_url = "http://ohgspringe.de/phocadownload/plan/subst_002.htm";

    @Test
    public void testDownload() {
        HttpManager heute = new HttpManager(heute_url);

        try {
            System.out.println(heute.fetchPlan());
        } catch (IOException e) {
            fail();
        }
    }
}
