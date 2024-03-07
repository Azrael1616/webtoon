package com.boot.board_240214.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
//@Table(name = "webtoon")
@Data
public class Webtoon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull

    private String title;
    private String category;
}
