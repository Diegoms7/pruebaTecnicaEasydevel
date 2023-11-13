package controladores;

import static controladores.SectionMappingConstants.SECTION_BASE_MAPPING;
import static controladores.SectionMappingConstants.SECTION_BY_ID_MAPPING;

import lombok.RequiredArgsConstructor;
import modelo.SectionApi;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import servicios.SectionService;

@RestController
@RequestMapping(SECTION_BASE_MAPPING)
@RequiredArgsConstructor
@Validated
public class SectionController {

	final SectionService sectionService;

	//Creamos el endpoint desde el cual encontraremos la sección. Añadimos en el path el id único por el cual buscaremos.
	@GetMapping(SECTION_BY_ID_MAPPING)
	public SectionApi getSectionById(@PathVariable final int idSection) {

		//Llamamos al servicio para que realice las operaciones
		return this.sectionService.getSectionById(idSection);
	}

	//Creamos el endpoint desde el cual guardaremos la sección. Solicitamos que venga el objeto a guardar en la request.
	@PostMapping
	public SectionApi addSection(@Validated @RequestBody final SectionApi request){

		//Llamamos al servicio para que realice las operaciones
		return this.sectionService.addSection(request);
	}
}
