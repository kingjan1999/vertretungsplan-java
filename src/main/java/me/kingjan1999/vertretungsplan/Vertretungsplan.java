package me.kingjan1999.vertretungsplan;

import com.google.gson.Gson;
import org.apache.commons.beanutils.DynaBean;

import java.util.List;

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

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
