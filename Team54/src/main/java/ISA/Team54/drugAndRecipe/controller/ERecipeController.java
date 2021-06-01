package ISA.Team54.drugAndRecipe.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.drugAndRecipe.dto.DrugForERecipeDTO;
import ISA.Team54.drugAndRecipe.dto.ERecipeDTO;
import ISA.Team54.drugAndRecipe.dto.NewERecipeDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugMapper;
import ISA.Team54.drugAndRecipe.model.Drug;
import ISA.Team54.drugAndRecipe.model.DrugInERecipe;
import ISA.Team54.drugAndRecipe.model.DrugInERecipeId;
import ISA.Team54.drugAndRecipe.model.ERecipe;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInERecipeService;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInPharmacyService;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugService;
import ISA.Team54.drugAndRecipe.service.interfaces.ERecipeService;
import ISA.Team54.users.exceptions.DrugNotFoundException;

@RestController
@RequestMapping(value = "/eRecipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ERecipeController {
	
	@Autowired
	private ERecipeService eRecipeService;
	
	@Autowired
	private DrugInERecipeService drugInERecipeService;
	
	@Autowired
	private DrugService drugService;
	
	@Autowired
	DrugInPharmacyService drugInPharmacyService;
	
	@GetMapping("")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<List<ERecipeDTO>> getAllEreicpesForPatient(){
		try {
			List<ERecipeDTO> eRecipeDTOs = new ArrayList<ERecipeDTO>();
			eRecipeService.getAllERecipesForPatient().forEach(eRecipe -> {
				eRecipeDTOs.add(new ERecipeDTO(eRecipe.getId(),eRecipe.getDateOfIssue(),drugService.getDrugsFromErecipe(eRecipe.getId())));
			});
			return new ResponseEntity<>(eRecipeDTOs,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/addERecipe")
	@PreAuthorize("hasRole('PATIENT')")
	public  ResponseEntity<String> addERecipe(@RequestBody NewERecipeDTO newERecipeDTO){
		try {
			ERecipe eRecipe = eRecipeService.addERecipe(new ERecipe(newERecipeDTO.getDateOfIssue()));
			addDrugsInERecipes(newERecipeDTO, eRecipe.getId());
			return new ResponseEntity<>(HttpStatus.CREATED);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}
		
	} 
	
	private List<Long> drugNamesToDrugIds(List<String> drugNames){
		List<Long> drugIds = new ArrayList();
		for(int i = 0; i < drugNames.size(); i++)
			drugIds.add(drugService.getDrugByName(drugNames.get(i)).getId());
		return drugIds;
	}
	
	private void addDrugsInERecipes(NewERecipeDTO newERecipeDTO, long eRecipeId) {
		List<Long> drugIds = drugNamesToDrugIds(newERecipeDTO.getDrugNames());
		for(int i = 0; i < drugIds.size(); i++){
			System.out.println(drugIds.get(i) + "leeeek");
			drugInPharmacyService.decreaseDrugQuantity(drugIds.get(i), newERecipeDTO.getPharmacyId(), newERecipeDTO.getQuantities().get(i));
			drugInERecipeService.addDrugInERecipe(new DrugInERecipe(new DrugInERecipeId(newERecipeDTO.getPharmacyId(),
																						drugIds.get(i),
																						eRecipeId),				
																	newERecipeDTO.getQuantities().get(i)));		
		}
	}
	
}
