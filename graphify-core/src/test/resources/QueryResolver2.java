package io.query;

import com.aso.Catto;
import com.aso.CattoService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class QueryResolver implements GraphQLQueryResolver {
   @Autowired
   private final DoggoService doggoService;

   @Autowired
   private final CattoService cattoService;

   public QueryResolver(DoggoService doggoService, CattoService cattoService) {
      this.doggoService = doggoService;
      this.cattoService = cattoService;
   }

   public final Iterable<Doggo> doggos() {
      return doggoService.getAll();
   }

   public final Doggo doggo(Integer id) {
      return doggoService.getById(id).orElseThrow(RuntimeException::new);
   }

   public final Iterable<Catto> cattos() {
      return cattoService.getAll();
   }

   public final Catto catto(String id) {
      return cattoService.getById(id).orElseThrow(RuntimeException::new);
   }
}
