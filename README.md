<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/keanetay624/gds-assignment">
  <picture>
    <source media="(prefers-color-scheme: dark)"  alt="Logo" srcset="./images/money-bill-solid-light.png" width="80" height="80">
    <img src="./images/money-bill-solid-dark.svg" alt="Logo" width="80" height="80">
  </picture>
  </a>
  </div>

<h3 align="center">Employee Salary Management</h3>

  <p align="center">
    Web application designed to make managing employee salaries for Human Resources simple, fun and easy to use.
  </p>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)

The Human Resources department requires a webapp to manage employee salaries. They require a responsive, mobile-first experience
to view, edit and delete existing employee salary details. The app features a file upload feature so users can easily upload a list of employees for batch update or insert.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [![Angular][Angular.io]][Angular-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple example steps.

### Prerequisites

To get the project running, you will need JDK (17), Node, Angular (15.1) and Docker installed.
* JDK<br/>
  <a>https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html</a>
* Node<br/>
  <a>https://nodejs.org/en/download/</a>
* Docker<br/>
  <a>https://www.docker.com/products/docker-desktop/</a>
* Jetbrains IntelliJ IDEA - for running spring boot and docker-compose<br/>
  <a>https://www.jetbrains.com/idea/download/#section=windows</a>
* Angular - after installing node<br/>
  ```sh
  npm install -g @angular/cli
  ```
### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/keanetay624/gds-assignment.git
   ```
2. Install NPM packages
   ```sh
   npm install
   ```

### Starting the Application

1. Startup database containers
   - Two services in docker-compose.yaml need to be started to activate the database.
   - This can be easily achieved by clicking docker-compose up within an IDE like Jetbrains IntelliJ IDEA.
2. Start Spring Boot application
    - Navigate to /src/main/java/com.ecquaria.gds/GdsApplication
    - Click on Run Spring Boot Application.
3. Start Angular application
    - Navigate to /gds-fe/
    - Open up a cmd shell from this path
    - Run the angular command for starting the application
     ```sh
   ng serve
   ```
4. Access the application frontend
    - Open your web browser of choice and navigate to localhost:4200.
    - The application is now ready for use. 


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

### Adding an Employee
To add an employee, click on the Add button above the employees table. Note the following rules for successful employee insertion:
- All fields are required.
- id and login must be unique.
- salary must follow currency format:
  - only numeric characters allowed
  - only up to 2 decimal places allowed
  - only one decimal point is allowed

If the employee to insert passes these validation rules, it will be added to the database after clicking the Save button. To cancel this employee insertion, click on Close.

![Add Employee Button][add-employee-button]<br/>
![Add Employee Screenshot][add-employee]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Better UX When Uploading Large CSV Files
- [ ] UI Localization

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Keane Tay - keanetay@ecquaria.com

Project Link: [https://github.com/github_username/repo_name](https://github.com/keanetay624/gds-assignment)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[add-employee-button]: images/add-button.png
[add-employee]: images/add-employee.PNG
[product-screenshot]: images/app.PNG
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/