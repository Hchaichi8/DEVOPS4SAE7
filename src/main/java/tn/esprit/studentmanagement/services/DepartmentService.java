package tn.esprit.studentmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class DepartmentService implements IDepartmentService {

    private static final Logger logger = LogManager.getLogger(DepartmentService.class);

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        logger.info("Début de getAllDepartments - Récupération de tous les départements");
        List<Department> departments = departmentRepository.findAll();
        logger.debug("Nombre de départements récupérés : {}", departments.size());
        logger.info("Fin de getAllDepartments - {} départements trouvés", departments.size());
        return departments;
    }

    @Override
    public Department getDepartmentById(Long idDepartment) {
        logger.info("Début de getDepartmentById - Recherche département ID: {}", idDepartment);
        try {
            Department department = departmentRepository.findById(idDepartment).get();
            logger.debug("Département trouvé : {}", department);
            logger.info("Département ID {} récupéré avec succès", idDepartment);
            return department;
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du département ID: {}", idDepartment, e);
            throw e;
        }
    }

    @Override
    public Department saveDepartment(Department department) {
        logger.info("Début de saveDepartment - Sauvegarde d'un nouveau département");
        logger.debug("Données département à sauvegarder : {}", department);
        try {
            Department savedDepartment = departmentRepository.save(department);
            logger.info("Département sauvegardé avec succès - ID: {}", savedDepartment.getIdDepartment());
            return savedDepartment;
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde du département", e);
            throw e;
        }
    }

    @Override
    public void deleteDepartment(Long idDepartment) {
        logger.info("Début de deleteDepartment - Suppression département ID: {}", idDepartment);
        try {
            departmentRepository.deleteById(idDepartment);
            logger.info("Département ID {} supprimé avec succès", idDepartment);
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du département ID: {}", idDepartment, e);
            throw e;
        }
    }
}