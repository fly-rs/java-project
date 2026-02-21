package student_manager.exception;

/**
 * ClassName: StudentValidationException
 * Package: student_manager.exception
 * Description:
 *
 * @Author fly
 * @Create 2026/2/20 23:47
 * @Version 1.0
 */
public class StudentValidationException extends RuntimeException {
    public StudentValidationException() {
    }
    public StudentValidationException(String message) {
        super(message);
    }
}
