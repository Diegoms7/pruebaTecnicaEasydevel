package servicios;

import static java.util.Objects.isNull;

import entidades.Section;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import modelo.SectionApi;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import repositorio.SectionRepository;

@Service
@RequiredArgsConstructor
//Clase encargada de las operaciones
public class SectionService {

	public static final String EXCEPTION_ERROR_MESSAGE = "No se encontró la sección con ID: ";

	final SectionRepository sectionRepository;
	final RedisTemplate<Integer, Section> redisTemplate;

	public SectionApi getSectionById(final int idSection) {

		//Primero consultamos si tenemos la sección determinada en Redis
		final Section sectionInRedis = this.getSectionByIdThroughRedis(idSection);

		//Comprobamos la info que nos llega de Redis
		final Section sectionToReturn = isNull(sectionInRedis)
			? this.getSectionByIdThroughDatabase(idSection) //Si es null, buscamos en la base de datos
			: sectionInRedis; //Si devuelve información, será la info que devolveremos

		return this.buildSectionApi(sectionToReturn);
	}

	public SectionApi addSection(final SectionApi sectionApi) {

		final Section sectionToSave = this.buildSection(sectionApi);

		//Guardamos el section que nos llega desde el controlador. Llega validado gracias a la etiqueta @Validated del controller
		return this.buildSectionApi(this.sectionRepository.save(sectionToSave));
	}

	private Section getSectionByIdThroughRedis(final int idSection){

		//llamamos a redis buscando por el id
		return this.redisTemplate.opsForValue().get(idSection);
	}

	private Section getSectionByIdThroughDatabase(final int idSection) {

		//Buscamos por id en la base de datos. Si no lo encuentra, lanza excepción
		final Section sectionInDatabase = this.sectionRepository.findById(idSection)
			.orElseThrow(() -> new NoSuchElementException(EXCEPTION_ERROR_MESSAGE + idSection));

		//si lo encuentra, lo guarda en Redis y lo devuelve al controller.
		return this.addSectionToRedis(sectionInDatabase);
	}

	private Section addSectionToRedis(final Section section) {

		//Guardamos la sección en Redis
		this.redisTemplate.opsForValue().set(section.getId(), section);

		return section;
	}

	private SectionApi buildSectionApi(final Section section){

		return SectionApi.builder()
			.id(section.getId())
			.nombre(section.getNombre())
			.build();
	}

	private Section buildSection(final SectionApi sectionApi){

		return Section.builder()
			.id(sectionApi.getId())
			.nombre(sectionApi.getNombre())
			.build();
	}
}
