package crm.myhrcrmproject.service;
import crm.myhrcrmproject.service.utills.Converter;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericService<T, RequestDTO, ResponseDTO> {
//    private final Converter<T, RequestDTO, ResponseDTO> converter;


    protected abstract JpaRepository<T, Integer> getRepository();
//    protected abstract boolean entityCreateValidation(RequestDTO requestDTO);
    protected abstract T entityAfterCreateProcedures(T entity, RequestDTO requestDTO);
    protected abstract T entityAfterUpdateProcedures(T entity, RequestDTO requestDTO);
    protected abstract Converter<T, RequestDTO, ResponseDTO> getConverter();

    public List<ResponseDTO> findAll() {
        return getRepository().findAll().stream()
                .map(getConverter()::toDTO)
                .collect(Collectors.toList());
    }
    public ResponseDTO findById(Integer id) {
        T entity = getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));
        return getConverter().toDTO(entity);
    }

    public ResponseDTO create(RequestDTO requestDTO) {
        T entity = getConverter().fromDTO(getConverter().newEntity(), requestDTO);
        entityAfterCreateProcedures(entity, requestDTO);
        return getConverter().toDTO(getRepository().save(entity));
    }

    public void delete(Integer id) {
        T entity = getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id: " + id + " not found!"));
        getRepository().delete(entity);
    }

    public ResponseDTO update(Integer id, RequestDTO requestDTO) {
        T existingEntity = getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id: " + id + " not found!"));

        // filled in existing fields with new dates
        getConverter().fromDTO(existingEntity, requestDTO);
        entityAfterUpdateProcedures(existingEntity, requestDTO);

        getRepository().save(existingEntity);

        return getConverter().toDTO(existingEntity);
    }

}
