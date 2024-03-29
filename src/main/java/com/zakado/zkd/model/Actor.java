package com.zakado.zkd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    private Integer nid;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "es-ES", timezone = "Europe/Madrid")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String cob;
    private String image;
    private String genre;

    @Override
    public String toString() {
        return "nid=" + nid +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", cob='" + cob + '\'';
    }
}
