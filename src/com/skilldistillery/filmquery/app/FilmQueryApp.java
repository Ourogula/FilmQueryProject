package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	private DatabaseAccessor db;

	//Initializer block for creating our DAO object
	{
		try {
			db = new DatabaseAccessorObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);
		startUserInterface(input);
		input.close();
	}

	//Run the user interface, continuing until the exit option is chosen which returns false
	private void startUserInterface(Scanner input) {
		boolean keepPlaying = false;
		do {
			keepPlaying = playMenu(input);
		} while (keepPlaying);

	}

	
	//Let's print a menu!
	private void printMenu() {
		System.out.println("****************************");
		System.out.println("*            MENU          *");
		System.out.println("****************************");
		System.out.println("* 1: Find Film by ID       *");
		System.out.println("* 2: Find Film by Keyword  *");
		System.out.println("* 3: Find Actor by ID      *");
		System.out.println("* 4: Exit                  *");
		System.out.println("****************************");
	}

	private boolean playMenu(Scanner input) {
		boolean again = true;
		boolean validInput = false;
		String response;
		int formattedResponse = 0;

		
		//Receive the user's input and validate the input
		while (!validInput) {
			System.out.println("Please choose an option from the menu:");
			printMenu();
			response = input.nextLine();
			try {
				if (Integer.parseInt(response) == 1 || Integer.parseInt(response) == 2
						|| Integer.parseInt(response) == 3 || Integer.parseInt(response) == 4) {
					validInput = true;
					formattedResponse = Integer.parseInt(response);
				} else {
					System.out.println("Please enter a valid option from the menu.");
				}
			} catch (Exception e) {
				System.out.println("Please enter a valid option from the menu.");
			}
		}

		//Switch for the Menu, with 1/2 being queries and 3 providing a response to exit the program
		switch (formattedResponse) {
		case 1:
			try {
				filmByIdQuery(input);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				filmByStringQuery(input);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			try {
				actorByIdQuery(input);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 4:
			System.out.println("Thanks for using our application!");
			again = false;
			break;
		}

		return again;
	}

	
	//Find a film by a string input, matching any film title that contains the input string
	private void filmByStringQuery(Scanner input) throws SQLException {
		boolean validInput = false;
		String response = "";

		while (!validInput) {
			try {
				System.out.println("Please input a string to search the film database for: ");
				response = input.next();
				input.nextLine();
				validInput = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		List<Film> films = db.findFilmsByQuery(response);

		if (films.isEmpty()) {
			System.out.println("There is no film matching the Query: " + response);
		} else {
			int counter = 0;
			
			System.out.println();
			System.out.println("ID | Title | Release Year | Rating | Description | Language | Catgegory");
			System.out.println("-----------------------------------------------------------------------");
			for (Film film : films) {
				counter++;
				System.out.println(film.getFilmId() + " | " + film.getTitle() + " | " + film.getReleaseYear() + " | "
						+ film.getRating() + " | " + film.getDescription()+ " | " + ((DatabaseAccessorObject)db).findLanguageByID(film.getFilmId()) + " | " + film.getCategories());
				
				//Let's print out all of the actors, with a bit of formatting depending on their index
				for (Actor actors : film.getActors()) {
					if (actors == film.getActors().get(0)) {
						System.out.print("Actors: ");
					}
					System.out.print(actors.getFirstName() + " " + actors.getLastName());
					if (actors != film.getActors().get(film.getActors().size() - 1)) {
						System.out.print(", ");
					}
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("Found results for: " + counter + " films");
			System.out.println();
		}
	}

	
	//Find a film by the film ID
	private void filmByIdQuery(Scanner input) throws SQLException {
		boolean validInput = false;
		int response = 0;

		while (!validInput) {
			try {
				System.out.println("Please input the film ID you are inquiring about: ");
				response = input.nextInt();
				input.nextLine();
				validInput = true;
			} catch (Exception e) {
				input.nextLine();
				System.out.println("Please input a valid film ID.");
			}
		}

		Film film = db.findFilmById(response);

		if (film == null) {
			System.out.println("There is no film matching the ID: " + response);
		} else {
			System.out.println();
			System.out.println("ID | Title | Release Year | Rating | Description | Language | Catgegory");
			System.out.println("-----------------------------------------------------------------------");
			System.out.println(film.getFilmId() + " | " + film.getTitle() + " | " + film.getReleaseYear() + " | "
					+ film.getRating() + " | " + film.getDescription() + " | " + ((DatabaseAccessorObject)db).findLanguageByID(film.getFilmId()) + " | " + film.getCategories());
			
			//Let's print out all of the actors, with a bit of formatting depending on their index
			for (Actor actors : film.getActors()) {
				if (actors == film.getActors().get(0)) {
					System.out.print("Actors: ");
				}
				System.out.print(actors.getFirstName() + " " + actors.getLastName());
				if (actors != film.getActors().get(film.getActors().size() - 1)) {
					System.out.print(", ");
				}
			}
			System.out.println();
			System.out.println();
		}
	}
	
	private void actorByIdQuery (Scanner input) throws SQLException {
		boolean validInput = false;
		int response = 0;

		while (!validInput) {
			try {
				System.out.println("Please input the actor ID you are inquiring about: ");
				response = input.nextInt();
				input.nextLine();
				validInput = true;
			} catch (Exception e) {
				input.nextLine();
				System.out.println("Please input a valid actor ID.");
			}
		}

		Actor actor = db.findActorById(response);

		if (actor == null) {
			System.out.println("There is no actor matching the ID: " + response);
		} else {
			int count = 1;
			System.out.println();
			System.out.println(response + " | " + actor.getFirstName() + " " + actor.getLastName());
			for (Film film : actor.getFilms()) {
				System.out.print(film.getTitle());
				if (count % 5 == 0) {
					System.out.println();
				}
				else if (film != actor.getFilms().get(actor.getFilms().size() - 1)) {
					System.out.print(" | ");
				}
				count++;
			}
			System.out.println();
			System.out.println();
		}
	}

}
