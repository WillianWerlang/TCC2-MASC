package org.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<_masc_usuario, Integer> {

    List<_masc_usuario> findByNome(String name);

    _masc_usuario findById(int id);

    _masc_usuario findCustomerById(Integer id);
}