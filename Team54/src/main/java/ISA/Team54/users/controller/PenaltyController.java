package ISA.Team54.users.controller;

import ISA.Team54.users.service.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class PenaltyController {

    @Autowired
    private PatientService patientService;


    @Scheduled(cron = "${penalty.remove}")
    public void deletePenaltyPointsForAll(){
        patientService.deletePenaltyPointsForAll();
    }
}
