package student_manager;

import java.util.List;

/**
 * ClassName: BatchInsertResult
 * Package: student_manager
 * Description:
 *
 * @Author fly
 * @Create 2026/2/19 22:33
 * @Version 1.0
 */
public record BatchInsertResult<T>(int successCount, List<T> lists, String failReason) {
    public BatchInsertResult(int successCount, List<T> lists, String failReason) {
        this.successCount = successCount;
        this.lists = lists;
        this.failReason = failReason;
    }
}
