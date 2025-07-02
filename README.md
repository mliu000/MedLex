# 🩺 MedLex – HackCamp 2024 Rebuild

A full-stack medical symptom search tool originally developed at **HackCamp 2024** and later reimplemented for better architecture and efficiency and stability.

> Full original repo: [HackCamp2024_MedLex](https://github.com/rayzelltj/HackCamp2024_MedLex.git)

## Project Background

- **Frontend**: React.js  
- **Original Backend**: C++ (polling-based JSON communication)

### C++ Limitations Encountered:

- Manual memory management → prone to bugs under time constraints
- Poor JS integration → no clean API interface
- Required constant polling of a shared `.json` file to detect changes to trigger C++ backend to run.
- C++ speed benefits were negligible due to the small symptom dataset and polynomial time algorithm.


## Rebuild:

To address these issues, I reimplemented the backend using **Java + Spring Boot**, while keeping the **frontend nearly unchanged**.

### Improvements with Java:

- Built a clean, RESTful API to replace JSON polling from C++ backend, which is error prone.
- Code is more maintainable and scalable


## Performance Consideration:

The core algorithm performs:

- For each input word (`m`), iterate through all symptom entries (`n`)
- **Runtime: `O(mn)`**

While Java handles this well for small/medium datasets, **C++ could still be beneficial** for extremely large inputs due to:

- Manual memory control
- No garbage collection overhead

The algorithm itself can still be further optimized regardless of language.

## Screenshots:

<img src="Readme_images/Screenshot 2025-05-20 133510.png" alt="Search Result View" width="400"/><br>
<img src="Readme_images/Screenshot 2025-05-20 133536.png" alt="Input View" width="400"/>

## Setup instructions:

### Requirements:

- Make sure you have ```Node Package Manager (npm)``` and ```Node.js``` installed on local computer.

### Steps to run the application:

- Clone the repository
- In ```/frontend```, type ```npm start```. The React.js UI will launch shortly.