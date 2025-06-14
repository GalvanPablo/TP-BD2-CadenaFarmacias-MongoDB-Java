package UNLa.GrupoH.repository;

import UNLa.GrupoH.config.MongoDBConfig;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;

import java.text.DecimalFormat;
import java.util.*;

public class VentaRepository {
    public static void reporteCantidadVentasPorSucursalYTotal(Date desde, Date hasta) {
        try (MongoClient client = MongoClients.create(MongoDBConfig.getMongoURI())) {
            MongoDatabase db = client.getDatabase(MongoDBConfig.getDatabaseName());
            MongoCollection<Document> collection = db.getCollection("ventas");

            // 1. Total de ventas en el rango
            long totalVentas = collection.countDocuments(
                    and(
                            gte("fecha", desde),
                            lte("fecha", hasta)
                    )
            );

            // 2. Ventas agrupadas por sucursal (accediendo a Venta -> Empleado caja -> Sucursal -> Nombre)
            List<Document> ventasPorSucursal = collection.aggregate(
                    Arrays.asList(
                            Aggregates.match(  // Filtra por rango de fechas
                                    and(
                                            gte("fecha", desde),
                                            lte("fecha", hasta)
                                    )
                            ),
                            Aggregates.group(  // Agrupa por el nombre de la sucursal anidada
                                    "$caja.sucursal.nombre",  // Ruta al campo anidado
                                    Accumulators.sum("totalVentas", 1)
                            ),
                            Aggregates.sort(Sorts.ascending("_id"))  // Opcional: ordena por nombre de sucursal
                    )
            ).into(new ArrayList<>());

            // Impresión del reporte
            System.out.println("\n## Ventas Totales y por Sucursal ##");
            System.out.println("# Informe de Ventas entre " + desde + " hasta " + hasta);
            System.out.println("--------------------------------------");
            System.out.println("Total de ventas en toda la cadena: " + totalVentas);
            System.out.println("Ventas por sucursal:");

            // Mostrar resultados por sucursal
            for (Document doc : ventasPorSucursal) {
                String sucursal = doc.getString("_id");  // "_id" es el campo de agrupación (sucursal)
                int ventas = doc.getInteger("totalVentas");
                System.out.println("- " + sucursal + ": " + ventas + " ventas");
            }
        }
    }

    public static void reporteVentasPorObraSocial(Date desde, Date hasta) {
        try (MongoClient client = MongoClients.create(MongoDBConfig.getMongoURI())) {
            MongoDatabase db = client.getDatabase(MongoDBConfig.getDatabaseName());
            MongoCollection<Document> collection = db.getCollection("ventas");

            List<Document> ventasPorObraSocial = collection.aggregate(
                    Arrays.asList(
                            // 1. Filtrar por rango de fechas
                            Aggregates.match(
                                    and(
                                            gte("fecha", desde),
                                            lte("fecha", hasta)
                                    )
                            ),
                            // 2. Proyectar para manejar casos nulos (sin afiliación)
                            Aggregates.project(
                                    Projections.fields(
                                            Projections.computed(
                                                    "obraSocial",
                                                    Document.parse("""
                                                                {$ifNull: ["$cliente.afiliacion.obraSocial", "Privado"]}
                                                            """)
                                            )
                                    )
                            ),
                            // 3. Agrupar por obraSocial y contar
                            Aggregates.group(
                                    "$obraSocial",
                                    Accumulators.sum("totalVentas", 1)
                            ),
                            // 4. Ordenar alfabéticamente (opcional)
                            Aggregates.sort(Sorts.ascending("_id"))
                    )
            ).into(new ArrayList<>());

            // Impresión del reporte
            System.out.println("\n## Ventas por Obra Social ##");
            System.out.println("# Informe entre " + desde + " hasta " + hasta);
            System.out.println("--------------------------------------");

            for (Document doc : ventasPorObraSocial) {
                String obraSocial = doc.getString("_id");
                int ventas = doc.getInteger("totalVentas");
                System.out.println("- " + obraSocial + ": " + ventas + " ventas");
            }
        }
    }

