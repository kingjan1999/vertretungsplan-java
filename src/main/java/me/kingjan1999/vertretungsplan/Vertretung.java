package me.kingjan1999.vertretungsplan;

import java.io.Serializable;

public class Vertretung implements Serializable {

    private static final long serialVersionUID = -4673494471632143019L;

    private final String klasse, stunde, fach, raum, neuesFach, neuerRaum;

    public Vertretung(final String klasse, final String stunde, final String fach, final String raum,
                      final String neuesFach, final String neuerRaum) {
        super();
        this.klasse = klasse;
        this.stunde = stunde;
        this.fach = fach;
        this.raum = raum;
        this.neuesFach = neuesFach;
        this.neuerRaum = neuerRaum;
    }

    @Override
    public String toString() {
        return String
                .format("Vertretung [klasse=%s, stunde=%s, fach=%s, raum=%s, neuesFach=%s, neuerRaum=%s]",
                        klasse, stunde, fach, raum, neuesFach, neuerRaum);
    }
}
