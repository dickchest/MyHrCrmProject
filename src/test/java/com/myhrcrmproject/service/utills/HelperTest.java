package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.UserDetails;
import com.myhrcrmproject.service.validation.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelperTest {
    @Mock
    JpaRepository<UserDetails, Integer> repository;
    @InjectMocks
    private Helper helper;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void setEntityById_WhenEntityNotExists_ShouldThrowNotFoundException() {
        // given
        int entityId = 1;
        Supplier<Integer> getFromRequestFunction = () -> entityId;
        Consumer<UserDetails> setToEntityFunction = entity -> {};

        when(repository.findById(entityId)).thenReturn(Optional.empty());

        // when
        Executable executable = () ->
                Helper.setEntityById(getFromRequestFunction, setToEntityFunction, repository, "Test Entity");

        // then
        assertThrows(NotFoundException.class, executable);
    }

    @Test
    void findAllByEntityId_WhenEntityNotExists_ShouldThrowNotFoundException() {
        // given
        int entityId = 1;

        when(repository.findById(entityId)).thenReturn(Optional.empty());

        // when
        Executable executable = () ->
                Helper.findAllByEntityId(entityId, repository, e -> Collections.emptyList(), Function.identity());

        // then
        assertThrows(NotFoundException.class, executable);
    }

}