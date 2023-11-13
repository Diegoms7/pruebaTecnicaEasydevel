package entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "t_section")
@AllArgsConstructor
@NoArgsConstructor
public class Section {

	//Entidad de la base de datos

	@Id
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "nombre", nullable = false)
	private String nombre;
}

