
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	public static int  temp;
	public static float average_time;
	public static int  a;
	public static int[]   proceses,burst_time,arrival_time,waiting_time,complete_time;
	public static int[]   turns;	
		

	Group hb;
	
	public static void main(String[] args) {
		Application.launch(args);

	}
	

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("FCFS");
		stage.setResizable(false);
		Group root = new Group();

		Text lbl = new Text(70,50,"FCFS Process Algorithms");	
		lbl.setFont(Font.font(25));
		
		Text lbl1 = new Text(90,90,"Enter the Number of Processess:");	
		lbl.setFont(Font.font(20));
		
		TextField pr = new TextField();
		pr.setTranslateX(110);
		pr.setTranslateY(120);
		
		Button c = new Button("Continue");
		c.setTranslateX(110);
		c.setTranslateY(160);
		
		c.setOnAction(e ->{
			try{
			a = Integer.parseInt(pr.getText());
			proceses = new int[a];
			burst_time = new int[a];
			arrival_time = new int[a];
			waiting_time = new int[a];
			complete_time = new int[a];
			turns = new int[a];		
			 if(a > 20 || a < 1){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setContentText("Processes Should be less than 20 & greater than 0!");

				alert.showAndWait();
			}else {
				 hb = new Group();
				 
				
				Text as1 = new Text(70,50,"FCFS Process Algorithms");
				as1.setFont(Font.font(20));
				
				TextField arrival[] = new TextField[a];
				TextField burst[] = new TextField[a];	
				Text a1 = new Text(80,90,"Arrival time:");		
				Button g = new Button("Generate Average Time");
				g.setTranslateX(110);
				g.setTranslateY(260);
			
				int ax=0;
				for (int i = 0; i < arrival.length; i++) {
					arrival[i] = new TextField();
					arrival[i].setTranslateX(60);
					arrival[i].setTranslateY(100+ax);
					ax=ax+30;
					hb.getChildren().add(arrival[i]);
					if(i==0)arrival[i].setText("10");else arrival[i].setText(""+i);
				
				}
				ax=0;
				Text a2 = new Text(230,90,"Burst time:");	
				for (int i = 0; i < burst.length; i++) {
					burst[i] = new TextField();
					burst[i].setTranslateX(230);
					burst[i].setTranslateY(100+ax);
					ax=ax+30;
					hb.getChildren().add(burst[i]);
					
				if(i==0)burst[i].setText("10");else	burst[i].setText(""+i);
				
					}
			
				ax=0;
				for(int i=0;i<a;i++) {
					hb.getChildren().add(new Text(25,115+ax,"p"+(i+1)));
					ax=ax+30;
				}
				g.setTranslateY(ax+100);
				hb.getChildren().addAll(as1,a1,a2,g);

				g.setOnAction(e1 -> {
					{boolean error=false;;
						for (int i = 0; i < a; i++) {
							if(arrival[i].getText().isEmpty()|| burst[i].getText().isEmpty()){
							error=true;
							}
						}
						if(error==true) {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Warning");
							alert.setContentText("Fill all of these fields!");

							alert.showAndWait();
						}
						else {
							for (int i = 0; i < arrival_time.length; i++) {
								arrival_time[i] = Integer.parseInt(arrival[i].getText());
								
							}
							for (int i = 0; i < burst_time.length; i++) {
								
								burst_time[i] = Integer.parseInt(burst[i].getText());
							
							}							
							
							//sorting according to arrival times
							for(int i = 0 ; i <a; i++){
								for(int  j=0;  j < a-(i+1) ; j++){
									if( arrival_time[j] > arrival_time[j+1] ){
										temp = arrival_time[j];
										arrival_time[j] = arrival_time[j+1];
										arrival_time[j+1] = temp;
										temp = burst_time[j];
										burst_time[j] = burst_time[j+1];
										burst_time[j+1] = temp;
									}
								}
							}
							
							// finding completion times
							for(int  i = 0 ; i < a; i++){
								if( i == 0){	
									complete_time[i] = arrival_time[i] + burst_time[i];
								}
								else{
									if( arrival_time[i] > complete_time[i-1]){
										complete_time[i] = arrival_time[i] + burst_time[i];
									}
									else
										complete_time[i] = complete_time[i-1] + burst_time[i];
								}
								turns[i] = complete_time[i] - arrival_time[i];         
								waiting_time[i] = turns[i] - burst_time[i] ;        
								average_time += waiting_time[i] ;     
							}
							

							Label Result = new Label("Result:");
							Result.setText("\n"+Result.getText()+"\nProcesses:\tArrival_Time\tBurst time:\tWaiting Time\tcomplete\n");
							for(int i=0; i<a; i++){
								Result.setText("\n"+Result.getText()+i+"\t\t\t"+arrival_time[i]+"\t\t\t"+burst_time[i]+"\t\t\t"+waiting_time[i]+"\t\t\t"+complete_time[i]+"\n");
							}

							Result.setText("\n"+Result.getText()+"Average Time: "+(average_time/3));
						 
						    CategoryAxis xaxis = new CategoryAxis();  
						    ArrayList<String> list = new ArrayList<String>();
						    for (int i = 0; i < a; i++) {
								list.add("p"+(i+1));
							}
						    xaxis.setCategories(FXCollections.<String>observableArrayList(list)); 
						    	      xaxis.setLabel("process");
						    NumberAxis yaxis = new NumberAxis();  
						    xaxis.setLabel("FCFS");  
						    yaxis.setLabel("Processes and Waiting Time");  
					  
						    StackedBarChart<String, Number> sb = new StackedBarChart<String, Number>(xaxis,yaxis);  
						    sb.setTitle("Process Algorithms of FCFS");     
						    
						            
						     
						    
						    
						    XYChart.Series<String, Number> series1 = new XYChart.Series<>();  
						      series1.setName("Arrival"); 
						      for (int i = 0; i < arrival_time.length; i++) {
							      series1.getData().add(new XYChart.Data<>("p"+(i+1), arrival_time[i])); 								
							}
						         
						      XYChart.Series<String, Number> series2 = new XYChart.Series<>(); 
						      series2.setName("Waiting"); 
						      for (int i = 0; i < waiting_time.length; i++) {
							      series2.getData().add(new XYChart.Data<>("p"+(i+1), waiting_time[i])); 								
							}
						      XYChart.Series<String, Number> series3 = new XYChart.Series<>(); 
						      series3.setName("Completing"); 
						      for (int i = 0; i < complete_time.length; i++) {
							      series3.getData().add(new XYChart.Data<>("p"+(i+1), complete_time[i])); 								
						      }    
						      //Setting the data to bar chart
						      sb.getData().addAll(series1, series2, series3); 
						      
	
						    GridPane gx = new GridPane();
						    gx.add(sb, 0, 0);
						    gx.add(Result, 1, 0);
							Scene scene = new Scene(gx, 500,500);
							stage.setScene(scene);
							stage.show();
						
						}
				}
					
				});
				
				Scene s = new Scene(hb,450,800);
			stage.setResizable(true);
				stage.setScene(s);
			}
			}catch(NumberFormatException e1){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setContentText("Enter the number of Processess!");
				alert.showAndWait();
			}
			
		});
		
	
		
		
		root.getChildren().addAll(lbl,lbl1,pr,c);
		
		Scene scene = new Scene(root,400,200);
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	

}
