
public class Student extends User {
private String studentid;
private int year;
public Student(String id,String n,String s,String mail,int y){
	super(n, s, mail);
	studentid=id;
	year=y;
}
public void bookSession(){
}
public void checkSession(){}
public String getStudentid() {
	return studentid;
}
public void setStudentid(String studentid) {
	this.studentid = studentid;
}
public int getYear() {
	return year;
}
public void setYear(int year) {
	this.year = year;
}
}
