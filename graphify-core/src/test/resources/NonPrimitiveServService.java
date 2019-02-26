package io.query;

import io.toro.ojtbe.jimenez.Graphify.core.generators.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@Transactional
@Autowired
public class NonPrimitiveServService extends ServiceImpl<NonPrimitiveServ, String> {
   @Autowired
   public NonPrimitiveServService(NonPrimitiveServRepository nonPrimitiveServRepository) {
      super(nonPrimitiveServRepository);
   }
}
