package tn.esprit.studentmanagement.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;
import tn.esprit.studentmanagement.entities.Enrollment;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class EnrollmentService implements IEnrollment {

    private static final Logger logger = LogManager.getLogger(EnrollmentService.class);

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Override
    public List<Enrollment> getAllEnrollments() {
        logger.info("Début de getAllEnrollments - Récupération de tous les enrollments");
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        logger.debug("Nombre d'enrollments récupérés : {}", enrollments.size());
        logger.info("Fin de getAllEnrollments - {} enrollments trouvés", enrollments.size());
        return enrollments;
    }

    @Override
    public Enrollment getEnrollmentById(Long idEnrollment) {
        logger.info("Début de getEnrollmentById - Recherche enrollment ID: {}", idEnrollment);
        try {
            Enrollment enrollment = enrollmentRepository.findById(idEnrollment).get();
            logger.debug("Enrollment trouvé : {}", enrollment);
            logger.info("Enrollment ID {} récupéré avec succès", idEnrollment);
            return enrollment;
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de l'enrollment ID: {}", idEnrollment, e);
            throw e;
        }
    }

    @Override
    public Enrollment saveEnrollment(Enrollment enrollment) {
        logger.info("Début de saveEnrollment - Sauvegarde d'un nouvel enrollment");
        logger.debug("Données enrollment à sauvegarder : {}", enrollment);
        try {
            Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
            logger.info("Enrollment sauvegardé avec succès - ID: {}", savedEnrollment.getIdEnrollment());
            return savedEnrollment;
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde de l'enrollment", e);
            throw e;
        }
    }

    @Override
    public void deleteEnrollment(Long idEnrollment) {
        logger.info("Début de deleteEnrollment - Suppression enrollment ID: {}", idEnrollment);
        try {
            enrollmentRepository.deleteById(idEnrollment);
            logger.info("Enrollment ID {} supprimé avec succès", idEnrollment);
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de l'enrollment ID: {}", idEnrollment, e);
            throw e;
        }
    }
}