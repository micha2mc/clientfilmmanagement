package com.zakado.zkd.clientfilmmanagement.paginator;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageRender<T> {
    private String url;
    private Page<T> page;

    private int totalPaginas;
    private int numElementosPorPagina;
    private int paginaActual;
    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<>();

        numElementosPorPagina = 5;
        totalPaginas = page.getTotalPages();
        paginaActual = page.getNumber() + 1;
        int desde, hasta;

        if (totalPaginas <= numElementosPorPagina) {
            desde = 1;
            hasta = totalPaginas;
        } else {
            hasta = numElementosPorPagina;
            if (paginaActual <= numElementosPorPagina / 2) {
                desde = 1;

            } else if (paginaActual >= totalPaginas - numElementosPorPagina / 2) {
                desde = totalPaginas - numElementosPorPagina + 1;

            } else {
                desde = totalPaginas - numElementosPorPagina / 2;
            }
        }
        for (int i = 0; i < hasta; i++) {
            paginas.add(new PageItem(desde + i, paginaActual == desde + i));
        }

    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean isHasNext() {
        return page.hasNext();
    }

    public boolean isHasPrevious() {
        return page.hasPrevious();
    }
}
