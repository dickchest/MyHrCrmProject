package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.service.validation.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Function;

public class EntityUtils {
    public static <T> T findByIdOrThrow(JpaRepository<T, Integer> repository, Integer id, String entityName) {
        return repository.findById(id)
                .orElseThrow((() -> new NotFoundException(entityName + " with id " + id + " not found")));
    }

}
