package org.university.software;

import org.university.people.*;
import org.university.hardware.*;
import org.university.software.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class UniversityGUI extends JFrame {
	private University university;
	private JMenuBar menuBar;
	
	private JMenu fileMenu;
	private JMenu studentsMenu;
	private JMenu administratorsMenu;
	
	private JMenuItem fileSave;
	private JMenuItem fileLoad;
	private JMenuItem fileExit;
	
	private JMenuItem addCourse;
	private JMenuItem dropCourse;
	private JMenuItem printSchedule;
	
	private JMenuItem printAllInfo;
	
	public UniversityGUI(String title, University university){
		super(title);
		this.university = university;
		
		add(new JLabel("<HTML><center>Welcome to the University." +
				"<BR>Choose an action from the above menus.</center></HTML>"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 100);
		buildGUI();	
		setVisible(true);
	}
	
	public void buildGUI(){
		menuBar = new JMenuBar();
     	
		// Menu's
		administratorsMenu = new JMenu("Administrators");
		fileMenu = new JMenu("File");
		studentsMenu = new JMenu("Students");
		
		// Sub-Menu's of regular menu's
		fileExit = new JMenuItem ("Exit");
		fileSave = new JMenuItem("Save");
		fileLoad = new JMenuItem("Load");
		
		addCourse = new JMenuItem("Add Course");
		dropCourse = new JMenuItem("Drop Course");
		printSchedule = new JMenuItem("Print Schedule");
		
		printAllInfo = new JMenuItem("Print All Info");

		fileExit.addActionListener(new MenuListener());		
		fileSave.addActionListener(new MenuListener());		
		fileLoad.addActionListener(new MenuListener());
		
		addCourse.addActionListener(new MenuListener());
		dropCourse.addActionListener(new MenuListener());
		printSchedule.addActionListener(new MenuListener());
		
		printAllInfo.addActionListener(new MenuListener());
		
		administratorsMenu.add(printAllInfo);
	    
		fileMenu.add(fileExit);
		fileMenu.add(fileSave);
		fileMenu.add(fileLoad);
	    
	    studentsMenu.add(addCourse);
	    studentsMenu.add(dropCourse);
	    studentsMenu.add(printSchedule);
	    
	    menuBar.add(administratorsMenu);
	    menuBar.add(fileMenu);
	    menuBar.add(studentsMenu);
	
		setJMenuBar(menuBar);
		
	}
	
	private static void createAddCourseWindow(String Title, String name, String dept, String course) {
	      JFrame frame = new JFrame(Title);
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      JLabel textLabel = new JLabel("I'm a label in the window",SwingConstants.CENTER);
	      textLabel.setPreferredSize(new Dimension(20, 50));
	      frame.getContentPane().add(textLabel, BorderLayout.CENTER);
	      frame.setLocationRelativeTo(null);
	      frame.pack();
	      frame.setVisible(true);
	   }
	
	private class MenuListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			JMenuItem source = (JMenuItem)(event.getSource());
			
			if(source.equals(fileExit))
			{
				exit();	
			}
			else if(source.equals(fileSave))
			{
				save();
			}
			else if(source.equals(fileLoad)) {
				load();
			}
			else if(source.equals(addCourse)) {
				addcourse();
			}
			else if(source.equals(dropCourse)) {
				dropcourse();
			}
			else if(source.equals(printSchedule)) {
				printschedule();
			}
			else if(source.equals(printAllInfo)) {
				printallinfo();
			}
		}
		public void exit() {
			System.exit(0);
		}
		public void load() {
			university = University.loadData();
		}
		public void save() {
			University.saveData(university);
		}
		public void addcourse() {
			JTextField studentName = new JTextField();
			JTextField departmentName = new JTextField();
			JTextField courseNumber = new JTextField();
			
			String studentValue;
			String departmentValue;
			int courseValue;		
			
			Object[]fields = {
				"Student Name:", studentName,
				"Department:", departmentName,
				"Course #:", courseNumber	
			};
			JOptionPane.showConfirmDialog(null, fields, "Add Course", JOptionPane.OK_CANCEL_OPTION);
			studentValue = studentName.getText();
			departmentValue = departmentName.getText();
			courseValue = Integer.parseInt(courseNumber.getText());
			
			Student theStudent = containsStudent(studentValue);
			CampusCourse theCampusCourse = containsCampusCourse(departmentValue, courseValue);
			OnlineCourse theOnlineCourse = containsOnlineCourse(departmentValue, courseValue);
			
			boolean studentExists, deptExists, courseExists = false;
			if(theStudent != null) {
				studentExists = true;
			}
			else
			{
				studentExists = false;
			}
			deptExists = containsDepartment(departmentValue);
			if((theCampusCourse != null)||(theOnlineCourse!=null))
			{
				courseExists = true;
			}
			else
			{
				courseExists = false;
			}
			if(studentExists && deptExists && courseExists) {
				CustomPrintStream printStream = new CustomPrintStream(); 
				PrintStream standard = System.out;
		        System.setOut(printStream); 
		        if(theCampusCourse!=null && theOnlineCourse==null) {
		        	if(!theStudent.detectConflict(theCampusCourse)&&(theCampusCourse.availableTo(theStudent))) {
			        	theStudent.addCourse(theCampusCourse);	
			        	JOptionPane.showMessageDialog(null, "Success you have added " + theCampusCourse.getName(), "Success", JOptionPane.PLAIN_MESSAGE);	
		        	}
		        	else{
		        		theStudent.addCourse(theCampusCourse);
		        	}
		        }
		        else if(theCampusCourse==null && theOnlineCourse!=null){
		        	if(theOnlineCourse.availableTo(theStudent)) {
		        		theStudent.addCourse(theOnlineCourse);
		        		JOptionPane.showMessageDialog(null, "Success you have added " + theOnlineCourse.getName(), "Success", JOptionPane.PLAIN_MESSAGE);
		        	}
		        	else{
		        		theStudent.addCourse(theOnlineCourse);
		        	}
		        }
		        else {
		        JOptionPane.showMessageDialog(null, "Student \"" + studentValue + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
		        }
		        System.setOut(standard);
		        
			}
			else if(!studentExists)
			{
				JOptionPane.showMessageDialog(null, "Student \"" + studentValue + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
			}
			else if(!deptExists) {
				JOptionPane.showMessageDialog(null, "Department \"" + departmentValue + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
			}
			else if(!courseExists) {
				JOptionPane.showMessageDialog(null, "Course \"" + departmentValue + courseValue + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
			}
		}
		public void dropcourse() {
			JTextField studentName = new JTextField();
			JTextField departmentName = new JTextField();
			JTextField courseNumber = new JTextField();
			String studentValue;
			String departmentValue;
			int courseValue;		
			
			Object[]fields = {
				"Student Name:", studentName,
				"Department:", departmentName,
				"Course #:", courseNumber
				
			};
			JOptionPane.showConfirmDialog(null, fields, "Drop Course", JOptionPane.OK_CANCEL_OPTION);
			studentValue = studentName.getText();
			departmentValue = departmentName.getText();
			courseValue = Integer.parseInt(courseNumber.getText());
			
			Student theStudent = containsStudent(studentValue);
			CampusCourse theCampusCourse = containsCampusCourse(departmentValue, courseValue);
			OnlineCourse theOnlineCourse = containsOnlineCourse(departmentValue, courseValue);
			
			
			
			boolean studentExists, deptExists, courseExists = false;
			if(theStudent != null) {
				studentExists = true;
			}
			else
			{
				studentExists = false;
			}
			deptExists = containsDepartment(departmentValue);
			if((theCampusCourse != null)||(theOnlineCourse!=null))
			{
				courseExists = true;
			}
			else
			{
				courseExists = false;
			}
			
			if(studentExists && deptExists && courseExists) {
				CustomPrintStream printStream = new CustomPrintStream(); 
				PrintStream standard = System.out;
		        System.setOut(printStream); 
		        if(theCampusCourse!=null && theOnlineCourse==null) {
		        	theStudent.dropCourse(theCampusCourse);	
		        }
		        else if(theCampusCourse==null && theOnlineCourse!=null){
		        	theStudent.dropCourse(theOnlineCourse);
		        }
		        System.setOut(standard);
			}
			else if(!studentExists)
			{
				JOptionPane.showMessageDialog(null, "Student \"" + studentValue + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
			}
			else if(!deptExists) {
				JOptionPane.showMessageDialog(null, "Department \"" + departmentValue + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
			}
			else if(!courseExists) {
				JOptionPane.showMessageDialog(null, "Course \"" + departmentValue + courseValue + "\" doesn't exist", "Error adding student to course", JOptionPane.OK_OPTION);
			}
		}
		public void printschedule() {
			String studentName;

			studentName = JOptionPane.showInputDialog(null, "Student Name: ", "Print Student Schedule", JOptionPane.QUESTION_MESSAGE);
			if(studentName != null)
			{
				if(studentName.trim().equals(""))
				{
					JOptionPane.showMessageDialog(null, 
												"Please enter correct Student name", 
												"Error Student doesn't exist", 
												JOptionPane.PLAIN_MESSAGE);
				}
				else
				{
					boolean studentExists = false;
					Student theStudent = containsStudent(studentName);
					if(theStudent != null) {
						studentExists = true;
					}
					if(!studentExists)
					{
						JOptionPane.showMessageDialog(null,
													"Student  \""+studentName+"\" doesn't exist.",
													"Error ",
													JOptionPane.PLAIN_MESSAGE);
					}
					else{
						JTextArea textArea = new JTextArea(18, 45); 
						textArea.setEditable(false);
						
						JScrollPane scrollBar = new JScrollPane(textArea);
						scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						
						PrintStream stream = new PrintStream(new WindowOutputStream(textArea));
						PrintStream standard = System.out;
						System.setOut(stream); 
						
						theStudent.printSchedule();
						
						JOptionPane.showMessageDialog(null, scrollBar, "University Info", JOptionPane.PLAIN_MESSAGE);
						System.setOut(standard);
						
					}
				}
			}
		}
		public Student containsStudent(String name){		
			for(Department department: university.getDepartments()) {
				for(Student student: department.getStudentList()) {
					if((student.getName()).equals(name)) {
						return student;
					}
				}
			}
			return null;
	    }
		public CampusCourse containsCampusCourse(String DepartmentName, int CourseNumber){		
			for(Department department: university.getDepartments()) {
				if(department.getDepartmentName().equals(DepartmentName)) {
					for(CampusCourse course : department.getCampusCourseList()) {
						if(course.getCourseNum()==CourseNumber){
							return course;
						}
					}
					return null;
				}
			}
			return null;
	    }
		
		public OnlineCourse containsOnlineCourse(String DepartmentName, int CourseNumber){		
			for(Department department: university.getDepartments()) {
				if(department.getDepartmentName().equals(DepartmentName)) {
					for(OnlineCourse course : department.getOnlineCourseList()) {
						if(course.getCourseNum()==CourseNumber){
							return course;
						}
					}
					return null;
				}
			}
			return null;
	    }
		public boolean containsDepartment(String DepartmentName){
			for(Department department : university.getDepartments()) {
				if(department.getDepartmentName().equals(DepartmentName)) {
					return true;
				}
			}
			return false;
		}
		public class CustomPrintStream extends PrintStream {  
		    public CustomPrintStream() {  
		        super(new ByteArrayOutputStream());  
		    }  

		  
		    public void println(String msg) {  
		        JOptionPane.showMessageDialog(null, msg);  
		    }  
		}
		
		private class WindowOutputStream extends OutputStream{
			private JTextArea text;
			public WindowOutputStream(JTextArea t) {
			text = t;
		
			}
			public void write(int a) throws IOException{
				text.append(String.valueOf((char)a));
				text.setCaretPosition(text.getDocument().getLength());
			}
		}
		public void printallinfo() {
			JTextArea textArea = new JTextArea(18, 45); 
			textArea.setEditable(false);
			
			JScrollPane scrollBar = new JScrollPane(textArea);
			scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			
			PrintStream stream = new PrintStream(new WindowOutputStream(textArea));
			PrintStream standard = System.out; 
			System.setOut(stream); 
			
			university.printAll();
			
			JOptionPane.showMessageDialog(null, scrollBar, "University Info", JOptionPane.PLAIN_MESSAGE);
			System.setOut(standard); 
		}
	}
}
