package org.university.people;

import java.util.ArrayList;
import java.util.*;

import org.university.hardware.*;
import org.university.software.*;

public class Student extends Person {
	private int enrolled_campus_credits;
	private int enrolled_online_credits;
	private int required_credits;
	private int completed_credits;
	private Department department;
	private int CampusCredits;
	private int OnlineCredits;
	
	public Student() {
		CampusCredits = 0;
		OnlineCredits = 0;
		enrolled_campus_credits = 0;
		enrolled_online_credits = 0;
		name = "unknown";
		CampusCourseList = new ArrayList<CampusCourse>();
		OnlineCourseList = new ArrayList<OnlineCourse>();
		schedule = new ArrayList<Integer>();
	}
	
	public void setRequiredCredits(int credits) {
		required_credits = credits;
	}
	
	public void setCompletedUnits( int credits ){
		completed_credits = credits;
	}
	
	public void setDepartment( Department department ) {
		this.department = department;
	}
	
	@Override
	public void addCourse(CampusCourse course) {
		if(!detectConflict(course)) {
			if(course.availableTo(this)) {
			CampusCourseList.add(course);
			course.addStudentToRoster(this);
			for(int i=0; i<course.getSchedule().size(); i++) {
				schedule.add(course.getSchedule().get(i));	
			}
			//course.increaseEnrolledStudents();
			enrolled_campus_credits += course.getUnits();
			campus_tuition += 300*course.getUnits();
			}
			else {
				String statement = name + " can't add Campus Course " + course.getDepartment().getDepartmentName() + course.getCourseNumber() + " " + course.getName();
				statement +=". Because this Campus course has enough student.";
				System.out.println(statement);
			}
		}
		
	}
	@Override
	public void addCourse(OnlineCourse course) {
		if(course.availableTo(this)) {
			course.addStudentToRoster(this);
			OnlineCourseList.add(course);
			enrolled_online_credits+= course.getUnits();
			increaseOnlineTuition(course);
		}
		else {
			String statement = "Student " + name + " has only " + String.valueOf(this.enrolled_online_credits) + " on campus credits enrolled. Should have at least 6 credits registered before registering online courses." ;
			statement += name + " can't add online Course " + course.getDepartment().getDepartmentName() + course.getCourseNumber() + " " + course.getName();
			statement += ". Because this student doesn't have enough Campus course credit.";
			System.out.println(statement);
		}
		
	}
	public void dropCourse(CampusCourse course) {
		boolean drop = false;
		int i = 0;
		String statement;
		while(drop == false){
			//remove schedule for course
			if((CampusCourseList.get(i).getName().compareTo(course.getName()) == 0)) {
				if(((enrolled_campus_credits-course.getUnits())>5) || (enrolled_online_credits==0)) {
					this.removeSchedule(course);
					course.removeStudent(this);
					CampusCourseList.remove(i);
					enrolled_campus_credits -= course.getUnits();
					if(enrolled_campus_credits < 6) {
						statement = "Student " + name + " has only " + enrolled_campus_credits + " on campus credits enrolled.";
						statement += "Should have at least 6 credits registered before registering online courses.";
						System.out.println(statement);
					}
					campus_tuition -= course.getUnits() * 300;
					drop = true;
					return;
				}
				else {
				statement = name + " can't drop this CampusCourse, because this student doesn't have enough campus course credit to hold the online course";
				System.out.println(statement);
				return;
				}
			}
			else if( i == (CampusCourseList.size() - 1)) {
				statement = "The course " + course.getDepartment().getDepartmentName();
				statement += course.getCourseNumber() + " could not be dropped because ";
				statement += this.getName() + " is not enrolled in ";
				statement += course.getDepartment().getDepartmentName()+course.getCourseNumber() + ".";
				System.out.println(statement);
				drop = true;
				return;
			}
			i++;
			
		}
	}
	public void dropCourse(OnlineCourse course) {
		boolean drop = false;
		int i = 0;
		String statement;
		while(drop == false){
			//remove schedule for course
			if((OnlineCourseList.get(i).getName().compareTo(course.getName()) == 0)) {
				//if((enrolled_campus_credits-course.getUnits())>5) {
					course.removeStudent(this);
					OnlineCourseList.remove(i);
					enrolled_online_credits -= course.getUnits();
					decreaseOnlineTuition(course);
					drop = true;
					return;
				/*}
				else {
				statement = name + " can't drop this " + course.getName()+ ", because student don't have enough campus course credit to hold online courses";
				return;
				}*/
			}
			else if( i == (OnlineCourseList.size() - 1)) {
				statement = "The course " + course.getDepartment().getDepartmentName();
				statement += course.getCourseNumber() + " could not be dropped because ";
				statement += this.getName() + " is not enrolled in ";
				statement += course.getDepartment().getDepartmentName()+course.getCourseNumber() + ".";
				System.out.println(statement);
				drop = true;
				return;
			}
			i++;
			
		}
	}
	
	public void increaseOnlineTuition(OnlineCourse course) {
		if(course.getUnits() == 3) {
			online_tuition += 2000;
		}
		else {
			online_tuition += 3000;
		}
	}
	
	public void removeSchedule(CampusCourse course){
		for(int i = 0; i < course.getSchedule().size(); i++) {
			for(int j = 0; j < schedule.size(); j++) {
				if(course.getSchedule().get(i).intValue()==schedule.get(j).intValue()){
					schedule.remove(j);
				}
			}
		}
	}
	
	public void decreaseOnlineTuition(OnlineCourse course) {
		if(course.getUnits() == 3) {
			online_tuition -= 2000;
		}
		else {
			online_tuition -= 3000;
		}
	}
	
	public int getEnrolledCampusCredits() {
		return enrolled_campus_credits;
	}
	
	public int getEnrolledOnlineCredits() {
		return enrolled_online_credits;
	}
	
	public int getCurrentlyEnrolledCredits() {
		return enrolled_campus_credits + enrolled_online_credits;
	}
	
	public int requiredToGraduate(){
		CampusCredits=0;
		for(Course course : CampusCourseList) {
			CampusCredits += course.getUnits();
		}
		OnlineCredits=0;
		for(Course course : OnlineCourseList) {
			OnlineCredits += course.getUnits();
		}
		
		return this.required_credits - completed_credits - CampusCredits - OnlineCredits;
	}
	
	public Department getDepartment() {
		return this.department;
	}
	
}
