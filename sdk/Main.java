
public class Main {

	public static void main(String[] args) {
		// example code
		HttpConnection http = new HttpConnection();
		float test = http.getFuelPrice("electric");
		System.out.println("Electric test : " + test);
		// add a verbose parameter for steps
		http.getFuelPrice("e85", true);
	}

}
