package al.run.userservice;

import al.run.userservice.persistence.entity.Role;
import al.run.userservice.persistence.entity.enums.RoleEnum;
import al.run.userservice.persistence.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppStartupRunner implements ApplicationRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Role role = new Role();
//        role.setRole(RoleEnum.ADMIN);
//        Role role2 = new Role();
//        role2.setRole(RoleEnum.ATHLETE);
//        roleRepository.save(role);
//        roleRepository.save(role2);
    }
}
