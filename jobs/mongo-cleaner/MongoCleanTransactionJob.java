package com.kynsoft.job;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.time.Instant;
import java.util.Date;

public class MongoCleanTransactionJob {
    public static void main(String[] args) {
        String mongoUri = System.getenv().getOrDefault("MONGO_CONNECTION_STRING", "mongodb://localhost:27017");
        String dbName = System.getenv().getOrDefault("MONGO_DATABASE_NAME", "paymentsDb");

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(dbName);
            MongoCollection<Document> collection = database.getCollection("transactions");

            Instant now = Instant.now();

            var filter = Filters.and(
                    Filters.lt("Expiration", Date.from(now)),
                    Filters.or(
                            Filters.eq("CurrentStatus", null),
                            Filters.eq("CurrentStatus", "")
                    )
            );
            var result = collection.deleteMany(filter);
            System.out.println("🧹 Se eliminaron " + result.getDeletedCount() + " transacciones caducadas/no aprobadas.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("💥 Error al intentar limpiar transacciones: " + e.getMessage());
        }
    }
}
