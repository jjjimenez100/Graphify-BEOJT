package io.query;

import io.toro.ojtbe.jimenez.Graphify.core.generators.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@Transactional
@Autowired
public class TwoSService extends ServiceImpl<TwoS, String> {
   @Autowired
   public TwoSService(TwoSRepository twoSRepository) {
      super(twoSRepository);
   }
}
