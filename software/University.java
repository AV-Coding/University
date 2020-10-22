package org.university.software;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import org.university.people.*;
import org.university.hardware.*;

public class University implements Serializable{
	
	public ArrayList<Classroom> classroomList;
	public ArrayList<Department> departmentList;
	private ArrayList<Student> student_list;
	private ArrayList<Course> campuscourse_list;
	private ArrayList<Course> onlinecourse_list;
	private ArrayList<Professor> professor_list;
	private ArrayList<Employee> staff_list;
	
	public University() {
		staff_list = new ArrayList<Employee>();
		professor_list = new ArrayList<Professor>();
		classroomList = new ArrayList<Classroom>();
		departmentList = new ArrayList<Department>();
		student_list = new ArrayList<Student>();
		campuscourse_list = new ArrayList<Course>();
		onlinecourse_list = new ArrayList<Course>();
	}
	
	public void printDepartmentList() {
		System.out.println("List of departments:");
		for(int i = 0; i< this.departmentList.size(); i++) {
			System.out.println(this.departmentList.get(i).getDepartmentName());
		}
	}
	
	public void printStudentList() {
		student_list.clear();
		for(int i = 0; i < this.departmentList.size(); i++) {
			this.student_list.addAll(this.departmentList.get(i).getStudentList());
		}
		 
		for(int j = 0; j < this.student_list.size(); j++) {
			System.out.println(this.student_list.get(j).getName());
		}
	}
	
	public void printProfessorList() {
		professor_list.clear();
		for(int i = 0; i < this.departmentList.size(); i++) {
			System.out.println("The professor list for department " + departmentList.get(i).getDepartmentName());
			for(Professor professor : departmentList.get(i).getProfessorList()) {
				System.out.println(professor.getName());
			}
			System.out.println();
			//System.out.println(this.professor_list.get(j).getName());
			//this.professor_list.addAll(this.departmentList.get(i).getProfessorList());
		}
		 
		/*for(int j = 0; j < this.professor_list.size(); j++) {
			System.out.println(this.professor_list.get(j).getName());
		}*/
	}
	
	public void printStaffList() {
		staff_list.clear();
		for(int i = 0; i < this.departmentList.size(); i++) {
			this.staff_list.addAll(this.departmentList.get(i).getStaffList());
		}
		 
		for(int j = 0; j < this.staff_list.size(); j++) {
			System.out.println(this.staff_list.get(j).getName());
		}
	}
	
	public void printCourseList() {
		campuscourse_list.clear();
		onlinecourse_list.clear();
		
		/*for(int i = 0; i < departmentList.size(); i++) {
			campuscourse_list.addAll(departmentList.get(i).getCampusCourseList());
		}
		
		for(int i = 0; i < departmentList.size(); i++) {
			onlinecourse_list.addAll(departmentList.get(i).getOnlineCourseList());
		}
		
		for(int j = 0; j < this.campuscourse_list.size(); j++) {
			System.out.println(campuscourse_list.get(j).getDepartment().getDepartmentName() + campuscourse_list.get(j).getCourseNumber() +" " + campuscourse_list.get(j).getName());
		}
		for(Course course : onlinecourse_list) {
			System.out.println(course.getDepartment().getDepartmentName() +course.getCourseNumber() + " " + course.getName());
		}*/
		//ArrayList<Course> currCourses = new ArrayList<Course>();
		for(int i = 0; i < departmentList.size(); i++){
			System.out.println("The course list for department " + departmentList.get(i).getDepartmentName());
			/*currCourses = (departmentList.get(i)).getCourseList();
			for(int j = 0; j < currCourses.size(); j++) {
				System.out.println(departmentList.get(i).getDepartmentName() + currCourses.get(j).getCourseNumber() + " " + currCourses.get(j).getName());
			}*/
			departmentList.get(i).printCourseList();
			System.out.println();
		}
	}
	public void printClassrooms() {
		System.out.println("Classroom list:");
		for(Classroom classroom : this.classroomList) {
			System.out.println(classroom.getRoomNumber());
		}
	}
	
	public void printClassroomSchedules() {
		for(Classroom classroom : this.classroomList) {
			System.out.println("The schedule for classroom " + classroom.getRoomNumber());
			classroom.printSchedule();
			System.out.println();
		}
	}
	
	public void printAll() {
		System.out.println();
		this.printDepartmentList();
		System.out.println();
		this.printClassrooms();
		System.out.println();
		this.printProfessorList();
		this.printCourseList();
		this.printClassroomSchedules();
		for(Department dept: departmentList) {
			dept.printInformation();
		}
	}
	
	public static void saveData(University university){
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut = null;
		try {
			fileOut = new FileOutputStream("university.ser");
			objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(university);
			objOut.close();
			fileOut.close();
		}
		catch(IOException i){
			i.printStackTrace();
		}
	}
	
	public static University loadData(){
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		
		University university = null;
		try {
			fileIn = new FileInputStream("university.ser");
			objIn = new ObjectInputStream(fileIn);
			university = (University) objIn.readObject();
			objIn.close();
			fileIn.close();
		}
		catch(IOException i) {
			i.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return university;
	}
	public ArrayList<Department> getDepartments() {
		return departmentList;
	}
}
