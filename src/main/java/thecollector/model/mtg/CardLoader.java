package thecollector.model.mtg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import thecollector.model.mtg.card.MtgCard;
import thecollector.model.mtg.card.MtgSet;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * MTG Card Loader utility class for handling card sets in JSON format.
 * 
 * @author Ian Claridge
 */
public class CardLoader {

	/**
	 * For the given file path, use a Jackson mapper to map the JSON file
	 * to a list of MTG sets.
	 * 
	 * @param filePath - String
	 * 
	 * @return List
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static List<MtgCard> loadCards(String filePath) throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		@SuppressWarnings("unchecked")
		List<MtgCard> allCards = getAllCards((Map<String, MtgSet>) mapper
				.readValue(new File(filePath), new TypeReference<Map<String, MtgSet>>() {}));

		return allCards;
	}
	
	/**
	 * Turn the given map of cards into a list.
	 * 
	 * @param sets - Map
	 * 
	 * @return List
	 */
	private static List<MtgCard> getAllCards(Map<String, MtgSet> sets) {
		List<MtgCard> allCards = new ArrayList<MtgCard>();

		for (MtgSet set : sets.values()) {
			for (MtgCard card : set.getCards()) {
				card.setSetCode(set.getCode());
				card.setSetName(set.getName());

				allCards.add(card);
			}
		}

		return allCards;
	}
}
