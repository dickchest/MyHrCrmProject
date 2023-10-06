package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.service.validation.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Helper {

    public static <T> void setEntityById(
            Supplier<Integer> getFromRequestFunction,
            Consumer<T> setToEntityFunction,
            JpaRepository<T, Integer> repository,
            String entityName
    ) {

        Integer id = getFromRequestFunction.get();
        if (id != null) {
            setToEntityFunction.accept(
                    repository.findById(id)
                            .orElseThrow(
                                    () -> new NotFoundException(entityName + " with id " + id + " not found")
                            )
            );
        }
    }

//    public static <T> T findByIdOrThrow(
//            JpaRepository<T, Integer> repository,
//            Integer id,
//            String entityName) {
//        return repository.findById(id)
//                .orElseThrow(() -> new NotFoundException(entityName + " with id " + id + " not found"));
//    }

    public static <E, T, R> List<R> findAllByEntityId(
            Integer id,
            JpaRepository<T, Integer> repository, // репозиторий сущности которую ищем по id
            Function<T, List<E>> taskMappingFunction, // метод поиска сущности по репозиторию
            Function<E, R> dtoMappingFunction // конвертор из сущности в ДТО
    ) {
        T entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));
        List<E> list = taskMappingFunction.apply(entity);
        return list.stream()
                .map(dtoMappingFunction::apply)
                .toList();
    }

    public static <T extends Enum<T>, E, R> List<R> findAllByEnumId(
            Integer id,
            Class<T> enumType,
            Function<T, List<E>> taskMappingFunction,
            Function<E, R> dtoMappingFunction) {

        T enumValue;
        T[] enumConstants = enumType.getEnumConstants();
        if (enumConstants != null && id >= 0 && id < enumConstants.length) {
            enumValue = enumConstants[id];
        } else {
            throw new NotFoundException("No enum found with id: " + id);
        }

        List<E> list = taskMappingFunction.apply(enumValue);
        return list.stream()
                .map(dtoMappingFunction)
                .toList();
    }

//    public static <T extends Enum<T>> T getEnumFromString(Class<T> enumType, String value) {
//        try {
//            return Enum.valueOf(enumType, value.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            throw new NotFoundException("Invalid enum value: " + value);
//        }
}
