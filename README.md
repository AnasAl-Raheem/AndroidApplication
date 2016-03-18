# Android Flight App 

Given a date, origin and destination the app will will provide available
flight itineraires sorted by time or cost. if space is available, the
flights can be booked for user by the user it self or by an admins.


For the app to work:
1.Push the files:
  - Flights.ser
  - Users.ser
  - passwords.txt
  in your emulator or phone to the following dirictory: "/data/data/team.csc207.androidapplication/".
2. If you can't access data/data then run cmd,go to sdk-platforms/ then run adb root.
3. If you don't want to use the provided flights then in Main.launchapp comment out the line that calls on
  Main.LoadFlightInfo() then login as an admin and use uploadinfo to upload your flights to the app.
4. The constants USERS_PATH, FLIGHTS_PATH and PASSWORDS_PATH contain the path that is used to load the app info,
  if you want to put the files somewhere else in your emulator or phone please don't forget to change these constants.
  
NOTE: Users.ser must be loaded so that the app will run.
