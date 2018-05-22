package org.student.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.student.dao.IStudentDao;
import org.student.entity.Student;
import org.student.util.DBUtil;

public class StudentDaoImpl implements IStudentDao {

	public boolean addStudent(Student student) {
		String sql = "insert into student values(?,?,?,?)";
		Object[] params = { student.getSno(), student.getSname(), student.getSage(), student.getSaddress() };
		return DBUtil.executeUpdate(sql, params);
	}

	// 根据学号修改学生： 根据sno知道待修改的人 ，把这个人 修改成student
	public boolean updateStudentBySno(int sno, Student student) {
		String sql = "update student set sname =?,sage=?,saddress=? where sno=?";
		Object[] params = { student.getSname(), student.getSage(), student.getSaddress(), sno };
		return DBUtil.executeUpdate(sql, params);
	}

	// 根据学号删除学生
	public boolean deleteStudentBYSno(int sno) {
		String sql = "delete from student where sno=?";
		Object[] params = { sno };
		return DBUtil.executeUpdate(sql, params);
	}

	// 查询全部学生(很多学生)
	public List<Student> queryAllStudents() {
		List<Student> students = new ArrayList<>();
		ResultSet rs = null;
		try {
			String sql = "select * from student";
			rs = DBUtil.executeQuery(sql, null);
			while (rs.next()) {
				int no = rs.getInt("sno");
				String name = rs.getString("sname");
				int age = rs.getInt("sage");
				String address = rs.getString("saddress");
				Student student = new Student(no, name, age, address);
				students.add(student);
			}
			return students;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeAll(rs, null, DBUtil.connection);
		}
	}

	// 查询此人是否存在
	public boolean isExist(int sno) {
		return queryStudentBySno(sno) == null ? false : true;
	}

	// 根据学号 查询学生
	public Student queryStudentBySno(int sno) {// 3
		Student student = null;
		ResultSet rs = null;
		try {
			String sql = "select * from student where sno =? ";
			Object[] params = { sno };
			rs = DBUtil.executeQuery(sql, params);
			if (rs.next()) {
				int no = rs.getInt("sno");
				String name = rs.getString("sname");
				int age = rs.getInt("sage");
				String address = rs.getString("saddress");
				student = new Student(no, name, age, address);
			}
			return student;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeAll(rs, null, DBUtil.connection);
		}
	}

	@Override
	public int getTotalCount() {
		String sql = "select count(*) from student";
		return DBUtil.getTotalCount(sql);
	}

	// 查询当前页（特定页）的学生集合
	// currentPage:页数、当前页的页码
	// pageSize：页面大小
	@Override
	public List<Student> queryStudentsByPage(int currentPage, int pageSize) {
		String sql = "select *from (" 
				+ "select rownum r,t.*from" 
				+ "	(select s.* from student s order by sno asc) t"
				+ "	where  rownum<=? 	" 
				+ ") where  r>=?";
		Object[] params = { currentPage * pageSize, (currentPage - 1) * pageSize + 1 };
		ResultSet rs = DBUtil.executeQuery(sql, params);
		List<Student> students = new ArrayList<>();
		try {
			while (rs.next()) {
				Student student = new Student(rs.getInt("sno"), rs.getString("sname"), rs.getInt("sage"),
						rs.getString("saddress"));
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, DBUtil.pstmt, DBUtil.connection);
		}
		return students;
	}
}

