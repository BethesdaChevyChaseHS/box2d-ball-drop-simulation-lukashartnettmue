[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/jdd_DDPd)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=17728558)
# Goal
Create a ball drop simulation. Become more familiar with box2d. Sample [here](https://mrbrownbcc.github.io/ball-drop-game-solution/)

# Setup steps:
1. Forward port 6080
2. Open Lwjgl3Launcher.java. This file contains the main method you will use to test your code. 

# Additional Resources:
[Box2d with libgdx](https://libgdx.com/wiki/extensions/physics/box2d#initialization)
If this is blocked on school Wifi, a pdf copy is available on Canvas. 

# Programming Instructions:
0. Go to core > src > Main to write your code. 
1. Add the pegs to your code by filling in the addAllPegs method. Use the addPeg() helper function. While not required, I do recommend that you 
use the constants provided at the top of the class (ROW_NUM, BALL_SIZE , etc. ). Your pegs should form a triangle shape similar to the example provided. 
2. Change gravity and bounciness to make the simulation more interesting - currently these values are not great. 
3. Add something cool! Shake things up a little bit, add your own obstacle that the balls will drop through. You can shrink the peg triangle area in order to make room for anything you would like to add. 
4. Share! Publish, then share your link on Canvas. Submit once in the discussion AND once in the all task assignment. 

# Testing:
To test your code, interact with the GUI you create! Click run with the file Lwjgl3Launcher.java open. This can be found in /lwjgl3/src/main/java/bcc/snowman/lwjgl3.
The running version of your project will be available at the forwarded address from port 6080. 

# Publishing:
Run 
```
1) npm run build
2) npm i gh-pages
3) npm run deploy
```

# Submission:
To submit, publish your project on github sites. Then share the link in the canvas discussion and the all task assignment. As a best practice, please save your work as well:
```
1) git add . 
2) git commit -m "Saving my code, because it is cool and I don't want it to dissapear when this codespace is deleted."
3) git push
```
This will save your project forever and ever, so that it is not deleted with the codespace you are currently using.