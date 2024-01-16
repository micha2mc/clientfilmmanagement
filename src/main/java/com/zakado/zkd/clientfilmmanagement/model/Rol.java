package com.zakado.zkd.clientfilmmanagement.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    private Integer idRol;

    private String authority;

    public Rol(String idRolAndName) {
        if (idRolAndName != null && idRolAndName.length() > 0) {
            String[] fieldPositions = idRolAndName.split("-");
            this.idRol = Integer.parseInt(fieldPositions[0]);
            this.authority = fieldPositions[1];
        }
    }
}
