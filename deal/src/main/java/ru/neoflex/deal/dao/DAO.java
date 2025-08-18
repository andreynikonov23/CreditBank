package ru.neoflex.deal.dao;

import java.util.List;

public interface DAO <Entity, Id>{
    List<Entity> getAll();
    Entity findById(Id id);
    void savaAndFlush(Entity entity);
    void delete(Entity entity);
}
