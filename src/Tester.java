import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Date;

public class Tester {
	public static String test_URL="http://smartcarpet.website/SQLtest.php";
	public static int current_delay = 0;
	public static float current_ram = 0;
	public static int network_fail_retry = 0;
	public static boolean is_Error = false;
	public static String error_Logger = "";
	public static String  myIPaddr = "127.0.0.1";
	public static String  Messager = "Status: Online";
	public static Date error_date = Calendar.getInstance().getTime();
	public static long error_timestamp = System.currentTimeMillis();
	public static void main(String[] args) throws Exception {
		System.out.println("Program begins in 3s...");
		Thread.sleep(3 * 1000);
		while(true){
			System.out.println("Processing current status...");

			try{
				myIPaddr = GetIP();
			}
			catch(Exception e){
			}

			int testNetworkDelay = getNetworkDelayMS();
			
			if(testNetworkDelay>0){
				current_delay = testNetworkDelay;
			}

			Runtime runtime = Runtime.getRuntime();
			long maxMemory = runtime.maxMemory();
			long allocatedMemory = runtime.totalMemory();
			//long freeMemory = runtime.freeMemory();




			clearConsole();
			//System.out.println("Total Usable RAM:" + (float)maxMemory/1024/1024 + "MB");
			//System.out.println("Used:"+ (float)allocatedMemory/1024/1024 + "MB");
			//System.out.println((freeMemory + (maxMemory - allocatedMemory)));
			current_ram = (float)allocatedMemory/1024/1024;


			System.out.println("Network Delay Tester \n");
			System.out.println("Local IP:"+myIPaddr);
			System.out.println("RAM: " + current_ram + "/" + (float)maxMemory/1024/1024 + " MB");
			System.out.println("Network Delay: "+current_delay+ " MS");
			System.out.println(Messager);
			//System.out.println(error_Logger);
			try{

				Thread.sleep(3 * 1000);}

			catch(InterruptedException e){

			}
		}



	}


	public static int getNetworkDelayMS(){
		long startTime=System.currentTimeMillis();
		String httpResult = HttpRequest.sendGet(test_URL, "");
		long endTime=System.currentTimeMillis();
		if(!httpResult.contains("Error")){
			System.out.println(httpResult);
			long twoWayDelay = endTime - startTime;
			int oneWayDelay = (int) twoWayDelay;
			return oneWayDelay;
		}
		else{
			return -1;
		}
	}

	public final static void clearConsole()
	{
		for(int i=0; i<5; i++){
			System.out.println("");
		}

	}

	
	public static String GetIP() throws UnknownHostException, SocketException{
		String returnIP = "127.0.0.1";
		//System.out.println("Host addr: " + InetAddress.getLocalHost().getHostAddress());  // often returns "127.0.0.1"
		Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
		for (; n.hasMoreElements();)
		{
			NetworkInterface e = n.nextElement();
			//System.out.println("Interface: " + e.getName());
			Enumeration<InetAddress> a = e.getInetAddresses();
			for (; a.hasMoreElements();)
			{
				InetAddress addr = a.nextElement();
				//System.out.println("  " + addr.getHostAddress());
				if(!addr.getHostAddress().contains("127") && !addr.getHostAddress().contains("%") && !addr.getHostAddress().contains("0:0")){
					returnIP=addr.getHostAddress();
				}
			}
		}
		return returnIP;
	}

}
