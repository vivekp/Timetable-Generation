package org.iitk.brihaspati.modules.timetable;

public class Course implements Constants {
	String courseCode;
	int professorId;
	int batchId;
	int lecturesPerWeek;
	int tutorialsPerWeek;
	int practicalsPerWeek;
	int seminarsPerWeek;
	int lectureDuration;
	int maxLecturesPerDay;
	int maxTutorialsPerDay;
	int maxLecturesInrow;
	int capacity;
	
	public Course(String courseCode) {
		this.courseCode = courseCode;
		this.maxLecturesPerDay = DEFAULT_MAX_LECTURES_DAY;
		this.maxTutorialsPerDay = DEFAULT_MAX_TUTORIALS_DAY;
		this.lecturesPerWeek = DEFAULT_LECTURES_WEEK;
		this.tutorialsPerWeek = DEFAULT_LECTURES_WEEK;
		this.practicalsPerWeek = DEFAULT_LECTURES_WEEK;
		this.seminarsPerWeek = DEFAULT_LECTURES_WEEK;
	
	}
	public Course(String course_code, int lecturesPerWeek, int tutorialsPerWeek,
								int max_lectures_per_day,int max_tutorials_per_day){
	
			this.courseCode = course_code;
			this.lecturesPerWeek = lecturesPerWeek; 
			this.maxLecturesPerDay = max_lectures_per_day;
			this.maxTutorialsPerDay = max_tutorials_per_day;
			this.tutorialsPerWeek = tutorialsPerWeek;
		}
		
	public String getCourseCode(){
			return courseCode ;
		}
		
	public int getLecturesPerWeek(){
			return lecturesPerWeek ;
		}
		
	public int getCapacity(){
			return capacity;
		}
		
	public void displayTimetable(){
			return ;
		}

	public int getMaxLecturesPerDay() {
		return maxLecturesPerDay;
	}
	
	public int getMaxTutorialsPerDay() {
		return maxTutorialsPerDay;
	}
	public void setNumLectues(int num_per_week) {
		this.lecturesPerWeek = num_per_week;
		
	}
	
	public void setNumTutorials(int num_per_week) {
		this.tutorialsPerWeek = num_per_week;
		
	}
	
	public void setNumPracticals(int num_per_week) {
		this.practicalsPerWeek = num_per_week;
		
	}
	public void setNumSeminars(int num_per_week) {
		this.seminarsPerWeek = num_per_week;
		
	}
}
		
	
