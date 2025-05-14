@smoke
Feature: Footer Link and Social Media Navigation

  As a user
  I want to navigate through the footer links and social media icons
  So that I can access different parts of the website and social media pages

  Scenario: Validate multiple footer link redirects correctly
    When I click on the following footer items:
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

  Scenario: Validate social media icon redirects correctly
    When I click on the following social media icons:
      | socialMedia | socialMediaUrlPart |
      | facebook    | facebook           |
      | linkedin    | linkedin           |
      | twitter     | x                  |
      | instagram   | instagram          |
