package ISA.Team54.users.service.implementations;

import ISA.Team54.users.model.Patient;
import ISA.Team54.users.repository.PatientRepository;
import ISA.Team54.users.service.interfaces.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PenaltyServiceImpl implements PenaltyService {

    @Autowired
    private PatientRepository patientRepository;

    private static final int PENALTY_LIMIT = 3;

    public void penalPatient(Patient patient){
        Patient patientDB = patientRepository.findById(patient.getId());
        patientDB.setPenaltyPoints(patientDB.getPenaltyPoints() + 1);
        patientRepository.save(patientDB);
    }
}
