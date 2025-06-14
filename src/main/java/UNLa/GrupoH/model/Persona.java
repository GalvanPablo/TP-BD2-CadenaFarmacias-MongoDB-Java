package UNLa.GrupoH.model;

public abstract class Persona {
    protected String apellido;
    protected String nombre;
    protected int dni;
    protected Domicilio domicilio;
    protected Afiliacion afiliacion;

    // CONSTRUCTOR
    public Persona() {
    }

    public Persona(String apellido, String nombre, int dni, Domicilio domicilio, Afiliacion afiliacion) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.domicilio = domicilio;
        this.afiliacion = afiliacion;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "apellido='" + apellido + '\'' +
                ", nombre='" + nombre + '\'' +
                ", dni=" + dni +
                ", domicilio=" + domicilio +
                ", afiliacion=" + afiliacion +
                '}';
    }

    // GETTERS Y SETTERS
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Afiliacion getAfiliacion() {
        return afiliacion;
    }

    public void setAfiliacion(Afiliacion afiliacion) {
        this.afiliacion = afiliacion;
    }
}
