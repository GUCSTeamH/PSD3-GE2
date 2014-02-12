
public class Student {
	private String studentid;
	private String name;
	private int year;
	public Student(String id,String n,int y){
		studentid=id;
		name=n;
		year=y;
	}

	public String getStudentid() {
		return studentid;
	}
	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
}
