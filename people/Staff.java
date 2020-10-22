package org.university.people;

import java.util.ArrayList;

import org.university.software.CampusCourse;
import org.university.software.Course;
import org.university.software.OnlineCourse;

public class Staff extends Employee {
	private int campus_tuition;
	private int online_tuition;
	private double hourly_rate;
	private int monthly_hours;
	private int required_credits;
	private int completed_credits;
	private int CampusCredits;
	private int OnlineCredits;
	
	public Staff() {
		required_credits = 0;
		completed_credits = 0;
		CampusCredits = 0;
		OnlineCredits = 0;
		name = "unknown";
		pay = 0.0;
		campus_tuition = 0;
		online_tuition = 0;
		hourly_rate = 0;
		schedule = new ArrayList<Integer>();
		CampusCourseList = new ArrayList<CampusCourse>();
		OnlineCourseList = new ArrayList<OnlineCourse>();
	}
	@Override
	public double earns() {
		return monthly_hours*hourly_rate;
	}

	@Override
	public void raise(double percent) {
		hourly_rate += hourly_rate * (percent/100);
		
	}

	@Override
	public void addCourse(CampusCourse course) {
		String statement;
		if(CampusCourseList.size() == 0) {
			if(OnlineCourseList.size() ==0 ) {
			//will need other if statement to check OnlineCourseList
			CampusCourseList.add(course);
			course.addStudentToRoster(this);
			campus_tuition += course.getUnits() * 300;
			for(int i=0; i<course.getSchedule().size(); i++) {
				this.schedule.add(course.getSchedule().get(i));	
			}
			}
			else {
				statement = OnlineCourseList.get(0).getDepartment().getDepartmentName() + OnlineCourseList.get(0).getCourseNumber()+" is removed from " + name +"'s schedule";
				statement += "(Staff can only take one class at a time)." + course.getDepartment().getDepartmentName() + course.getCourseNumber() ;
				statement += " has been added to " + name + "'s Schedule.";
				OnlineCourseList.get(0).removeStudent(this); // removes staff from old course list
				decreaseOnlineTuition(OnlineCourseList.get(0));
				OnlineCourseList.remove(0);
				CampusCourseList.add(course);
				campus_tuition += course.getUnits() * 300;
				System.out.println(statement);
				this.schedule.clear();
			for(int i=0; i<course.getSchedule().size(); i++) {
				this.schedule.add(course.getSchedule().get(i));	
			}
			}
		}
		else {
			statement = CampusCourseList.get(0).getDepartment().getDepartmentName() + CampusCourseList.get(0).getCourseNumber()+" is removed from " + name +"'s schedule";
			statement += "(Staff can only take one class at a time)." + course.getDepartment().getDepartmentName() + course.getCourseNumber() ;
			statement += " has been added to " + name + "'s Schedule.";
			CampusCourseList.get(0).removeStudent(this); // removes staff from old course list
			campus_tuition -= CampusCourseList.get(0).getUnits() * 300;
			campus_tuition += course.getUnits() * 300;
			CampusCourseList.remove(0);
			CampusCourseList.add(course);
			System.out.println(statement);
			this.schedule.clear();
			for(int i=0; i<course.getSchedule().size(); i++) {
				this.schedule.add(course.getSchedule().get(i));	
			}
		}
		
	}
	
	public void setRequiredCredits(int credits) {
		required_credits = credits;
	}
	
	public void setCompletedUnits( int credits ){
		completed_credits = credits;
	}
	
	public void decreaseOnlineTuition(OnlineCourse course) {
		if(course.getUnits() == 3) {
			online_tuition -= 2000;
		}
		else {
			online_tuition -= 3000;
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
	
	public void setPayRate(double rate) {
		hourly_rate = rate;
	}
	
	public void setMonthlyHours(int hoursWorked) {
		monthly_hours = hoursWorked;
	}

	@Override
	public void addCourse(OnlineCourse course) {
		String statement;
		if(OnlineCourseList.size() == 0) {
			if(CampusCourseList.size() ==0 ) {
			//will need other if statement to check OnlineCourseList
			OnlineCourseList.add(course);
			course.addStudentToRoster(this);
			}
			else {
				statement = CampusCourseList.get(0).getDepartment().getDepartmentName() + CampusCourseList.get(0).getCourseNumber()+" is removed from " + name +"'s schedule";
				statement += "(Staff can only take one class at a time)." + course.getDepartment().getDepartmentName() + course.getCourseNumber() ;
				statement += " has been added to " + name + "'s Schedule.";
				CampusCourseList.get(0).removeStudent(this); // removes staff from old course list
				campus_tuition -= CampusCourseList.get(0).getUnits() * 300;
				CampusCourseList.remove(0);
				increaseOnlineTuition(course);
				OnlineCourseList.add(course);
				System.out.println(statement);
			}
		}
		else {
			statement = OnlineCourseList.get(0).getDepartment().getDepartmentName() + OnlineCourseList.get(0).getCourseNumber()+" is removed from " + name +"'s schedule";
			statement += "(Staff can only take one class at a time)." + course.getDepartment().getDepartmentName() + course.getCourseNumber() ;
			statement += " has been added to " + name + "'s Schedule.";
			OnlineCourseList.get(0).removeStudent(this); // removes staff from old course list
			decreaseOnlineTuition(OnlineCourseList.get(0));
			OnlineCourseList.remove(0);
			increaseOnlineTuition(course);
			OnlineCourseList.add(course);
			System.out.println(statement);
		}
		
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
	public int getTuitionFee() {
		return campus_tuition + online_tuition;
	}
}
