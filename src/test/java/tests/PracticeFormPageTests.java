package tests;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import components.DatePicker;
import components.DatePicker.Month;
import components.SubmittedFormModal;
import config.BaseTest;
import org.junit.jupiter.api.Test;
import pages.PracticeFormPage;
import pages.PracticeFormPage.Hobby;
import pages.PracticeFormPage.Gender;
import pages.PracticeFormPage.Subject;
import pages.PracticeFormPage.State;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PracticeFormPageTests extends BaseTest {
    PracticeFormPage practiceFormPage = new PracticeFormPage();
    DatePicker datePicker = new DatePicker();
    SubmittedFormModal submittedFormModal = new SubmittedFormModal();
    private final String formattedBirthDate = LocalDate.of(1995, 1, 12)
            .format(DateTimeFormatter.ofPattern("dd MMM yyyy", new Locale("EN", "US")));
    private final String formattedBirthDateInModal = LocalDate.of(1995, 1, 12)
            .format(DateTimeFormatter.ofPattern("dd MMMM,yyyy", new Locale("EN", "US")));

    LocalDateTime time = LocalDateTime.now();
    private final String currentTime = time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));
    private final String testFirstName = "Test first name " + currentTime;
    private final String testLastName = "Test last name " + currentTime;
    private final String testEmail = "test_email_" + currentTime + "@qa.qa";
    private final String testMobile = "8005553535";
    private final String currentAddress = "Moskva, ulitsa Pushkina, dom Kolotushkina";
    private final State stateToSelect = State.UTTAR_PRADESH;
    private final String cityToSelect = practiceFormPage.citiesToStatesMapping.get(stateToSelect).get(0);

    @Test
    void checkSubmitFormWithoutData() {
        practiceFormPage.goToPracticeFormPage();

        // the lines below are not to be used in a real test
        switchTo().window("DEMOQA");
        executeJavaScript("document.body.style.zoom='85%'");

        practiceFormPage.assertPracticeFormPage();

        practiceFormPage.submitButton.click(ClickOptions.usingJavaScript());
        Selenide.sleep(500);
        practiceFormPage.assertFieldsNegativeValidationPresent(practiceFormPage.firstNameInput, practiceFormPage.lastNameInput, practiceFormPage.mobileInput);
        practiceFormPage.submittedFormModal.shouldNotBe(visible);
    }

    @Test
    void checkSubmitFormWithValidData() {
        practiceFormPage.goToPracticeFormPage();
        removeAds();

        // the lines below are not to be used in a real test
        executeJavaScript("document.body.style.zoom='80%'");

        practiceFormPage.firstNameInput.sendKeys(testFirstName);
        practiceFormPage.lastNameInput.sendKeys(testLastName);
        practiceFormPage.emailInput.sendKeys(testEmail);
        practiceFormPage.selectGender(Gender.MALE);
        practiceFormPage.mobileInput.sendKeys(testMobile);
        practiceFormPage.dateOfBirthInput.click(ClickOptions.usingJavaScript());

        datePicker.pickYear(1995);
        datePicker.pickMonth(Month.January);
        datePicker.pickDay(12, Month.January, 1995);
        datePicker.assertDatePickerDisappeared();

        practiceFormPage.dateOfBirthInput.shouldHave(Condition.attribute("value", formattedBirthDate));

        // pick subject
        practiceFormPage.subjectsInput.sendKeys("En");
        practiceFormPage.subjectsAutocompleteDropdown.shouldBe(visible);
        practiceFormPage.assertSubjectsAutocompleteDropdownHas(Subject.ENGLISH, Subject.COMPUTER_SCIENCE);
        practiceFormPage.assertSubjectsAutocompleteDropdownHasNot(Subject.MUSIC);
        practiceFormPage.pickSubject(Subject.ENGLISH);
        practiceFormPage.subjectsInput.sendKeys("En");
        practiceFormPage.assertSubjectsAutocompleteDropdownHasNot(Subject.ENGLISH);
        practiceFormPage.pickSubject(Subject.COMPUTER_SCIENCE);

        // select hobbies
        practiceFormPage.tickHobbiesCheckbox(Hobby.SPORTS, Hobby.MUSIC);
        practiceFormPage.currentAddressTextarea.sendKeys(currentAddress);

        // pick state
        practiceFormPage.cityInput.shouldBe(disabled);
        practiceFormPage.clickStateInput();
        practiceFormPage.stateSelectorDropdownMenu.shouldBe(visible);
        practiceFormPage.pickState(stateToSelect);

        // pick city
        practiceFormPage.clickCityInput();
        practiceFormPage.citySelectorDropdownMenu.shouldBe(visible);
        practiceFormPage.cityDropdownMenuShouldHaveCitiesMatchingState(stateToSelect);
        practiceFormPage.pickCity(cityToSelect);

        practiceFormPage.submitButton.click(ClickOptions.usingJavaScript());

        // assert data has been submitted correctly
        submittedFormModal.studentName.shouldHave(exactOwnText(testFirstName + " " + testLastName));
        submittedFormModal.studentEmail.shouldHave(exactOwnText(testEmail));
        submittedFormModal.gender.shouldHave(exactOwnText(Gender.MALE.getGenderString()));
        submittedFormModal.mobile.shouldHave(exactOwnText(testMobile));
        submittedFormModal.dateOfBirth.shouldHave(exactOwnText(formattedBirthDateInModal));
        submittedFormModal.subjects.shouldHave(exactOwnText(String.join(", ",
                Subject.ENGLISH.getSubjectString(), Subject.COMPUTER_SCIENCE.getSubjectString())));
        submittedFormModal.hobbies.shouldHave(exactOwnText(String.join(", ",
                Hobby.SPORTS.getHobbyString(), Hobby.MUSIC.getHobbyString())));
        submittedFormModal.address.shouldHave(exactOwnText(currentAddress));
        submittedFormModal.stateAndCity.shouldHave(exactOwnText(String.format("%s %s",
                stateToSelect.getStateString(), cityToSelect)));

        executeJavaScript("arguments[0].click();", submittedFormModal.closeButton);
        submittedFormModal.modalWindow.shouldNotBe(visible);
    }
}