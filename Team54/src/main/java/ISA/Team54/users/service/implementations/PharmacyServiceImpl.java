package ISA.Team54.users.service.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import ISA.Team54.drugAndRecipe.model.DrugInPharmacy;
import ISA.Team54.drugAndRecipe.model.DrugReservation;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInPharmacyService;
import ISA.Team54.Examination.model.Examination;
import ISA.Team54.Examination.repository.ExaminationRepository;
import ISA.Team54.loyalty.repository.LoyaltyRepository;
import ISA.Team54.rating.model.Rating;
import ISA.Team54.users.model.*;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ISA.Team54.drugAndRecipe.dto.DrugWithPharmacyDTO;
import ISA.Team54.security.Authority;
import ISA.Team54.shared.service.interfaces.EmailService;
import ISA.Team54.users.dto.DermatologistRequestDTO;
import ISA.Team54.users.dto.PharmacistRequestDTO;
import ISA.Team54.users.dto.PharmacyAdministratorRequestDTO;
import ISA.Team54.users.dto.PharmacyDTO;
import ISA.Team54.users.dto.UserRequestDTO;
import ISA.Team54.users.mappers.PharmacyMapper;
import ISA.Team54.users.mappers.UserMapper;
import ISA.Team54.users.repository.DermatologistRepository;
import ISA.Team54.users.repository.PatientRepository;
import ISA.Team54.users.repository.PharmacyAdministratorRepository;
import ISA.Team54.users.repository.PharmacyRepository;
import ISA.Team54.users.repository.SupplierRepository;
import ISA.Team54.users.repository.SystemAdministratorRepository;
import ISA.Team54.users.service.interfaces.AuthorityService;
import ISA.Team54.users.service.interfaces.PharmacyService;

@Service
public class PharmacyServiceImpl implements PharmacyService {

	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PharmacyAdministratorRepository pharmacyAdministratorRepository;
	
	@Autowired
	private SystemAdministratorRepository systemAdministratorRepository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authService;

	@Autowired
	private PharmacyRepository pharmacyRepository;

	@Autowired
	private ExaminationRepository examinationRepository;
	
	@Autowired
	private LoyaltyRepository loyaltyRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private DrugInPharmacyService drugInPharmacyService;
	
	
	@Override
	public Pharmacy addPharmacy(PharmacyDTO pharmacyDTO) {
		return pharmacyRepository.save(PharmacyMapper.PharmacyDTOToPharmacy(pharmacyDTO));		
	} 
	
	@Override
	public Patient addPatient(UserRequestDTO userRequest) {
		Patient patient = new Patient();
		UserMapper.UserRequestDTOToUser(userRequest,patient);
		patient.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		List<Authority> auth = authService.findByname("ROLE_PATIENT");
		patient.setAuthorities(auth);
		patient.setActivated(false);
		patient = patientRepository.save(patient);
		emailService.sendEmail(patient.getEmail(),"Aktivaija naloga","Da biste aktivirali svoj nalog pritisnite "
				+ "na sledeci link http://localhost:8080/activation?id=" + patient.getId());
		return patient;		
	}
	
	@Override
	public SystemAdministrator addSystemAdministrator(UserRequestDTO userRequest) {
		SystemAdministrator systemAdministrator = new SystemAdministrator();
		UserMapper.UserRequestDTOToUser(userRequest,systemAdministrator);
		systemAdministrator.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		List<Authority> auth = authService.findByname("ROLE_SYSTEM_ADMIN");
		systemAdministrator.setAuthorities(auth);
		return systemAdministratorRepository.save(systemAdministrator);			
	}
	
	@Override
	public Supplier addSupplier(UserRequestDTO userRequest) {
		Supplier supplier = new Supplier();
		UserMapper.UserRequestDTOToUser(userRequest,supplier);	
		supplier.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		List<Authority> auth = authService.findByname("ROLE_SUPPLIER");
		supplier.setAuthorities(auth); 
		return supplierRepository.save(supplier);			
	}

	@Override
	public PharmacyAdministrator addPharmacyAdministrator(
		PharmacyAdministratorRequestDTO pharmacyAdministratorRequestDTO) {
		PharmacyAdministrator pharmacyAdministrator = UserMapper.PharmacyAdministratorRequestDTOToPharmacist(pharmacyAdministratorRequestDTO);
		pharmacyAdministrator.setPassword(passwordEncoder.encode(pharmacyAdministratorRequestDTO.getPassword()));
		List<Authority> auth = authService.findByname("ROLE_PHARMACY_ADMIN");
		pharmacyAdministrator.setAuthorities(auth);
		pharmacyAdministrator.setPharmacy(pharmacyRepository.findById(pharmacyAdministratorRequestDTO.getPharmacyId()));
		return pharmacyAdministratorRepository.save(pharmacyAdministrator);			

	}

