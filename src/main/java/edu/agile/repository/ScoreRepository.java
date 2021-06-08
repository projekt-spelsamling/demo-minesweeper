package edu.agile.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import edu.agile.service.DatabaseService;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Handles CRUD for Scores Collection in local MongoDB
 */
public class ScoreRepository {
    private static final String COLLECTION_NAME = "scores";
    private static ScoreRepository instance;

    private final MongoCollection<Document> scoreCollection;

    private ScoreRepository(String collectionName) {
        this.scoreCollection = DatabaseService.getInstance().getCollection(collectionName);
    }

    public static ScoreRepository getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ScoreRepository(COLLECTION_NAME);
        }
        return instance;
    }

    /**
     * Add a score to the database
     *
     * @param document score to add
     */
    public void addScore(Document document) {
        scoreCollection.insertOne(document);
    }

    /**
     * Finds the highest scores
     *
     * @param size how many scores should be included
     * @return list of documents containing the scores
     */
    public List<Document> findHighScore(int size) {
        List<Document> allScores = new ArrayList<>();
        try (MongoCursor<Document> cursor = scoreCollection.find().sort(new BasicDBObject("score", -1)).limit(size).iterator()) {
            cursor.forEachRemaining(allScores::add);
        }
        return allScores;
    }
}
