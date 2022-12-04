package com.skilldistillery.filmquery.entities;

import java.util.List;
import java.util.Objects;

public class Film {

	private int filmId;
	private String title;
	private String description;
	private short releaseYear;
	private int languageId;
	private int rentalDuration;
	private double rate;
	private int length;
	private double reparationCost;
	private String rating;
	private String features;
	private List<Actor> actors;
	private List<String> categories;
	
	public Film () {
		
	}
	
	public Film(int filmId, String title, String description, short releaseYear, int languageId, int rentalDuration,
			double rate, int length, double reparationCost, String rating, String features) {
		this.filmId = filmId;
		this.title = title;
		this.description = description;
		this.releaseYear = releaseYear;
		this.languageId = languageId;
		this.rentalDuration = rentalDuration;
		this.rate = rate;
		this.length = length;
		this.reparationCost = reparationCost;
		this.rating = rating;
		this.features = features;
	}
	
	public Film(int filmId, String title, String description, short releaseYear, int languageId, int rentalDuration,
			double rate, int length, double reparationCost, String rating, String features, List<Actor> actors) {
		this(filmId, title, description, releaseYear, languageId, rentalDuration, rate, length, reparationCost, rating, features);
		this.actors = actors;
	}
	
	public Film(int filmId, String title, String description, short releaseYear, int languageId, int rentalDuration,
			double rate, int length, double reparationCost, String rating, String features, List<Actor> actors, List<String> categories) {
		this(filmId, title, description, releaseYear, languageId, rentalDuration, rate, length, reparationCost, rating, features, actors);
		this.categories = categories;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public int getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public double getReparationCost() {
		return reparationCost;
	}

	public void setReparationCost(double reparationCost) {
		this.reparationCost = reparationCost;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	public int getFilmId() {
		return filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public short getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(short releaseYear) {
		this.releaseYear = releaseYear;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	@Override
	public int hashCode() {
		return Objects.hash(filmId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return filmId == other.filmId;
	}

	@Override
	public String toString() {
		return "Film [filmId=" + filmId + ", title=" + title + ", description=" + description + ", releaseYear="
				+ releaseYear + ", languageId=" + languageId + ", rentalDuration=" + rentalDuration + ", rate=" + rate
				+ ", length=" + length + ", reparationCost=" + reparationCost + ", rating=" + rating + ", features="
				+ features + ", actors=" + actors + ", categories=" + categories + "]";
	}

}
