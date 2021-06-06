package ISA.Team54.drugAndRecipe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ISA.Team54.drugAndRecipe.model.DrugInStorage;
import ISA.Team54.drugAndRecipe.repository.DrugInStorageRepository;
import ISA.Team54.drugAndRecipe.service.interfaces.DrugInStorageService;
import ISA.Team54.users.model.Supplier;
import ISA.Team54.users.repository.SupplierRepository;

@Service
public class DrugsInStorageServiceImpl implements DrugInStorageService{

	@Autowired
	private DrugInStorageRepository drugInStorageRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Override
	public List<DrugInStorage> findAllDrugsInStorageForSupplier() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Supplier supplier = supplierRepository.findById(((Supplier) authentication.getPrincipal()).getId());
		if(drugInStorageRepository.findAllBySupplier(supplier).size() != 0)
			return drugInStorageRepository.findAllBySupplier(supplier);
		return null;
	}


}
