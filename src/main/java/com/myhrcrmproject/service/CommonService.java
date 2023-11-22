package com.myhrcrmproject.service;

import java.util.List;

public interface CommonService<Q, R> {

    List<R> findAll();

    R findById(Integer id);

    R create(Q requestDTO);

    R update(Integer id, Q requestDTO);

    void delete(Integer id);
}