    public static void reporteCobranzaTotalYPorSucursal(Date desde, Date hasta) {
        try (MongoClient client = MongoClients.create(MongoDBConfig.getMongoURI())) {
            MongoDatabase db = client.getDatabase(MongoDBConfig.getDatabaseName());
            MongoCollection<Document> collection = db.getCollection("ventas");

            // Pipeline para la cobranza total
            Document cobranzaTotalDoc = collection.aggregate(
                    Arrays.asList(
                            Aggregates.match(  // Filtra por rango de fechas
                                    and(
                                            gte("fecha", desde),
                                            lte("fecha", hasta)
                                    )
                            ),
                            Aggregates.group(  // Agrupa todo para sumar el total
                                    null,  // Agrupa todos los documentos
                                    Accumulators.sum("totalCobranza", "$total")
                            )
                    )
            ).first();  // Obtiene el primer (y único) resultado

            double totalCobranza = cobranzaTotalDoc != null ? cobranzaTotalDoc.getDouble("totalCobranza") : 0.0;

            // Pipeline para la cobranza por sucursal
            List<Document> cobranzaPorSucursal = collection.aggregate(
                    Arrays.asList(
                            Aggregates.match(  // Filtra por rango de fechas
                                    and(
                                            gte("fecha", desde),
                                            lte("fecha", hasta)
                                    )
                            ),
                            Aggregates.group(  // Agrupa por sucursal y suma el total
                                    "$caja.sucursal.nombre",
                                    Accumulators.sum("cobranzaSucursal", "$total")
                            ),
                            Aggregates.sort(Sorts.ascending("_id"))  // Ordena por nombre de sucursal
                    )
            ).into(new ArrayList<>());

            // Impresión del reporte
            System.out.println("\n## Cobranza Total y por Sucursal ##");
            System.out.println("# Informe entre " + desde + " hasta " + hasta);
            System.out.println("--------------------------------------");
            System.out.printf("Cobranza total: $%.2f\n", totalCobranza);
            System.out.println("\nCobranza por sucursal:");

            for (Document doc : cobranzaPorSucursal) {
                String sucursal = doc.getString("_id");
                double cobranza = doc.getDouble("cobranzaSucursal");
                System.out.printf("- %s: $%.2f\n", sucursal, cobranza);
            }
        }
    }

    public static void reporteVentasPorTipoProducto(Date desde, Date hasta) {
        try (MongoClient client = MongoClients.create(MongoDBConfig.getMongoURI())) {
            MongoDatabase db = client.getDatabase(MongoDBConfig.getDatabaseName());
            MongoCollection<Document> collection = db.getCollection("ventas");

            // Pipeline de agregación corregido
            List<Document> ventasPorTipo = collection.aggregate(
                    Arrays.asList(
                            // 1. Filtrar por rango de fechas
                            Aggregates.match(
                                    and(
                                            gte("fecha", desde),
                                            lte("fecha", hasta)
                                    )
                            ),
                            // 2. Proyectar para identificar tipos únicos por venta (versión corregida)
                            Aggregates.project(
                                    new Document("tiposProducto",
                                            new Document("$reduce",
                                                    new Document("input", "$items")
                                                            .append("initialValue", new ArrayList<String>())
                                                            .append("in",
                                                                    new Document("$cond",
                                                                            new Document("if",
                                                                                    new Document("$not",
                                                                                            new Document("$in",
                                                                                                    Arrays.asList("$$this.producto.tipoProducto", "$$value")
                                                                                            )
                                                                                    )
                                                                            )
                                                                                    .append("then",
                                                                                            new Document("$concatArrays",
                                                                                                    Arrays.asList("$$value",
                                                                                                            Arrays.asList("$$this.producto.tipoProducto")
                                                                                                    )
                                                                                            )
                                                                                    )
                                                                                    .append("else", "$$value")
                                                                    )
                                                            )
                                            )
                                    )
                            ),
                            // 3. Desanidar los tipos únicos
                            Aggregates.unwind("$tiposProducto"),
                            // 4. Agrupar por tipo y contar ventas
                            Aggregates.group(
                                    "$tiposProducto",
                                    Accumulators.sum("totalVentas", 1)
                            ),
                            // 5. Ordenar alfabéticamente
                            Aggregates.sort(Sorts.ascending("_id"))
                    )
            ).into(new ArrayList<>());

            // Impresión del reporte
            System.out.println("\n## Ventas por Tipo de Producto ##");
            System.out.println("# Informe entre " + desde + " hasta " + hasta);
            System.out.println("--------------------------------------");
            System.out.println("Aclaración: Una misma venta que incluya ambos tipos (ej.: 1 Medicamento + 1 Perfume) se cuenta en ambas categorías.\nEsto no es un error, sino que refleja cuántas ventas incluyeron cada tipo de producto, incluso si fueron combinados.\nPor la suma total de los siguientes valores puede exceder la cantidad de ventas realizadas por la cadena.\n");

            for (Document doc : ventasPorTipo) {
                String tipoProducto = doc.getString("_id");
                int cantidadVentas = doc.getInteger("totalVentas");
                System.out.println("- " + tipoProducto + ": " + cantidadVentas + " ventas");
            }
        }
    }

