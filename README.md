# CS3398--Europa--S2018

Final Review and Retrospective Readme Info

TEAM
	How to build
		-Hello. We have hosted the application for you to easily access it on our linux server. To access it,
		insert http://45.33.13.200:4200/home into your browser and hit enter.
		-Otherwise, you can scroll down to the full installation instructions below.
	WHAT WENT WELL
		-Each team member vastly increased personal knoledge of web development 
		-The team was supportive of other members and gave positive feedback to eachother
		-We completed a finished demo web page that addressed our team goal of a car database webpage
		-The collection of data and the proper frameworks to use for displaying said data
	IMPEDEMENTS
		-Underestimating goals and how long they would take to complete
	IMPROVEMENTS
		-Start with a clear understanding of what skills we need to learn and accomplish them in a timely matter
		-Have more off campus team meetings with clear undertanding of what our sprint goals are



Nathan Hancock
	- Code is in main directory which includes data.sql, carData.db, sqlConvert.py
	
	- This python program added a carData.db file which include basic car database for the site to use
	
	- Measurable improvement was the understanding and integration of a sqlite file on the web page
	
Phillip Tran
	- Code is in main branch, which includes stuff in CS3398--Europa--S2018/frontend/europa-fe/src/app/ (All of the modules besides 	about), as well as the stuff in CS3398--Europa--S2018/backend/client/
	
	- The modules were responsible for creating the main dashboard application for the project as well as the API which was responsible for handling the requests to the database.
	
	- I completed my objectives/improvements for Sprint 2, however, I had to sort of band aid the application. The pages present records from the database in a random sort of way, because there were issues with the preflight headers when trying to post user 	 input to the server. This can be seen in the diceroll area of the application.
	
	Status: Well, the semester is over so I probably will not be working on this project for a while.
	
	Next step: I will be continuously hosting the application on our server for a month period for it to be graded
	








----------------------------------------------------------------------------------------------------------
Supercharged is currently in its alpha state:

Version 0.0.0

To install this:

Clone repo

Make sure you have Node and NPM installed

Execute command: npm install in your console

Execute command: npm start

Optionally if Angular CLI is installed,

Execute command: ng serve

Browse to localhost:4200


--------------------------------------------------------------------------------------------------
Currently Status: Hooked it up

Front end is connected to the backend, backend correctly populates the database

Next thing to work on:

Phillip - Creating actual user functionality, Filtering data, Receiving user inputs

Trenton - I'll be adding pages to the website like the about us, contact us, user login page, advanced search, account page etc.

Fernando - Continue gathering free data sets and merge to create a diverse and extensive dataabase.
		   Follow up on API's referenced by USAA presenter and find alternatives that are free.

Nathan - Added SQLite database and explored flask functions. Next step find api and work on web intigration.

Chase - Help work on database functionality and information/access

--------------------------------------------------------------------------------------------------
											11/7/18
Current Status : The website has more pages added to it, we finally got the database connected to the 
		 website and can start displaying all the data
		 
Next thing to work on:
Phillip - I'll be working more with the Python Application to populate the web page with usable data for the user

Trenton - I'll be working more with the frontend to modify that about us page, I'll also be changing up the header/footer,
	  adding a contact us page (moving the current info from the footer into that page), a sign in page, etc.

Fernando - Pending rates from commercial APIs, I will assist in API implementation

Nathan - Learn how to manipulate .db file to display on website and develope flask functions 

Chase - Expand the existing db files. Then we need to get the website to display more information with queries. Also look into the alternative of an existing API. Can look into method to have user/pass into a users.db
