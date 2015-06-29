package me.kingjan1999.vertretungsplan;

import com.google.gson.Gson;
import org.apache.commons.beanutils.DynaBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Vertretungsplan-Klasse
 */
public class Vertretungsplan {

    private final VMeta meta;
    private final String[] infos;
    private final List<DynaBean> vertretungen;

    public Vertretungsplan(VMeta meta, String[] infos, List<DynaBean> vertretungen) {
        this.meta = meta;
        this.infos = infos;
        this.vertretungen = vertretungen;
    }

    public VMeta getMeta() {
        return meta;
    }

    public String[] getInfos() {
        return infos;
    }

    public List<DynaBean> getVertretungen() {
        return vertretungen;
    }

    /**
     * Searches for course in vertretungen
     *
     * @param course     Course to search for
     * @param classIndex Index of the class-col
     * @return List<DynaBean> containing all found courses
     */
    public List<DynaBean> getVertretungenForCourse(String course, int classIndex) {
        List<DynaBean> found = new ArrayList<DynaBean>();
        if (vertretungen.size() < 0) {
            return found;
        }
        String colName = vertretungen.get(0).getDynaClass().getDynaProperties()[classIndex].getName(); //Read col name from first Vertretung

        found = vertretungen.stream().filter(v -> v.get(colName).toString().contains(course)).collect(Collectors.toList()); //Filter vertretungen

        return found;
    }

    /**
     * @see Vertretungsplan#getVertretungenForCourse(String, int)
     */
    public List<DynaBean> getVertretungenForCourse(String course) {
        return this.getVertretungenForCourse(course, 0);
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
