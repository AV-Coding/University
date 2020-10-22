package org.university.software;

import org.university.hardware.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.university.people.*;

import java.util.ArrayList;
import java.util.Collections;

public class CampusCourse extends Course{
	
	private ArrayList<Integer> schedule;
	private int maxNumberStudents;
	private Classroom assigned_room;
	
	public CampusCourse() {
		hasProfessor = false;
		name = "unknown";
		schedule = new ArrayList<Integer>();
		student_roster = new ArrayList<Person>();
		currentEnrolledStudents = 0;
	}
	
	public void setRoomAssigned(Classroom classroom) {
		if(classroom.noOverLap(this)) {
		assigned_room = classroom;
		classroom.courses_list.add(this);
		}
	}
	
	public void setSchedule( int schedule ) {
		this.schedule.add(schedule);
	}
	
	public void setMaxCourseLimit(int max){
		this.maxNumberStudents = max;
	}
	
	public void printSchedule(){
		Collections.sort(this.schedule);
		for(int i = 0; i < schedule.size(); i++) {
			String string_slot = convertSlot(schedule.get(i));
			String cr_id = getCourseNumber();
			System.out.println(string_slot + " " + assigned_room.getRoomNumber());
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
	
	public void increaseEnrolledStudents() {
		currentEnrolledStudents +=1;
	}
	
	public ArrayList<Integer> getSchedule(){
		return this.schedule;
	}
	@Override
	public boolean availableTo(Student student) {
		return currentEnrolledStudents != maxNumberStudents;
	}
	public int getMaxCourseLimit() {
		return maxNumberStudents;
	}

}
