package controladores;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;

//Clase donde guardaremos los mappings de los endpoints
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SectionMappingConstants {

	public static final String SECTION_BASE_MAPPING = "/section";
	public static final String SECTION_BY_ID_MAPPING = SECTION_BASE_MAPPING + "/{idSection}";
}
