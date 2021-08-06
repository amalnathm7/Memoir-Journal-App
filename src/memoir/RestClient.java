package memoir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class MemoirRestClient{
    public static void main(String[] args) {
        System.out.println("\nFETCHING SERVER TIME...");
        RestClient.check_server_time();
        System.out.println("\nLOGGING IN AND RETURNING THE AUTH TOKEN...");
        String auth_token = RestClient.login("test","Ajith@2001");
        System.out.println(auth_token);
        System.out.println("\nFETCHING CURRENT USER INFO... ");
        User me = RestClient.me(auth_token);
        System.out.println(me.email+"|"+me.username+"|"+me.id);
        System.out.println("\nCREATING A NEW USER...");
        System.out.println(RestClient.create_user("ajitshffdd@gsam.com","sss","Ajith@2001","Ajith@2001"));
        System.out.println("\nFETCHING ALL JOURNALS AND DIARIES...");
        System.out.println(RestClient.get_all_journals_and_diaries(auth_token));
        System.out.println("\nFETCHING CATEGORIZED JOURNAL OR DIARY...");
        System.out.println(RestClient.get_categorized_list("j",auth_token));
        System.out.println("\nGET OR DELETE A SINGLE JOURNAL/DIARY...");
        Journal journal = RestClient.get_or_delete_journal(20,"GET",auth_token);
        System.out.println("\nMAKING MODIFICATIONS TO A JOURNALS OR DIARY...");
        System.out.println(RestClient.put_journal(journal,auth_token));
        System.out.println("\nFETCHING ALL NOTES...");
        System.out.println(RestClient.get_all_todo(auth_token));
        System.out.println("\nCREATING A JOURNAL OR DIARY...");
        System.out.println(RestClient.create_journal("title","description","emotion","image","category",auth_token));
        System.out.println("\nFCREATING A  NOTE...");
        System.out.println(RestClient.create_todo("title","description","2020-12-25 22:25:14",auth_token));
        System.out.println("\nGET OR DELETE A NOTE...");
        Todo note = RestClient.get_or_delete_todo(3,"GET",auth_token);
        System.out.println(note.id);
        System.out.println("\nMAKING MODIFICATIONS TO A NOTE...");
        System.out.println(RestClient.put_todo(note,auth_token));
    }
}






public class RestClient {
    // to check server time
    public static void check_server_time() {
      try {
        URL url = new URL("https://memoir-api-v1.herokuapp.com/checkserver");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
            (conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }

        conn.disconnect();

      } catch (MalformedURLException e) {

        e.printStackTrace();

      } catch (IOException e) {

        e.printStackTrace();

      }

    }

