package UNLa.GrupoH.model;

public class Sucursal {
    private String nombre;
    private Domicilio domicilio;
    private int cod_PuntoDeVenta;

    public Sucursal() {
    }

    public Sucursal(String nombre, Domicilio domicilio, int cod_PuntoDeVenta) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.cod_PuntoDeVenta = cod_PuntoDeVenta;
    }

    @Override
    public String toString() {
        return "Sucursal{" +
                "nombre='" + nombre + '\'' +
                ", domicilio=" + domicilio +
                ", cod_PuntoDeVenta=" + cod_PuntoDeVenta +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
