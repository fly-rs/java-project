package student_manager.exception;

/**
 * ClassName: StudentNotFoundException
 * Package: student_manager.exception
 * Description:
 *
 * @Author fly
 * @Create 2026/2/21 01:13
 * @Version 1.0
 */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
