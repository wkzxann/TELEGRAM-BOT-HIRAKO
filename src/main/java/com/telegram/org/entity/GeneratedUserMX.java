package com.telegram.org.entity;

import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

public class GeneratedUserMX {
	
	public static String fetchDataFromApi() {
	    RestTemplate restTemplate = new RestTemplate();
	    String apiUrl = "https://randomuser.me/api/?nat=MX";

	    Map<String, Object> result = restTemplate.getForObject(apiUrl, Map.class);
	    if (result != null) {
	        List<Map<String, Object>> results = (List<Map<String, Object>>) result.get("results");
	        if (results != null && !results.isEmpty()) {
	            Map<String, Object> user = results.get(0);
	            Map<String, Object> nameInfo = (Map<String, Object>) user.get("name");
	            Map<String, Object> locationInfo = (Map<String, Object>) user.get("location");

	            // Acceder a los datos de la ubicación
	            Object streetObj = locationInfo.get("street");
	            String numberStreet = (streetObj instanceof Map) ? ((Map<String, Object>) streetObj).get("number").toString() : "";
	            String nameStreet = (streetObj instanceof Map) ? ((Map<String, Object>) streetObj).get("name").toString() : "";

	            String firstName = (String) nameInfo.get("first");
	            String lastName = (String) nameInfo.get("last");
	            String gender = (String) user.get("gender");
	            String city = (String) locationInfo.get("city");
	            String estate = (String) locationInfo.get("state");
	            String country = (String) locationInfo.get("country");
	            int postcode = (int) locationInfo.get("postcode");
	            String phone = (String) user.get("phone");

	            return "*ふ Hirako Fake Adress ふ*\n"
		                + "-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -\n"
		                + "*Nombres →* `" + firstName + "`\n"
		                + "*Apellidos →* `" + lastName + "`\n"
		                + "*Género →* `" + gender + "`\n"
		                + "*Dirección →* `" + numberStreet + " " + nameStreet + "`\n"
		                + "*Ciudad →* `" + city + "`\n"
		                + "*Estado →* `" + estate + "`\n"
		                + "*País →* `" + country + "`\n"
		                + "*Código Postal →* `" + postcode + "`\n"
		                + "*Teléfono →* `" + phone + "`\n"
		                + "-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -\n"
		                + "*Bot By →* @whoisxannie";
	        } else {
	            return "No se encontraron resultados en la respuesta de la API.";
	        }
	    } else {
	        return "Error al obtener datos desde la API.";
	    }
	}

}
