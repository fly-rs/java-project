package student_manager.exception;

/**
 * ClassName: AgeIllegalException
 * Package: student_manager.exception
 * Description:
 *
 * @Author fly
 * @Create 2026/2/19 21:49
 * @Version 1.0
 */
public class AgeIllegalException extends RuntimeException{
    public AgeIllegalException(){
        super();
    }
    public AgeIllegalException(String message){
        super(message);
    }
    public AgeIllegalException(String message, Throwable cause){
        super(message, cause);
    }
}
