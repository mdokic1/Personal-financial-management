package ba.unsa.etf.rma.rma20djokicmilica36;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class TransactionListInteractor extends AsyncTask<String, Integer, Void>  implements ITransactionListInteractor {

    LocalDate trDatum = TransactionsModel.trDatum;
    TransactionsModel model;
    BudgetModel bModel;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    DateTimeFormatter df = DateTimeFormatter .ofPattern("yyyy-MM-dd");

    String api_id = "dd11f314-79cd-443b-9fbf-8425e3424f46";
    ArrayList<Transaction> transactions;
    ArrayList<Float> mjesecniGrafPotrosnja;
    ArrayList<Float> mjesecniGrafZarada;
    ArrayList<Float> mjesecniGrafUkupno;
    ArrayList<Float> sedmicniGrafPotrosnja;
    ArrayList<Float> sedmicniGrafZarada;
    ArrayList<Float> sedmicniGrafUkupno;
    ArrayList<Float> dnevniGrafPotrosnja;
    ArrayList<Float> dnevniGrafZarada;
    ArrayList<Float> dnevniGrafUkupno;

    Transaction transakcija;

    public interface OnTransactionsGetDone{
        public void onDone(ArrayList<Transaction> results);

        void returnGraphs(ArrayList<Float> mjesecniGrafPotrosnja, ArrayList<Float> mjesecniGrafZarada, ArrayList<Float> mjesecniGrafUkupno,
                          ArrayList<Float> sedmicniGrafPotrosnja, ArrayList<Float> sedmicniGrafZarada, ArrayList<Float> sedmicniGrafUkupno,
                          ArrayList<Float> dnevniGrafPotrosnja, ArrayList<Float> dnevniGrafZarada, ArrayList<Float> dnevniGrafUkupno);
        //public void onAddDone(Transaction transakcija);
    }

    private OnTransactionsGetDone caller;
    private String tipZahtjeva="";

    public ArrayList<Transaction> getTransact() {
        return transactions;
    }

    public TransactionListInteractor(OnTransactionsGetDone p, String tipZahtjeva) {
        caller = p;
        this.tipZahtjeva = tipZahtjeva;
        transactions = new ArrayList<Transaction>();
        mjesecniGrafPotrosnja = new ArrayList<Float>();
        mjesecniGrafZarada = new ArrayList<Float>();
        mjesecniGrafUkupno = new ArrayList<Float>();
        sedmicniGrafPotrosnja = new ArrayList<Float>();
        sedmicniGrafZarada = new ArrayList<Float>();
        sedmicniGrafUkupno = new ArrayList<Float>();
        dnevniGrafPotrosnja = new ArrayList<Float>();
        dnevniGrafZarada = new ArrayList<Float>();
        dnevniGrafUkupno = new ArrayList<Float>();
        //transakcija = new Transaction(LocalDate.now(), 0.0, "", transactionType.INDIVIDUALPAYMENT, "", null, null);
    };

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new
                InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }


    public TransactionsModel getModel() {
        return model;
    }
    public BudgetModel getBModel() {
        return bModel;
    }


    @Override
    protected Void doInBackground(String... strings) {

        String query = null;
        /*try {
            query = URLEncoder.encode(strings[0]);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        if (tipZahtjeva.equals("get transactions") || tipZahtjeva.equals("make graphs")) {
            boolean imaDovoljno = true;
            int i = 0;
            String url1 = "";

            while (i >= 0) {
                if (i == 0) {
                    url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                            + api_id + "/transactions";
                } else {
                    url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                            + api_id + "/transactions?page=" + i;
                }

                try {
                    URL url = new URL(url1);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String result = convertStreamToString(in);
                    JSONObject jo = new JSONObject(result);
                    JSONArray results = jo.getJSONArray("transactions");
                    if (results.length() == 5) {
                        imaDovoljno = true;
                    } else {
                        imaDovoljno = false;
                    }
                    for (int j = 0; j < results.length(); j++) {
                        JSONObject transaction = results.getJSONObject(j);
                        Integer id = transaction.getInt("id");
                        String date = transaction.getString("date");
                        String title = transaction.getString("title");
                        double amount = transaction.getDouble("amount");
                        String itemDescription = transaction.getString("itemDescription");
                        String transactionInterval = transaction.getString("transactionInterval");
                        String endDate = transaction.getString("endDate");
                        String createdAt = transaction.getString("createdAt");
                        String updatedAt = transaction.getString("updatedAt");
                        Integer AccountId = transaction.getInt("AccountId");
                        Integer TransactionTypeId = transaction.getInt("TransactionTypeId");

                        String datum = date.substring(0, 10);

                        transactionType tip = transactionType.REGULARPAYMENT;

                        if (transactionType.REGULARPAYMENT.getID() == TransactionTypeId) {
                            tip = transactionType.REGULARPAYMENT;
                        }

                        if (transactionType.REGULARINCOME.getID() == TransactionTypeId) {
                            tip = transactionType.REGULARINCOME;
                        }

                        if (transactionType.PURCHASE.getID() == TransactionTypeId) {
                            tip = transactionType.PURCHASE;
                        }

                        if (transactionType.INDIVIDUALINCOME.getID() == TransactionTypeId) {
                            tip = transactionType.INDIVIDUALINCOME;
                        }

                        if (transactionType.INDIVIDUALPAYMENT.getID() == TransactionTypeId) {
                            tip = transactionType.INDIVIDUALPAYMENT;
                        }

                        Integer interval = 0;

                        try {
                            if (transactionInterval != null)
                                interval = Integer.parseInt(transactionInterval);
                        } catch (NumberFormatException e) {
                            interval = 0;
                        }

                        LocalDate krajnjiDatum = null;
                        try {
                            if (endDate != null && endDate != "null") {
                                endDate = endDate.substring(0, 10);
                                krajnjiDatum = LocalDate.parse(endDate, df);
                            }
                        } catch (DateTimeParseException e) {
                            krajnjiDatum = null;
                        }

                        LocalDate dat = null;
                        try {
                            if (datum != null) {
                                dat = LocalDate.parse(datum, df);
                            }
                        } catch (DateTimeParseException e) {
                            dat = null;
                        }

                        transactions.add(new Transaction(id, dat, amount, title, tip, itemDescription, interval, krajnjiDatum));
                        //if (i==4) break;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (imaDovoljno) {
                    i++;
                } else {
                    break;
                }
            }

            if(strings[0].equals("Time interval") || strings[0].equals("Month")){
                for(int j = 1; j <= 12; j++){
                    mjesecniGrafPotrosnja.add(MjesecnaPotrosnja(j));
                    mjesecniGrafZarada.add(MjesecnaZarada(j));
                    mjesecniGrafUkupno.add(MjesecnoUkupno(j));
                }
            }

            if(strings[0].equals("Week")){
                for(int j = 1; j <= 4; j++){
                    sedmicniGrafPotrosnja.add(SedmicnaPotrosnja(j));
                    sedmicniGrafZarada.add(SedmicnaZarada(j));
                    sedmicniGrafUkupno.add(SedmicnoUkupno(j));
                }
            }

            if(strings[0].equals("Day")){
                int trenutniMjesec = LocalDate.now().getMonthValue();
                int pocPetlje = pocetakSedmice();
                int krajPetlje = pocPetlje + 6;
                if(pocPetlje > 28){
                    krajPetlje = brojDanaUMjesecu.get(trenutniMjesec - 1);
                }

                for(int j = pocPetlje; j <= krajPetlje; j++){
                    dnevniGrafPotrosnja.add(DnevnaPotrosnja(j));
                    dnevniGrafZarada.add(DnevnaZarada(j));
                    dnevniGrafUkupno.add(DnevnoUkupno(j));
                }
            }


        } else if (tipZahtjeva.equals("refresh transactions")) {

            boolean imaDovoljno = true;
            int i = 0;
            while (i >= 0) {
                String url1 = "";
                if (i == 0) {
                    if (strings[2] != null && strings[2] != "" && strings[3] != null && strings[3] != "") {
                        if (strings[0] == "" && strings[1] == "") {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?month=" + strings[2] + "&year=" + strings[3];
                        }
                        if (strings[0] != null && strings[0] != "" && strings[1] == "") {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?typeId=" + strings[0] + "&month=" + strings[2] + "&year=" + strings[3];
                        }
                        if (strings[0] == "" && strings[1] != "" && strings[1] != null) {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?sort=" + strings[1] + "&month=" + strings[2] + "&year=" + strings[3];
                        }
                        if (strings[0] != null && strings[0] != "" && strings[1] != null && strings[1] != "") {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?sort=" + strings[1] + "&typeId=" + strings[0] + "&month=" + strings[2] + "&year=" + strings[3];
                        }
                    } else {
                        if (strings[0] != null && strings[0] != "" && strings[1] == "") {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?typeId=" + strings[0];
                        }
                        if (strings[0] == "" && strings[1] != "" && strings[1] != null) {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?sort=" + strings[1];
                        }
                        if (strings[0] != null && strings[0] != "" && strings[1] != null && strings[1] != "") {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?sort=" + strings[1] + "&typeId=" + strings[0];
                        }
                    }

                } else {

                    if (strings[2] != null && strings[2] != "" && strings[3] != null && strings[3] != "") {
                        if (strings[0] == "" && strings[1] == "") {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?page=" + i + "&month=" + strings[2] + "&year=" + strings[3];
                        }
                        if (strings[0] != null && strings[0] != "" && strings[1] == "") {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?page=" + i + "&typeId=" + strings[0] + "&month=" + strings[2] + "&year=" + strings[3];
                        }
                        if (strings[0] == "" && strings[1] != "" && strings[1] != null) {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?page=" + i + "&sort=" + strings[1] + "&month=" + strings[2] + "&year=" + strings[3];
                        }
                        if (strings[0] != null && strings[0] != "" && strings[1] != null && strings[1] != "") {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?page=" + i + "&sort=" + strings[1] + "&typeId=" + strings[0] + "&month=" + strings[2] + "&year=" + strings[3];
                        }
                    } else {
                        if (strings[0] != null && strings[0] != "" && strings[1] == "") {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?page=" + i + "&typeId=" + strings[0];
                        }
                        if (strings[0] == "" && strings[1] != "" && strings[1] != null) {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?page=" + i + "&sort=" + strings[1];
                        }
                        if (strings[0] != null && strings[0] != "" && strings[1] != null && strings[1] != "") {
                            url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                                    + api_id + "/transactions/filter?page=" + i + "&sort=" + strings[1] + "&typeId=" + strings[0];
                        }
                    }

                }


                try {
                    URL url = new URL(url1);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String result = convertStreamToString(in);
                    JSONObject jo = new JSONObject(result);
                    JSONArray results = jo.getJSONArray("transactions");
                    if (results.length() == 5) {
                        imaDovoljno = true;
                    } else {
                        imaDovoljno = false;
                    }
                    for (int j = 0; j < results.length(); j++) {
                        JSONObject transaction = results.getJSONObject(j);
                        Integer id = transaction.getInt("id");
                        String date = transaction.getString("date");
                        String title = transaction.getString("title");
                        double amount = transaction.getDouble("amount");
                        String itemDescription = transaction.getString("itemDescription");
                        String transactionInterval = transaction.getString("transactionInterval");
                        String endDate = transaction.getString("endDate");
                        String createdAt = transaction.getString("createdAt");
                        String updatedAt = transaction.getString("updatedAt");
                        Integer AccountId = transaction.getInt("AccountId");
                        Integer TransactionTypeId = transaction.getInt("TransactionTypeId");

                        String datum = date.substring(0, 10);

                        transactionType tip = transactionType.REGULARPAYMENT;

                        if (transactionType.REGULARPAYMENT.getID() == TransactionTypeId) {
                            tip = transactionType.REGULARPAYMENT;
                        }

                        if (transactionType.REGULARINCOME.getID() == TransactionTypeId) {
                            tip = transactionType.REGULARINCOME;
                        }

                        if (transactionType.PURCHASE.getID() == TransactionTypeId) {
                            tip = transactionType.PURCHASE;
                        }

                        if (transactionType.INDIVIDUALINCOME.getID() == TransactionTypeId) {
                            tip = transactionType.INDIVIDUALINCOME;
                        }

                        if (transactionType.INDIVIDUALPAYMENT.getID() == TransactionTypeId) {
                            tip = transactionType.INDIVIDUALPAYMENT;
                        }

                        Integer interval = 0;

                        try {
                            if (transactionInterval != null)
                                interval = Integer.parseInt(transactionInterval);
                        } catch (NumberFormatException e) {
                            interval = 0;
                        }

                        LocalDate krajnjiDatum = null;
                        try {
                            if (endDate != null && endDate != "null") {
                                endDate = endDate.substring(0, 10);
                                krajnjiDatum = LocalDate.parse(endDate, df);
                            }
                        } catch (DateTimeParseException e) {
                            krajnjiDatum = null;
                        }

                        LocalDate dat = null;
                        try {
                            if (datum != null) {
                                dat = LocalDate.parse(datum, df);
                            }
                        } catch (DateTimeParseException e) {
                            dat = null;
                        }

                        /*if(endDate != null && endDate != "null" && transactionInterval != null && transactionInterval != "null"){
                            LocalDate pomocni = dat;
                            //long numberOfDays = DAYS.between(dat, krajnjiDatum);
                            pomocni = dat.plusDays(interval);
                            if(pomocni.compareTo(krajnjiDatum) <= 0){
                                while(pomocni.compareTo(krajnjiDatum) <= 0){
                                    if(pomocni.getMonthValue() == dat.getMonthValue() )
                                    transactions.add(new Transaction(pomocni, amount, title, tip, itemDescription, interval, krajnjiDatum));
                                    pomocni = pomocni.plusDays(interval);
                                }
                            }


                        }*/

                        transactions.add(new Transaction(id, dat, amount, title, tip, itemDescription, interval, krajnjiDatum));
                        //if (i==4) break;
                    }

                    if (imaDovoljno) {
                        i++;
                    } else {
                        break;
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } else if (tipZahtjeva.equals("add transaction")) {
            String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                    + api_id + "/transactions";
            try {
                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                String jsonInputString = "";
                if (strings[3].equals("Regular payment")) {
                    strings[3] = "1";
                }
                if (strings[3].equals("Regular income")) {
                    strings[3] = "2";
                }
                if (strings[3].equals("Purchase")) {
                    strings[3] = "3";
                }
                if (strings[3].equals("Individual income")) {
                    strings[3] = "4";
                }
                if (strings[3].equals("Individual payment")) {
                    strings[3] = "5";
                }

                if (strings[4] == null) {
                    if (strings[5] != null && strings[6] != null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[0] + "\"" + ", \"amount\": " + strings[1] + ", \"title\": " + "\"" + strings[2] + "\"" +
                                ", \"TransactionTypeId\": " + strings[3] + ", \"transactionInterval\": " + "\"" + strings[5] + "\"" +
                                ", \"endDate\": " + "\"" + strings[6] + "\"" + "}";
                    }
                    if (strings[5] == null && strings[6] == null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[0] + "\"" + ", \"amount\": " + strings[1] + ", \"title\": " + "\"" + strings[2] + "\"" +
                                ", \"TransactionTypeId\": " + strings[3] + "}";
                    }
                    if (strings[5] != null && strings[6] == null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[0] + "\"" + ", \"amount\": " + strings[1] + ", \"title\": " + "\"" + strings[2] + "\"" +
                                ", \"TransactionTypeId\": " + strings[3] + ", \"transactionInterval\": " + "\"" + strings[5] + "\"" + "}";
                    }
                    if (strings[5] == null && strings[6] != null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[0] + "\"" + ", \"amount\": " + strings[1] + ", \"title\": " + "\"" + strings[2] + "\"" +
                                ", \"TransactionTypeId\": " + strings[3] + ", \"endDate\": " + "\"" + strings[6] + "\"" + "}";
                    }
                }

                if (strings[4] != null) {
                    if (strings[5] != null && strings[6] != null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[0] + "\"" + ", \"amount\": " + strings[1] + ", \"title\": " + "\"" + strings[2] + "\"" +
                                ", \"TransactionTypeId\": " + strings[3] + ", \"itemDescription\": " + "\"" + strings[4] + "\"" + ", \"transactionInterval\": " + "\"" + strings[5] + "\"" +
                                ", \"endDate\": " + "\"" + strings[6] + "\"" + "}";
                    }
                    if (strings[5] == null && strings[6] == null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[0] + "\"" + ", \"amount\": " + strings[1] + ", \"title\": " + "\"" + strings[2] + "\"" +
                                ", \"TransactionTypeId\": " + strings[3] + ", \"itemDescription\": " + "\"" + strings[4] + "\"" + "}";
                    }
                    if (strings[5] != null && strings[6] == null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[0] + "\"" + ", \"amount\": " + strings[1] + ", \"title\": " + "\"" + strings[2] + "\"" +
                                ", \"TransactionTypeId\": " + strings[3] + ", \"itemDescription\": " + "\"" + strings[4] + "\"" + ", \"transactionInterval\": " + "\"" + strings[5] + "\"" + "}";
                    }
                    if (strings[5] == null && strings[6] != null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[0] + "\"" + ", \"amount\": " + strings[1] + ", \"title\": " + "\"" + strings[2] + "\"" +
                                ", \"TransactionTypeId\": " + strings[3] + ", \"itemDescription\": " + "\"" + strings[4] + "\"" + ", \"endDate\": " + "\"" + strings[6] + "\"" + "}";
                    }
                }

                transactionType tip = transactionType.REGULARPAYMENT;

                if (strings[3].equals("Regular payment")) {
                    tip = transactionType.REGULARPAYMENT;
                }

                if (strings[3].equals("Regular income")) {
                    tip = transactionType.REGULARINCOME;
                }

                if (strings[3].equals("Purchase")) {
                    tip = transactionType.PURCHASE;
                }

                if (strings[3].equals("Individual income")) {
                    tip = transactionType.INDIVIDUALINCOME;
                }

                if (strings[3].equals("Individual payment")) {
                    tip = transactionType.INDIVIDUALPAYMENT;
                }

                Integer interval = null;

                try {
                    if (strings[5] != null)
                        interval = Integer.parseInt(strings[5]);
                } catch (NumberFormatException e) {
                    interval = null;
                }

                LocalDate krajnjiDatum = null;
                try {
                    if (strings[6] != null) {
                        //strings[6] = strings[6].substring(0, 10);
                        krajnjiDatum = LocalDate.parse(strings[6], df);
                    }
                } catch (DateTimeParseException e) {
                    krajnjiDatum = null;
                }

                LocalDate dat = null;
                try {
                    if (strings[0] != null) {
                        dat = LocalDate.parse(strings[0], df);
                    }
                } catch (DateTimeParseException e) {
                    dat = null;
                }

                try (OutputStream os = urlConnection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (tipZahtjeva.equals("change transaction")) {
            String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                    + api_id + "/transactions/" + strings[0];

            try {
                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);


                if (strings[4].equals("Regular payment")) {
                    strings[4] = "1";
                }
                if (strings[4].equals("Regular income")) {
                    strings[4] = "2";
                }
                if (strings[4].equals("Purchase")) {
                    strings[4] = "3";
                }
                if (strings[4].equals("Individual income")) {
                    strings[4] = "4";
                }
                if (strings[4].equals("Individual payment")) {
                    strings[4] = "5";
                }

                String jsonInputString = "";

                if (strings[5] == null) {
                    if (strings[6] != null && strings[7] != null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[1] + "\"" + ", \"amount\": " + strings[2] + ", \"title\": " + "\"" + strings[3] + "\"" +
                                ", \"TransactionTypeId\": " + strings[4] + ", \"transactionInterval\": " + "\"" + strings[6] + "\"" +
                                ", \"endDate\": " + "\"" + strings[7] + "\"" + "}";
                    }
                    if (strings[6] == null && strings[7] == null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[1] + "\"" + ", \"amount\": " + strings[2] + ", \"title\": " + "\"" + strings[3] + "\"" +
                                ", \"TransactionTypeId\": " + strings[4] + "}";
                    }
                    if (strings[6] != null && strings[7] == null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[1] + "\"" + ", \"amount\": " + strings[2] + ", \"title\": " + "\"" + strings[3] + "\"" +
                                ", \"TransactionTypeId\": " + strings[4] + ", \"transactionInterval\": " + "\"" + strings[6] + "\"" + "}";
                    }
                    if (strings[6] == null && strings[7] != null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[1] + "\"" + ", \"amount\": " + strings[2] + ", \"title\": " + "\"" + strings[3] + "\"" +
                                ", \"TransactionTypeId\": " + strings[4] + ", \"endDate\": " + "\"" + strings[7] + "\"" + "}";
                    }
                }

                if (strings[5] != null) {
                    if (strings[6] != null && strings[7] != null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[1] + "\"" + ", \"amount\": " + strings[2] + ", \"title\": " + "\"" + strings[3] + "\"" +
                                ", \"TransactionTypeId\": " + strings[4] + ", \"itemDescription\": " + "\"" + strings[5] + "\"" + ", \"transactionInterval\": " + "\"" + strings[6] + "\"" +
                                ", \"endDate\": " + "\"" + strings[7] + "\"" + "}";
                    }
                    if (strings[6] == null && strings[7] == null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[1] + "\"" + ", \"amount\": " + strings[2] + ", \"title\": " + "\"" + strings[3] + "\"" +
                                ", \"TransactionTypeId\": " + strings[4] + ", \"itemDescription\": " + "\"" + strings[5] + "\"" + "}";
                    }
                    if (strings[6] != null && strings[7] == null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[1] + "\"" + ", \"amount\": " + strings[2] + ", \"title\": " + "\"" + strings[3] + "\"" +
                                ", \"TransactionTypeId\": " + strings[4] + ", \"itemDescription\": " + "\"" + strings[5] + "\"" + ", \"transactionInterval\": " + "\"" + strings[6] + "\"" + "}";
                    }
                    if (strings[6] == null && strings[7] != null) {
                        jsonInputString = "{\"date\": " + "\"" + strings[1] + "\"" + ", \"amount\": " + strings[2] + ", \"title\": " + "\"" + strings[3] + "\"" +
                                ", \"TransactionTypeId\": " + strings[4] + ", \"itemDescription\": " + "\"" + strings[5] + "\"" + ", \"endDate\": " + "\"" + strings[7] + "\"" + "}";
                    }
                }

                try (OutputStream os = urlConnection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                }


            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else if(tipZahtjeva.equals("delete transaction")) {
            String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/"
                    + api_id + "/transactions/" + strings[0];

            try {
                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("DELETE");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String jsonInputString = "";

        }
            return null;
    }


    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        caller.onDone(transactions);
        caller.returnGraphs(mjesecniGrafPotrosnja, mjesecniGrafZarada, mjesecniGrafUkupno, sedmicniGrafPotrosnja, sedmicniGrafZarada,
                            sedmicniGrafUkupno, dnevniGrafPotrosnja, dnevniGrafZarada, dnevniGrafUkupno);
    }

    ArrayList<Integer> brojDanaUMjesecu = new ArrayList<Integer>(){
        {
            add(31);
            add(29);
            add(31);
            add(30);
            add(31);
            add(30);
            add(31);
            add(31);
            add(30);
            add(31);
            add(30);
            add(31);
        }
    };

    @Override
    public int getTotLimit() {
        return bModel.racun.getTotalLimit();
    }

    @Override
    public float MjesecnaPotrosnja(int mjesec) {
        float potrosnja = 0.0f;
        for(Transaction t : transactions){
            if(t.getType() == transactionType.INDIVIDUALPAYMENT || t.getType() == transactionType.PURCHASE){
                if(t.getDate().getYear() == LocalDate.now().getYear() && t.getDate().getMonthValue() == mjesec){
                    potrosnja += t.getAmount();
                }
            }
            if(t.getType() == transactionType.REGULARPAYMENT){
                LocalDate pocetak = t.getDate();
                LocalDate kraj = t.getEndDate();
                if(!(pocetak.getYear() > LocalDate.now().getYear() || kraj.getYear() < LocalDate.now().getYear())){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();
                    if(t.getDate().getMonthValue() == mjesec){
                        potrosnja += t.getAmount();
                    }
                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(!(novi.getYear() == LocalDate.now().getYear())){
                        novi = novi.plusDays(t.getTransactionInterval());
                    }
                    while(novi.getYear() == LocalDate.now().getYear() && novi.compareTo(t.getEndDate()) <= 0){
                        if(novi.getMonthValue() == mjesec){
                            potrosnja += t.getAmount();
                        }

                        novi = novi.plusDays(t.getTransactionInterval());
                    }
                }

            }
        }
        return potrosnja;
    }

    @Override
    public float MjesecnaZarada(int mjesec) {
        float zarada = 0.0f;

        for(Transaction t : transactions){
            if(t.getType() == transactionType.INDIVIDUALINCOME){
                if(t.getDate().getYear() == LocalDate.now().getYear() && t.getDate().getMonthValue() == mjesec){
                    zarada += t.getAmount();
                }
            }
            if(t.getType() == transactionType.REGULARINCOME){
                LocalDate pocetak = t.getDate();
                LocalDate kraj = t.getEndDate();
                if(!(pocetak.getYear() > LocalDate.now().getYear() || kraj.getYear() < LocalDate.now().getYear())){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();
                    if(t.getDate().getMonthValue() == mjesec){
                        zarada += t.getAmount();
                    }
                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(!(novi.getYear() == LocalDate.now().getYear())){
                        novi = novi.plusDays(t.getTransactionInterval());
                    }
                    while(novi.getYear() == LocalDate.now().getYear() && novi.compareTo(t.getEndDate()) <= 0){
                        if(novi.getMonthValue() == mjesec){
                            zarada += t.getAmount();
                        }

                        novi = novi.plusDays(t.getTransactionInterval());
                    }
                }

            }
        }

        return zarada;
    }

    @Override
    public float MjesecnoUkupno(int mjesec) {
       float ukupno = 0.0f;
       for(int i = 1; i <= 12; i++){
           if(i <= mjesec){
               ukupno += (MjesecnaZarada(i) - MjesecnaPotrosnja(i));
           }
       }
       return ukupno;

    }

    @Override
    public float SedmicnaPotrosnja(int sedmica) {
        float potrosnja = 0.0f;

        LocalDate pocetak = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1 + 7*(sedmica - 1));
        LocalDate kraj = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 7 + 7*(sedmica - 1));

        for(Transaction t : transactions){
            if(t.getType() == transactionType.INDIVIDUALPAYMENT || t.getType() == transactionType.PURCHASE){
                if(t.getDate().compareTo(pocetak) >= 0 && t.getDate().compareTo(kraj) <= 0){
                    potrosnja += t.getAmount();
                }
            }

            if(t.getType() == transactionType.REGULARPAYMENT){
                LocalDate poc = t.getDate();
                LocalDate kr = t.getEndDate();

                if(poc.compareTo(kraj) <= 0 && kr.compareTo(pocetak) >= 0){
                     if(poc.compareTo(pocetak) >= 0 && poc.compareTo(kraj) <= 0){
                         potrosnja += t.getAmount();
                     }

                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();

                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(novi.compareTo(pocetak) < 0){
                        novi = novi.plusDays(t.getTransactionInterval());
                    }

                    while(novi.compareTo(pocetak) >= 0 && novi.compareTo(kraj) <= 0 && novi.compareTo(t.getEndDate()) <= 0){
                        potrosnja += t.getAmount();
                        novi = novi.plusDays(t.getTransactionInterval());
                    }
                }
            }
        }

        return potrosnja;
    }

    @Override
    public float SedmicnaZarada(int sedmica) {
        float zarada = 0.0f;

        LocalDate pocetak = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1 + 7*(sedmica - 1));
        LocalDate kraj = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 7 + 7*(sedmica - 1));

        for(Transaction t : transactions){
            if(t.getType() == transactionType.INDIVIDUALINCOME){
                if(t.getDate().compareTo(pocetak) >= 0 && t.getDate().compareTo(kraj) <= 0){
                    zarada += t.getAmount();
                }
            }

            if(t.getType() == transactionType.REGULARINCOME){
                LocalDate poc = t.getDate();
                LocalDate kr = t.getEndDate();

                if(poc.compareTo(kraj) <= 0 && kr.compareTo(pocetak) >= 0){
                    if(poc.compareTo(pocetak) >= 0 && poc.compareTo(kraj) <= 0){
                        zarada += t.getAmount();
                    }

                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();

                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(novi.compareTo(pocetak) < 0){
                        novi = novi.plusDays(t.getTransactionInterval());
                    }

                    while(novi.compareTo(pocetak) >= 0 && novi.compareTo(kraj) <= 0 && novi.compareTo(t.getEndDate()) <= 0){
                        zarada += t.getAmount();
                        novi = novi.plusDays(t.getTransactionInterval());
                    }
                }
            }
        }

        return zarada;
    }

    @Override
    public float SedmicnoUkupno(int sedmica) {
        float ukupno = 0.0f;

        for(int i = 1; i <= 4; i++){
            if(i <= sedmica){
                ukupno += (SedmicnaZarada(i) - SedmicnaPotrosnja(i));
            }
        }

        return ukupno;
    }

    @Override
    public float DnevnaPotrosnja(int dan) {
        float potrosnja = 0.0f;

        LocalDate datum = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), dan);

        for(Transaction t : transactions){

            if(t.getType() == transactionType.INDIVIDUALPAYMENT || t.getType() == transactionType.PURCHASE){
                if(t.getDate().compareTo(datum) == 0){
                    potrosnja += t.getAmount();
                }
            }

            if(t.getType() == transactionType.REGULARPAYMENT){
                LocalDate pocetak = t.getDate();
                LocalDate kraj = t.getEndDate();

                if(pocetak.compareTo(datum) <= 0 && kraj.compareTo(datum) >= 0){
                    if(pocetak.compareTo(datum) == 0){
                        potrosnja += t.getAmount();
                    }

                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();

                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(novi.compareTo(datum) < 0){
                        novi = novi.plusDays(t.getTransactionInterval());
                    }

                    if(novi.compareTo(datum) == 0){
                        potrosnja += t.getAmount();
                    }
                }
            }
        }

        return potrosnja;
    }

    @Override
    public float DnevnaZarada(int dan) {
        float zarada = 0.0f;

        LocalDate datum = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), dan);

        for(Transaction t : transactions){

            if(t.getType() == transactionType.INDIVIDUALINCOME){
                if(t.getDate().compareTo(datum) == 0){
                    zarada += t.getAmount();
                }
            }

            if(t.getType() == transactionType.REGULARINCOME){
                LocalDate pocetak = t.getDate();
                LocalDate kraj = t.getEndDate();

                if(pocetak.compareTo(datum) <= 0 && kraj.compareTo(datum) >= 0){
                    if(pocetak.compareTo(datum) == 0){
                        zarada += t.getAmount();
                    }

                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();

                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(novi.compareTo(datum) < 0){
                        novi = novi.plusDays(t.getTransactionInterval());
                    }

                    if(novi.compareTo(datum) == 0){
                        zarada += t.getAmount();
                    }
                }
            }
        }

        return zarada;

    }

    @Override
    public float DnevnoUkupno(int dan) {
        float ukupno = 0.0f;
        int pocSedmice = pocetakSedmice();
        int trenutniMjesec = LocalDate.now().getMonthValue();
        int krajSedmice = pocSedmice + 6;
        if(pocSedmice > 28){
            krajSedmice = brojDanaUMjesecu.get(trenutniMjesec - 1);
        }
        for(int i = pocSedmice; i <= krajSedmice; i++){
            if(i <= dan){
                ukupno += (DnevnaZarada(i) - DnevnaPotrosnja(i));
            }

        }

        return ukupno;
    }

    @Override
    public int pocetakSedmice(){
        int trenutniDan = LocalDate.now().getDayOfMonth();
        int trenutniMjesec = LocalDate.now().getMonthValue();

        int pocPetlje = 0;
        int krajPetlje = 0;

        int pocetni = 1;
        int krajnji = brojDanaUMjesecu.get(trenutniMjesec - 1);
        int krajSedmice = 7;
        if(pocetni <= trenutniDan && krajSedmice >= trenutniDan){
            pocPetlje = pocetni;
            krajPetlje = krajSedmice;
        }
        else{
            while(pocetni <= krajnji){

                if(pocetni <= trenutniDan && krajSedmice >= trenutniDan){
                    pocPetlje = pocetni;
                    krajPetlje = krajSedmice;
                    break;
                }

                pocetni = pocetni + 7;
                krajSedmice = krajSedmice + 7;
                if(pocetni > 28){
                    krajSedmice = krajnji;
                    pocPetlje = pocetni;
                    krajPetlje = krajnji;
                }
            }
        }
        return pocetni;
    }


    @Override
    public int getMjLimit() {
        return bModel.racun.getMonthLimit();
    }

    @Override
    public boolean CheckTotalLimit(Transaction transaction){
        double potrosnja = 0;
        for(Transaction t : transactions){
            if(t.getType() == transactionType.INDIVIDUALPAYMENT || t.getType() == transactionType.PURCHASE){
                potrosnja += t.getAmount();
            }
            if(t.getType() == transactionType.REGULARPAYMENT){
                long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                long number = numOfDays/t.getTransactionInterval();
                potrosnja += t.getAmount()*number;
            }
        }

        if(transaction.getType() == transactionType.REGULARPAYMENT){
            long numOfDays = DAYS.between(transaction.getDate(), transaction.getEndDate());
            long number = numOfDays/transaction.getTransactionInterval();
            potrosnja += transaction.getAmount()*number;
        }

        else{
            potrosnja += transaction.getAmount();
        }

        return (potrosnja <= getTotLimit());
    }

    @Override
    public boolean CheckMonthLimit(Transaction transaction){
        double potrosnja = 0;
        for(Transaction t : transactions){
            if(t.getDate().getMonthValue() == transaction.getDate().getMonthValue()){
                if(t.getType() == transactionType.INDIVIDUALPAYMENT || t.getType() == transactionType.PURCHASE){
                    potrosnja += t.getAmount();
                }
                if(t.getType() == transactionType.REGULARPAYMENT){
                    long numOfDays = DAYS.between(t.getDate(), t.getEndDate());
                    long number = numOfDays/t.getTransactionInterval();
                    LocalDate novi = t.getDate().plusDays(t.getTransactionInterval());
                    while(novi.getMonthValue() == t.getDate().getMonthValue() && novi.compareTo(t.getEndDate()) <= 0){
                        potrosnja += t.getAmount();
                        novi = novi.plusDays(t.getTransactionInterval());
                    }

                }
            }
        }

        if(transaction.getType() == transactionType.REGULARPAYMENT){
            long numOfDays = DAYS.between(transaction.getDate(), transaction.getEndDate());
            long number = numOfDays/transaction.getTransactionInterval();
            LocalDate novi = transaction.getDate().plusDays(transaction.getTransactionInterval());
            while(novi.getMonthValue() == transaction.getDate().getMonthValue() && novi.compareTo(transaction.getEndDate()) <= 0){
                potrosnja += transaction.getAmount();
                novi = novi.plusDays(transaction.getTransactionInterval());
            }
        }
        else{
            potrosnja += transaction.getAmount();
        }

        return (potrosnja <= getMjLimit());
    }

    @Override
    public ArrayList<Transaction> getByDate(){
        ArrayList<Transaction> odgovarajuce = new ArrayList<>();
        ArrayList<Transaction> sve = transactions;
        for(Transaction t : sve){
            if(t.getType() == transactionType.INDIVIDUALINCOME || t.getType() == transactionType.INDIVIDUALPAYMENT || t.getType() == transactionType.PURCHASE){
                if(t.getDate().getMonthValue() == trDatum.getMonthValue() && t.getDate().getYear() == trDatum.getYear()){
                    odgovarajuce.add(t);
                }
            }

            if (t.getType() == transactionType.REGULARPAYMENT || t.getType() == transactionType.REGULARINCOME) {
                if(!(trDatum.getYear() < t.getDate().getYear() || (trDatum.getYear() == t.getDate().getYear()
                        && trDatum.getMonthValue() < t.getDate().getMonthValue()))
                    &&  !(trDatum.getYear() > t.getEndDate().getYear() || (trDatum.getYear() == t.getEndDate().getYear()
                        && trDatum.getMonthValue() > t.getEndDate().getMonthValue())) ){

                    odgovarajuce.add(t);
                }

            }
        }
        return odgovarajuce;
    }

    @Override
    public ArrayList<Transaction> getByTypeSorted(String type, String sortType) {
        ArrayList<Transaction> odgovarajuce = new ArrayList<>();
        ArrayList<Transaction> sortirane = new ArrayList<>();
        ArrayList<Transaction> sve = getByDate();
        for(Transaction t : sve){
            if(t.getType().equals(transactionType.INDIVIDUALPAYMENT) && type.equals("Individual payment")){
                odgovarajuce.add(t);
            }
            if(t.getType().equals(transactionType.REGULARPAYMENT) && type.equals("Regular payment")){
                odgovarajuce.add(t);
            }
            if(t.getType().equals(transactionType.PURCHASE) && type.equals("Purchase")){
                odgovarajuce.add(t);
            }
            if(t.getType().equals(transactionType.REGULARINCOME) && type.equals("Regular income")){
                odgovarajuce.add(t);
            }
            if(t.getType().equals(transactionType.INDIVIDUALINCOME) && type.equals("Individual income")){
                odgovarajuce.add(t);
            }
            if(type.equals("All types") || type.equals("Filter by")){
                odgovarajuce.add(t);
            }
        }

        if(sortType.equals("Sort by")){
            return odgovarajuce;
        }

        if(sortType.equals("Price - Ascending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparingDouble(Transaction::getAmount)).collect(Collectors.toList());
        }

        if(sortType.equals("Price - Descending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparingDouble(Transaction::getAmount).reversed()).collect(Collectors.toList());
        }

        if(sortType.equals("Title - Ascending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparing(Transaction::getTitle)).collect(Collectors.toList());
        }

        if(sortType.equals("Title - Descending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparing(Transaction::getTitle).reversed()).collect(Collectors.toList());
        }

        if(sortType.equals("Date - Ascending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparing(Transaction::getDate)).collect(Collectors.toList());
        }

        if(sortType.equals("Date - Descending")){
            sortirane = (ArrayList<Transaction>) odgovarajuce.stream().
                    sorted(Comparator.comparing(Transaction::getDate).reversed()).collect(Collectors.toList());
        }
        return sortirane;
    }
}
