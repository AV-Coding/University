package org.university.hardware;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;

import org.university.people.*;
import org.university.software.*;

public class Classroom implements Serializable{

	private String room_number;
	public ArrayList<Integer> schedule;
	public ArrayList<CampusCourse> courses_list;
	
	public Classroom() {
		this.room_number = "unknown";
		this.schedule = new ArrayList<Integer>();
		this.courses_list = new ArrayList<CampusCourse>();
	}
	
	public void setRoomNumber( String roomNumber) {
		this.room_number = roomNumber;
	}
	
	public boolean noOverLap( CampusCourse course ) {
		int counter =0;
		if(this.courses_list.size() == 0) {
			for(int i=0; i<course.getSchedule().size(); i++) {
				this.schedule.add(course.getSchedule().get(i));	
			}
			return true;
		}
		else {
			for(int i = 0; i < course.getSchedule().size(); i++) {
				for(int j = 0; j < this.courses_list.size(); j++) {
					for(int k = 0; k < this.courses_list.get(j).getSchedule().size(); k++){
						if(course.getSchedule().get(i).intValue() == this.courses_list.get(j).getSchedule().get(k).intValue()) {
							String statement = course.getDepartment().getDepartmentName()+course.getCourseNumber();
							statement += " conflicts with ";
							statement += this.courses_list.get(j).getDepartment().getDepartmentName() + this.courses_list.get(j).getCourseNumber() + ".";
							statement += " Conflicting time slot " + this.convertSlot(course.getSchedule().get(i)) + ". ";
							statement += course.getDepartment().getDepartmentName()+course.getCourseNumber();
							statement += " course cannot be added to " + this.room_number + "'s Schedule.";
							System.out.println(statement);
							counter ++;
						}
					}
				}
			}
			if(counter>0) {
				return false;
			}
			for(int i=0; i<course.getSchedule().size(); i++) {
				this.schedule.add(course.getSchedule().get(i));	
			}
			return true;
		}
	}
	
	public String getRoomNumber() {
		return this.room_number;
	}
	
	public String convertSlot(Integer slot) {
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
	
	public void printSchedule() {
		Collections.sort(this.schedule);
		for(int i = 0; i < this.schedule.size(); i++) {
			int slots = this.schedule.get(i);
			String string_slot = convertSlot(slots);
			for(CampusCourse cr: this.courses_list) {
				for(Integer ints: cr.getSchedule()) {
					if(slots == ints.intValue()){
						String dept = cr.getDepartment().getDepartmentName();
						String cr_id = cr.getCourseNumber();
						System.out.println(string_slot + " " + dept + cr_id + " " + cr.getName());
					}
				}
			}
		}
	}
	
}
