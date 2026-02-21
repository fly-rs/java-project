package student_manager;

import org.jetbrains.annotations.NotNull;
import student_manager.exception.AgeIllegalException;
import student_manager.exception.ScoreException;
import student_manager.exception.StudentValidationException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * ClassName: Student
 * Package: student_manager
 * Description:
 *
 * @Author fly
 * @Create 2026/2/19 21:22
 * @Version 1.0
 */
public class Student implements Serializable, Cloneable {
    private final static Long serialVersionUID = 20262020L;

    // 学号，以10位数字类型的字符构成，id应该是唯一的
    private String id;
    private String name;
    private Integer age;
    // 身份证号，确认学生对信息应该以名字加身份证
    private String idCardNumber;
    // 存储学生的各科成绩
    private Map<Discipline, Float> scores;
    // 生成一个序号，用这个序号加上年月日来表示学生ID
    private static Integer num = 100000;

    private Student() {
        if (num >= 150000) {
            throw new RuntimeException("学生已达最大容量");
        }
        num ++;
        generateId();
    }
    public Student(String name, Integer age, @NotNull String idCardNumber, Map<Discipline, Float> scores) {
        this();
        setName(name);
        setAge(age);
        setIdCardNumber(idCardNumber);
        setScores(scores);
    }


    public String getIdCardNumber() {
        return idCardNumber;
    }


    public void setIdCardNumber(String idCardNumber) {
        if (idCardNumber == null || idCardNumber.length() != 6) {
            throw new StudentValidationException("idCardNumber is not Illegal");
        }
        this.idCardNumber = idCardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) && Objects.equals(age, student.age) && Objects.equals(idCardNumber, student.idCardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, idCardNumber, scores);
    }

    /**
     * 用于生成学号id的方法，不考虑多线程环境
     * 日期生成使用当前年份 + num 序列号
     */
    private void generateId() {
        // 获取当前年份
        int year = java.time.LocalDate.now().getYear();
        // 生成id
        this.id = String.format("%d%06d",  year, num);
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(@NotNull Integer age) {
        if (age > 17 && age < 50) {
            this.age = age;
        } else {
            throw new AgeIllegalException("age range should be between 17 and 50, but is " + age);
        }

    }

    public Map<Discipline, Float> getScores() {
        return scores == null ? new HashMap<>() : new HashMap<>(scores);
    }

    public void setScore(@NotNull Discipline disciplineName, @NotNull Float score) {
        if (score < 0.0F) {
            throw new ScoreException("score should be >= 0.0 but is " + score);
        }
        if (this.scores == null) {
            this.scores = new HashMap<>();
        }
        this.scores.put(disciplineName, score);
    }
    public void setScores(@NotNull Map<Discipline, Float> scores) {
        if (this.scores == null) {
            this.scores = new HashMap<>(scores);
        } else {
            this.scores.putAll(scores);
        }

    }

    private void resetScores() {
        this.scores = null;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", idCardNumber='" + idCardNumber + '\'' +
                ", scores=" + scores +
                '}';
    }

    public Student clone() throws CloneNotSupportedException {
        Student student = (Student) super.clone();
        Map<Discipline, Float> map = new HashMap<>(scores);
        student.resetScores();
        student.setScores(map);
        return student;
    }
}
