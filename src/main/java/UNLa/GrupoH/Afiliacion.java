package UNLa.GrupoH;

public class Afiliacion {
    private String obraSocial;
    private String nroAfiliacion;

    // CONSTRUCTOR
    public Afiliacion() {
    }

    public Afiliacion(String obraSocial, String nroAfiliacion) {
        this.obraSocial = obraSocial;
        this.nroAfiliacion = nroAfiliacion;
    }

    @Override
    public String toString() {
        return "Afiliacion{" +
                "obraSocial='" + obraSocial + '\'' +
                ", nroAfiliacion='" + nroAfiliacion + '\'' +
                '}';
    }

    // GETTERS Y SETTERS
    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getNroAfiliacion() {
        return nroAfiliacion;
    }

    public void setNroAfiliacion(String nroAfiliacion) {
        this.nroAfiliacion = nroAfiliacion;
    }
}
