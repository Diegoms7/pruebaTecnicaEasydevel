package modelo;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;


@Builder(toBuilder = true)
@Value
@Jacksonized
public class SectionApi {

	@NotBlank
	int id;

	String nombre;
}
