package com.zakado.zkd.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    private Integer id;

    private String authority;

    public Rol(String idRolAndName) {
        if (idRolAndName != null && !idRolAndName.isEmpty()) {
            String[] fieldPositions = idRolAndName.split("-");
            this.id = Integer.parseInt(fieldPositions[0]);
            this.authority = fieldPositions[1];
        }
    }

    @Override
    public String toString() {
        return "" + id + "-" + this.authority;
    }
}
