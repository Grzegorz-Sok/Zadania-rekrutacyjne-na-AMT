import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Calculator {

    DecimalFormat f = new DecimalFormat("##.00");

    public String average(List<Double> list){
        double sum = list.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        return f.format(sum / list.size());
    }

    public String median(List<Double> list)
    {
        int n = list.size();
        Collections.sort(list);
        if (n % 2 != 0)
            return f.format(list.get(n / 2));
        return f.format((list.get(n / 2-1) + list.get(n / 2))/2.0);
    }

    public String standardDeviation(List<Double> list) {
        double avg = Double.parseDouble((average(list)).replace(',','.'));
        double X = 0;
        for (double x : list)
            X += Math.pow(x-avg,2);
        return f.format(Math.sqrt(X/list.size()));
    }

    public String min(List<Double> list){
        return f.format(Collections.min(list));
    }

    public String max(List<Double> list){
        return f.format(Collections.max(list));
    }
}