	@Override
	public Dermatologist addDermatologist(DermatologistRequestDTO dermatologistRequestDTO) {
		Dermatologist dermatologist = UserMapper.DermatologistRequestDTOToDermatologist(dermatologistRequestDTO);
		dermatologist.setPassword(passwordEncoder.encode(dermatologistRequestDTO.getPassword()));
		List<Authority> auth = authService.findByname("ROLE_DERMATOLOGIST");
		dermatologist.setAuthorities(auth);
		return dermatologistRepository.save(dermatologist);	
	}

	@Override
	public Pharmacist addPharmacist(PharmacistRequestDTO pharmacistRequestDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pharmacy getPharmacyById(long pharmacyId) {
		return pharmacyRepository.findById(pharmacyId);
	}

	public List<Pharmacy> findAll() {
		if(pharmacyRepository.findAll().size() != 0)
			return pharmacyRepository.findAll();
		return null;
	}

	@Override
	public List<Pharmacy> getPatientPharmacies(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());

		List<Examination> examinations =  examinationRepository.findByPatientId(patient.getId());
		List<Pharmacy> pharmacies = new ArrayList<Pharmacy>();
		examinations.forEach(e -> pharmacies.add(e.getPharmacy()));

		return pharmacies;
	}

	@Override
	public void updatePharmacyInfo(PharmacyDTO pharmacyDTO) {
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyDTO.getId());
		PharmacyMapper.PharmacyDTOToPharmacyUpdateInfo(pharmacyDTO, pharmacy);
		pharmacyRepository.save(pharmacy);
		
	}

	@Override
	public double getPharmacistPriceWithDiscount(double price) {		
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = patientRepository.findById(((Patient) authentication.getPrincipal()).getId());
		return  0.01 * price * (100 - loyaltyRepository.getLoyaltyCategory(patient.getLoyaltyPoints()).getDiscount());
		    
	}
	
	@Override
	public HashMap<Long, Float> getPharmaciesWithTotalPrices(List<Long> drugIds,List<Integer> drugQuantities){
		HashMap<Long, Float> pharmaciesWithTotalPrices= new HashMap<>();
		List<Long> pharmaciesForErecipe = getPharmaciesForErecipe(drugIds, drugQuantities);
		for(int i = 0; i < pharmaciesForErecipe.size(); i++) {
			pharmaciesWithTotalPrices.put(pharmaciesForErecipe.get(i),
										  getTotalPriceForDrugsInPharmacy(pharmaciesForErecipe.get(i),drugIds,drugQuantities));
		}
		return pharmaciesWithTotalPrices;
	}

	private List<Long> getPharmaciesForErecipe(List<Long> drugIds, List<Integer> drugQuantities) {			
		List<Long> pharmaciesWithOneDrug = new ArrayList<Long>();
		List<Long> pharmaciesWithAllDrugs = new ArrayList<Long>();
		for(int i = 0; i < drugIds.size(); i++){
			for(DrugInPharmacy drugInPharmacy : drugInPharmacyService.getDrugsInPharmaciesByDrug(drugIds.get(i))) {
				if(drugInPharmacy.getQuantity() > 0)
					pharmaciesWithOneDrug.add(drugInPharmacy.getDrugInPharmacyId().getPharmaciId());
			}
			if(pharmaciesWithOneDrug.size() == 0) 
				return null;
			pharmaciesWithAllDrugs = comparePharmacies(pharmaciesWithOneDrug, pharmaciesWithAllDrugs);
			if(pharmaciesWithAllDrugs.size() == 0)
				return null;
			pharmaciesWithOneDrug = new ArrayList<Long>();
		}
		return pharmaciesWithAllDrugs;	
	}
	
	private List<Long> comparePharmacies(List<Long> pharmaciesWithOneDrug,List<Long> pharmaciesWithAllDrugs) {
		if(pharmaciesWithAllDrugs.size() == 0) 
			pharmaciesWithAllDrugs = pharmaciesWithOneDrug;
		else {
			Iterator<Long> it = pharmaciesWithAllDrugs.iterator();
			while (it.hasNext()) {
				Long id = it.next();
				if(!pharmaciesWithOneDrug.contains(id)) {
					it.remove();			
				}
			}
		}
		return pharmaciesWithAllDrugs;
	}
	
	private float getTotalPriceForDrugsInPharmacy(long pharmacyId, List<Long> drugIds, List<Integer> drugQuantities) {
		float totalPrice = 0;
		for(DrugInPharmacy drugInPharmacy : drugInPharmacyService.getDrugsInPharmaciesByPharmacy(pharmacyId)){		
			for(int i = 0; i < drugIds.size(); i++){
				if(drugInPharmacy.getDrugInPharmacyId().getDrugId() == drugIds.get(i))
					totalPrice += drugQuantities.get(i) * drugInPharmacy.getPricelist().getPrice();
			}			
		}
		return totalPrice;
	}
	
}
