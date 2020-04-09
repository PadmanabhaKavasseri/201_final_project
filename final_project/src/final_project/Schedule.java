package final_project;

import java.util.ArrayList;
import java.util.Calendar;

public class Schedule {
	
	private ArrayList<Course> courses;
	private int user_id;
	
	Schedule(ArrayList<Course> courses, int user_id){
		this.courses = courses;
		this.user_id = user_id;
	}
	
	//method to save this schedule to the database
	public void save_schedule() {
		Database.insert_schedule(this, user_id);
	}
	
	//method to check if the schedule is valid
	public boolean is_valid() {
		//create 2d array to represent a schedule and initialize to 0 for each time slot
		int[][] schedule_slots = new int[5][24];
		for(int i = 0; i < schedule_slots.length; i++){
		    for(int j = 0; j < schedule_slots[i].length; j++){
		       schedule_slots[i][j] = 0;
		    }
		}
		
		Calendar calendar = Calendar.getInstance();
		//slowly add courses, updating the array and seeing if each added courses is valid
		for(Course course:this.courses) {
			int[] days_list = course.get_days_list();
			calendar.setTime(course.start_time);
			int start_hour = calendar.get(Calendar.HOUR_OF_DAY);
			calendar.setTime(course.end_time);
			int end_hour = calendar.get(Calendar.HOUR_OF_DAY);
			
			//for each day for the course, verify the time slot isn't taken
			//if the slot is open, fill the time slot with this course
			for(int day: days_list) {
				if(schedule_slots[day][start_hour]==1) {
					return false;
				}else {
					schedule_slots[day][start_hour]=1;
				}
			}
		}
		return true;
	}
}
