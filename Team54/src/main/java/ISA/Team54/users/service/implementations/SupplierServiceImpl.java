package ISA.Team54.users.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ISA.Team54.users.model.Supplier;
import ISA.Team54.users.repository.SupplierRepository;
import ISA.Team54.users.service.interfaces.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
	SupplierRepository supplierRepository;
	
	@Override
	public List<Supplier> findAll() {
		if(supplierRepository.findAll().size() != 0)
			return supplierRepository.findAll();
		return null;
	}

	@Override
	public Supplier findById(long id) {
		return supplierRepository.findById(id);
	}
}
