package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.dto.candidatesDTO.CandidatesResponseDTO;

public interface Converter<T, RequestDTO, ResponseDTO> {
    public ResponseDTO toDTO (T entity);
    public T fromDTO (T entity, RequestDTO dto);
    public T newEntity();
}
