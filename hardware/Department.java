package org.university.hardware;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.university.people.*;
import org.university.software.*;

public class Department implements Serializable{
	
	private String name;
	private ArrayList<Professor> professor_list;
	private ArrayList<Employee> staff_list;
	private ArrayList<Student> enrolled_students;
	private ArrayList<Course> offered_courses;
	private ArrayList<CampusCourse> CampusCourseList;
	private ArrayList<OnlineCourse> OnlineCourseList;
	
	public Department() {
		name="unknown";
		offered_courses = new ArrayList<Course>();
		enrolled_students = new ArrayList<Student>();
		professor_list = new ArrayList<Professor>();
		staff_list = new ArrayList<Employee>();
		CampusCourseList = new ArrayList<CampusCourse>();
		OnlineCourseList = new ArrayList<OnlineCourse>();
	}
	
	public void addProfessor( Professor professor ) {
		this.professor_list.add(professor);
		//staff_list.add(professor);
	}
	
	public void setDepartmentName( String name ) {
		this.name = name;
	}
	
	public void addCourse( CampusCourse course ) {
		course.setDepartment(this);
		offered_courses.add(course);
		CampusCourseList.add(course);
	}
	
	public void addCourse( OnlineCourse course) {
		course.setDepartment(this);
		offered_courses.add(course);
		OnlineCourseList.add(course);
	}
	
	public void addStudent( Student student ) {
		student.setDepartment(this);
		this.enrolled_students.add(student);
	}
	
	public void addStaff( Employee staff ) {
		staff.setDepartment(this);
		staff_list.add(staff);
	}
	
	public ArrayList<Student> getStudentList(){
		return this.enrolled_students;
	}
	
	public ArrayList<Course> getCourseList(){
		return offered_courses;
	}
	
	public ArrayList<Employee> getStaffList(){
		return this.staff_list;
	}
	
	public String getDepartmentName() {
		return this.name;
	}
	
	public ArrayList<CampusCourse> getCampusCourseList(){
		return CampusCourseList;
	}
	
	public ArrayList<OnlineCourse> getOnlineCourseList(){
		return OnlineCourseList;
	}
	
	public ArrayList<Professor> getProfessorList(){
		return this.professor_list;
	}
	
	public void printStaffList() {
		for(Employee ep : staff_list) {
			System.out.println(ep.getName());
		}
	}
	
	public void printStudentList() {
		for(Student st : enrolled_students) {
			System.out.println(st.getName());
		}
	}
	
	public void printProfessorList() {
		for(Professor st : professor_list) {
			System.out.println(st.getName());
		}
	}
	
	public void printCourseList() {
		for(Course course : CampusCourseList) {
			System.out.println(course.getDepartment().getDepartmentName() + course.getCourseNumber() + " " + course.getName());
		}
		for(Course course : OnlineCourseList) {
			System.out.println(course.getDepartment().getDepartmentName() + course.getCourseNumber() + " " + course.getName());
		}
		/*for(Course st : offered_courses) {
			System.out.println(st.getDepartment().getDepartmentName() + st.getCourseNumber() + " " + st.getName());
		}*/
	}
	public void printInformation() {
		System.out.println("Department " + this.getDepartmentName());
		System.out.println();
		System.out.println("Printing Professor Schedules: ");
		System.out.println();
		for(Professor pr : professor_list) {
			System.out.println("The Schedule for Prof. " + pr.getName() + ":");
			pr.printSchedule();
			System.out.println();
		}
		
		System.out.println("Printing Student Schedules: ");
		System.out.println();
		for(Student student : enrolled_students) {
			System.out.println("The schedule for Student " + student.getName() + ":");
			student.printSchedule();
			System.out.println();
		}
		
		System.out.println("Printing Staff Schedules:");
		System.out.println();
		for(Employee staff : staff_list){
			System.out.println("The schedule for Employee " + staff.getName() +":");
			staff.printSchedule();
			System.out.println();
			System.out.println("Staff: " + staff.getName() + " earns " + String.valueOf(staff.earns()) + " this month");
		}
		
		System.out.println();
		System.out.println("The rosters for courses offered by " + this.getDepartmentName());
		System.out.println();
		
		for(Course course : CampusCourseList) {
			System.out.println("The roster for course " + this.getDepartmentName() + course.getCourseNumber());
			course.printRoster();
			System.out.println();
		}
	}
	
}
