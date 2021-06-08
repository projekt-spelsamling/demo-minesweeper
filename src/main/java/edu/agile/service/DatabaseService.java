package edu.agile.service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class DatabaseService {
    private static final String HOST = "localhost";
    private static final String PORT = "27017";
    private static final String DB_NAME = "database-name";
    private static final DatabaseService INSTANCE = new DatabaseService();

    private final MongoDatabase database;

    public static DatabaseService getInstance() {
        return INSTANCE;
    }

    private DatabaseService() {
        ConnectionString connectionString = getConnectionString();
        CodecRegistry codecRegistry = buildCodecRegistry();
        MongoClientSettings clientSettings = buildMongoClientSettings(connectionString, codecRegistry);
        MongoClient client = MongoClients.create(clientSettings);
        this.database = client.getDatabase(DB_NAME);
    }


    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    private static ConnectionString getConnectionString() {
        return new ConnectionString(String.format("mongodb://%s:%s", HOST, PORT));
    }

    /**
     * @param connectionString
     * @param codecRegistry
     * @return
     */
    private static MongoClientSettings buildMongoClientSettings(ConnectionString connectionString, CodecRegistry codecRegistry) {
        return MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
    }

    /**
     * Combines the default codec registry with a custom one that handles pojo objects.
     *
     * @return a codec
     */
    private static CodecRegistry buildCodecRegistry() {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        return CodecRegistries.fromRegistries(defaultCodecRegistry, pojoCodecRegistry);
    }
}
