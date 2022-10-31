# Stampli DevOps Assignment

The goal of this assignment is to create a full working environment for serving to the public two backend micro services and one frontend service.
The backend and frontend code is part of this repository. 


# backend_1

This backend was developed in java (with spring boot).
It should be open to the internet because the frontend communicates with it.
backend_1 should communicate with backend_2 and grab data from it.
The address for backend_2 should be stored in the parameter store under the key "networking" (string).
>install command: **mvn install**
>run command: **java -jar ./target/backend_1-0.0.1-SNAPSHOT.jar**

# backend_2
This backend was developed in node.js.
It should not be available from the internet and only backend_1 should be able to communicate with it.
>install command: **npm install**
>run command: **node index.js**

# frontend_1
This frontend was developed in react.js.
>install command: **npm install**
>build command: **npm run build**
>location of the static files: **build**
