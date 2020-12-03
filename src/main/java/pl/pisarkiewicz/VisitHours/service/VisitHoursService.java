package pl.pisarkiewicz.VisitHours.service;

import org.springframework.stereotype.Service;
import pl.pisarkiewicz.User.service.UserService;
import pl.pisarkiewicz.VisitHours.dto.VisitHoursDTO;
import pl.pisarkiewicz.VisitHours.entity.VisitHours;
import pl.pisarkiewicz.VisitHours.repository.VisitHoursRepository;

@Service
public class VisitHoursService implements IVisitHoursService {
    private final VisitHoursRepository visitHoursRepository;
    private final UserService userService;

    public VisitHoursService(VisitHoursRepository visitHoursRepository, UserService userService) {
        this.visitHoursRepository = visitHoursRepository;
        this.userService = userService;
    }

    @Override
    public void addVisitHours(VisitHoursDTO visitHoursDTO) {
        VisitHours visitHours = new VisitHours();
        visitHours.setStartDate(visitHoursDTO.getStartDate());
        visitHours.setVisitCost(visitHoursDTO.getVisitCost());
        visitHours.setVisitLength(visitHoursDTO.getVisitLength());
        visitHours.setVisitsCount(visitHoursDTO.getVisitsCount());
        visitHours.setDoctor(userService.getUser(visitHoursDTO.getDoctorId()));
        visitHours.setEndDate(
                visitHours.getStartDate().plusMinutes(
                        visitHours.getVisitLength() * visitHours.getVisitsCount()));
        visitHoursRepository.save(visitHours);
    }
}
