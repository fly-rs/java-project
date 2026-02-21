package student_manager.test;

import org.junit.Test;
import student_manager.*;
import student_manager.util.IOUtil;

import java.util.*;

/**
 * ClassName: TestStuManager
 * Package: student_manager
 * Description:
 *
 * @Author fly
 * @Create 2026/2/19 00:20
 * @Version 1.0
 */
public class TestStuManager {
    public static void main(String[] args) {

    }

    @Test
    public void testAddOne(){
        ManagerStudent ms = new ManagerStudentImpl();
        HashMap<Discipline, Float> score1 = new HashMap<>();
        score1.put(Discipline.CHINESE, 80.5F);
        score1.put(Discipline.ENGLISH, 70.1F);
        score1.put(Discipline.MATH, 95.0F);
        ms.addOne(new Student("张三", 18, "000001", score1));
        ms.writeToFile();
    }

    @Test
    public void testAddAll(){
        ManagerStudent ms = new ManagerStudentImpl();
        HashMap<Discipline, Float> score1 = new HashMap<>();
        score1.put(Discipline.CHINESE, 80.5F);
        score1.put(Discipline.ENGLISH, 70.1F);
        score1.put(Discipline.MATH, 95.0F);
        HashMap<Discipline, Float> score2 = new HashMap<>();
        score2.put(Discipline.CHINESE, 60.5F);
        score2.put(Discipline.ENGLISH, 73.1F);
        score2.put(Discipline.MATH, 90.0F);

        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("张三", 18, "000001", score1));
        studentList.add(new Student("李四", 18, "000002", score2));
        BatchInsertResult batchInsertResult = ms.addAll(studentList);
        System.out.println(batchInsertResult.successCount());
        ms.writeToFile();
    }

    @Test
    public void testFindById(){
        ManagerStudent ms = new ManagerStudentImpl();
        Optional<Student> student = ms.findById("张三", "000001");
        student.ifPresent(System.out::println);
    }

    @Test
    public void testFindAll(){
        ManagerStudent ms = new ManagerStudentImpl();
        List<Student> students = ms.findAll();
        students.forEach(System.out::println);
    }

    @Test
    public void testUpdate(){
        ManagerStudent ms = new ManagerStudentImpl();
        HashMap<Discipline, Float> score2 = new HashMap<>();
        score2.put(Discipline.CHINESE, 60.5F);
        score2.put(Discipline.ENGLISH, 73.1F);
        score2.put(Discipline.MATH, 90.0F);
        Student student = new Student("张三", 20, "000001", score2);
        Student student1 = ms.updateStudentById(student);
        ms.writeToFile();

    }

    @Test
    public void testDelete(){
        ManagerStudent ms = new ManagerStudentImpl();
        Optional<Student> student = ms.deleteById("000001");
        ms.writeToFile();
    }
}