    public static void rankingMontoVendidoPorProductoYSucursal(Date desde, Date hasta) {
        try (MongoClient client = MongoClients.create(MongoDBConfig.getMongoURI())) {
            MongoDatabase db = client.getDatabase(MongoDBConfig.getDatabaseName());
            MongoCollection<Document> collection = db.getCollection("ventas");

            List<Document> ranking = collection.aggregate(
                    Arrays.asList(
                            Aggregates.match(and(gte("fecha", desde), lte("fecha", hasta))),
                            Aggregates.unwind("$items"),
                            Aggregates.addFields(
                                    new Field<>("montoItem",
                                            new Document("$multiply", Arrays.asList("$items.cantidad", "$items.precioUnitario"))
                                    )
                            ),
                            Aggregates.group(
                                    new Document()
                                            .append("producto", "$items.producto.descripcion")
                                            .append("sucursal", "$caja.sucursal.nombre"),
                                    Accumulators.sum("montoTotal", "$montoItem"),
                                    Accumulators.sum("cantidadTotal", "$items.cantidad")
                            ),
                            Aggregates.sort(Sorts.descending("montoTotal")),
                            Aggregates.project(
                                    Projections.fields(
                                            Projections.include("montoTotal", "cantidadTotal"),
                                            Projections.computed("producto", "$_id.producto"),
                                            Projections.computed("sucursal", "$_id.sucursal"),
                                            Projections.excludeId()
                                    )
                            )
                    )
            ).into(new ArrayList<>());

            // Impresión con número de ranking
            System.out.println("\n## Ranking de Monto Vendido por Producto y Sucursal ##");
            System.out.println("# Informe entre " + desde + " hasta " + hasta);
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Top | Producto                      | Sucursal    | Cantidad | Monto Total");
            System.out.println("-------------------------------------------------------------------------------");

            int top = 1;
            for (Document doc : ranking) {
                System.out.printf(
                        "%-3d | %-30s | %-11s | %8d | $%,10.2f\n",
                        top++,
                        doc.getString("producto"),
                        doc.getString("sucursal"),
                        doc.getInteger("cantidadTotal"),
                        doc.getDouble("montoTotal")
                );
            }
        }
    }

