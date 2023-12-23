package com.zakado.zkd.clientfilmmanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nid;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "es-ES", timezone = "Europe/Madrid")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
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
