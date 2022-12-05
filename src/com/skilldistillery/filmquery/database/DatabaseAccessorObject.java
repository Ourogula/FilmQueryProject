package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	// Connect to the local sdvid database
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";

	// Add the driver to the class path
	public DatabaseAccessorObject() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}

	// Return a Film given a Film's ID
	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;

		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT * FROM film WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			filmId = rs.getInt("id");
			String title = rs.getString("title");
			String description = rs.getString("description");
			short releaseYear = rs.getShort("release_year");
			int languageId = rs.getInt("language_id");
			int rentalDuration = rs.getInt("rental_duration");
			double rate = rs.getDouble("rental_rate");
			int length = rs.getInt("length");
			double reparationCost = rs.getDouble("replacement_cost");
			String rating = rs.getString("rating");
			String features = rs.getString("special_features");
			film = new Film(filmId, title, description, releaseYear, languageId, rentalDuration, rate, length,
					reparationCost, rating, features, findActorsByFilmId(filmId), findCategoriesByID(filmId));
		}

		rs.close();
		stmt.close();
		conn.close();
		return film;
	}

	// Returns a List of Films that match the given String query
	public List<Film> findFilmsByQuery(String filmQuery) throws SQLException {
		List<Film> films = new ArrayList<>();
		Film film = null;
		String query = "%" + filmQuery + "%";

		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT * FROM film WHERE title LIKE ? OR description LIKE ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, query);
		stmt.setString(2, query);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int filmId = rs.getInt("id");
			String title = rs.getString("title");
			String description = rs.getString("description");
			short releaseYear = rs.getShort("release_year");
			int languageId = rs.getInt("language_id");
			int rentalDuration = rs.getInt("rental_duration");
			double rate = rs.getDouble("rental_rate");
			int length = rs.getInt("length");
			double reparationCost = rs.getDouble("replacement_cost");
			String rating = rs.getString("rating");
			String features = rs.getString("special_features");
			film = new Film(filmId, title, description, releaseYear, languageId, rentalDuration, rate, length,
					reparationCost, rating, features, findActorsByFilmId(filmId), findCategoriesByID(filmId));
			films.add(film);
		}

		rs.close();
		stmt.close();
		conn.close();
		return films;
	}

	// Return a List of Films when given an Actor's ID, only used internally currently
	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT * FROM film JOIN film_actor ON film.id = film_actor.film_id WHERE actor_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int filmId = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				short releaseYear = rs.getShort("release_year");
				int languageId = rs.getInt("language_id");
				int rentalDuration = rs.getInt("rental_duration");
				double rate = rs.getDouble("rental_rate");
				int length = rs.getInt("length");
				double reparationCost = rs.getDouble("replacement_cost");
				String rating = rs.getString("rating");
				String features = rs.getString("special_features");
				Film film = new Film(filmId, title, description, releaseYear, languageId, rentalDuration, rate, length,
						reparationCost, rating, features, findActorsByFilmId(filmId), findCategoriesByID(filmId));
				films.add(film);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	// Return an Actor when given their ID
	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;

		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT * FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor(); // Create the object
			// Here is our mapping of query columns to our object fields:
			actor.setId(actorResult.getInt("id"));
			actor.setFirstName(actorResult.getString("first_name"));
			actor.setLastName(actorResult.getString("last_name"));
			actor.setFilms(findFilmsByActorId(actorId));
		}

		actorResult.close();
		stmt.close();
		conn.close();

		return actor;
	}

	// Return a list of all actors by a film's ID
	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {

		List<Actor> actors = new ArrayList<>();

		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT * FROM actor JOIN film_actor fact ON fact.actor_id = actor.id "
				+ " JOIN film ON fact.film_id = film.id WHERE film.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet actorResult = stmt.executeQuery();

		// What is the goal? Find all actors in a given film
		while (actorResult.next()) {
			Actor actor = new Actor(); // Create the object
			actor.setId(actorResult.getInt("id"));
			actor.setFirstName(actorResult.getString("first_name"));
			actor.setLastName(actorResult.getString("last_name"));
			// actor.setFilms(findFilmsByActorId(filmId));
			actors.add(actor);
		}

		actorResult.close();
		stmt.close();
		conn.close();

		return actors;
	}

	// Get Language name by Language_Id
	public String findLanguageByID(int filmId) throws SQLException {
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String language = "";
		String sql = "SELECT language.name FROM language JOIN film ON language.id = film.language_id";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			language = rs.getString("language.name");
		}
		rs.close();
		stmt.close();
		conn.close();
		return language;
	}

	// Get Film Categories by ID
	public List<String> findCategoriesByID(int filmId) throws SQLException {

		String user = "student";
		String pass = "student";
		List<String> categories = new ArrayList<>();

		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT category.name FROM category JOIN film_category ON film_category.category_id = category.id JOIN film ON film.id = film_category.film_id WHERE film.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			categories.add(rs.getString("category.name"));
		}
		rs.close();
		stmt.close();
		conn.close();

		return categories;
	}

}
