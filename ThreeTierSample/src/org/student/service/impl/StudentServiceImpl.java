package org.student.service.impl;

import java.util.List;

import org.student.dao.IStudentDao;
import org.student.dao.impl.StudentDaoImpl;
import org.student.entity.Student;
import org.student.service.IStudentService;

public class StudentServiceImpl implements IStudentService {
	IStudentDao studentDao = new StudentDaoImpl();

	public Student queryStudentBySno(int sno) {
		return studentDao.queryStudentBySno(sno);
	}

	public List<Student> queryAllStudents() {
		return studentDao.queryAllStudents();
	}

	public boolean updateStudentBySno(int sno, Student student) {
		if (studentDao.isExist(sno)) {
			return studentDao.updateStudentBySno(sno, student);
		}
		return false;
	}

	public boolean deleteStudentBySno(int sno) {
		if (studentDao.isExist(sno)) {
			return studentDao.deleteStudentBYSno(sno);
		}
		return false;
	}

	public boolean addStudent(Student student) {
		if (!studentDao.isExist(student.getSno())) {
			return studentDao.addStudent(student);
		} else {
			System.out.println("此人已存在");
			return false;
		}
	}

	@Override
	public int getTotalCount() {
		return studentDao.getTotalCount();
	}

	@Override
	public List<Student> queryStudentsByPage(int currentPage, int pageSize) {
		return studentDao.queryStudentsByPage(currentPage, pageSize);
	}
}