    public static void rankingCantidadVendidaPorProductoYSucursal(Date desde, Date hasta) {
        try (MongoClient client = MongoClients.create(MongoDBConfig.getMongoURI())) {
            MongoDatabase db = client.getDatabase(MongoDBConfig.getDatabaseName());
            MongoCollection<Document> collection = db.getCollection("ventas");

            List<Document> ranking = collection.aggregate(
                    Arrays.asList(
                            Aggregates.match(and(gte("fecha", desde), lte("fecha", hasta))),
                            Aggregates.unwind("$items"),
                            Aggregates.group(
                                    new Document()
                                            .append("producto", "$items.producto.descripcion")
                                            .append("sucursal", "$caja.sucursal.nombre"),
                                    Accumulators.sum("cantidadTotal", "$items.cantidad"),
                                    Accumulators.sum("montoTotal",
                                            new Document("$multiply", Arrays.asList("$items.cantidad", "$items.precioUnitario"))
                                    )
                            ),
                            Aggregates.sort(Sorts.descending("cantidadTotal")),
                            Aggregates.project(
                                    Projections.fields(
                                            Projections.include("cantidadTotal", "montoTotal"),
                                            Projections.computed("producto", "$_id.producto"),
                                            Projections.computed("sucursal", "$_id.sucursal"),
                                            Projections.excludeId()
                                    )
                            )
                    )
            ).into(new ArrayList<>());

            // Impresión con número de ranking
            System.out.println("\n## Ranking de Cantidad Vendida por Producto y Sucursal ##");
            System.out.println("# Informe entre " + desde + " hasta " + hasta);
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Top | Producto                      | Sucursal    | Cantidad | Monto Total");
            System.out.println("-------------------------------------------------------------------------------");

            DecimalFormat df = new DecimalFormat("#,###");
            DecimalFormat moneyFormat = new DecimalFormat("$#,###.00");
            int top = 1;

            for (Document doc : ranking) {
                String producto = doc.getString("producto");
                System.out.printf(
                        "%-3d | %-30s | %-11s | %8s | %12s\n",
                        top++,
                        producto.length() > 30 ? producto.substring(0, 27) + "..." : producto,
                        doc.getString("sucursal"),
                        df.format(doc.getInteger("cantidadTotal")),
                        moneyFormat.format(doc.getDouble("montoTotal"))
                );
            }
        }
    }

    public static void rankingComprasPorCliente(Date desde, Date hasta) {
        try (MongoClient client = MongoClients.create(MongoDBConfig.getMongoURI())) {
            MongoDatabase db = client.getDatabase(MongoDBConfig.getDatabaseName());
            MongoCollection<Document> collection = db.getCollection("ventas");

            // Pipeline de agregación
            List<Document> ranking = collection.aggregate(
                    Arrays.asList(
                            Aggregates.match(
                                    and(
                                            gte("fecha", desde),
                                            lte("fecha", hasta)
                                    )
                            ),
                            Aggregates.group(
                                    new Document()
                                            .append("idCliente", "$cliente.idCliente")
                                            .append("nombre", "$cliente.nombre")
                                            .append("apellido", "$cliente.apellido")
                                            .append("dni", "$cliente.dni"),
                                    Accumulators.sum("montoTotal", "$total"),
                                    Accumulators.sum("cantidadCompras", 1),
                                    Accumulators.addToSet("sucursales", "$caja.sucursal.nombre")
                            ),
                            Aggregates.addFields(
                                    new Field<>("sucursalesDistintas",
                                            new Document("$size", "$sucursales")
                                    )
                            ),
                            Aggregates.sort(Sorts.descending("montoTotal")),
                            Aggregates.project(
                                    Projections.fields(
                                            Projections.include("montoTotal", "cantidadCompras", "sucursalesDistintas"),
                                            Projections.computed("cliente",
                                                    new Document()
                                                            .append("id", "$_id.idCliente")
                                                            .append("nombreCompleto",
                                                                    new Document("$concat", Arrays.asList(
                                                                            new Document("$ifNull", Arrays.asList("$_id.apellido", "")),
                                                                            ", ",
                                                                            new Document("$ifNull", Arrays.asList("$_id.nombre", ""))
                                                                    ))
                                                            )
                                                            .append("dni", "$_id.dni")
                                            ),
                                            Projections.excludeId()
                                    )
                            )
                    )
            ).into(new ArrayList<>());

            // Impresión del reporte
            System.out.println("\n## Ranking de Compras por Cliente (Total Cadena) ##");
            System.out.println("# Informe entre " + desde + " hasta " + hasta);
            System.out.println("--------------------------------------------------------------------------------------------------");
            System.out.println("Top | ID Cliente | Nombre Completo       | DNI        | Compras | Sucursales | Monto Total");
            System.out.println("--------------------------------------------------------------------------------------------------");

            DecimalFormat df = new DecimalFormat("#,###");
            DecimalFormat moneyFormat = new DecimalFormat("$#,###.00");
            int top = 1;

            for (Document doc : ranking) {
                Document cliente = doc.get("cliente", Document.class);

                // Manejo seguro de tipos
                String idCliente = cliente.get("id") != null ? cliente.get("id").toString() : "N/A";
                String nombreCompleto = cliente.getString("nombreCompleto");
                String dni = cliente.get("dni") != null ? cliente.get("dni").toString() : "N/A";
                int cantidadCompras = doc.getInteger("cantidadCompras");
                int sucursalesDistintas = doc.getInteger("sucursalesDistintas");
                double montoTotal = doc.getDouble("montoTotal");

                System.out.printf(
                        "%-3d | %-9s | %-20s | %-10s | %6s | %9s | %12s\n",
                        top++,
                        idCliente,
                        nombreCompleto,
                        dni,
                        df.format(cantidadCompras),
                        sucursalesDistintas,
                        moneyFormat.format(montoTotal)
                );
            }
        }
    }

