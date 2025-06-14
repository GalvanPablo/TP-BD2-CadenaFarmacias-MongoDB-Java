package UNLa.GrupoH.db;

import UNLa.GrupoH.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class VentaFactory {
    public static List<Venta> generarVentasDePrueba(){
        // PRODUCTOS (10 productos: 7 medicamentos y 3 perfumes)
        Producto ibuprofeno = new Producto(1001, "Ibuprofeno 400", "Medicamento", "Bayer", 3500.0f);
        Producto amoxixilina = new Producto(1002, "Amoxixilina", "Medicamento", "Bayer", 4000.0f);
        Producto paracetamol = new Producto(1003, "Paracetamol", "Medicamento", "Bago", 2500.0f);
        Producto omeprazol = new Producto(1004, "Omeprazol", "Medicamento", "Roemmers", 3200.0f);
        Producto loratadina = new Producto(1005, "Loratadina", "Medicamento", "Elea", 2800.0f);
        Producto aspirina = new Producto(1006, "Aspirina", "Medicamento", "Bayer", 3000.0f);
        Producto diclofenac = new Producto(1007, "Diclofenac", "Medicamento", "Roemmers", 3800.0f);

        Producto chanel = new Producto(2001, "Chanel N°5", "Perfume", "Chanel", 63000.0f);
        Producto dior = new Producto(2002, "J'adore", "Perfume", "Dior", 58000.0f);
        Producto armani = new Producto(2003, "Acqua di Gio", "Perfume", "Armani", 55000.0f);

        // CLIENTES (10 clientes)
        Cliente cliente1 = new Cliente(1001, "Perez", "Juan", 12345678,
                new Domicilio("29 de Septiembre", 0, "Lanús", "Buenos Aires"),
                new Afiliacion("Pami", "123456"));

        Cliente cliente2 = new Cliente(1002, "Alvárez", "Alberto", 12345679,
                new Domicilio("Av. Siempre viva", 1243, "Lanús", "Buenos Aires"),
                new Afiliacion("OSPECOM", "345665"));

        Cliente cliente3 = new Cliente(1003, "Gomez", "Julian", 14345679,
                new Domicilio("Av. Alsina", 143, "Lanús", "Buenos Aires"),
                null);

        Cliente cliente4 = new Cliente(1004, "Rodriguez", "Maria", 15345679,
                new Domicilio("Av. San Martín", 543, "Lomas de Zamora", "Buenos Aires"),
                new Afiliacion("OSDE", "987654"));

        Cliente cliente5 = new Cliente(1005, "Lopez", "Carlos", 16345679,
                new Domicilio("Av. Hipólito Yrigoyen", 876, "Adrogué", "Buenos Aires"),
                new Afiliacion("Swiss Medical", "456789"));

        Cliente cliente6 = new Cliente(1006, "Martinez", "Ana", 17345679,
                new Domicilio("Calle Falsa", 123, "Banfield", "Buenos Aires"),
                new Afiliacion("Galeno", "567890"));

        Cliente cliente7 = new Cliente(1007, "Garcia", "Pedro", 18345679,
                new Domicilio("Av. Mitre", 432, "Temperley", "Buenos Aires"),
                null);

        Cliente cliente8 = new Cliente(1008, "Fernandez", "Lucia", 19345679,
                new Domicilio("Av. Pavón", 765, "Burzaco", "Buenos Aires"),
                new Afiliacion("Medicus", "678901"));

        Cliente cliente9 = new Cliente(1009, "Sanchez", "Miguel", 20345679,
                new Domicilio("Av. Espora", 321, "Llavallol", "Buenos Aires"),
                new Afiliacion("OSPE", "789012"));

        Cliente cliente10 = new Cliente(1010, "Diaz", "Sofia", 21345679,
                new Domicilio("Av. Eva Perón", 987, "Monte Grande", "Buenos Aires"),
                new Afiliacion("OSDEPYM", "890123"));

        // SUCURSALES (3 sucursales)
        Sucursal sucursal1 = new Sucursal(
                "Banfield",
                new Domicilio("Casa inventada", 3023, "Banfield", "Buenos Aires"),
                1001
        );

        Sucursal sucursal2 = new Sucursal(
                "Lanús",
                new Domicilio("Av. Jose Hernandez", 3023, "Lanús", "Buenos Aires"),
                1002
        );

        Sucursal sucursal3 = new Sucursal(
                "Lomas",
                new Domicilio("Av. Meeks", 1234, "Lomas de Zamora", "Buenos Aires"),
                1003
        );

        // EMPLEADOS (3 por sucursal: 1 encargado y 2 empleados)
        // Sucursal 1
        Empleado encargado1 = new Empleado(
                "Gonzalez", "Carlos", 22222222,
                new Domicilio("Calle 1", 123, "Banfield", "Buenos Aires"),
                new Afiliacion("Medife","86486426413"),
                "23222222228", sucursal1, true
        );

        Empleado empleado1_1 = new Empleado(
                "Perez", "Laura", 33333333,
                new Domicilio("Calle 2", 456, "Banfield", "Buenos Aires"),
                new Afiliacion("Medife","86486426412"),
                "23333333338", sucursal1, false
        );

        Empleado empleado1_2 = new Empleado(
                "Lopez", "Marcos", 44444444,
                new Domicilio("Calle 3", 789, "Banfield", "Buenos Aires"),
                new Afiliacion("Medife","86486426414"),
                "23444444448", sucursal1, false
        );

        // Sucursal 2
        Empleado encargado2 = new Empleado(
                "Martinez", "Ana", 55555555,
                new Domicilio("Av. Alsina", 3023, "Lanús", "Buenos Aires"),
                new Afiliacion("Medife","86986226113"),
                "23555555558", sucursal2, true
        );

        Empleado empleado2_1 = new Empleado(
                "Rodriguez", "Jorge", 66666666,
                new Domicilio("Av. San Martín", 456, "Lanús", "Buenos Aires"),
                new Afiliacion("Medife","86986226114"),
                "23666666668", sucursal2, false
        );

        Empleado empleado2_2 = new Empleado(
                "Gomez", "Patricia", 77777777,
                new Domicilio("Av. Hipólito Yrigoyen", 789, "Lanús", "Buenos Aires"),
                new Afiliacion("Medife","86986226115"),
                "23777777778", sucursal2, false
        );

        // Sucursal 3
        Empleado encargado3 = new Empleado(
                "Sanchez", "Miguel", 88888888,
                new Domicilio("Av. Meeks", 1234, "Lomas de Zamora", "Buenos Aires"),
                new Afiliacion("Medife","86986226116"),
                "23888888888", sucursal3, true
        );

        Empleado empleado3_1 = new Empleado(
                "Diaz", "Sofia", 99999999,
                new Domicilio("Av. Eva Perón", 567, "Lomas de Zamora", "Buenos Aires"),
                new Afiliacion("Medife","86986226117"),
                "23999999998", sucursal3, false
        );

        Empleado empleado3_2 = new Empleado(
                "Fernandez", "Lucas", 10101010,
                new Domicilio("Av. Pavón", 890, "Lomas de Zamora", "Buenos Aires"),
                new Afiliacion("Medife","86986226118"),
                "23101010108", sucursal3, false
        );

        // LISTADO DE VENTAS (aproximadamente 30 por sucursal con variación del 20%)
        List<Venta> ventas = new ArrayList<>();

        // Generar ventas para Sucursal 1 (Banfield) - 32 ventas
        ventas.addAll(generarVentasSucursal(sucursal1, encargado1, new Empleado[]{empleado1_1, empleado1_2},
                new Cliente[]{cliente1, cliente2, cliente3, cliente4, cliente5},
                new Producto[]{ibuprofeno, amoxixilina, paracetamol, omeprazol, loratadina, aspirina, diclofenac, chanel, dior, armani}, 32));

        // Generar ventas para Sucursal 2 (Lanús) - 28 ventas (20% menos)
        ventas.addAll(generarVentasSucursal(sucursal2, encargado2, new Empleado[]{empleado2_1, empleado2_2},
                new Cliente[]{cliente2, cliente3, cliente6, cliente7, cliente8},
                new Producto[]{ibuprofeno, amoxixilina, paracetamol, omeprazol, loratadina, aspirina, diclofenac, chanel, dior, armani}, 28));

        // Generar ventas para Sucursal 3 (Lomas) - 36 ventas (20% más)
        ventas.addAll(generarVentasSucursal(sucursal3, encargado3, new Empleado[]{empleado3_1, empleado3_2},
                new Cliente[]{cliente1, cliente4, cliente5, cliente9, cliente10},
                new Producto[]{ibuprofeno, amoxixilina, paracetamol, omeprazol, loratadina, aspirina, diclofenac, chanel, dior, armani}, 36));

        return ventas;
    }

    private static List<Venta> generarVentasSucursal(Sucursal sucursal, Empleado encargado, Empleado[] empleados,
                                                     Cliente[] clientes, Producto[] productos, int cantidadVentas) {

        List<Venta> ventas = new ArrayList<>();
        String codigoBase = sucursal.getCod_PuntoDeVenta() + "-";

        for (int i = 0; i < cantidadVentas; i++) {
            // Seleccionar cliente, vendedor y encargado aleatoriamente
            Cliente cliente = clientes[(int)(Math.random() * clientes.length)];
            Empleado vendedor = empleados[(int)(Math.random() * empleados.length)];

            // Determinar si el encargado aprobó la venta (50% de probabilidad)
            Empleado caja = (Math.random() > 0.5) ? encargado : vendedor;

            // Determinar método de pago aleatorio
            String metodoPago = (Math.random() > 0.3) ? "Efectivo" : "Tarjeta";

            // Generar items de venta (entre 1 y 3 productos para promedio de 1.5)
            int cantidadItems = (Math.random() > 0.5) ? 1 : 2; // 50% 1 item, 50% 2 items
            List<ItemVenta> items = new ArrayList<>();

            float total = 0.0f;
            for (int j = 0; j < cantidadItems; j++) {
                Producto producto = productos[(int)(Math.random() * productos.length)];
                int cantidad = (int)(Math.random() * 3) + 1; // 1 a 3 unidades
                float precioUnitario = producto.getPrecio();
                float subtotal = precioUnitario * cantidad;

                items.add(new ItemVenta(producto, cantidad, precioUnitario, subtotal));
                total += subtotal;
            }

            // Generar fecha aleatoria en mayo 2025
            long fechaAleatoria = (long)(Math.random() * 30 * 24 * 60 * 60 * 1000); // 30 días en milisegundos
            Date fecha = new Date(125, 4, 1); // 1 de mayo 2025 (124 = 2024 + 1900, 4 = mayo)
            fecha.setTime(fecha.getTime() + fechaAleatoria);

            ventas.add(new Venta(
                    codigoBase + (10000000 + i), // Código único
                    fecha,
                    items,
                    cliente,
                    vendedor,
                    caja,
                    metodoPago,
                    total
            ));
        }

        return ventas;
    }
}