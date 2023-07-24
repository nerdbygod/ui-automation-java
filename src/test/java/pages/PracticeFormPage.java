package pages;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.Color;

import java.util.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PracticeFormPage {
    String practiceFormPageUrl = "/automation-practice-form";

    // locators
    public SelenideElement studentRegistrationFormTitle = $("h5");
    public SelenideElement studentRegistrationForm = $("#userForm");
    public SelenideElement firstNameInput = $("#firstName");
    public SelenideElement lastNameInput = $("#lastName");
    public SelenideElement emailInput = $("#userEmail");
    public SelenideElement mobileInput = $("#userNumber");
    public SelenideElement dateOfBirthInput = $("#dateOfBirthInput");
    public SelenideElement subjectsInput = $("#subjectsInput");
    public SelenideElement selectedSubjectsValueContainer = $(".subjects-auto-complete__value-container");
    public SelenideElement currentAddressTextarea = $("#currentAddress");
    public SelenideElement stateInput = $("#react-select-3-input");
    public SelenideElement stateSelectorDropdownMenu = $x("//*[@id='state']/div[2]");
    public SelenideElement selectedState = $x("//*[@id='state']/div/div[1]/div[1]");
    public SelenideElement citySelector = $("#city");
    public SelenideElement cityInput = $("#react-select-4-input");
    public SelenideElement citySelectorDropdownMenu = $x("//*[@id='city']/div[2]");
    public SelenideElement selectedCity = $x("//*[@id='city']/div/div[1]/div[1]");
    public ElementsCollection cityMenuItems = $$x("//*[@id='city']/div[2]/div/div");
    public SelenideElement practiceFormMenuItem = $x("//ul[.='Practice Form']");
    public SelenideElement submitButton = $("#submit");
    public SelenideElement submittedFormModal = $(".modal-content");
    public SelenideElement subjectsAutocompleteDropdown = $(".subjects-auto-complete__menu-list");
    public ElementsCollection gendersRadio = $$("[name='gender']");
    public String studentRegistrationFormTitleText = "Student Registration Form";

    public enum Gender {
        MALE("Male"),
        FEMALE("Female"),
        OTHER("Other");

        final String genderString;

        Gender(String genderString) {
            this.genderString = genderString;
        }

        public String getGenderString() {
            return genderString;
        }
    }

    public enum Subject {
        ENGLISH("English"),
        COMPUTER_SCIENCE("Computer Science"),
        MUSIC("Music");

        final String subjectString;

        Subject(String subjectString) {
            this.subjectString = subjectString;
        }

        public String getSubjectString() {
            return subjectString;
        }
    }

    public enum State {
        NCR("NCR"),
        UTTAR_PRADESH("Uttar Pradesh"),
        HARYANA("Haryana"),
        RAJASTHAN("Rajasthan");

        final String stateString;

        State(String stateString) {
            this.stateString = stateString;
        }

        public String getStateString() {
            return stateString;
        }
    }

    public HashMap<State, List<String>> citiesToStatesMapping = new HashMap<>() {{
        put(State.NCR, Arrays.asList("Delhi", "Gurgaon", "Noida"));
        put(State.UTTAR_PRADESH, Arrays.asList("Agra", "Lucknow", "Merrut"));
        put(State.HARYANA, Arrays.asList("Karnal", "Panipat"));
        put(State.RAJASTHAN, Arrays.asList("Jaipur", "Jaiselmer"));
    }};

    public enum Hobby {
        SPORTS("Sports"),
        READING("Reading"),
        MUSIC("Music");
        final String hobbyString;
        Hobby(String hobbyString) {
            this.hobbyString = hobbyString;
        }
        public String getHobbyString() {
            return hobbyString;
        }
    }

    public void goToPracticeFormPage() {
        open("https://demoqa.com/automation-practice-form");
    }

    public void assertPracticeFormPage() {
        practiceFormMenuItem.shouldBe(visible);
        studentRegistrationForm.shouldBe(visible);
        studentRegistrationFormTitle.shouldHave(
                text(studentRegistrationFormTitleText));
    }

    public void selectGender(Gender gender) {
        switch (gender) {
            case MALE -> gendersRadio.get(0).click(ClickOptions.usingJavaScript());
            case FEMALE -> gendersRadio.get(1).click(ClickOptions.usingJavaScript());
            case OTHER -> gendersRadio.get(2).click(ClickOptions.usingJavaScript());
        }
    }

    public void assertFieldsNegativeValidationPresent(SelenideElement... elements) {
        for (SelenideElement e : elements) {
//            Assertions.assertEquals("form-control:invalid", e.getCssValue("class"));
            Color inputBorderColor = Color.fromString(e.getCssValue("border-color"));
            Assertions.assertEquals("#dc3545", inputBorderColor.asHex());
        }
    }

    public void assertFieldsPositiveValidationPresent(SelenideElement... elements) {
        for (SelenideElement e : elements) {
//            Assertions.assertEquals("form-control:valid", e.getCssValue("class"));
            Color inputBorderColor = Color.fromString(e.getCssValue("border-color"));
            Assertions.assertEquals("#28a745", inputBorderColor.asHex());
        }
    }

    public void assertSubjectsAutocompleteDropdownHas(Subject... subjects) {
        for (Subject s : subjects) {
            subjectsAutocompleteDropdown.$x("div[contains(text(), '" + s.getSubjectString() + "')]")
                    .shouldBe(visible);
        }
    }

    public void assertSubjectsAutocompleteDropdownHasNot(Subject... subjects) {
        for (Subject s : subjects) {
            subjectsAutocompleteDropdown.$x("div[contains(text(), '" + s.getSubjectString() + "')]")
                    .shouldNotBe(visible);
        }
    }

    public void pickSubject(Subject s) {
        String subjectToSelect = "div[contains(text(), '" + s.getSubjectString() + "')]";
        subjectsAutocompleteDropdown.$x(subjectToSelect)
                .click(ClickOptions.usingJavaScript());
        subjectsAutocompleteDropdown.shouldNotBe(visible);
        selectedSubjectsValueContainer.shouldBe(visible);
        selectedSubjectsValueContainer.$x("/" + subjectToSelect).shouldBe();
    }

    public void tickHobbiesCheckbox(Hobby... hobbies) {
        for (Hobby h : hobbies)
            $("#hobbies-checkbox-" + (h.ordinal() + 1))
                    .click(ClickOptions.usingJavaScript());
    }

    public void clickStateInput() {
        stateInput.sendKeys("");
        stateInput.press(Keys.SPACE);
    }

    public void pickState(State s) {
        stateSelectorDropdownMenu.$("#react-select-3-option-" + s.ordinal())
                .click(ClickOptions.usingJavaScript());
        stateSelectorDropdownMenu.shouldNotBe(visible);
        selectedState.shouldHave(exactOwnText(s.getStateString()));
    }

    public void clickCityInput() {
        cityInput.sendKeys(" ");
//        cityInput.press(Keys.SPACE);
    }

    public void cityDropdownMenuShouldHaveCitiesMatchingState(State s) {
        List<String> cities = citiesToStatesMapping.get(s);
        Assertions.assertEquals(cityMenuItems.size(), cities.size());
        for (int i = 0; i < cities.size(); i++) {
            cityMenuItems.get(i).shouldHave(exactOwnText(cities.get(i)));
        }
    }

    public void pickCity(String city) {
        citySelectorDropdownMenu.$x("div/div[contains(text(), '" + city + "')]")
                .click(ClickOptions.usingJavaScript());
        citySelectorDropdownMenu.shouldNotBe(visible);
        selectedCity.shouldHave(exactOwnText(city));
    }

}
