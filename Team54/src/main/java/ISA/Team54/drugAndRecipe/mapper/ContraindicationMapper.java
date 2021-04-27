package ISA.Team54.drugAndRecipe.mapper;

import ISA.Team54.drugAndRecipe.dto.ContraindicationDTO;
import ISA.Team54.drugAndRecipe.model.Contraindication;

public class ContraindicationMapper {
	
	public static Contraindication ContraindicationDTOToContraindication(ContraindicationDTO contraindicationDTO) {
		Contraindication contraindication = new Contraindication(contraindicationDTO.getName());
		return contraindication;
	}
	
	public static ContraindicationDTO ContraindicationToContraindicationDTO(Contraindication contraindication) {
		ContraindicationDTO contraindicationDTO = new ContraindicationDTO(contraindication.getName());
		return contraindicationDTO;
	}
	
}
