package com.goeuro.main;

import com.goeuro.controller.Controller;

public class MainClass {

	public static void main(String[] args){
		if (null == args || args.length == 0) {
			return;
		}
		Controller controller = new Controller();
		controller.createCityCSV(args);

	}

}
