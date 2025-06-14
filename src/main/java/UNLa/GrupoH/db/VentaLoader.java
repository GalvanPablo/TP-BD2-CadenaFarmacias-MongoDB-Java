package UNLa.GrupoH.db;
import UNLa.GrupoH.config.MongoDBConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import UNLa.GrupoH.model.Venta;

public class VentaLoader {
    public static void reiniciarVentas(List<Venta> ventas) {
        ObjectMapper mapper = new ObjectMapper();

        // Configurar Jackson para escribir fechas como Strings ISO-8601 (ej: "2025-05-25T20:40:00.000Z")
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));  // Asegura formato ISO

        List<Document> documentos = new ArrayList<>();

        for (Venta venta : ventas) {
            try {
                String json = mapper.writeValueAsString(venta);
                Document doc = Document.parse(json);

                // Convertir manualmente el String de fecha a java.util.Date (si es necesario)
                if (doc.containsKey("fecha")) {
                    String fechaStr = doc.getString("fecha");
                    Date fechaDate = mapper.readValue("\"" + fechaStr + "\"", Date.class);
                    doc.put("fecha", fechaDate);  // Ahora se guardará como ISODate en MongoDB
                }

                documentos.add(doc);
            } catch (IOException e) {
                System.err.println("Error al convertir una venta a JSON: " + e.getMessage());
            }
        }

        try (MongoClient client = MongoClients.create(MongoDBConfig.getMongoURI())) {
            MongoDatabase db = client.getDatabase(MongoDBConfig.getDatabaseName());
            MongoCollection<Document> collection = db.getCollection("ventas");

            // Borrar la colección antes de insertar
            collection.drop();

            if (!documentos.isEmpty()) {
                collection.insertMany(documentos);
                System.out.println("Se reinició la colección y se insertaron " + documentos.size() + " ventas.");
            } else {
                System.out.println("No se insertó ninguna venta.");
            }
        }
    }


}
