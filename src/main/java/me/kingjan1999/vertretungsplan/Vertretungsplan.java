package me.kingjan1999.vertretungsplan;

import java.io.*;
import java.util.List;

public class Vertretungsplan implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1663590620496804995L;
    String datum;
    final List<String> infos;
    final List<Vertretung> vertretungen;
    String periodString;

    public Vertretungsplan(final String datum, final List<String> infos,
                           final List<Vertretung> vertretungen, final String periodString) {
        super();
        this.datum = datum;
        this.infos = infos;
        this.vertretungen = vertretungen;
        this.periodString = periodString;
    }

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder("Datum: ");
        b.append(datum);
        b.append("\nInfos: ");
        for (final String lo : infos) {
            b.append(lo);
            b.append("\n");
        }
        b.append("Vertretungen: ");
        for (final Vertretung e : vertretungen) {
            b.append(e.toString());
            b.append("\n");
        }
        b.append("Period: ");
        b.append(periodString);
        return b.toString();
    }

    public static void save(final String fileName, final Vertretungsplan plan) {
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            final ObjectOutputStream o = new ObjectOutputStream(fos);
            o.writeObject(plan);
        } catch (final IOException e) {
            System.err.println(e);
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static Vertretungsplan read(final String fileName) {
        InputStream fis = null;

        try {
            fis = new FileInputStream(fileName);
            final ObjectInputStream o = new ObjectInputStream(fis);
            final Vertretungsplan plan = (Vertretungsplan) o.readObject();
            return plan;
        } catch (final IOException e) {
            System.err.println(e);
        } catch (final ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            try {
                fis.close();
            } catch (final Exception e) {
            }
        }
        return null;
    }

}
