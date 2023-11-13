package repositorio;

import entidades.Section;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends CrudRepository<Section, Integer> {

	//creamos la interfaz con la cual interactuaremos con la base de datos
}
