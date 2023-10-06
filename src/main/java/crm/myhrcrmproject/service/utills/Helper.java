package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.service.validation.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class Helper {
    public static <T> T findByIdOrThrow(JpaRepository<T, Integer> repository, Integer id, String entityName) {
        return repository.findById(id)
                .orElseThrow((() -> new NotFoundException(entityName + " with id " + id + " not found")));
    }

//    public static <T extends Enum<T>> T getEnumFromString(Class<T> enumType, String value) {
//        try {
//            return Enum.valueOf(enumType, value.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            throw new NotFoundException("Invalid enum value: " + value);
//        }
}
