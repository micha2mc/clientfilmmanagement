package com.zakado.zkd.clientfilmmanagement.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Reviews {
    private Integer id;
    private Integer idMovie;
    private String assessment;
    private Integer note;
    private LocalDate date;
    private User user;
}
