package com.zakado.zkd.clientfilmmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    private Integer nid;
    private String name;
    private String dni;
    private String dob;
    private String cob;
    private String image;
    private String status;
    private String genre;

    @Override
    public String toString() {
        return "nid=" + nid +
                ", name='" + name + '\'' +
                ", dni='" + dni + '\'' +
                ", dob='" + dob + '\'' +
                ", cob='" + cob + '\'';
    }
}
