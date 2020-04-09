package final_project;

import java.sql.Time;

public class Course {
	//items corresponding to database fields
	private String prefix;
	private String code;
	private String professor_name;
	private String classroom;
	private String days;
	public Time start_time;
	public Time end_time;
	private String course_type;
	
	//constructor
	Course(String prefix, String code, String professor_name, String classroom, 
			String days, Time start_time, Time end_time, String course_type){
		this.prefix = prefix;
		this.code = code;
		this.professor_name = professor_name;
		this.classroom = classroom;
		this.days = days;
		this.start_time = start_time;
		this.end_time = end_time;
		this.course_type = course_type;
	}
	
	public int[] get_days_list() {
		String[] parts = this.days.split(",");
		int[] result = new int[parts.length];
		for(int i=0; i<parts.length; i++) {
			int this_day = 0;
			//choose proper index value to be used in schedule functions based on the day
			if(parts[i]=="M") {
				this_day=0;
			}else if(parts[i]=="T") {
				this_day=1;
			}else if(parts[i]=="W") {
				this_day=2;
			}else if(parts[i]=="TH") {
				this_day=3;
			}else if(parts[i]=="F") {
				this_day=4;
			}
			result[i] = this_day;
		}
		return result;
		
	}
}
