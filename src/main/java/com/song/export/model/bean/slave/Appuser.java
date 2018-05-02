package com.song.export.model.bean.slave;

public class Appuser {
    private Integer id;

    private String name;

    public Appuser(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Appuser() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}