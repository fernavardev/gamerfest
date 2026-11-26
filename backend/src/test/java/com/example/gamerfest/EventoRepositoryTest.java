package com.example.gamerfest;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class EventoRepositoryTest {

    @Test
    @SuppressWarnings("unchecked")
    void deberiaFiltrarEventosPorNombreNormalizado() {
        Root<Evento> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Path<String> nombre = mock(Path.class);
        Expression<String> nombreNormalizado = mock(Expression.class);
        Predicate filtro = mock(Predicate.class);
        Predicate resultadoEsperado = mock(Predicate.class);

        when(root.<String>get("nombre")).thenReturn(nombre);
        when(cb.lower(nombre)).thenReturn(nombreNormalizado);
        when(cb.like(nombreNormalizado, "%gamerfest%"))
                .thenReturn(filtro);
        when(cb.and(new Predicate[]{filtro}))
                .thenReturn(resultadoEsperado);

        var specification = EventoRepository.conFiltros(
                " GamerFest ", null, null, null, null, null, null);

        Predicate resultado = specification.toPredicate(root, query, cb);

        assertSame(resultadoEsperado, resultado);
        verify(cb).like(nombreNormalizado, "%gamerfest%");
    }
}