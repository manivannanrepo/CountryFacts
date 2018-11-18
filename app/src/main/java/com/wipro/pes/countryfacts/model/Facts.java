package com.wipro.pes.countryfacts.model;

import java.io.Serializable;
import java.util.List;

public class Facts implements Serializable {

    private String title;
    private List<Row> rows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public List<Row> getRows() {
        return rows;

    }

}
