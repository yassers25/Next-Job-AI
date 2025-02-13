# 🚀 **Job Data Analysis Application**

## Table of Contents

- [Introduction](#introduction)
- [Web Scraping with Jsoup](#web-scraping-with-jsoup)
- [Graphical User Interface with JavaFX](#graphical-user-interface-with-javafx)
- [Machine Learning with Weka](#machine-learning-with-weka)

---

## Introduction 🌟

The primary goal of this project is to develop a mini-application that integrates multiple advanced Java libraries to achieve a blend of web scraping, user interactivity, and machine learning capabilities. This application focuses on three main components:

- **Jsoup**: For efficient web scraping of job postings from employment websites.
- **JavaFX**: For designing a responsive and user-friendly graphical user interface (GUI).
- **Weka**: For performing predictive analysis and data categorization on the collected job information.

This project demonstrates the seamless integration of these technologies to create a robust tool for analyzing job market trends and patterns.

---

## Web Scraping with Jsoup 🕸️

### Overview
Jsoup is a powerful Java library designed for parsing and manipulating HTML documents. In this project, Jsoup is used to extract job postings from employment websites such as **rukute.com**, **mjob.ma**, and **emploi.ma**. Its key features include:

- Efficient extraction of job titles, descriptions, company names, and locations.
- Mimicking browser behavior by managing cookies and HTTP headers.
- Handling dynamic or nested HTML structures.

### Implementation
A multi-threaded approach was implemented to fetch and parse job data efficiently using Jsoup’s capabilities. This ensures scalability and accuracy in data collection.

---

## Graphical User Interface with JavaFX 💻

### Design and Features
The GUI was developed using **JavaFX**, an open-source platform for building rich client applications. Key features include:

- An intuitive dashboard for simplicity and efficiency.
- Buttons for initiating web scraping, viewing charts, and running machine learning-powered detections.

### Usage
Users can interact with the app through the following functionalities:
- **Scrape Button**: Collect job postings from selected job sites.
- **View Charts Button**: Explore visual representations of the data, such as graphs or statistics.
- **Detect Button**: Use machine learning algorithms to identify specific attributes or make predictions based on the extracted data.

This interactive setup ensures that all core functionalities are easily accessible, making the app user-friendly and effective.

---

## Machine Learning with Weka 📊

### Overview of Weka
Weka is a collection of machine learning and data analysis tools licensed under the GNU General Public License. Developed at the University of Waikato, New Zealand, Weka provides:

- A collection of visualization tools and algorithms for data analysis and predictive modeling.
- Graphical user interfaces for easy access to its functions.

### Implementation in the Project
The project integrates Weka to classify and analyze job data extracted via web scraping. Using this library allows us to:

- Process the data effectively.
- Train multiple machine learning models.
- Display predictions and insights.

This integration showcases the potential of combining web scraping, GUI development, and machine learning in real-world scenarios.