    //User Login to get the auth token
    public static String login(String username,String password){
        String token = "token is not available";

        try {
            URL url = new URL("https://memoir-api-v1.herokuapp.com/auth/token/login/?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"username\":\""+username+"\"" + ",\"password\":\""+password+"\"}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output,result="";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            String[] arrOfStr = result.split(":");
            token =arrOfStr[1].substring(1,arrOfStr[1].length()-2);  


            conn.disconnect();

      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
     }
     return token;
    }

    //Current user info
    public static User me(String token) {
        User user = new User();
      try {
            URL url = new URL("https://memoir-api-v1.herokuapp.com/auth/users/me/?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "token "+ token);

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

            String output,result="";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            
            String arrobj[]= result.split(",");
            ArrayList<String> arr = new ArrayList();
            for(String obj : arrobj){
                String element= obj.split(":")[1].replace("\"","").replace("}","");
                arr.add(element);
            }
            user = new User(arr);
            conn.disconnect();

      } catch (MalformedURLException e) {

        e.printStackTrace();

      } catch (IOException e) {

        e.printStackTrace();

      }
      return user;
    }
    //Create new user , token will not be provided
    public static String create_user(String email,String username,String password,String re_password){
        String status = "Something went wrong";
        try {
            URL url = new URL("https://memoir-api-v1.herokuapp.com/auth/users/?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"username\":\""+username+"\",\"password\":\""+password+"\",\"re_password\":\""+re_password+"\",\"email\":\""+email+"\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output,result="";
     
            while ((output = br.readLine()) != null) {
                result += output;
            }
            status = "user created";


            conn.disconnect();

      } catch (MalformedURLException e) {

        e.printStackTrace();

      } catch (IOException e) {

        e.printStackTrace();

     }
     return status;

    }
    //Get all journals and diaries in publication date order
    public static ArrayList<Journal> get_all_journals_and_diaries(String token){
        ArrayList<Journal> list = new ArrayList<Journal>();
        try {
            URL url = new URL("https://memoir-api-v1.herokuapp.com/journal/?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "token "+ token);
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output,result="";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            list = JsonParserer.parsejournal(result);
            conn.disconnect();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
     }
     return list;
    }

    //Get categoriesed list
    public static ArrayList<Journal> get_categorized_list(String category,String token) {
        ArrayList<Journal> list = new ArrayList<Journal>();
      try {
        URL url = new URL("https://memoir-api-v1.herokuapp.com/journal/category/"+category);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", "token "+token);

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
            (conn.getInputStream())));

        String output,result="";
        while ((output = br.readLine()) != null) {
            result += output;
        }
         list = JsonParserer.parsejournal(result);

        conn.disconnect();

      } catch (MalformedURLException e) {

        e.printStackTrace();

      } catch (IOException e) {

        e.printStackTrace();

      }
      return list;

    }

    //ACCessing individual journals get and put method
    public static Journal get_or_delete_journal(int val,String method,String token) {
        Journal journal = new Journal();
        try {
            URL url = new URL("https://memoir-api-v1.herokuapp.com/journal/"+val);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(method == "GET" || method == "DELETE"){
                conn.setRequestMethod(method);
            }

            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "token "+token);

            if (conn.getResponseCode() != 200 && method == "GET" || conn.getResponseCode() != 202 && method == "DELETE" ) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }


            BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

            String output,result="";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            if(method == "GET"){
                String arrobj[]= result.split(",");
                ArrayList<String> arr = new ArrayList();
                for(String obj : arrobj){
                    String element= obj.split(":")[1].replace("\"","").replace("}","");
                    arr.add(element);
            }
            journal = new Journal(arr);
            }


            conn.disconnect();

      } catch (MalformedURLException e) {

        e.printStackTrace();

      } catch (IOException e) {

        e.printStackTrace();

      }
      return journal;

    }
    //Making corrections in journal
    public static String put_journal(Journal journal,String token){

        String status = "Something went wrong!!";
        try {
            URL url = new URL("https://memoir-api-v1.herokuapp.com/journal/"+ journal.id+"/?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "token "+token);

            String input = "{\"title\":\""+journal.title+"\",\"description\":\""+journal.description+"\",\"emotion\":\""+journal.emotion+"\",\"image\":\""+journal.image+"\",\"category\":\""+journal.category+"\""+ "}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output,result="";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            status = "Object updated";
            conn.disconnect();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
     }
     return status;
    }
    //Create a new journal
    public static String create_journal(String title,String description,String emotion,String image,String category,String token){
        String status = "token is not available";
        try {
            URL url = new URL("https://memoir-api-v1.herokuapp.com/journal/?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "token "+token);
            String input = "{\"title\":\""+title+"\",\"description\":\""+description+"\",\"emotion\":\""+emotion+"\",\"image\":\""+image+"\",\"category\":\""+category+"\""+ "}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output,result="";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            status = result;
            conn.disconnect();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
     }

     return status;

    }


    

//NOTES
    //get all the notes
    public static ArrayList<Todo> get_all_todo(String token){
        ArrayList<Todo> list = new ArrayList<Todo>();
        try {
            URL url = new URL("https://memoir-api-v1.herokuapp.com/notes/?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "token "+ token);
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output,result="";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            list = JsonParserer.parsetodo(result);
            conn.disconnect();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
     }
     return list;
    }


    //Create a new note
    public static String create_todo(String title,String description,String end_date,String token){
        String status = "token is not available";
        try {
            URL url = new URL("https://memoir-api-v1.herokuapp.com/notes/?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "token "+token);
            String input = "{\"title\":\""+title+"\",\"description\":\""+description+"\""+",\"end_date\":\""+end_date+"\""+ "}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output,result="";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            status = result;
            conn.disconnect();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
     }

     return status;

    }

        //ACCessing individual Note get and put method
    public static Todo get_or_delete_todo(int val,String method,String token) {
        Todo note = new Todo();
        try {
            URL url = new URL("https://memoir-api-v1.herokuapp.com/notes/"+val);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(method == "GET" || method == "DELETE"){
                conn.setRequestMethod(method);
            }

            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "token "+token);

            if (conn.getResponseCode() != 200 && method == "GET" || conn.getResponseCode() != 202 && method == "DELETE" ) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }


            BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

            String output,result="";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            if(method == "GET"){
                String arrobj[]= result.split(",");
                ArrayList<String> arr = new ArrayList();
                for(String obj : arrobj){
                    String element= obj.split(":")[1].replace("\"","").replace("}","");
                    arr.add(element);
            }
            note = new Todo(arr);
            }


            conn.disconnect();

      } catch (MalformedURLException e) {

        e.printStackTrace();

      } catch (IOException e) {

        e.printStackTrace();

      }
      return note;

    }

    //Making corrections in note
    public static String put_todo(Todo note,String token){

        String status = "Something went wrong!!";
        try {
            URL url = new URL("https://memoir-api-v1.herokuapp.com/notes/"+ note.id+"/?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "token "+token);

            String input = "{\"title\":\""+note.title+"\",\"description\":\""+note.description+"\",\"end_date\":\""+note.end_date+"\"" + "}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output,result="";
            while ((output = br.readLine()) != null) {
                result += output;
            }
            status = "Object updated";
            conn.disconnect();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
     }
     return status;
    }
}








class JsonParserer{
    public static ArrayList<Journal> parsejournal(String data){
        ArrayList<Journal> s = new ArrayList<Journal>();
        if (data.startsWith("[{")){
            String[] arrobj = data.split("},");
            for(String obj: arrobj){
                ArrayList<String> args = new ArrayList<String>();
                String[] keys = obj.split(",");
                for (String key : keys){
                    String val = ((key.split(":")[1]).replace("\"","")).replace("}]","");
                    args.add(val);
                }
                Journal el = new Journal(args);
               
            
                s.add(el);
                

            }
        }
        return s;

    }


    public static ArrayList<Todo> parsetodo(String data){
        ArrayList<Todo> s = new ArrayList<Todo>();
        if (data.startsWith("[{")){
            String[] arrobj = data.split("},");
            for(String obj: arrobj){
                ArrayList<String> args = new ArrayList<String>();
                String[] keys = obj.split(",");
                for (String key : keys){
                    String val = ((key.split(":")[1]).replace("\"","")).replace("}]","");
                    args.add(val);
                }
                Todo el = new Todo(args);
               
            
                s.add(el);
                

            }
        }
        return s;

    }
}

class Journal{
    int id;
    String title,published_date,description,username,emotion,image,category;
    public  Journal(ArrayList<String> args ){
        this.id = Integer.parseInt(args.get(0));
        this.title = args.get(1);
        this.published_date = args.get(2);
        this.description = args.get(3);
        this.username = args.get(4);
        this.emotion = args.get(5);
        this.image = args.get(6);
        this.category = args.get(7);
    }
    public Journal(){
        this.id = -1;
    }
    public String toString(){
        return this.published_date;
    }
}


class Todo{
    int id;
    String title,published_date,description,username,end_date;
    public  Todo(ArrayList<String> args ){
        this.id = Integer.parseInt(args.get(0));
        this.title = args.get(1);
        this.published_date = args.get(2);
        this.description = args.get(3);
        this.username = args.get(4);
        this.end_date = args.get(5).split("T")[0]+" 00:00:00";
    }
    public Todo(){
        this.id = -1;
    }
    public String toString(){
        return this.published_date;
    }
}

class User{
    int id;
    String username,email;
    public User(ArrayList<String> u){
        this.email = u.get(0);
        this.id = Integer.parseInt(u.get(1));
        this.username = u.get(2);
    }
    public User(){

    }
}