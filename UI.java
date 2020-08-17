import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/* JSON LIBRARY */
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
/***************/

public class UI extends Application {

    public static Stage primStage = new Stage();

    public static void main(String argv[]) {
        launch();
    }

    @Override
    public void start(Stage mainStage) {
        loginPage(primStage);
    }

    private static JSONObject GetData(String name) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        try(FileReader reader = new FileReader(name)){
            JSONObject jsonObj = (JSONObject) parser.parse(reader);
            return jsonObj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null; //If all fails
        }
    }

    public static void loginPage(Stage primaryStage){
        primaryStage.setTitle("BNIS .Inc Staff Management");
        GridPane mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(15, 15, 15, 15));
        mainGrid.setHgap(20);

        Text welcome = new Text("Welcome");
        welcome.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        welcome.setTextAlignment(TextAlignment.CENTER);

        Label username = new Label("Username: ");
        Label password = new Label("Password: ");

        TextField userField = new TextField();
        PasswordField passField = new PasswordField();

        Button logIn = new Button("Log In");
        HBox signIn = new HBox();
        signIn.setAlignment(Pos.BOTTOM_CENTER);
        signIn.getChildren().add(logIn);

        mainGrid.add(welcome, 1, 0, 4, 1);
        mainGrid.add(username, 0, 1);
        mainGrid.add(userField, 1, 1);
        mainGrid.add(password, 0, 2);
        mainGrid.add(passField, 1, 2);
        mainGrid.add(signIn, 1, 3);

        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);

        logIn.setOnAction(e -> {
            String usrname = userField.getText();
            String passwrd = passField.getText();

            try {
                JSONArray array = (JSONArray) GetData("loginData.json").get("login");
                assert array != null;  //   Assertion check for my sanity

                //A very linear way of doing this.  I will find a better solution for this if there are any
                for(int i = 0; i < array.size(); ++i){
                    JSONObject obj = (JSONObject) array.get(i);
                    if(usrname.equals(obj.get("uname"))){
                        if(passwrd.equals(obj.get("password"))){
                            if(obj.get("clearance").equals("2")){
                                Entry success = new Entry(GetData("Database.json"), usrname);
                                primaryStage.setTitle("Employee Information");
                                primaryStage.setScene(success.LandingPage()); 
                                primaryStage.show();
                            } else if (obj.get("clearance").equals("1")){
                                //TBD
                            }
                            break;
                        }
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });

        Scene scene = new Scene(mainGrid, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}