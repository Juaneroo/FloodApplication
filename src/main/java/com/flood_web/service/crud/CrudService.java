package com.flood_web.service.crud;

import java.util.List;
import java.util.Optional;

public interface CrudService<Model> {

    void save(Model obj);

    List<Model> listAll();

    Optional<Model> findById(String id);
}
