package com.notdecaf.foodinder;

import android.graphics.drawable.Drawable;

import com.andtinder.model.CardModel;

/**
 * Created by Purav on 3/3/2015.
 */
public class FoodCardModel extends CardModel {
	//TODO: Find out what api we are using and what we need to find food locations to complete this class
	private String location;

	public FoodCardModel(String title, String description, Drawable cardImage) {
		super(title, description, cardImage);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


}
