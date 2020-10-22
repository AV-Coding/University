package org.university.software;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.university.people.Person;
import org.university.people.Student;

public class OnlineCourse extends Course {
	
	public OnlineCourse() {
		hasProfessor = false;
		name = "unknown";
		student_roster = new ArrayList<Person>();
	}
	@Override
	public boolean availableTo(Student student) {
		return student.getEnrolledCampusCredits() > 5;
	}

}
