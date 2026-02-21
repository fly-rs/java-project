package student_manager;

import student_manager.exception.StudentCacheException;
import student_manager.exception.StudentExistException;
import student_manager.exception.StudentNotFoundException;
import student_manager.exception.StudentValidationException;
import student_manager.util.IOUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * ClassName: ManagerStudentImpl
 * Package: student_manager
 * Description:
 *
 * @Author fly
 * @Create 2026/2/19 22:23
 * @Version 1.0
 */
public class ManagerStudentImpl implements ManagerStudent {

    // 用于存储student数据缓存的集合，将student从文件中预加载到此集合
    private static List<Student> studentList;
    // 用于记录是否初始化过studentList
    private static int readCount = 0;

    /**
     * 向集合中添加学生数据
     * 如果集合不存在，先读取文件中的内容，对集合进行初始化
     * 如果集合存在，先判断集合中有没有这个学生
     * @param student 要添加的学生
     * @return 返回生成ID的学生
     * @throws student_manager.exception.StudentExistException
     */
    @Override
    public Student addOne(Student student) {
        if (studentList == null) {
            if (readCount == 0) {
                studentList = IOUtil.readAll();
                readCount++;
            } else {
                throw new StudentCacheException("studentList is empty after reading date");
            }
        }

        if (!studentList.isEmpty() && studentList.contains(student)) {
            throw new StudentExistException("Student: " + student.getName() + " already exists");
        }
        studentList.add(student);
        return student;
    }

    /**
     * 向当前缓存中添加所有学生
     * @param students List<Student> (非空，且集合中有效数据大于0，并且小于3000)
     * @return BatchInsertResult { successCount, failList, failReason }
     * @throws StudentValidationException：整体验证失败 size>3000
     */
    @Override
    public BatchInsertResult addAll(List<Student> students) {
        int count = 0;
        List<Student> failList = new ArrayList<>();
        if (students == null) {
            throw new StudentValidationException("list out of range, range is 1 to 3000, but list is null");
        }
        if (students.isEmpty() || students.size() >= 3000) {
            throw new StudentValidationException("list out of range, range is 1 to 3000, but size is " + students.size());
        }
        if (studentList == null) {
            if (readCount == 0) {
                studentList = IOUtil.readAll();
                readCount++;
            } else {
                throw new StudentCacheException("studentList is empty after reading date");
            }
        }
        if (studentList == null || studentList.isEmpty()) {
            studentList = students;
            return new BatchInsertResult<Student>(students.size(), failList, null);
        }
        if (students.size() == 1) {
            addOne(students.get(0));
            return new BatchInsertResult<Student>(1, failList, null);
        }

        StringBuilder builder = new StringBuilder();
        for (Student student : students) {
            if (studentList.contains(student)) {
                builder.append(student.getName());
                builder.append(" ");
                builder.append(student.getIdCardNumber());
                builder.append(" 用户已存在");
                count ++;
                failList.add(student);
                continue;
            }
            studentList.add(student);
        }
        return new BatchInsertResult<Student>(students.size() - count, failList, builder.toString());

    }

    /**
     * 根据用户名和身份证查找用户
     * 先找用户名，用户名存在在找身份证
     * @param name
     * @param idCardNumber
     * @return
     */
    @Override
    public Optional<Student> findById(String name, String idCardNumber) {
        if (studentList == null || studentList.isEmpty()) {
            // 先尝试从文件读取，初始化集合
            studentList = IOUtil.readAll();
        }
        if (idCardNumber == null || idCardNumber.length() != 6) {
            throw new StudentValidationException("idCardNumber is not Illegal");
        }
        return studentList.stream().filter(student -> student.getName().equals(name) && student.getIdCardNumber().equals(idCardNumber)).findFirst();
    }


    /**
     * 查看所有学生
     * - 返回不可变列表
     * - 空缓存时返回空列表
     * @return List<Student>，可能为空列表，不为null
     * @throws StudentCacheException（缓存加载失败）
     */
    @Override
    public List<Student> findAll() {
        if (studentList == null && readCount == 0) {
            studentList = IOUtil.readAll();
            readCount++;
        }
        if (studentList == null) {
            throw new StudentCacheException("studentList is empty after reading date");
        }
        return studentList;
    }

    /**
     *   ID不可修改，其他字段全量覆盖
     * @param student Student(非空)
     * @return Student （旧的学生信息）
//     * @throws StudentValidationException：参数校验失败
     * @throws StudentNotFoundException ：学生不存在
     */
    @Override
    public Student updateStudentById(Student student) {

        if (studentList == null && readCount == 0) {
            studentList = IOUtil.readAll();
        }
        if (studentList == null || studentList.isEmpty()) {
            throw new StudentCacheException("studentList is empty after reading date");
        }
        for (Student stu :  studentList) {
            if (stu.getName().equals(student.getName()) && stu.getIdCardNumber().equals(student.getIdCardNumber())) {
                // 找到了学生，将信息覆盖
                Student s = null;
                try {
                    s = stu.clone();
                    HashMap<Discipline, Float> map = new HashMap<>(s.getScores());
                    s.setScores(map);
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                stu.setAge(student.getAge());
                stu.setScores(student.getScores());
                System.out.println(s.toString());
                return s;
            }
        }
        throw new StudentNotFoundException("Student: " + student.getName() + " does not exist");
    }

    /**
     * 删除指定学号的学生
     * 物理删除，数据移至delete_student文件
     * @param idCardNum String idCardNum（非空，长度6)
     * @return Optional<Student> （被删除的学生，不存在返回empty）
     * @throws StudentValidationException（ID校验失败）
     */
    @Override
    public Optional<Student> deleteById(String idCardNum) {
        Student student = null;
        if (idCardNum.length() != 6) {
            throw new StudentValidationException("idCardNumber is not legal");
        }
        if (studentList == null && readCount == 0) {
            studentList = IOUtil.readAll();
        }
        if (studentList == null || studentList.isEmpty()) {
            return Optional.empty();
        }
        int index = -1;
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getIdCardNumber().equals(idCardNum)) {
                index = i;
                student = studentList.get(i);
                break;
            }
        }
        if (index >= 0) {
            System.out.println("执行remove");
            studentList.remove(index);
        }
        if (student == null) {
            return Optional.empty();
        }
        return Optional.of(student);
    }

    @Override
    public void writeToFile() {
        IOUtil.write(studentList);
    }
}
