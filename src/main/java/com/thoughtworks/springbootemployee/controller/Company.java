package com.thoughtworks.springbootemployee.controller;

public class Company {
    private final Long id;
    private final String name;

    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
