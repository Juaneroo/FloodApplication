package com.flood_web.service;

import java.util.List;

public interface CrudService<Model> {

    void save(Model obj);

    List<Model> listAll();
}
