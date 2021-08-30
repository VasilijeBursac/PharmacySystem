package ISA.Team54.drugAndRecipe.mapper;

import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyDTO;
import ISA.Team54.drugAndRecipe.dto.DrugInPharmacyRequestDTO;
import ISA.Team54.drugAndRecipe.dto.DrugWithPharmacyDTO;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.model.DrugInPharmacyId;
import ISA.Team54.users.model.Pharmacy;

public class DrugInPharmacyMapper {
	
	public static DrugInPharmacyDTO DrugInPharmacyToDrugInPharmacyDTO(DrugInPharmacy drugInPharmacy, Drug drug) {
		return new DrugInPharmacyDTO(
				drugInPharmacy.getDrugInPharmacyId().getPharmaciId(),
				drugInPharmacy.getDrugInPharmacyId().getDrugId(),
				drug.getName(),
				drug.getCode(),
				drug.getLoyalityPoints(),
				drugInPharmacy.getQuantity(),
				drugInPharmacy.getPricelist() != null ? drugInPharmacy.getPricelist().getPrice() : -1,
				drug.getRatings(),
				drug.getType(),
				drug.getShape(),
				drug.getManifacturer(),
				drug.getAdditionalInfo());
	}
	
	public static DrugInPharmacy DrugInPharmacyRequestDTOToDrugInPharmacy(DrugInPharmacyRequestDTO drugInPharmacyRequestDTO) {
		return new DrugInPharmacy(
				new DrugInPharmacyId(drugInPharmacyRequestDTO.getPharmacyId(), drugInPharmacyRequestDTO.getDrugId()),
				drugInPharmacyRequestDTO.getQuantity()
				);
	}
}
