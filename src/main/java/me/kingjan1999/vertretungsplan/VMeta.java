package me.kingjan1999.vertretungsplan;

/**
 * Created by jan on 28.06.15.
 *
 * Meta-Infos about the Vertretungsplan (As of, Untis Version, Day, Current Page, Max Pages)
 */
public class VMeta {

    private String lastUpdated, untis_ver, tag;
    int current, max;

    /**
     *
     * @param lastUpdated Date the Vertretungsplan was updated
     * @param untis_ver Version of Untis Software
     * @param tag Day of the Vertretungsplan
     * @param current Current Page
     * @param max Max Pages
     */
    public VMeta(String lastUpdated, String untis_ver, String tag, int current, int max) {
        this.lastUpdated = lastUpdated;
        this.untis_ver = untis_ver;
        this.tag = tag;
        this.current = current;
        this.max = max;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getUntis_ver() {
        return untis_ver;
    }

    public String getTag() {
        return tag;
    }

    public int getCurrent() {
        return current;
    }

    public int getMax() {
        return max;
    }

    @Override
    public String toString() {
        return "VMeta{" +
                "lastUpdated='" + lastUpdated + '\'' +
                ", untis_ver='" + untis_ver + '\'' +
                ", tag='" + tag + '\'' +
                ", current=" + current +
                ", max=" + max +
                '}';
    }
}
