import org.json.simple.JSONObject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

class Employee {

    String name;
    String position;
    int age;
    int salary;

    Employee(JSONObject info, String loginName){
        JSONObject person = (JSONObject) info.get(loginName);
        this.name = (String) person.get("name");
        this.position = (String) person.get("position");
        this.age = Integer.parseInt((String) person.get("age"));
        this.salary = Integer.parseInt((String) person.get("salary"));
    }

}

class Manager extends Employee{

    Manager(JSONObject info, String logIn){
        super(info, logIn);
    }
}


/*  An entry level employee can only view information, not edit. 
    Pretty basic stuff like displaying information on the current
    logged in Employee
*/

class Entry extends Employee{

    Entry(JSONObject info, String logIn){
        super(info, logIn);
    }

    public Scene LandingPage(){
        Text welcome = new Text("Welcome " + this.name);
        welcome.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        welcome.setTextAlignment(TextAlignment.CENTER);

        Label name = new Label("Name: " + this.name);
        Label position = new Label("Current Position: " + this.position);
        Label age = new Label("Age: " + Integer.toString(this.age));
        Label salary = new Label("Salary (U.S Dollars): " + Integer.toString(this.salary));

        HBox btn = new HBox();
        Button signOut = new Button("Sign out");
        btn.getChildren().addAll(signOut);
        btn.setAlignment(Pos.CENTER_RIGHT);

        signOut.setOnAction(e -> {
            UI.loginPage(UI.primStage); //Static methods so it works
        });

        VBox body = new VBox();
        body.setSpacing(10);
        body.setPadding(new Insets(10,20,10,20));

        body.getChildren().addAll(welcome,name,position,age,salary, btn);
        body.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(body, 400, 600);
        return scene;
    }
}