package io.query;

import io.toro.ojtbe.jimenez.Graphify.core.generators.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@Transactional
@Autowired
public class OneSService extends ServiceImpl<OneS, Integer> {
   @Autowired
   public OneSService(OneSRepository oneSRepository) {
      super(oneSRepository);
   }
}
