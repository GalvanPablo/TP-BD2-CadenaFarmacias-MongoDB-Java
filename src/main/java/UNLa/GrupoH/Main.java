package UNLa.GrupoH;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Venta venta = new Venta(
                "0000-12345678",
                new Date("May 25, 2025 17:35:00"),
                new ArrayList<>(Arrays.asList(
                        new ItemVenta(
                                new Producto(
                                        123,
                                        "Ibuprofeno 400",
                                        "Medicamento",
                                        "Bayer",
                                        3500
                                ),
                                3,
                                20000,
                                60000
                        ),
                        new ItemVenta(
                                new Producto(
                                        124,
                                        "Amoxixilina",
                                        "Medicamento",
                                        "Bayer",
                                        4000
                                ),
                                1,
                                4000,
                                4000
                        )
                )),
                new Cliente(
                        123,
                        "Perez",
                        "Juan",
                        12345678,
                        new Domicilio("29 de Septiembre", 0, "Lanús", "Buenos Aires"),
                        new Afiliacion("Pami", "123456")
                ),
                new Empleado(
                        "Empleado",
                        "Atención",
                        33333333,
                        new Domicilio("Casa inventada", 3023, "Banfield", "Buenos Aires"),
                        new Afiliacion("Medife","86486426412"),
                        "23333333338",
                        new Sucursal(
                                new Domicilio("Casa inventada", 3023, "Banfield", "Buenos Aires"),
                                1234
                        ),
                        false
                ),
                new Empleado(
                        "Empleado",
                        "Cajero",
                        22222222,
                        new Domicilio("Casa inventada", 3023, "Banfield", "Buenos Aires"),
                        new Afiliacion("Medife","86486426413"),
                        "23222222228",
                        new Sucursal(
                                new Domicilio("Casa inventada", 3023, "Banfield", "Buenos Aires"),
                                1234
                        ),
                        true
                ),
                "Efectivo",
                64000
        );

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // para que no use el número de milisegundos
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // JSON indentado

        try {
            mapper.writeValue(new File("datos_salida.json"), venta);  // guarda el objeto 'venta' como JSON en un archivo
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}