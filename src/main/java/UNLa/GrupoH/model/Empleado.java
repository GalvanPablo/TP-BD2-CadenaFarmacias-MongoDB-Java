package UNLa.GrupoH.model;

public class Empleado extends Persona{
    private String cuil;
    private Sucursal sucursal;
    private boolean encargado;

    public Empleado() {
    }

    public Empleado(String apellido, String nombre, int dni, Domicilio domicilio, Afiliacion afiliacion, String cuil, Sucursal sucursal, boolean encargado) {
        super(apellido, nombre, dni, domicilio, afiliacion);
        this.cuil = cuil;
        this.sucursal = sucursal;
        this.encargado = encargado;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "cuil=" + cuil +
                ", sucursal=" + sucursal +
                ", encargado=" + encargado +
                ", apellido='" + apellido + '\'' +
                ", nombre='" + nombre + '\'' +
                ", dni=" + dni +
                ", domicilio=" + domicilio +
                ", afiliacion=" + afiliacion +
                '}';
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public boolean isEncargado() {
        return encargado;
    }

    public void setEncargado(boolean encargado) {
        this.encargado = encargado;
    }
}
