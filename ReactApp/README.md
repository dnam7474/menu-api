# React Project

This project works in conjuntion wtih [Dinner & a Movie Java Server](https://github.com/stevshil/daam-server-java).

This is an example only and not to be taken literaly.

There are many ways to acheive this project.

# Running this app

You will need to have your own Java servers that will provide the relevant APIs for his application.

There are 2 servers that can be ran with this;

* The authentication server
    * This listens on port **9000** by default
    * The URL to login would be **http://localhost:9000/login**
        * This is a POST request and returns a plain text JSON Web Token
        * The application will use this JWT for the main page **/** or **Dinner** tab.
            * You can use this tab and the **Login** tab to check security with your API
            * No other pages have been coded to work with AUTH.
* The resource server
    * This listens on port **8080** by default
    * The URLs are specified in the design document
    * This server can be ran in both secure and unsecure mode
        * DAAM_SEC is an environment variable that can be set on the command line prior to the application running.  It is set to **true** by default.
            * PowerShell
                ```$env:DAAM_SEC=false```
            * Command Prompt
                ```set DAAM_SEC=false```
            * Linux
                ```export DAAM_SEC=false```

The above instructions are only required if you are running the JAVA API service to demo this front end.

## Development project

If you are on a training course where you are required to develop the Java API for this front end, then you will receive the documentation required to create the APIs necessary for this applicaiton.

## Running this app

```
npm run dev
```

The console will show the URL to access your app.

### Configuration

The **vite.config.ts** file contains essential configuration information to connect to the 2 Java servers.

```
proxy: {
      '/api': 'http://localhost:8080',
      '/login': 'http://localhost:9000',
    },
```

These are the proxies that allow React to redirect calls to /api and /login to the correct servers.

Change these accordingly.

There is also a **.env** file with 2 environment variables for TAX and TIP rates.