package pl.pisarkiewicz;

import org.springframework.stereotype.Component;
import pl.pisarkiewicz.Role.entity.Role;
import pl.pisarkiewicz.Role.service.IRoleService;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.User.service.IUserService;

import javax.annotation.PostConstruct;

@Component
public class DataConstructor {

    private final IRoleService roleService;
    private final IUserService userService;

    public DataConstructor(IRoleService roleService, IUserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void dataConstruct() {
        Role admin = new Role();
        admin.setRole("ROLE_ADMIN");
        roleService.addRole(admin);
        Role doctor = new Role();
        doctor.setRole("ROLE_DOCTOR");
        roleService.addRole(doctor);
        Role patient = new Role();
        patient.setRole("ROLE_PATIENT");
        roleService.addRole(patient);

        User userAdmin = new User();
        userAdmin.setEmail("admin@mc.pl");
        userAdmin.setPassword("root");
        userAdmin.setFirstName("Admin");
        userAdmin.setLastName("Admin");
        userAdmin.setPesel(11111111111L);
        userAdmin.setTelephone("111111111");
        userAdmin.setEnabled(true);
        userAdmin.getRoles().add(admin);
        userService.addUser(userAdmin);
        User userDoctor = new User();
        userDoctor.setEmail("doctor@mc.pl");
        userDoctor.setPassword("root");
        userDoctor.setFirstName("doctor");
        userDoctor.setLastName("doctor");
        userDoctor.setPesel(11111111111L);
        userDoctor.setTelephone("111111111");
        userDoctor.setEnabled(true);
        userDoctor.getRoles().add(doctor);
        userService.addUser(userDoctor);
        User userPatient = new User();
        userPatient.setEmail("patient@mc.pl");
        userPatient.setPassword("root");
        userPatient.setFirstName("patient");
        userPatient.setLastName("patient");
        userPatient.setPesel(11111111111L);
        userPatient.setTelephone("111111111");
        userPatient.setEnabled(true);
        userService.addUser(userPatient);

    }
}
