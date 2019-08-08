package com.mashangshouche.car.common;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface BaseService<E, ID extends Serializable> {

    public JpaRepository<E, ID> getRepository();

    /**
     * 根据ID获取
     */
    public default Optional<E> findById(ID id) {
        return getRepository().findById(id);
    }

    /**
     * 保存
     */
    public default E save(E entity) {
        return getRepository().save(entity);
    }

    /**
     * 修改
     */
    public default E update(E entity) {
        return getRepository().saveAndFlush(entity);
    }


    /**
     * 根据Id删除
     */
    public default void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    /**
     * 清空缓存，提交持久化
     */
    public default void flush() {
        getRepository().flush();
    }

    /**
     * Example查询
     */
    public default Page<E> findAll(Example<E> example, Pageable page) {
        return getRepository().findAll(example, page);
    }
}
