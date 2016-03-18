push the files: Flights.ser, Users.ser and passwords.txt in your emulators to the following dirictory: "/data/data/team.csc207.androidapplication/".
if you can't access data/data then run cmd,go to sdk-platforms/ then run adb root
if you don't want to use the provided flights then in Main.launchapp comment out the line that calls on Main.LoadFlightInfo() then login as an admin and use uploadinfo to upload your flights to the app.
Users.ser must load so that the app will run.
the constants USERS_PATH, FLIGHTS_PATH and PASSWORDS_PATH contain the path that is used to load the app info, if you want to put the files somewhere else in your emulator or phone please don't forget to change these constants.