package student_manager.test;

import org.junit.Test;
import student_manager.Discipline;
import student_manager.Student;
import student_manager.util.IOUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: TestIOUtil
 * Package: student_manager.test
 * Description:
 *
 * @Author fly
 * @Create 2026/2/20 23:04
 * @Version 1.0
 */
public class TestIOUtil {

    @Test
    public void testWrite(){
        List<Student> list = new ArrayList();
        HashMap<Discipline, Float> scores = new HashMap<>();
        scores.put(Discipline.CHINESE, 97.5F);
        scores.put(Discipline.ENGLISH, 87.5F);
        scores.put(Discipline.MATH, 99.0F);
        HashMap<Discipline, Float> scores1 = new HashMap<>();
        scores1.put(Discipline.CHINESE, 90.5F);
        scores1.put(Discipline.ENGLISH, 88.5F);
        scores1.put(Discipline.MATH, 99.0F);

//        list.add(new Student("张三", 18, scores));
//        list.add(new Student("李四", 18, scores1));
        IOUtil.write(list);
//        IOUtil.write(null);

    }

    @Test
    public void testReadAll(){
        List<Student> objects = IOUtil.readAll();
        for (Student student : objects) {
            System.out.println(student);
        }

    }
}
