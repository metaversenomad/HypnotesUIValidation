@smoke
Feature: Footer Link and Social Media Navigation

  As a user
  I want to navigate through the footer links and social media icons
  So that I can access different parts of the website and social media pages

  Background:
    Given I open the "https://hypnotes.net" homepage

  # Footer linklerinin testi
  Scenario Outline: Validate footer link redirects correctly
    When I click on the "<footerItem>" footer item
    Then I should be redirected to a page containing "<expectedUrlPart>"

    Examples:
      | footerItem                                | expectedUrlPart                |
      | Product Demo                              | youtube                        |
      | FAQs                                      | faq                            |
      | Blog                                      | blog                           |
      | Find a Therapist                          | therapist                      |
      | How Does Hypnotes Work?                   | how-it-works                   |
      | Dashboard & Reporting                     | get-organized                  |
      | Client Portal                             | client-patient-portal          |
      | Biofeedback/Emotion-Detection             | biofeedback                    |
      | Handwriting to Text Conversion            | textconversion                 |
      | About Us                                  | about-us                       |
      | Privacy Policy                            | privacy-policy                 |
      | Terms Of Service                          | terms-of-service               |
      | Contact Us                                | contact-us                     |
      | Calendar                                  | therapy-appointment-scheduling |
      | Services                                  | customize-service              |
      | eSign and Document Storage                | esign                          |
      | Billing and Invoicing                     | billing                        |
      | Free Secure Telehealth Video Conferencing | telehealth                     |

  # Sosyal medya ikonları testi
  Scenario Outline: Validate social media icon redirects correctly
    When I click on the "<socialMedia>" social media icon
    Then I should be redirected to a page containing "<socialMediaUrlPart>"

    Examples:
      | socialMedia | socialMediaUrlPart |
      | facebook    | facebook           |
      | linkedin    | linkedin           |
      | twitter     | x                  |
      | instagram   | instagram          |
