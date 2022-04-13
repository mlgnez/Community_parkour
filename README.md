# Community_parkour
A spigot plugin that lets players design parkour courses and upload them to a line of courses. (hi)

<NOT FINISHED>

Long description:

Each player has a set 50 x 100 x 45 area to build a parkour course. When they are done building, they play test their course.
If they are able to complete the play test, the parkour course is saved as a schematic and added to a line of courses.
The building uses a complex plot system where 30 players can be building at a time. When players stop editing or start play testing, their plots are saved a schematic.
When players start editing, the schematic of their previous parkour course is loaded in.

Commands:

/play - sends the player to the start of the line of courses.
/menu - opens a gui menu that has all of the commands as buttons
/edit - player starts editing a course
/publish - player starts play testing, if they finish play testing their plots are added the line
/pause - saves players build and teleports player to the start of the line of courses.
-------------ADMIN COMMANDS--------------
/resetplot [number] (resets a building plot)
/setplotid [player] [number] (sets players plot number, again this is apart of the complicated plot system)
/plotcount (returns a number of currently active plots (basically a dev command left over from testing))
  
Most of this plugin has been completed but certain features like the checkpoints system haven't been added / finished.
I haven't mentioned even half of the complexity this plugin has. Most of it is backend functions and features.
If you were to build this and put it on a server, good luck. (prepare for 31 bedrock boxes.)
If you actually wanted to run this, get in contact with me at bigF#6723 for details on how to get this to work on your server.
