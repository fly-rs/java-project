package student_manager;

import java.util.List;
import java.util.Optional;

/**
 * ClassName: ManagerStudent
 * Package: student_manager
 * Description:
 *
 * @Author fly
 * @Create 2026/2/19 22:23
 * @Version 1.0
 */
public interface ManagerStudent {

    Student addOne(Student student);
    BatchInsertResult addAll(List<Student> students);
//    Optional<Student> findById(String id);

    Optional<Student> findById(String name, String idCardNumber);

    List<Student> findAll();
    Student updateStudentById(Student student);
    Optional<Student> deleteById(String id);

    void writeToFile();
}
