package br.com.agenda.categoria.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.agenda.categoria.domain.entity.Categoria;
import br.com.agenda.categoria.domain.entity.Tipo;

public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
	
//	@Query(value=" FROM Categoria categoria " + 
//					"WHERE ("
//					+ " ((:tipo IS NULL) OR (categoria.tipo = :tipo)) "
//					+ " AND FILTER(categoria.nome, :nome) = TRUE "
//					+ " AND FILTER(categoria.descricao, :descricao) = TRUE "
//					+ " AND ((:desativada IS NULL) OR (categoria.desativada = :desativada)) "
//					+")"
//			)
//	public Page<Categoria> listByFilters (  @Param("tipo") Tipo tipo,
//											@Param("nome") String nome,
//											@Param("descricao") String descricao,
//											@Param("desativada") Boolean desativada,
//											Pageable pageable );
								
			
			
			
	@Query(value=" FROM Categoria categoria " +
					" WHERE " 
					//+ " ((:tipo IS NULL) OR (categoria.tipo = :tipo)) "
					+ " (FILTER(categoria.nome, :nome) = TRUE) "
					//+ " AND ((:nome IS NULL) OR (:nome like '%' || categoria.nome || '%' )) "
				
	)
	public Page<Categoria> listByFilters ( // @Param("tipo") Tipo tipo,
											@Param("nome") String nome,
									Pageable pageable );
			
	
			
											
	
	


	@Query(value=" FROM Categoria categoria " +
			" WHERE " 
			+ " ((:tipo IS NULL) OR (categoria.tipo = :tipo)) "
			+ " AND (FILTER(categoria.nome, :nome) = TRUE) "
			+ " AND (FILTER(categoria.descricao, :descricao) = TRUE) "
	)
	public Page<Categoria> listByFiltersFull (  @Param("tipo") Tipo tipo,
											@Param("nome") String nome,
											@Param("descricao") String descricao,
											Pageable pageable );



		





}
