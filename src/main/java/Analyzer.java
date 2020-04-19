import com.opencsv.CSVWriter;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Analyzer {

    int actualMonth = 0;
    String actualDate = "";
    String[] lastData;

    private void sortByThree(List<OneSale> saleList){
        Collections.sort(saleList, Comparator
                .comparing(OneSale::getProductGroup)
                .thenComparing(OneSale::getPayment)
                .thenComparing(OneSale::getRegion));
    }

    private String[] prepareNextLine(OneSale sale){
        Calculator calculator = new Calculator();
        String[] s = new String[6];
        s[0] = sale.getProductGroup() + "," + sale.getPayment() + "," + sale.getRegion() + "," + this.actualDate;
        s[1] = calculator.average(sale.getSaleList());
        s[2] = calculator.median(sale.getSaleList());
        s[3] = calculator.min(sale.getSaleList());
        s[4] = calculator.max(sale.getSaleList());
        s[5] = calculator.standardDeviation(sale.getSaleList());
        return s;
    }

    private void writeSales(List<OneSale> saleList, CSVWriter writer){
        for(OneSale sale : saleList) {
            writer.writeNext(prepareNextLine(sale));
        }
    }

    private void alterDate(String[] data){
        this.actualMonth = Integer.parseInt(data[2]);
        this.actualDate = this.actualMonth + " " + Integer.parseInt(data[0]);
    }

    private void alterDateFirstTime(String[] data){
        this.actualMonth = 1;
        this.actualDate = this.actualMonth + " " + Integer.parseInt(data[0]);
    }

    private void writeHeader(CSVWriter writer){
        String[] header = {"Grupa produktowa,Forma Platnosci,Wojewodztwo,Miesiac i rok transakcji,Sprzedaz (srednia),Sprzedaz (mediana),Sprzedaz (minimum),Sprzedaz (maksimum),Sprzedaz (odchylenie standardowe)"};
        writer.writeNext(header);
    }

    private void writeCsv(List<OneSale> saleList, String[] data, CSVWriter writer){
        if (this.actualMonth != Integer.parseInt(data[2])) {
            if (this.actualMonth != 0) {
                sortByThree(saleList);
                writeSales(saleList, writer);
                alterDate(data);
            }
            else{
                writeHeader(writer);
                alterDateFirstTime(data);
            }
        }
    }

    private  void lastMonth(List<OneSale> saleList,CSVWriter writer ){
        this.actualMonth ++;
        writeCsv(saleList, lastData, writer);
    }

    private boolean isHoliday(String[] data){
        return data[7].equals("T");
    }

    private void readProductPaymentRegion(List<String> key, String[] data){
        key.set(0, data[11]);
        key.set(1,data[17]);
        key.set(2,data[15]);
    }

    private double acquireSaleValue(String[] data){
        return Double.parseDouble(data[19])*Double.parseDouble(data[21].substring(0,data[21].length()-3));
    }

    private void firstEntry(List<OneSale> saleList, double saleValue, List<String> key){
        if (saleList.isEmpty())
            saleList.add(new OneSale(key, saleValue));
    }

    private boolean isKeyPresent(List<OneSale> saleList, List<String> key){
    return saleList.stream()
            .filter(x -> x.getProductGroup().equals(key.get(0)))
            .filter(x -> x.getPayment().equals(key.get(1)))
            .filter(x -> x.getRegion().equals(key.get(2)))
            .count() == 1;
    }

    private void addSale(List<OneSale> saleList, List<String> key, double saleValue) {
        saleList.stream()
                .filter(x -> x.getProductGroup().equals(key.get(0)))
                .filter(x -> x.getPayment().equals(key.get(1)))
                .filter(x -> x.getRegion().equals(key.get(2)))
                .collect(Collectors.toList())
                .get(0).addSaleValue(saleValue);
    }

    public void analyze() throws IOException {

        double saleValue;
        String row;
        List<OneSale> saleList = new ArrayList<>();
        List<String> key = new ArrayList<>();
        key.add("");
        key.add("");
        key.add("");

        BufferedReader reader = new BufferedReader(new FileReader("AGD_COMPLEX.csv"));
        reader.readLine();

        File file = new File(System.getProperty("user.dir") + "\\result.csv");
        FileWriter outputFile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputFile);

        do {
            row = reader.readLine();
            if (row == null){
                lastMonth(saleList, writer);
                continue;
            }

            String[] data = row.split(",");
            lastData = data;

            if (isHoliday(data))
                continue;

            data = Formatter.dataFormat(row);
            writeCsv(saleList, data, writer);
            readProductPaymentRegion(key, data);

            if (data[19].isEmpty() || data[21].isEmpty())
                continue;

            saleValue = (acquireSaleValue(data));
            firstEntry(saleList, saleValue, key);

            if (isKeyPresent(saleList, key))
                addSale(saleList, key, saleValue);
            else
                saleList.add(new OneSale(key, saleValue));
        }
        while (row  != null);

        reader.close();
        writer.close();
    }
}
