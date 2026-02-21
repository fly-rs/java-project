package student_manager.exception;

/**
 * ClassName: StudentExistException
 * Package: student_manager.exception
 * Description:
 *
 * @Author fly
 * @Create 2026/2/19 22:31
 * @Version 1.0
 */
public class StudentExistException extends RuntimeException {
    public StudentExistException(String message) {
        super(message);
    }
}
