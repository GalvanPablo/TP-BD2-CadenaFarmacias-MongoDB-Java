package UNLa.GrupoH;

import UNLa.GrupoH.db.VentaFactory;
import UNLa.GrupoH.db.VentaLoader;
import UNLa.GrupoH.model.Venta;
import UNLa.GrupoH.repository.VentaRepository;

import java.util.Date;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        // LIMPIAR COLLECTION DE VENTAS Y CARGAR DOCUMENTOS DE PRUEBA
        List<Venta> ventasDePrueba = VentaFactory.generarVentasDePrueba();
        VentaLoader.reiniciarVentas(ventasDePrueba);

        Date desde = new Date("May 1, 2025 00:00:00");
        Date hasta = new Date("Jun 1, 2025 00:00:00");

        VentaRepository.reporteCantidadVentasPorSucursalYTotal(desde, hasta);
        VentaRepository.reporteVentasPorObraSocial(desde, hasta);
        VentaRepository.reporteCobranzaTotalYPorSucursal(desde, hasta);
        VentaRepository.reporteVentasPorTipoProducto(desde, hasta);
        VentaRepository.rankingMontoVendidoPorProductoYSucursal(desde, hasta);
        VentaRepository.rankingCantidadVendidaPorProductoYSucursal(desde, hasta);
        VentaRepository.rankingComprasPorCliente(desde, hasta);
        VentaRepository.rankingComprasPorClienteYSucursal(desde, hasta);
    }
}
