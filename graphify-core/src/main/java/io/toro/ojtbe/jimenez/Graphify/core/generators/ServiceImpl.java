package io.toro.ojtbe.jimenez.Graphify.core.generators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class ServiceImpl<T, S> implements Service<T, S>{

    @Autowired
    private final CrudRepository<T, S> repository;

    private final Logger logger;

    public ServiceImpl(CrudRepository<T, S> repository){
        this.repository = repository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public Iterable<T> getAll() {
        logger.debug("Call on findAll() at " + this.getClass());
        return repository.findAll();
    }

    @Override
    public Optional<T> getById(S id) {
        logger.debug("Call on getById() at " + this.getClass());
        return repository.findById(id);
    }

    @Override
    public T save(T newEntity) {
        logger.debug("Call on save() at " + this.getClass());
        return repository.save(newEntity);
    }

    @Override
    public void delete(S id) {
        logger.debug("Call on delete() at " + this.getClass());
        Optional<T> match = getById(id);
        if(match.isPresent()){
            repository.deleteById(id);
        }
    }
}