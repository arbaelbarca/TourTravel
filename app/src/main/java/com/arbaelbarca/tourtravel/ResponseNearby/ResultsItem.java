package com.arbaelbarca.tourtravel.ResponseNearby;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("reference")
	private String reference;

	@SerializedName("types")
	private List<String> types;

	@SerializedName("scope")
	private String scope;

	@SerializedName("icon")
	private String icon;

	@SerializedName("name")
	private String name;

	@SerializedName("geometry")
	private Geometry geometry;

	@SerializedName("vicinity")
	private String vicinity;

	@SerializedName("id")
	private String id;

	@SerializedName("plus_code")
	private PlusCode plusCode;

	@SerializedName("place_id")
	private String placeId;

	@SerializedName("user_ratings_total")
	private int userRatingsTotal;

	@SerializedName("rating")
	private double rating;

	@SerializedName("photos")
	private List<PhotosItem> photos;

	@SerializedName("opening_hours")
	private OpeningHours openingHours;

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setScope(String scope){
		this.scope = scope;
	}

	public String getScope(){
		return scope;
	}

	public void setIcon(String icon){
		this.icon = icon;
	}

	public String getIcon(){
		return icon;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setGeometry(Geometry geometry){
		this.geometry = geometry;
	}

	public Geometry getGeometry(){
		return geometry;
	}

	public void setVicinity(String vicinity){
		this.vicinity = vicinity;
	}

	public String getVicinity(){
		return vicinity;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPlusCode(PlusCode plusCode){
		this.plusCode = plusCode;
	}

	public PlusCode getPlusCode(){
		return plusCode;
	}

	public void setPlaceId(String placeId){
		this.placeId = placeId;
	}

	public String getPlaceId(){
		return placeId;
	}

	public void setUserRatingsTotal(int userRatingsTotal){
		this.userRatingsTotal = userRatingsTotal;
	}

	public int getUserRatingsTotal(){
		return userRatingsTotal;
	}

	public void setRating(double rating){
		this.rating = rating;
	}

	public double getRating(){
		return rating;
	}

	public void setPhotos(List<PhotosItem> photos){
		this.photos = photos;
	}

	public List<PhotosItem> getPhotos(){
		return photos;
	}

	public void setOpeningHours(OpeningHours openingHours){
		this.openingHours = openingHours;
	}

	public OpeningHours getOpeningHours(){
		return openingHours;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"reference = '" + reference + '\'' + 
			",types = '" + types + '\'' + 
			",scope = '" + scope + '\'' + 
			",icon = '" + icon + '\'' + 
			",name = '" + name + '\'' + 
			",geometry = '" + geometry + '\'' + 
			",vicinity = '" + vicinity + '\'' + 
			",id = '" + id + '\'' + 
			",plus_code = '" + plusCode + '\'' + 
			",place_id = '" + placeId + '\'' + 
			",user_ratings_total = '" + userRatingsTotal + '\'' + 
			",rating = '" + rating + '\'' + 
			",photos = '" + photos + '\'' + 
			",opening_hours = '" + openingHours + '\'' + 
			"}";
		}
}