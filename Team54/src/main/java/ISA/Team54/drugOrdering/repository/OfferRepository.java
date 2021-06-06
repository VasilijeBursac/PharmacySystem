package ISA.Team54.drugOrdering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ISA.Team54.drugOrdering.model.Offer;
import ISA.Team54.users.model.Supplier;

public interface OfferRepository extends JpaRepository<Offer, Long>{
	List<Offer> findAllBySupplier(Supplier supplier);
}
