package student_manager.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: IOUtil
 * Package: student_manager
 * Description:
 *
 * @Author fly
 * @Create 2026/2/20 16:16
 * @Version 1.0
 */
public class IOUtil {
    public static int writeNum = 0;
    public static <T> List<T> readAll() {
        File file = new File("src/student_manager/data/student.txt");
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try (
                FileInputStream fis = new FileInputStream("src/student_manager/data/student.txt");
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis));
                ) {
            List<T> students = (List<T>) ois.readObject();
            return students;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void write(List<T> list) {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("src/student_manager/data/student.txt")));
        ){
            if (list != null && !list.isEmpty()) {
                oos.writeObject(list);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
