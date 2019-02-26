package io.query;

import io.toro.ojtbe.jimenez.Graphify.core.generators.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@Transactional
@Autowired
public class PrimitiveServService extends ServiceImpl<PrimitiveServ, Integer> {
   @Autowired
   public PrimitiveServService(PrimitiveServRepository primitiveServRepository) {
      super(primitiveServRepository);
   }
}
