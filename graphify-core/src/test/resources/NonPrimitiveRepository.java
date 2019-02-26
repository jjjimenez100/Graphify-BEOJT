package io.query;

import org.springframework.data.repository.CrudRepository;

public interface NonPrimitiveRepository extends CrudRepository<NonPrimitive, String> {
}
