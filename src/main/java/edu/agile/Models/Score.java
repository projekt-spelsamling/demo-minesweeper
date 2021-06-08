package edu.agile.Models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {
    private int points;
    private String player;
    private String game;
}
