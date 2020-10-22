package org.university.software;

import org.university.people.*;
import org.university.hardware.*;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Course implements Serializable{
	
	protected String name;
	public boolean hasProfessor;
	protected Department department;
	protected int course_number;
	protected int currentEnrolledStudents;
	protected ArrayList<Person> student_roster;
	protected Professor professor;
	protected int units;
	private Classroom assigned_room;
	
	public void addStudentToRoster(Person person){
		student_roster.add(person);
		currentEnrolledStudents+=1;
	}
	
	public void setCreditUnits(int units){
		this.units = units;
	}
	
	public void setDepartment( Department department ) {
		this.department = department;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
	
	public void setCourseNumber( int course_number ) {
		this.course_number = course_number;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
		this.hasProfessor = true;
	}
	
	public void removeStudent(Person student){
		boolean remove = false;
		int i = 0;
		while(!remove) {
			if(student_roster.get(i).getName().compareTo(student.getName()) == 0) {
				student_roster.remove(i);
				currentEnrolledStudents-=1;
			}
			remove = true;
		}
	}
	
	public ArrayList<Person> getStudentRoster() {
		return student_roster;
	}
	
	public Department getDepartment() {
		return department;
	}
	
	public int getUnits() {
		return units;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public String getCourseNumber() {
		return String.valueOf(this.course_number);
	}
	
	public String getName() {
		return name;
	}
	public void printInfo() {
		String statement = "Course Number:" + getCourseNumber() +", Course Name:" + name + ", Credits:" + String.valueOf(units);
		System.out.println(statement);
	}
	public void printProfessor() {
		System.out.println("Professor:" + getProfessor().getName());
	}
	public void printRoster() {
		for(Person student: student_roster) {
			System.out.println(student.getName());
		}
		
	}
	public int getCourseNum() {
		return this.course_number;
	}
	
	public abstract boolean availableTo( Student student );
	
}
