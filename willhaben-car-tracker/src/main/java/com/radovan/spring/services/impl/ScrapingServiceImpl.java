package com.radovan.spring.services.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.radovan.spring.dto.CarDto;
import com.radovan.spring.exceptions.TooManyRequestsException;
import com.radovan.spring.services.CarService;
import com.radovan.spring.services.ScrapingService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.HttpStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScrapingServiceImpl implements ScrapingService {

	@Autowired
	private CarService carService;

	private Boolean carAdded = false;

	@Override
	public void scrapeCars() throws IOException {
		System.out.println("Attempt to access willhaben");
		Boolean isAdded = false;
		try {
			// String url =
			// "https://www.willhaben.at/iad/gebrauchtwagen/auto/gebrauchtwagenboerse";
			String url = "https://www.willhaben.at/iad/gebrauchtwagen/auto/gebrauchtwagenboerse?sfId=dfb35cf6-236d-4ca3-8b67-8d485a9252e8&isNavigation=true&DEALER=1&PRICE_TO=12000";
			Document doc = Jsoup.connect(url).get();
			Element script = doc.selectFirst("#__NEXT_DATA__");
			String scriptContent = script.html();

			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(scriptContent, JsonObject.class);

			JsonObject props = jsonObject.getAsJsonObject("props");
			JsonObject pageProps = props.getAsJsonObject("pageProps");
			JsonObject searchResult = pageProps.getAsJsonObject("searchResult");

			int rowsFound = searchResult.get("rowsFound").getAsInt();
			System.out.println("Found " + rowsFound + " cars");

			JsonObject advertSummaryList = searchResult.getAsJsonObject("advertSummaryList");
			if (advertSummaryList != null) {
				Map.Entry<String, JsonElement> firstEntry = advertSummaryList.entrySet().iterator().next();
				JsonArray adverts = firstEntry.getValue().getAsJsonArray();

				for (JsonElement advertElement : adverts) {
					JsonObject advert = advertElement.getAsJsonObject();

					String id = advert.get("id").getAsString();
					String description = advert.get("description").getAsString();

					JsonObject attributes = advert.getAsJsonObject("attributes");
					JsonArray attributeArray = attributes.getAsJsonArray("attribute");
					JsonObject attributeDetails = new JsonObject();
					for (JsonElement attributeElement : attributeArray) {
						JsonObject attributeObject = attributeElement.getAsJsonObject();
						String name = attributeObject.get("name").getAsString();
						JsonArray values = attributeObject.getAsJsonArray("values");
						JsonArray valueArray = new JsonArray();
						for (JsonElement value : values) {
							valueArray.add(value.getAsString());
						}
						attributeDetails.add(name, valueArray);
					}

					String price = "";
					String location = "";
					String country = "";
					String brand = "";
					String model = "";
					String mileage = "";
					String address = "";
					String postcode = "";
					String state = "";
					String district = "";
					String year = "";
					String fuel = "";
					String transmission = "";
					String carImageUrl = "";
					String engine = "";
					String published = "";
					String seoUrl = "";
					List<String> allImageUrls = new ArrayList<>();

					if (attributeDetails.has("PRICE/AMOUNT")) {
						price = attributeDetails.getAsJsonArray("PRICE/AMOUNT").get(0).getAsString();
					}

					if (attributeDetails.has("LOCATION")) {
						location = attributeDetails.getAsJsonArray("LOCATION").get(0).getAsString();
					}

					if (attributeDetails.has("COUNTRY")) {
						country = attributeDetails.getAsJsonArray("COUNTRY").get(0).getAsString();
					}

					if (attributeDetails.has("CAR_MODEL/MAKE")) {
						brand = attributeDetails.getAsJsonArray("CAR_MODEL/MAKE").get(0).getAsString();
					}

					if (attributeDetails.has("CAR_MODEL/MODEL")) {
						model = attributeDetails.getAsJsonArray("CAR_MODEL/MODEL").get(0).getAsString();
					}

					if (attributeDetails.has("MILEAGE")) {
						mileage = attributeDetails.getAsJsonArray("MILEAGE").get(0).getAsString();
					}

					if (attributeDetails.has("ADDRESS")) {
						address = attributeDetails.getAsJsonArray("ADDRESS").get(0).getAsString();
					}

					if (attributeDetails.has("POSTCODE")) {
						postcode = attributeDetails.getAsJsonArray("POSTCODE").get(0).getAsString();
					}

					if (attributeDetails.has("STATE")) {
						state = attributeDetails.getAsJsonArray("STATE").get(0).getAsString();
					}

					if (attributeDetails.has("DISTRICT")) {
						district = attributeDetails.getAsJsonArray("DISTRICT").get(0).getAsString();
					}

					if (attributeDetails.has("YEAR_MODEL")) {
						year = attributeDetails.getAsJsonArray("YEAR_MODEL").get(0).getAsString();
					}

					if (attributeDetails.has("ENGINE/FUEL_RESOLVED")) {
						fuel = attributeDetails.getAsJsonArray("ENGINE/FUEL_RESOLVED").get(0).getAsString();
					}

					if (attributeDetails.has("TRANSMISSION_RESOLVED")) {
						transmission = attributeDetails.getAsJsonArray("TRANSMISSION_RESOLVED").get(0).getAsString();
					}

					if (attributeDetails.has("ENGINE/EFFECT")) {
						engine = attributeDetails.getAsJsonArray("ENGINE/EFFECT").get(0).getAsString();
					}

					if (attributeDetails.has("MMO")) {
						carImageUrl = "https://cache.willhaben.at/mmo/"
								+ attributeDetails.getAsJsonArray("MMO").get(0).getAsString();
					}

					if (attributeDetails.has("PUBLISHED_String")) {
						published = attributeDetails.getAsJsonArray("PUBLISHED_String").get(0).getAsString();
					}

					if (attributeDetails.has("SEO_URL")) {
						seoUrl = "https://www.willhaben.at/iad/"
								+ attributeDetails.getAsJsonArray("SEO_URL").get(0).getAsString();
					}

					if (attributeDetails.has("ALL_IMAGE_URLS")) {
						JsonArray imageUrlsArray = attributeDetails.getAsJsonArray("ALL_IMAGE_URLS");
						// System.out.println("Total images: " + imageUrlsArray.size());

						for (JsonElement imageUrlsElement : imageUrlsArray) {
							String imageUrlsString = imageUrlsElement.getAsString();
							String[] imageUrls = imageUrlsString.split(";");
							for (String imageUrl : imageUrls) {
								allImageUrls.add("https://cache.willhaben.at/mmo/" + imageUrl);
							}
						}

					}

					CarDto car = new CarDto();
					car.setId(Long.valueOf(id));
					car.setAddress(address);
					car.setBrand(brand);
					car.setCarImageUrl(carImageUrl);
					car.setCountry(country);
					car.setDistrict(district);
					car.setEngine(engine);
					car.setFuel(fuel);
					car.setLocation(location);
					car.setMileage(Integer.valueOf(mileage));
					car.setModel(model);
					car.setPostcode(postcode);
					car.setPrice(Float.valueOf(price));
					car.setPublished(published);
					car.setState(state);
					car.setTransmission(transmission);
					car.setCarYear(Integer.valueOf(year));
					car.setDescription(description);
					car.setSeoUrl(seoUrl);

					if (carService.addCar(car)) {
						isAdded = true;
					}

				}

			} else {
				System.out.println("No summary list");
			}

		} catch (HttpStatusException e) {
			if (e.getStatusCode() == 429) {
				throw new TooManyRequestsException(new Error("Sending too many request in short period!"));
			} else {
				throw e;
			}
		} catch (IOException exc) {
			exc.printStackTrace();
		}

		if (isAdded) {
			carAdded = true;
		} else {
			carAdded = false;
		}

	}

	@Override
	public Boolean getCartAdded() {
		return carAdded;
	}

}
