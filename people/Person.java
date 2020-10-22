package org.university.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.university.hardware.*;
import org.university.software.*;

public abstract class Person implements Serializable{
	
	protected String name;
	protected Department department;
	protected int required_credits;
	protected int completed_credits;
	protected ArrayList<Integer> schedule;
	protected ArrayList<CampusCourse> CampusCourseList;
	protected ArrayList<OnlineCourse> OnlineCourseList;
	protected int campus_tuition;
	protected int online_tuition;
	
	public void setName( String name ) {
		this.name = name;
	}
	
	public void setDepartment( Department department ) {
		this.department = department;
	}
	
	public void setRequiredCredits(int credits) {
		this.required_credits = credits;
	}
	
	public void setCompletedUnits( int credits ){
		this.completed_credits = credits;
	}
	
	public boolean detectConflict( CampusCourse course ) {
		int counter = 0;
		for(int i = 0; i < course.getSchedule().size(); i++) {
			for(int j = 0; j < this.CampusCourseList.size(); j++) {
				for(int k = 0; k < this.CampusCourseList.get(j).getSchedule().size() ; k++){
					if(course.getSchedule().get(i).compareTo(this.CampusCourseList.get(j).getSchedule().get(k))==0) {
						String statement = course.getDepartment().getDepartmentName()+course.getCourseNumber();
						statement += " course cannot be added to " + this.getName() + "'s Schedule. ";
						statement += course.getDepartment().getDepartmentName()+course.getCourseNumber();
						statement += " conflicts with ";
						statement += this.CampusCourseList.get(j).getDepartment().getDepartmentName() + this.CampusCourseList.get(j).getCourseNumber() + ".";
						statement += " Conflicting time slot is " + this.convertSlot(course.getSchedule().get(i)) + ".";
						System.out.println(statement);
						counter +=1;
					}
				}
			}
		}
		if(counter>0) {
			return true;
		}
		return false;
	}
	
	// Fix this from the function, since the staff class is calling this function when trying to print schedule
	public void printSchedule(){
		Collections.sort(this.schedule);
		for(int i = 0; i < schedule.size(); i++) {
			String string_slot = convertSlot(schedule.get(i));
			for(CampusCourse cr: CampusCourseList) {
				for(Integer ints: cr.getSchedule()) {
					if(schedule.get(i) == ints.intValue()){
						String dept = cr.getDepartment().getDepartmentName();
						String cr_id = cr.getCourseNumber();
						System.out.println(string_slot + " " + dept + cr_id + " " + cr.getName());
					}
				}
			}
		}
		for(OnlineCourse oc : OnlineCourseList) {
			System.out.println(oc.getCourseNumber() +" "+ oc.getName());
		}
	}
	
	public String convertSlot(Integer slot){
		String temp;
		String day;
		String slots;
		int Day;
		int time_slot;
		String[] Week={"Mon","Tue","Wed","Thu","Fri"};
		String[] Slot= { "8:00am to 9:15am ",
				"9:30am to 10:45am" ,
				"11:00am to 12:15pm" ,
				"12:30pm to 1:45pm",
				"2:00pm to 3:15pm",
				"3:30pm to 4:45pm"};
		
		temp = String.valueOf(slot);
		day = Character.toString(temp.charAt(0));
		slots = Character.toString(temp.charAt(1));
		slots += Character.toString(temp.charAt(2));
		Day = Integer.parseInt(day);
		time_slot = Integer.parseInt(slots);
		return Week[Day-1] + " " + Slot[time_slot-1]; 
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getTuitionFee() {
		return campus_tuition + online_tuition;
	}
	
	public void printCourses() {
		for(CampusCourse cc : CampusCourseList) {
			System.out.println(cc.getName());
		}
		for(OnlineCourse oc : OnlineCourseList) {
			System.out.println(oc.getName());
		}
	}
	
	public abstract void addCourse( CampusCourse course );
	
	
	public abstract void addCourse( OnlineCourse course );
	
}
