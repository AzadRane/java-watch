//package gygd.stopwatch.choi;
//coded by harish Rane
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;//coded by harish Rane
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;//coded by harish Rane

/**
 * 
 * 
 * Stopwatch
 * Copyright (C) 2022 Harish Rane
 * Version 2
 * 
 * //coded by harish Rane
 *
 */
public class Stopwatch implements ActionListener{
	private SimpleTimer simpleTimer = new SimpleTimer();
	private JFrame window = new JFrame("Stopwatch offered by Harish Rane");
	
	private JButton btnCurrentTime;
	private JButton btnCountDown;
	private JButton btnCountUp;
	//coded by harish Rane
	private JButton btnOnOff;
	private JTextField tfTime;
	
	
	public Stopwatch(){
		iniGUI();
		onBtnCurrentTime();
	}
	
	/*
	 * establish the Graphic Interface //coded by harish Rane
	 */
	public void iniGUI(){
		window.setLayout(new BorderLayout());
		
		JPanel plButtons = new JPanel();
		plButtons.setLayout(new GridLayout(1,3));
		btnCurrentTime = new JButton("Current Time");
		btnCountDown = new JButton("Count Down");//coded by harish Rane
		btnCountUp = new JButton("Count Up");
		btnCurrentTime.addActionListener(this);
		btnCountDown.addActionListener(this);
		btnCountUp.addActionListener(this);
		//btnCountDown.setBorder(null);
		//btnCountUp.setBorder(null);//coded by harish Rane
		
		plButtons.add(btnCurrentTime);
		plButtons.add(btnCountDown);
		plButtons.add(btnCountUp);
		window.add(plButtons, BorderLayout.NORTH);
		
		JPanel plTime = new JPanel();
		plTime.setLayout(new FlowLayout());
		btnOnOff = new JButton("On");
		btnOnOff.addActionListener(this);//coded by harish Rane
		tfTime.setEditable(false);
		plTime.add(tfTime);
		plTime.add(btnOnOff);
		
		window.add(plTime, BorderLayout.CENTER);
	
		window.setResizable(false);
		window.setSize(400,100);
		window.setAlwaysOnTop(true);//coded by harish Rane
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	
	/*
	 * Method to react to the button clicks
	 * @Override
	 */
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		if(btn == btnCurrentTime){
			onBtnCurrentTime();
		}
		else if(btn == btnCountDown){//coded by harish Rane
			onBtnCountDown();
		}else if(btn == btnCountUp){
			onBtnCountUp();
			
		}else if(btn == btnOnOff){
			onBtnOnOff();
		}
	
	}

