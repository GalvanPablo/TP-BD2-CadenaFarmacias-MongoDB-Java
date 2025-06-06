package UNLa.GrupoH;

public class Cliente extends Persona{
    private long idCliente;

    // CONSTRUCTOR
    public Cliente() {
    }

    public Cliente(long idCliente, String apellido, String nombre, int dni, Domicilio domicilio, Afiliacion afiliacion) {
        super(apellido, nombre, dni, domicilio, afiliacion);
        this.idCliente = idCliente;
    }

    // TO STRING
    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", apellido='" + apellido + '\'' +
                ", nombre='" + nombre + '\'' +
                ", dni=" + dni +
                ", domicilio=" + domicilio +
                ", afiliacion=" + afiliacion +
                '}';
    }

    // GETTERS Y SETTERS
    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }
}
