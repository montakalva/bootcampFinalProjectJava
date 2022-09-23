package com.example.housemanagementsystem.users;

import com.example.housemanagementsystem.SceneController;
import com.example.housemanagementsystem.database.DataRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class UserController implements Initializable {

    public Text profileTitleText;
    public Text userInfoTitleText;
    public Text changeEmailTitleText;
    public Text changePasswordTitleText;
    public Text changePhoneNumberTitleText;
    public ImageView image1;
    public ImageView image2;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField apartmentField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;
    @FXML
    private TextField newPhoneNumberField;
    @FXML
    private TextField newEmailField;

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, Integer> userIDColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> apartmentNoColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> phoneNumberColumn;

    UserRepository userRepository = new UserRepository();

    public UserController() {
    }

    @FXML
    protected void onUserLoginClick(ActionEvent actionEvent) {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String password = passwordField.getText();

            int userID = this.userRepository.verifyLoginData(firstName, lastName, password);
            User user = this.userRepository.getUserByID(userID);
            UserType userType = this.userRepository.checkUserType(userID);
            DataRepository.getInstance().setLoggedInUserID(userID);
            DataRepository.getInstance().setLoggedInUser(user);

            if (userType == UserType.MANAGER) {
                SceneController.changeScene(actionEvent, "manager_profile");
            } else if (userType == UserType.OWNER) {
                SceneController.changeScene(actionEvent, "owner_profile");
            }

            SceneController.showAlert("Login successful", "You have logged in successfully!", Alert.AlertType.CONFIRMATION);

        } catch (Exception e) {
            SceneController.showAlert("Login failed", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void navigateToScene(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        SceneController.changeScene(actionEvent, source.getId());
    }

    public void onRegisterNewOwnerClick(ActionEvent actionEvent) {
        try {
            User user = new User(
                    null,
                    apartmentField.getText(),
                    UserType.OWNER,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    passwordField.getText(),
                    emailField.getText(),
                    phoneField.getText()
            );

            this.validateOwnerRegistrationInfo(user);
            this.userRepository.registerOwner(user);

            SceneController.showAlert("Registration successful!",
                    "Apartment owner " + firstNameField.getText() + " " + lastNameField.getText() + " has been registered successfully!",
                    Alert.AlertType.CONFIRMATION);

            SceneController.changeScene(actionEvent, "manager_profile");

        } catch (Exception e) {
            SceneController.showAlert("Registration failed!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void validateOwnerRegistrationInfo(User user) throws Exception {

        if (user.getFirstName().isEmpty()) throw new Exception("Please provide owner's first name!");
        if (!user.getFirstName().matches("[a-zA-Z]*")) throw new Exception("Please provide valid first name!");
        if (user.getLastName().isEmpty()) throw new Exception("Please provide owner's last name!");
        if (!user.getLastName().matches("[a-zA-Z]*")) throw new Exception("Please provide valid last name!");
        if (user.getPassword().isEmpty()) throw new Exception("Please choose owner's password!");
        if (user.getPassword().length() < 6) throw new Exception("Password must contain at least 6 characters!");
        if (!user.getPassword().equals(confirmPasswordField.getText())) throw new Exception("Password does not match!");
        if (user.getApartmentNo().isEmpty()) throw new Exception("Please choose the number of the apartment!");
        if (!user.getApartmentNo().matches("[0-9]*")) throw new Exception("Please provide valid apartment number!");
        if (Integer.parseInt(user.getApartmentNo()) < 1 || Integer.parseInt(user.getApartmentNo()) > 15)
            throw new Exception("Please choose valid apartment number (from 1 to 15)!");
        if (user.getEmail().isEmpty()) throw new Exception("Please provide owner's e-mail address!");
        if (!user.getEmail().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"))
            throw new Exception("Please provide valid e-mail address!");
        if (user.getPhoneNumber().isEmpty()) throw new Exception("Please provide owner's phone number!");
        if (user.getPhoneNumber().length() < 8)
            throw new Exception("Please provide valid phone number! Phone number must consist of at least 8 digits!");
        if (!user.getPhoneNumber().matches("[0-9]*")) throw new Exception("Please provide valid phone number!");
    }

    private void validatePasswordInfo(User user, String password, String newPassword, String confirmNewPassword) throws Exception {

        if (password.isEmpty()) throw new Exception("Please provide your password!");
        if (!user.getPassword().equals(password)) throw new Exception("Password is incorrect!");
        if (newPassword.length() < 6) throw new Exception("Password must contain at least 6 characters!");
        if (confirmNewPassword.isEmpty()) throw new Exception("Please confirm your new password!");
        if (!newPassword.equals(confirmNewPassword)) throw new Exception("Password does not match!");
    }

    private void validatePhoneNumberChangeInfo(User user, String newPhoneNumber, String password) throws Exception {

        if (newPhoneNumber.isEmpty()) throw new Exception("Please provide updated phone number!");
        if (!newPhoneNumber.matches("[0-9]*")) throw new Exception("Please provide valid phone number!");
        if (newPhoneNumber.length() < 8)
            throw new Exception("Please provide valid phone number! Phone number must consist of at least 8 digits!");
        if (password.isEmpty()) throw new Exception("Please provide your password!");
        if (!user.getPassword().equals(password)) throw new Exception("Password is not correct!");
    }

    private void validateEmailChangeInfo(User user, String newEmail, String password) throws Exception {

        if (newEmail.isEmpty()) throw new Exception("Please provide updated e-mail address!");
        if (!newEmail.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"))
            throw new Exception("Please provide valid e-mail address!");
        if (password.isEmpty()) throw new Exception("Please provide your password!");
        if (!user.getPassword().equals(password)) throw new Exception("Password is not correct!");
    }

    private void validateDeletingOwnerInfo(User user, String firstName, String lastName, String apartmentNo, String password) throws Exception {

        if (firstName.isEmpty()) throw new Exception("Please provide owner's first name!");
        if (!firstName.matches("[a-zA-Z]*")) throw new Exception("Please provide valid first name!");
        if (lastName.isEmpty()) throw new Exception("Please provide owner's last name!");
        if (!lastName.matches("[a-zA-Z]*")) throw new Exception("Please provide valid last name!");
        if (apartmentNo.isEmpty()) throw new Exception("Please provide owner's apartment number!");
        if (!apartmentNo.matches("[0-9]*")) throw new Exception("Please provide valid apartment number!");
        if (Integer.parseInt(apartmentNo) < 1 || Integer.parseInt(apartmentNo) > 15)
            throw new Exception("Please choose valid apartment number (from 1 to 15)!");
        if (password.isEmpty()) throw new Exception("Please enter your password!");
        if (!user.getPassword().equals(password)) throw new Exception("Password is incorrect!");
    }

    public void onChangePasswordClick(ActionEvent actionEvent) {

        try {
            String password = passwordField.getText();
            String newPassword = newPasswordField.getText();
            String confirmNewPassword = confirmNewPasswordField.getText();

            User user = DataRepository.getInstance().getLoggedInUser();
            Integer userID = DataRepository.getInstance().getLoggedInUserID();

            this.userRepository.verifyPassword(userID, password);
            this.validatePasswordInfo(user, password, newPassword, confirmNewPassword);
            this.userRepository.updatePassword(userID, newPassword);
            DataRepository.getInstance().setLoggedInUser(this.userRepository.getUserByID(userID));

            UserType userType = this.userRepository.checkUserType(userID);

            SceneController.showAlert("Password change successful", "You have changed your password successfully!", Alert.AlertType.CONFIRMATION);

            if (userType == UserType.MANAGER) {
                SceneController.changeScene(actionEvent, "manager_profile");
            } else if (userType == UserType.OWNER) {
                SceneController.changeScene(actionEvent, "owner_profile");
            }

        } catch (Exception e) {
            SceneController.showAlert("Password change failed!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void onUpdatePhoneNumberClick(ActionEvent actionEvent) {
        try {
            String newPhoneNumber = newPhoneNumberField.getText();
            String password = passwordField.getText();

            User user = DataRepository.getInstance().getLoggedInUser();
            Integer userID = DataRepository.getInstance().getLoggedInUserID();

            validatePhoneNumberChangeInfo(user, newPhoneNumber, password);
            this.userRepository.verifyPassword(userID, password);
            this.userRepository.updatePhoneNumber(userID, newPhoneNumber);
            UserType userType = this.userRepository.checkUserType(userID);

            SceneController.showAlert("Phone number change successful", "You have updated your phone number successfully!", Alert.AlertType.CONFIRMATION);

            if (userType == UserType.MANAGER) {
                SceneController.changeScene(actionEvent, "manager_profile");
            } else if (userType == UserType.OWNER) {
                SceneController.changeScene(actionEvent, "owner_profile");
            }
        } catch (Exception e) {
            SceneController.showAlert("Phone number change failed!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void onUpdateEmailClick(ActionEvent actionEvent) {
        try {
            String newEmail = newEmailField.getText();
            String password = passwordField.getText();

            User user = DataRepository.getInstance().getLoggedInUser();
            Integer userID = DataRepository.getInstance().getLoggedInUserID();

            validateEmailChangeInfo(user, newEmail, password);
            this.userRepository.verifyPassword(userID, password);
            this.userRepository.updateEmailAddress(userID, newEmail);

            UserType userType = this.userRepository.checkUserType(userID);

            SceneController.showAlert("E-mail address change successful", "You have updated your e-mail address successfully!", Alert.AlertType.CONFIRMATION);

            if (userType == UserType.MANAGER) {
                SceneController.changeScene(actionEvent, "manager_profile");
            } else if (userType == UserType.OWNER) {
                SceneController.changeScene(actionEvent, "owner_profile");
            }

        } catch (Exception e) {
            SceneController.showAlert("E-mail address change failed!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void onDeleteOwnerClick(ActionEvent actionEvent) {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String apartmentNo = apartmentField.getText();
            String password = passwordField.getText();

            User user = DataRepository.getInstance().getLoggedInUser();
            Integer userID = DataRepository.getInstance().getLoggedInUserID();

            validateDeletingOwnerInfo(user, firstName, lastName, apartmentNo, password);
            this.userRepository.verifyPassword(userID, password);
            this.userRepository.deleteOwner(firstName, lastName, apartmentNo);

            UserType userType = this.userRepository.checkUserType(userID);

            SceneController.showAlert("Deleting owner successful", "You have deleted apartment owner successfully!", Alert.AlertType.CONFIRMATION);

            if (userType == UserType.MANAGER) {
                SceneController.changeScene(actionEvent, "manager_profile");
            } else if (userType == UserType.OWNER) {
                SceneController.changeScene(actionEvent, "owner_profile");
            }

        } catch (Exception e) {
            SceneController.showAlert("Deleting apartment owner failed!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void onGoBackClick(ActionEvent actionEvent) throws Exception {

        Integer userID = DataRepository.getInstance().getLoggedInUserID();
        UserType userType = this.userRepository.checkUserType(userID);

        if (userType == UserType.MANAGER) {
            SceneController.changeScene(actionEvent, "manager_profile");
        } else if (userType == UserType.OWNER) {
            SceneController.changeScene(actionEvent, "owner_profile");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeUserTable();
        } catch (Exception exception){
            System.out.println("Problem with initialize method");
            exception.printStackTrace();
        }
    }

    @FXML
    public void initializeUserTable() {
        try {
            userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
            apartmentNoColumn.setCellValueFactory(new PropertyValueFactory<>("apartmentNo"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

            usersTable.setItems(this.userRepository.getAllOwnersFromDB());

        }catch (Exception e){
        }
    }
}