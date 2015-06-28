package me.kingjan1999.vertretungsplan;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class Vertretungsplan {

    private final VMeta meta;
    private final String[] infos;
    private final List<Map<String, String>> vertretungen;

    public Vertretungsplan(VMeta meta, String[] infos, List<Map<String, String>> vertretungen) {
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

    public List<Map<String, String>> getVertretungen() {
        return vertretungen;
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
