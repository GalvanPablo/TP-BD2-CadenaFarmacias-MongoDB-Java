package UNLa.GrupoH;

public class Sucursal {
    private Domicilio domicilio;
    private int cod_PuntoDeVenta;

    public Sucursal() {
    }

    public Sucursal(Domicilio domicilio, int cod_PuntoDeVenta) {
        this.domicilio = domicilio;
        this.cod_PuntoDeVenta = cod_PuntoDeVenta;
    }

    @Override
    public String toString() {
        return "Sucursal{" +
                "domicilio=" + domicilio +
                ", cod_PuntoDeVenta=" + cod_PuntoDeVenta +
                '}';
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public int getCod_PuntoDeVenta() {
        return cod_PuntoDeVenta;
    }

    public void setCod_PuntoDeVenta(int cod_PuntoDeVenta) {
        this.cod_PuntoDeVenta = cod_PuntoDeVenta;
    }
}
