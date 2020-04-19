import java.util.ArrayList;
import java.util.List;

public class Formatter {

    private static void findQuotes(List<Integer> quotes, String row) {
        int quoteBeginning = 0, quoteEnd = 0, iter = 0;
        while (quoteBeginning != -1) {
            quoteBeginning = row.indexOf('"', quoteBeginning + 1);

            if (iter % 2 == 1) {
                quoteEnd = quoteBeginning;
                quotes.add(quoteEnd);
            } else if (quoteBeginning != -1)
                quotes.add(quoteBeginning);
            iter++;
        }
    }

    private static void fillStrip(List<String> strip, List<Integer> quotes, String row){
        strip.add(row.substring(0,quotes.get(0)-1));
        for (int i = 0; i<quotes.size()-1; i+=2){
            strip.add(row.substring(quotes.get(i)+1, quotes.get(i+1)));
        }
        strip.add(row.substring(quotes.get(quotes.size()-1)+1));
    }

    private static void replaceCommas(List<String> strip){
        for (int i = 1; i<strip.size()-1; i++){
            strip.set(i,"," + strip.get(i).replace(",","."));
        }
    }

    private static String[] prepareRow(String row, List<String> strip){
        row = "";
        for (String s : strip){
            row += s;
        }
        row = row.replaceAll("\\u00a0","");
        row = row.replaceAll("\\s+","");
        return row.split(",");
    }

    public static String[] dataFormat(String row){
        List<String> strip = new ArrayList<>();
        List<Integer> quotes = new ArrayList<>();

        findQuotes(quotes, row);
        fillStrip(strip, quotes, row);
        replaceCommas(strip);
        return prepareRow(row, strip);
    }
}
