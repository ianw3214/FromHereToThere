
public class Main {

	public static void main(String[] args) {
		// example code
		HttpConnection http = new HttpConnection();
		float test = http.getFuelPrice("electric");
		System.out.println("Electric test : " + test);
		// add a verbose parameter for steps
		http.getFuelPrice("e85", true);
		// get the longitude/latitude position of the input
		System.out.println(http.geoLocation("Kingston Ontario"));
		// get the time needed to drive from Kingston to Ontario
		System.out.println(http.getTime("Kingston Ontario","Toronto Ontario","driving"));
	}

}
