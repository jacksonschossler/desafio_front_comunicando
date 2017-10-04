package br.com.agenda.registro.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.agenda.registro.domain.entity.Registro;

public interface IRegistroRepository extends JpaRepository<Registro, Long> {
	
	@Query(value=" FROM Registro registro " + 
			"WHERE ("
			+ " ((:mes IS NULL) OR ( month(registro.data) = :mes) )  "
			+ " AND ((:ano IS NULL) OR (year(registro.data) = :ano))  "
			+ " AND ((:categoriaId IS NULL) OR (registro.categoria.id = :categoriaId)) "
			+ ")"
			)
	public Page<Registro> listByFilters (   @Param("mes") Integer mes,
											@Param("ano") Integer ano,
											@Param("categoriaId") Long categoriaId,
											Pageable pageable );	
	
	
}
