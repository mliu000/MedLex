# MedLex Hackcamp 2024 Rebuild

*Brief description*

This project was originally built in a group during HackCamp 2024. Implementation wise, the frontend was implemented React JS, while the backend in C++. However, we then realized that C++ isn't the best choice of language because it requires memory management (making it more prone to bugs), isn't very compatible with JS when it comes to API's. In the short period of time given, we had to implement our C++ backend to run in a polling fashion while the program was running the whole time. It basically checks the json file (the one we use to communicate between front and back end) to repeatedly see if there is any change, and if there is, the C++ runs the program, reducing efficiency. Also, the benefits of the speed of C++ are negligible, since our database of symptoms is very small. 

Therefore, I decided to reimplement the MedLex project using Java as the backend, as it is more compatible with JS. For the API, the choice was SpringBoot. The functionality is exactly the same, with minor edits to the JS to adhere Java and Springboot. Java is also more stable when it comes to exception and error handling. 

For large databases however, it is likely a good idea to still use C++ (even though it is complicated) because the algorithm to retireve the symptoms is computationally heavy. For each input word in the set of imput words (let say size m) you have to iterate through all the symptoms (let's say size n). Therefore, the runtime is be O(mn), which could slightly be faster in C++ due to no garbage collection in the background (although I think the algorithm can still be further optimized).

*How to operate:**

To start the program, navigate to the **frontend** folder in the terminal, then type **npm start**.

*Credits:*

**Group Members:** Ryan Zhang, Rayzell Tjandra, Kevin Zhai (all CS students studying at UBC)
**Original working repo:** https://github.com/rayzelltj/HackCamp2024_MedLex.git 
