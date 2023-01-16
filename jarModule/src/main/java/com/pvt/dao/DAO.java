package com.pvt.dao;


import com.pvt.exception.MyException;

public interface DAO<T,K> {
    T save(T t) throws MyException;

    T get(K k);

    void update(T t) throws MyException;

    void delete(K k);
}
