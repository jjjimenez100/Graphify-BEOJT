package io.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class QueryResolver implements GraphQLQueryResolver {
   @Autowired
   private final PersonService personService;

   public QueryResolver(PersonService personService) {
      this.personService = personService;
   }

   public final Iterable<Person> persons() {
      return personService.getAll();
   }

   public final Person person(Integer id) {
      return personService.getById(id).orElseThrow(RuntimeException::new);
   }
}
