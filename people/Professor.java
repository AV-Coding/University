package org.university.people;

import java.util.ArrayList;
import java.util.Collections;

import org.university.people.*;
import org.university.software.*;

public class Professor extends Employee{
	private ArrayList<Course> taught_courses;
	
	public Professor() {
		name = "unknown";
		pay = 0;
		schedule = new ArrayList<Integer>();
		taught_courses = new ArrayList<Course>();
		CampusCourseList = new ArrayList<CampusCourse>();
		OnlineCourseList = new ArrayList<OnlineCourse>();
	}
	
	public void addCourse( CampusCourse course ) {
		if(course.hasProfessor == false) {
			if(!detectConflict(course)) { //This may have issues when declaring this detect conflict since it is referring to the detect conflict in "Person"
				CampusCourseList.add(course);  //With this detect conflict
				course.setProfessor(this);
				for(int i=0; i<course.getSchedule().size(); i++) {
					this.schedule.add(course.getSchedule().get(i));	
				}
			}
		}
		else {
			String statement = "The professor " + name +" cannot be assigned to this campus course because professor " + course.getProfessor().getName();
			statement += " is already assigned to the course " + course.getName() + ".";
			System.out.println(statement);
		}
	}
	
	public void addCourse( OnlineCourse course ) {
		if(course.hasProfessor == false) { //This may have issues when declaring this detect conflict since it is referring to the detect conflict in "Person"
		this.OnlineCourseList.add(course);  //With this detect conflict
		course.setProfessor(this);
		}
		else {
			String statement ="The professor cannot be assigned to this online course because professor ";
			statement += course.getProfessor().getName() + " is already assigned to the online course ";
			statement += course.getName() + ".";
			System.out.println(statement);
		}
	}
	public void setSalary(double salary) {
		pay = salary;
	}

	@Override
	public double earns() {
		return pay/26;
		
	}
	@Override
	public void raise(double percent) {
			pay += pay * (percent/100);
	}
	
	
}
