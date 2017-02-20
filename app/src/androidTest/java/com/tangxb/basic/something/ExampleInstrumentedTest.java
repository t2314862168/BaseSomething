package com.tangxb.basic.something;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.tangxb.basic.something.greendao.model.DaoMaster;
import com.tangxb.basic.something.greendao.model.DaoSession;
import com.tangxb.basic.something.greendao.model.Student;
import com.tangxb.basic.something.greendao.model.Teacher;
import com.tangxb.basic.something.greendao.model.TeacherDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.LazyList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.tangxb.basic.something", appContext.getPackageName());
    }

    /**
     * https://github.com/greenrobot/greenDAO/blob/master/tests/DaoTestEntityAnnotation/src/androidTest/java/org/greenrobot/greendao/test/entityannotation/CustomerOrderTest.java
     */
    private DaoSession daoSession;
    private TeacherDao teacherDao;

    @Before
    public void needBefore() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(appContext, "greendao-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        teacherDao = getDaoSession().getTeacherDao();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Test
    public void testS1() {
        Teacher teacher = new Teacher();
        teacher.setTeacherIndex("teacherIndex1");
        teacher.setName("teacher1");
        teacher.setAge(1);
        teacher.setSex(1);

        Teacher teacher2 = new Teacher();
        teacher2.setTeacherIndex("teacherIndex2");
        teacher2.setName("teacher2");
        teacher2.setAge(2);
        teacher2.setSex(1);

        Teacher teacher3 = new Teacher();
        teacher3.setTeacherIndex("teacherIndex3");
        teacher3.setName("teacher3");
        teacher3.setAge(3);
        teacher3.setSex(1);

        List<Teacher> teacherList = new ArrayList<>();
        teacherList.add(teacher);
        teacherList.add(teacher2);
        teacherList.add(teacher3);

        teacherDao.insertInTx(teacherList);
        List<Teacher> teacherList2 = teacherDao.loadAll();
        assert teacherList.size() == teacherList2.size();
        System.out.println();
    }

    @Test
    public void testS2() {
        Teacher teacher = new Teacher();
        teacher.setTeacherIndex("teacherIndex1");
        teacher.setName("teacher1");
        teacher.setAge(1);
        teacher.setSex(1);
//        long insert = teacherDao.insert(teacher);
//        assert insert == 0;

        Teacher load = teacherDao.load(new Long(1));
        LazyList<Teacher> teacherLazyList = teacherDao.queryBuilder().where(TeacherDao.Properties.TeacherIndex.eq("teacherIndex1")).listLazy();
        assert teacherLazyList.size() == 1;
        List<Teacher> teacherList = teacherLazyList.subList(0, teacherLazyList.size());
        assert teacherList.size() == 1;

        assert load.getId() == teacherList.get(0).getId();

        teacherDao.deleteAll();
        long count = teacherDao.count();
        assert count == 0;
    }

    @Test
    public void testS3() {
        Student student = new Student();
        student.setStudentId(new Long(1));
        student.setName("student1");
        student.setAge(1);

        Teacher teacher = new Teacher();
        teacher.setTeacherId(new Long(1));
        teacher.setTeacherIndex("teacherIndex1");
        teacher.setName("teacher1");
        teacher.setAge(1);
        teacher.setSex(1);
        teacher.setStudentId(student.getStudentId());
        getDaoSession().insert(teacher);

        student.setTeacherId(teacher.getTeacherId());
        getDaoSession().insert(student);

        List<Teacher> teacherList = teacherDao.loadAll();
        List<Student> students = teacherList.get(0).getStudents();
        assert students.size() == 0;
    }

    @Test
    public void testS4() {
        teacherDao.deleteAll();
        List<Teacher> teacherList = teacherDao.loadAll();
        assert teacherList.size() == 0;
    }

    @Test
    public void testS5() {
        List<Student> students = getDaoSession().getStudentDao().loadAll();
        assert students.size() == 0;
    }
}
