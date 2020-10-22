package org.university.people;

import org.university.software.CampusCourse;
import org.university.software.OnlineCourse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Employee extends Person {
	
	protected double pay;
	
	public abstract double earns();
	
	
	public abstract void raise( double percent );

}
