package com.flood_web.service.crud;

import com.flood_web.controller.Administrators;
import com.flood_web.data.entity.AdministratorsEntity;
import com.flood_web.data.repository.AdministratorsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("administratorsCrudService")
public class AdministratorsCrudService implements CrudService<Administrators> {

    @Autowired
    private AdministratorsRepository administratorsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private com.flood_web.service.AdminService adminService; // Asegúrate de que la ruta a tu AdminService sea correcta

    @Override
    public Optional<Administrators> findById(String idNumber) {
        Long id = Long.parseLong(idNumber); // Convertir el ID de String a Long
        Optional<AdministratorsEntity> foundAdministratorsEntity = this.administratorsRepository.findById(String.valueOf(id));

        if (foundAdministratorsEntity.isEmpty()) {
            return Optional.of(Administrators.builder().build());
        }

        AdministratorsEntity administratorsEntity = foundAdministratorsEntity.get();
        Administrators administrators = modelMapper.map(administratorsEntity, Administrators.class);

        return Optional.of(administrators);
    }

    @Override
    public List<Administrators> listAll() {
        return StreamSupport.stream(administratorsRepository.findAll().spliterator(), false)
                .map(entity -> modelMapper.map(entity, Administrators.class))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Administrators administrators) {
        AdministratorsEntity administratorsEntity = modelMapper.map(administrators, AdministratorsEntity.class);

        if (administratorsEntity.getId() != null) {
            // Es una actualización
            Optional<AdministratorsEntity> existingAdminOptional = administratorsRepository.findById(administratorsEntity.getId().toString());
            existingAdminOptional.ifPresent(existingAdmin -> {
                String oldRole = existingAdmin.getRole();
                String newRole = administratorsEntity.getRole();
                if (newRole != null && !newRole.equals(oldRole)) {
                    // El rol ha cambiado, forzar el logout
                    adminService.logoutUser(existingAdmin.getCedula()); // Usar la cédula como ID de usuario
                }
                // Mantener la contraseña existente si no se proporciona una nueva
                if (administratorsEntity.getPassword() == null || administratorsEntity.getPassword().isEmpty()) {
                    administratorsEntity.setPassword(existingAdmin.getPassword());
                } else {
                    administratorsEntity.setPassword(passwordEncoder.encode(administratorsEntity.getPassword()));
                }
            });
        } else {
            // Es una nueva creación
            administratorsEntity.setPassword(passwordEncoder.encode(administratorsEntity.getPassword()));
        }
        administratorsRepository.save(administratorsEntity);
    }

    public Optional<Administrators> getByCedula(String cedula) {
        Optional<AdministratorsEntity> foundAdministratorsEntity = this.administratorsRepository.findByCedula(cedula);

        if (foundAdministratorsEntity.isEmpty()) {
            return Optional.empty();
        }

        AdministratorsEntity administratorsEntity = foundAdministratorsEntity.get();
        Administrators administrators = modelMapper.map(administratorsEntity, Administrators.class);

        return Optional.ofNullable(administrators);
    }
}