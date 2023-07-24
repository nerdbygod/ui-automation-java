package components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class SubmittedFormModal {
    public SelenideElement modalWindow = $(".modal-content");
    public SelenideElement getSubmittedFormModalTitle = $("#example-modal-sizes-title-lg");
    public SelenideElement studentName = $("tbody > tr:nth-of-type(1) > td:nth-of-type(2)");
    public SelenideElement studentEmail = $("tbody > tr:nth-of-type(2) > td:nth-of-type(2)");
    public SelenideElement gender = $("tbody > tr:nth-of-type(3) > td:nth-of-type(2)");
    public SelenideElement mobile = $("tbody > tr:nth-of-type(4) > td:nth-of-type(2)");
    public SelenideElement dateOfBirth = $("tbody > tr:nth-of-type(5) > td:nth-of-type(2)");
    public SelenideElement subjects = $("tbody > tr:nth-of-type(6) > td:nth-of-type(2)");
    public SelenideElement hobbies = $("tbody > tr:nth-of-type(7) > td:nth-of-type(2)");
    public SelenideElement address = $("tbody > tr:nth-of-type(9) > td:nth-of-type(2)");
    public SelenideElement stateAndCity = $("tbody > tr:nth-of-type(10) > td:nth-of-type(2)");
    public SelenideElement closeButton = $("#closeLargeModal");
}
