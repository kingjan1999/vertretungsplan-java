package me.kingjan1999.vertretungsplan.net;

import me.kingjan1999.vertretungsplan.Vertretung;
import me.kingjan1999.vertretungsplan.Vertretungsplan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpManager {

    private final static String[] toRemove = new String[]{"<tr class='", "odd", "even", "<td class=\"list\"", "<th class=\"list\"", "align=\"center\"", "list", ">", "'", "</tr"};

    final String url;

    public HttpManager(String url) {
        this.url = url;
    }

    public Vertretungsplan fetchPlan() throws IOException {
        return this.analysePlan(this.loadPlan());
    }

    private List<String> loadPlan() throws IOException {
        final List<String> result = new ArrayList<String>();
        BufferedReader in = null;
        try {

            final URL downloadUrl = new URL(url);
            in = new BufferedReader(new InputStreamReader(downloadUrl.openStream()));
            String str;
            boolean started = false;
            while ((str = in.readLine()) != null) {
                if (!started && str.startsWith("<div class=\"mon_title\">")) {
                    started = true;
                }
                if (started) {
                    if (str.equals("</font></center>")) {
                        started = false;
                        break;
                    }

                    result.add(str);
                }
            }
        } finally {
            in.close();
        }
        return result;
    }

    private Vertretungsplan analysePlan(final List<String> plan) {

        String datum = "";
        boolean infosBool = false, list = false, period = false, listReady = false;
        int count = 0;
        final List<String> infos = new ArrayList<String>(6);
        final List<Vertretung> vertretungen = new ArrayList<Vertretung>(50);
        String periodString = "";


        for (final String s : plan) {
            if (count == 0) {
                datum = s.replaceAll("<div class=\"mon_title\">", "").replaceAll("</div>", "");
                count++;
                continue;
            }
            if (s.contains("<table class=\"info\"")) {
                infosBool = true;
                count++;
                continue;
            }
            if (infosBool && s.equals("</table>")) {
                infosBool = false;
                count++;
                continue;
            }
            if (!infosBool && s.equals("<p>") && !listReady) {
                list = true;
                count++;
                continue;
            }
            if (list && s.equals("</table>")) {
                list = false;
                listReady = true;
            }
            if (!infosBool && !list && !period) {
                period = true;
                count++;
                continue;
            }
            if (infosBool) {
                if (s.contains("Nachrichten zum Tag") || s.equals("<br>") || s.contains("<tr")) {
                    count++;
                    continue;
                }
                infos.add(s.replaceAll("<br>", "").replaceAll("</td></tr>", ""));
            }
            if (list) {
                if(!(s.contains("odd") || s.contains("even"))) {
                    String copy = String.copyValueOf(s.toCharArray()).replaceAll("<th class=\"list\">&nbsp;", " Leer");
                    for (final String remove : toRemove) {
                        copy = copy.replaceAll(remove, "");
                    }

                    System.out.println(copy);
                }
                if (!s.contains("<table class") && (s.contains("odd") || s.contains("even"))) {
                    String copy = String.copyValueOf(s.toCharArray()).replaceAll("<td class=\"list\">&nbsp;", " Leer");
                    for (final String remove : toRemove) {
                        copy = copy.replaceAll(remove, "");
                    }
                    copy = copy.replaceAll("&nbsp;", "Leer");
                    final String klasse;
                    final String stunde;
                    final String fach;
                    final String raum;
                    final String neuesFach;
                    final String neuerRaum;
                    final String[] tableData = copy.split("</td");
                    if (tableData.length != 6) {
                        klasse = "Kann";
                        stunde = "den";
                        fach = "Eintrag";
                        raum = "leider";
                        neuesFach = "nicht";
                        neuerRaum = "verarbeiten";
                    } else {
                        klasse = tableData[0].trim();
                        stunde = tableData[1].trim();
                        fach = tableData[2].trim();
                        raum = tableData[3].trim();
                        neuesFach = tableData[4].trim();
                        neuerRaum = tableData[5].trim();
                    }
                    final Vertretung v = new Vertretung(klasse, stunde, fach, raum, neuesFach, neuerRaum);
                    vertretungen.add(v);
                }
            }
            if (period) {
                if (!s.contains("<"))
                    periodString = s;
            }
            count++;
        }
        final Vertretungsplan vplan = new Vertretungsplan(datum, infos, vertretungen, periodString);
        return vplan;
    }

}
