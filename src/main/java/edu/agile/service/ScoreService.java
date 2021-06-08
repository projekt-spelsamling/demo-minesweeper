package edu.agile.service;

import edu.agile.Models.Score;
import edu.agile.repository.ScoreRepository;
import org.bson.Document;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ScoreService {
    private static ScoreService instance;

    private ScoreRepository scoreRepository;

    private ScoreService() {
        this.scoreRepository = ScoreRepository.getInstance();
    }

    public static ScoreService getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ScoreService();
        }
        return instance;
    }

    public void addScore(Score score) {
        scoreRepository.addScore(toDocument(score));
    }


    /**
     * Find all games in the database
     *
     * @return list of documents
     */
    public List<Score> findHighScore(int size) {
        return scoreRepository.findHighScore(size)
                .stream()
                .map(ScoreService::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Converts document to score entity
     *
     * @param document score document
     * @return Score object
     */
    private static Score toEntity(Document document) {
        return Score.builder()
                .points(document.getInteger("score"))
                .player(document.getString("player"))
                .game(document.getString("game"))
                .build();
    }

    private static Document toDocument(Score score) {
        //todo name should be optional (don't add null name)
        return new Document().append("score", score.getPoints())
                .append("player", score.getPlayer())
                .append("game", score.getGame());
    }
}
