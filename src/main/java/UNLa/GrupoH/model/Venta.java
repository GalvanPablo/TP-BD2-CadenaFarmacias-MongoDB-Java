package UNLa.GrupoH.model;

import java.util.Date;
import java.util.List;

public class Venta {
    private String ticket;
    private Date fecha;
    private List<ItemVenta> items;
    private Cliente cliente;
    private Empleado atencion;
    private Empleado caja;
    private String formaDePago;
    private float total;

    public Venta() {
    }

    public Venta(String ticket, Date fecha, List<ItemVenta> items, Cliente cliente, Empleado atencion, Empleado caja, String formaDePago, float total) {
        this.ticket = ticket;
        this.fecha = fecha;
        this.items = items;
        this.cliente = cliente;
        this.atencion = atencion;
        this.caja = caja;
        this.formaDePago = formaDePago;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "ticket='" + ticket + '\'' +
                ", fecha=" + fecha +
                ", items=" + items +
                ", cliente=" + cliente +
                ", atencion=" + atencion +
                ", caja=" + caja +
                ", formaDePago='" + formaDePago + '\'' +
                ", total=" + total +
                '}';
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<ItemVenta> getItems() {
        return items;
    }

    public void setItems(List<ItemVenta> items) {
        this.items = items;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getAtencion() {
        return atencion;
    }

    public void setAtencion(Empleado atencion) {
        this.atencion = atencion;
    }

    public Empleado getCaja() {
        return caja;
    }

    public void setCaja(Empleado caja) {
        this.caja = caja;
    }

    public String getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = formaDePago;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
