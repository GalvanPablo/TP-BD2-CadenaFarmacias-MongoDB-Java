package UNLa.GrupoH.model;

public class Producto {
    private long codigo;
    private String descripcion;
    private String tipoProducto;
    private String laboratorio;
    private float precio;

    public Producto() {
    }

    public Producto(long codigo, String descripcion, String tipoProducto, String laboratorio, float precio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.tipoProducto = tipoProducto;
        this.laboratorio = laboratorio;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigo=" + codigo +
                ", descripcion='" + descripcion + '\'' +
                ", tipoProducto='" + tipoProducto + '\'' +
                ", laboratorio='" + laboratorio + '\'' +
                ", precio=" + precio +
                '}';
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
