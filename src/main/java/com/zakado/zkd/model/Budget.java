package com.zakado.zkd.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    private int id;
    private int idmov;
    private double budgetIni;
    private double budgetCurrent;
}
