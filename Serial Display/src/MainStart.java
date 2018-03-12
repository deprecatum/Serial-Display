
//Codigo produzido por Rui Fernandes
//28-1-2015

public class MainStart {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		gui asd = new gui();
		
		SerialTest main = new SerialTest();
		main.initialize();
		
		System.out.println("Started");
		
		
		
		while(true){
			asd.status.setText(sharedResources.status);
			asd.temp0.setText(sharedResources.temp0);
			asd.temp1.setText(sharedResources.temp1);
			
			if(sharedResources.reconnect){
				main.close();
				main.initialize();
			}
		}
	}

}
