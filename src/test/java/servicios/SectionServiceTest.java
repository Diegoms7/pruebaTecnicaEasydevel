package servicios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import entidades.Section;
import java.util.NoSuchElementException;
import java.util.Optional;
import modelo.SectionApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import repositorio.SectionRepository;

@ExtendWith(MockitoExtension.class)
class SectionServiceTest {

	private static final ValueOperations<Integer, Section> VALUE_OPERATIONS_MOCK = mock(ValueOperations.class);
	public static final String EXCEPTION_ERROR_MESSAGE = "No se encontró la sección con ID: ";
	private static final int SECTION_ID = 5;
	private static final String SECTION_NAME = "SECCIÓN";

	private static final Section SECTION = Section.builder()
		.id(SECTION_ID)
		.nombre(SECTION_NAME)
		.build();

	private static final SectionApi SECTION_API = SectionApi.builder()
		.id(SECTION_ID)
		.nombre(SECTION_NAME)
		.build();

	@Mock
	private SectionRepository sectionRepository;

	@Mock
	private RedisTemplate<Integer, Section> redisTemplate;

	@InjectMocks
	private SectionService sectionService;

	@Test
	void should_save_section(){

		given(this.sectionRepository.save(SECTION))
			.willReturn(SECTION);

		assertThat(this.sectionService.addSection(SECTION_API))
			.usingRecursiveComparison()
			.isEqualTo(SECTION);
	}

	@Test
	void should_get_section_by_id_found_in_redis(){

		given(this.redisTemplate.opsForValue())
			.willReturn(VALUE_OPERATIONS_MOCK);

		given(this.redisTemplate.opsForValue().get(SECTION_ID))
			.willReturn(SECTION);

		assertThat(this.sectionService.getSectionById(SECTION_ID))
			.usingRecursiveAssertion()
			.isEqualTo(SECTION);
	}

	@Test
	void should_save_section_in_redis_if_found_in_database(){

		given(this.redisTemplate.opsForValue())
			.willReturn(VALUE_OPERATIONS_MOCK);

		given(this.redisTemplate.opsForValue().get(SECTION_ID))
			.willReturn(null);

		given(this.sectionRepository.findById(SECTION_ID))
			.willReturn(Optional.of(SECTION));

		this.sectionService.getSectionById(SECTION_ID);

		verify(VALUE_OPERATIONS_MOCK).set(SECTION_ID, SECTION);
	}

	@Test
	void should_throw_exception_when_section_not_found(){

		given(this.redisTemplate.opsForValue())
			.willReturn(VALUE_OPERATIONS_MOCK);

		given(this.redisTemplate.opsForValue().get(SECTION_ID))
			.willReturn(null);

		given(this.sectionRepository.findById(SECTION_ID))
			.willReturn(Optional.empty());

		assertThatThrownBy(() -> this.sectionService.getSectionById(SECTION_ID))
			.isInstanceOf(NoSuchElementException.class)
			.hasMessage(EXCEPTION_ERROR_MESSAGE + SECTION_ID);
	}
}