	private void onBtnCurrentTime() {
		if(!btnOnOff.getText().equals("On")){//coded by harish Rane
			simpleTimer.stop();
			btnOnOff.setText("On");
		}
		
		btnCurrentTime.setEnabled(false);
		btnCountUp.setEnabled(true);
		btnCountDown.setEnabled(true);
		
		tfTime.setEditable(false);
		btnOnOff.setVisible(false);//coded by harish Rane
		btnOnOff.setText("Pause");

		tfTime.setEditable(false);
		
		Calendar calendar = new GregorianCalendar();
	    
		simpleTimer.countUp(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
	}

	private void onBtnCountDown() {
		if(!btnOnOff.getText().equals("On")){//coded by harish Rane
			simpleTimer.stop();
			btnOnOff.setText("On");
		}
		
		btnOnOff.setVisible(true);
		
		btnCurrentTime.setEnabled(true);//coded by harish Rane
		btnCountUp.setEnabled(true);
		btnCountDown.setEnabled(false);
		tfTime.setEditable(true);
		tfTime.setText("Put Time in Format \"HR:MIN:SEC\"");
	}

	private void onBtnCountUp() {
		if(!btnOnOff.getText().equals("On")){//coded by harish Rane
			simpleTimer.stop();
			btnOnOff.setText("On");
		}
	
		btnOnOff.setVisible(true);
		
		btnCurrentTime.setEnabled(true);
		btnCountDown.setEnabled(true);//coded by harish Rane
		btnCountUp.setEnabled(false);
		tfTime.setEditable(false);
		tfTime.setText("00:00:00");
	}

	private void onBtnOnOff() {
		if((btnCurrentTime.isEnabled() && btnCountDown.isEnabled() && btnCountUp.isEnabled())){
			tfTime.setText("Choose options");
		}else{
			if(btnOnOff.getText().equals("On")){
				//coded by harish Rane
				if(!btnCurrentTime.isEnabled()){
					//SHOULD NEVER HAPPEN
					System.err.println("Current Time's button shouldn't be clicked!");
					btnOnOff.setText("Pause");
					tfTime.setEditable(false);
					simpleTimer.countUp(Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND);
				}
				else if(!btnCountDown.isEnabled()){
					int HR, MIN, SEC;
					HR=MIN=SEC=-1;
					
					String[] in = tfTime.getText().split(":");
					if(in.length!=3){
						tfTime.setText("WRONG FORMAT ERR.");//coded by harish Rane
					}else{
						try{
							HR = Integer.parseInt(in[0]);
							MIN = Integer.parseInt(in[1]);
							SEC = Integer.parseInt(in[2]);
						}catch(Exception except){}
						
						if(HR<0 || MIN<0 || SEC<0 ||MIN>=60 || SEC>=60){
							tfTime.setText("NUMBER RANGE ERR.");
						}else{
							btnOnOff.setText("Pause");//coded by harish Rane
							tfTime.setEditable(false);
							simpleTimer.countDown(HR, MIN, SEC);
						}
					}
					
				}else{
					simpleTimer.countUp(0,0,0);
					btnOnOff.setText("Pause");
				}
			}else if(btnOnOff.getText().equals("Pause")){
				btnOnOff.setText("Resume");
				simpleTimer.pause();//coded by harish Rane
			}else if(btnOnOff.getText().equals("Resume")){
				btnOnOff.setText("Pause");
				simpleTimer.resume();
				
				//countDown.start();
			}
			
			
		}
	}

	/**
	 * Class to manipulate time (algorithm)
	 * @author HArish Rane
	 *
	 */
	class SimpleTimer{
		private Toolkit toolkit = Toolkit.getDefaultToolkit(); 
		private int HR, MIN, SEC;
		private Timer timer;
		private boolean isPaused;//coded by harish Rane
		
		public void pause(){
			isPaused = true;	
		}
		
		public void resume(){
			isPaused = false;
		}
		//coded by harish Rane
		public void stop(){
			timer.cancel();
		}
		
		public void countDown(int timeH, int timeM, int timeS){
			HR = timeH;
			MIN = timeM;
			SEC = timeS;
			isPaused = false;
			timer = new Timer();//coded by harish Rane
			timer.scheduleAtFixedRate(new TimerTask(){
				public void run() {	
					if(!isPaused){
						tfTime.setText(getTimeString(HR, MIN, SEC));
					
						SEC--;
						if(SEC==-1){
							SEC = 59;
							MIN--;
						}
						
						if(MIN==-1){
							MIN = 59;
							HR--;
						}
						//coded by harish Rane
						if(HR==-1){
							timer.cancel();	
							toolkit.beep();
						}
					}
				}
			}, 0, 1000);
		}
		
		public void countUp(int timeH, int timeM, int timeS) {
			HR=timeH;
			MIN=timeM;
			SEC=timeS;
			isPaused = false;//coded by harish Rane
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask(){
				public void run() {	
					if(!isPaused){
						tfTime.setText(getTimeString(HR, MIN, SEC));
					
						SEC++;
						if(SEC==60){
							SEC = 0;
							MIN++;
						}
						
						if(MIN==60){
							MIN = 0;
							HR++;
						}//coded by harish Rane
					}
				}
			}, 0, 1000);
		
		}

	}
	
	public String getTimeString(int HR, int MIN, int SEC){
		String h = HR+"";
		String m= MIN+"";
		String s=SEC+"";
		
		while(h.length()<=1) h="0"+h;
		while(m.length()<=1) m="0"+m;
		while(s.length()<=1) s="0"+s;
		
		return h+":"+m+":"+s;//coded by harish Rane
		
		
	}
	
	
	/*
	 * main method
	 */
	public static void main(String args[]){//coded by harish Rane
		new Stopwatch();
	}
}