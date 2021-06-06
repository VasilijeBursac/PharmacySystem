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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.drugAndRecipe.dto.DrugDTO;
import ISA.Team54.drugAndRecipe.dto.DrugInStorageDTO;
import ISA.Team54.drugAndRecipe.mapper.DrugInStorageMapper;
import ISA.Team54.drugAndRecipe.mapper.DrugMapper;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInStorageService;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugService;


@RestController
@RequestMapping(value = "/drugInStorage", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugInStorageController {
	
	@Autowired
	private DrugInStorageService drugInStorageService;
	
	@GetMapping("/allDrugsInStorage")
	@PreAuthorize("hasRole('SUPPLIER')")
	public ResponseEntity<List<DrugInStorageDTO>> getAllDrugsInStorage(){
		try {
			List<DrugInStorageDTO> drugInStorageDTOs = new ArrayList<DrugInStorageDTO>();
			this.drugInStorageService.findAllDrugsInStorageForSupplier().forEach(drugInStorage -> 
				drugInStorageDTOs.add(DrugInStorageMapper.DrugInStorageToDrugInStorageDTO(drugInStorage)));
			return new ResponseEntity<>(drugInStorageDTOs,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
