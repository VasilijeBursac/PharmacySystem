package ISA.Team54.drugAndRecipe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ISA.Team54.drugAndRecipe.model.ERecipe;
import ISA.Team54.drugAndRecipe.repository.DrugInERecipeRepository;
import ISA.Team54.drugAndRecipe.repository.ERecipeRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.ERecipeService;
import ISA.Team54.shared.service.interfaces.EmailService;
import ISA.Team54.users.model.Patient;
import ISA.Team54.users.repository.PatientRepository;

@Service
public class ERecipeServiceImpl implements ERecipeService{

	@Autowired
	private ERecipeRepository eRecipeRepository;
	
	@Autowired
	private DrugInERecipeRepository drugInERecipeRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	EmailService emailService;
	
	@Override
	public List<ERecipe> getAllERecipesForPatient() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());
		if(eRecipeRepository.findAllByPatient(patient).size() == 0)
			return null;
		return eRecipeRepository.findAllByPatient(patient);
	}

	@Override
	public ERecipe addERecipe(ERecipe eRecipe) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());
		eRecipe.setPatient(patient);
		emailService.sendEmail("mdjurisic98@gmail.com","Izdavanje e recepta","Uspesno ste generisali e recept!");
		return eRecipeRepository.save(eRecipe);
	}
	
	

}
