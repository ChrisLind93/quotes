package facade;

import exceptions.QuoteNotFoundException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class Facade {
    
    static Map<Integer, String> quotes = new HashMap() {
        {
            put(1, "Friends are kisses blown to us by angels");
            put(2, "Do not take life too seriously. You will never get out of it alive");
            put(3, "Behind every great man, is a woman rolling her eyes");
        }
    };

    public static AbstractMap.SimpleEntry<Integer, String> createQuote(String quote) {
        int id = getNextId();
        quotes.put(id, quote);
        AbstractMap.SimpleEntry<Integer, String> pair = new AbstractMap.SimpleEntry(id, quote);
        return pair;
    }

    public static String getQuote(int id) throws QuoteNotFoundException {
        
        if (quotes.get(id) != null) {
            return quotes.get(id);
        } else {
            throw new QuoteNotFoundException("Quote with requested id not found");
        }
    }
    
    public static String getRandomQuote() throws QuoteNotFoundException {
        
        try {
            int num = 1 + (int)(Math.random() * ((quotes.size() - 1) + 1));
            return quotes.get(num);
        } catch (NullPointerException e) {
            throw new QuoteNotFoundException("No Quotes Created yet");
        }
    }

    public static String deleteQuote(int id) throws QuoteNotFoundException {
        
        if (quotes.containsKey(id)) {
            String res = quotes.get(id);
            quotes.remove(id);
            return res;
        } else {
            throw new QuoteNotFoundException("Quote with requested id not found");
        }
    }

    public static AbstractMap.SimpleEntry<Integer, String> updateQuote(int id, String quote) {
        quotes.put(id, quote);
        AbstractMap.SimpleEntry<Integer, String> pair = new AbstractMap.SimpleEntry(id, quote);
        return pair;
    }
    
    private static int getNextId() { 
        
        int count = 1;
        
        for (Map.Entry<Integer, String> elm : quotes.entrySet()) {
            
            if (elm.getKey() != count) {
                return count;
            }
            
            count++;
        }
        
        return quotes.size() +1;
    }
}
