
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;


public class SerialTest implements SerialPortEventListener {
	SerialPort serialPort;
	
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void connect(){
		CommPortIdentifier portId = null;
		
		Enumeration/*<CommPortIdentifier>*/ portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			
			if (currPortId.getName().equals(sharedResources.comPort)) {
				portId = currPortId;
				break;
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}
	}
	
	public void initialize() {
  
		CommPortIdentifier portId = null;
		
		Enumeration/*<CommPortIdentifier>*/ portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			
			if (currPortId.getName().equals(sharedResources.comPort)) {
				portId = currPortId;
				break;
			}
		}
		if (portId == null) {
			sharedResources.status="Could not find COM port.";
			System.out.println("Could not find COM port.");
			return;
		}
		
		
		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
			
			sharedResources.status="opening port";
			
			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			
			sharedResources.status="port set/open";
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	
	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	
	PrintWriter out;
	
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		try {
			 out = new PrintWriter(new FileWriter(sharedResources.csv, true));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)  {
			
			DateTimeFormatter form = DateTimeFormatter.ofPattern("HH:mm:ss");
			
			try {
				String inputLine=input.readLine();
				
				if(inputLine.substring(7, 8).contains("0")){
					sharedResources.temp0=inputLine;
				}
				else if(inputLine.substring(7, 8).contains("1")){
					sharedResources.temp1=inputLine;
					
					out.println(LocalDate.now()+" "+LocalTime.now().format(form)+"; "+sharedResources.temp0+"; "+sharedResources.temp1);
					out.flush();
				}
				
				System.out.println(inputLine);
			} catch (Exception e){
				
			}
		}
	}

	
}