package ISA.Team54.users.controller;

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

import ISA.Team54.users.dto.UserInfoDTO;
import ISA.Team54.users.mappers.UserInfoMapper;
import ISA.Team54.users.model.Dermatologist;
import ISA.Team54.users.model.Supplier;
import ISA.Team54.users.service.interfaces.SupplierService;

@RestController
@RequestMapping(value = "/supplier", produces = MediaType.APPLICATION_JSON_VALUE)
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;
	
	@GetMapping("/allSuppliers")
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public ResponseEntity<List<UserInfoDTO>> findAll(){
		try {
			List<UserInfoDTO> userDTOs = new ArrayList<UserInfoDTO>();
			this.supplierService.findAll().forEach(supplier -> userDTOs.add(UserInfoMapper.UserTOUserInfoDTO(supplier)));
			return new ResponseEntity<>(userDTOs,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('SUPPLIER')")
	public UserInfoDTO loadById(@PathVariable long id){
		return UserInfoMapper.UserTOUserInfoDTO(this.supplierService.findById(id));
	}
	
}
