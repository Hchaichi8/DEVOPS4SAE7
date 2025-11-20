package tn.esprit.studentmanagement.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.StudentRepository;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class StudentService implements IStudentService {

    private static final Logger logger = LogManager.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        logger.info("Début de getAllStudents - Récupération de tous les étudiants");
        List<Student> students = studentRepository.findAll();
        logger.debug("Nombre d'étudiants récupérés : {}", students.size());
        logger.info("Fin de getAllStudents - {} étudiants trouvés", students.size());
        return students;
    }

    @Override
    public Student getStudentById(Long id) {
        logger.info("Début de getStudentById - Recherche étudiant ID: {}", id);
        try {
            Student student = studentRepository.findById(id).orElse(null);
            if (student != null) {
                logger.debug("Étudiant trouvé : {}", student);
                logger.info("Étudiant ID {} récupéré avec succès", id);
            } else {
                logger.warn("Aucun étudiant trouvé avec l'ID: {}", id);
            }
            return student;
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de l'étudiant ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public Student saveStudent(Student student) {
        logger.info("Début de saveStudent - Sauvegarde d'un nouvel étudiant");
        logger.debug("Données étudiant à sauvegarder : {}", student);
        try {
            Student savedStudent = studentRepository.save(student);
            logger.info("Étudiant sauvegardé avec succès - ID: {}", savedStudent.getIdStudent());
            return savedStudent;
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde de l'étudiant", e);
            throw e;
        }
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Début de deleteStudent - Suppression étudiant ID: {}", id);
        try {
            studentRepository.deleteById(id);
            logger.info("Étudiant ID {} supprimé avec succès", id);
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de l'étudiant ID: {}", id, e);
            throw e;
        }
    }
}