package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;

public class FilmQueryApp {

	private DatabaseAccessor db;
	
	{
		try {
			db = new DatabaseAccessorObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		//app.test();
//    app.launch();
		System.out.println("Hello World");
	}

	private void test() throws SQLException {
//		Film film = db.findFilmById(2);
//		System.out.println(film);
		
//		List<Actor> actors = db.findActorsByFilmId(52);
//		for (Actor a : actors) {
//			System.out.println(a);
//		}
		Actor actor = db.findActorById(154);
		System.out.println(actor);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {

	}

}