    public static void rankingComprasPorClienteYSucursal(Date desde, Date hasta) {
        try (MongoClient client = MongoClients.create(MongoDBConfig.getMongoURI())) {
            MongoDatabase db = client.getDatabase(MongoDBConfig.getDatabaseName());
            MongoCollection<Document> collection = db.getCollection("ventas");

            // Pipeline de agregación corregido
            List<Document> ranking = collection.aggregate(
                    Arrays.asList(
                            Aggregates.match(
                                    and(
                                            gte("fecha", desde),
                                            lte("fecha", hasta)
                                    )
                            ),
                            Aggregates.group(
                                    new Document()
                                            .append("idCliente", "$cliente.idCliente")
                                            .append("nombre", "$cliente.nombre")
                                            .append("apellido", "$cliente.apellido")
                                            .append("dni", "$cliente.dni")
                                            .append("sucursal", "$caja.sucursal.nombre"),
                                    Accumulators.sum("montoTotal", "$total"),
                                    Accumulators.sum("cantidadCompras", 1)
                            ),
                            Aggregates.sort(
                                    Sorts.orderBy(
                                            Sorts.ascending("_id.sucursal"),
                                            Sorts.descending("montoTotal")
                                    )
                            ),
                            // Proyección completamente corregida
                            Aggregates.project(
                                    Projections.fields(
                                            Projections.include("montoTotal", "cantidadCompras"),
                                            Projections.computed("cliente",
                                                    new Document()
                                                            .append("id", "$_id.idCliente")
                                                            .append("nombreCompleto",
                                                                    new Document("$concat", Arrays.asList(
                                                                            "$_id.apellido",
                                                                            ", ",
                                                                            "$_id.nombre"
                                                                    ))
                                                            )
                                                            .append("dni", "$_id.dni")
                                            ),
                                            Projections.computed("sucursal", "$_id.sucursal"),
                                            Projections.excludeId()
                                    )
                            )
                    )
            ).into(new ArrayList<>());

            // Resto del código de impresión...
            System.out.println("\n## Ranking de Compras por Cliente y Sucursal ##");
            System.out.println("# Informe entre " + desde + " hasta " + hasta);
            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("Sucursal      | Top | ID Cliente | Nombre Completo       | DNI        | Compras | Monto Total");
            System.out.println("----------------------------------------------------------------------------------------------------");

            DecimalFormat df = new DecimalFormat("#,###");
            DecimalFormat moneyFormat = new DecimalFormat("$#,###.00");
            String currentSucursal = "";
            int top = 1;

            for (Document doc : ranking) {
                String sucursal = doc.getString("sucursal");
                Document cliente = doc.get("cliente", Document.class);

                if (!sucursal.equals(currentSucursal)) {
                    currentSucursal = sucursal;
                    top = 1;
                }

                System.out.printf(
                        "%-13s | %-3d | %-9s | %-20s | %-10s | %6s | %12s\n",
                        sucursal,
                        top++,
                        cliente.get("id") != null ? cliente.get("id").toString() : "N/A",
                        cliente.getString("nombreCompleto"),
                        cliente.get("dni") != null ? cliente.get("dni").toString() : "N/A",
                        df.format(doc.getInteger("cantidadCompras")),
                        moneyFormat.format(doc.getDouble("montoTotal"))
                );
            }
        }
    }
}