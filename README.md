# *Genshin Impact* wish tracker

## Optimized wishing

*Genshin Impact*, an open-world RPG game developed by
HoYoverse, has a "gacha" component which is central
to the progression of the game. The term "gacha" 
is derived from *Gashapon* machines in Japan where
a toy is dispensed at random. In the context of
video games, "gacha" refers to a mechanic where in-game
items are obtained at random. *Genshin Impact*
implements this in the form of **wishing**: by using
in-game currency, one is given a random chance to 
obtain exclusive items. Although some items
in *Genshin Impact* are obtained through non-gacha
means, the vast majority must be **wished** for.


 This project will serve to record wishes, differentiate 
 wishes done on each *banner*, and calculate the probability 
 that the next wish will grant a rare item. Players will 
 prefer this tool over the in-game wish history since the 
 in-game implementation is limited in that it will
 lose track of wishes done too long ago. This project is of
 interest to me since the wishing mechanic is complex
 in terms of probability, and the game does not provide the
 tools needed to use the mechanic optimally. I have also 
 lost my wish history to time several times after relying 
 on the in-game wish history.
 

## User Stories

- As a user, I want to be able to add a wish to my wish history
- As a user, I want to be able to view my wish history
- As a user, I want to be able to see the probability that I will
obtain a rare item in my next wish
- As a user, I want to be able to remove a wish from my wish history
in case it was wrongly inputted
- As a user, I want to be able to see how close I am to "pity"
- As a user, I want to be able to see if I am guaranteed to win
the "50/50"
- As a user, I want to be able to see how many "fate points" I have
- As a user, I want to save my wishing history on all three banners to a file
- As a user, I want to be able to load a file containing 
information on my wishing history

## Phase 3: Instructions for Grader

- You can add a wish to a banner by entering the result into the text field, and
pressing the record button
- You can analyze the wishes on a banner by pressing the analyze button
- You can locate the visual component next to the banner option buttons
- You can save the state of my application by pressing the save button
- You can reload the state of my application by pressing the load button

## Phase 4: Task 2

Tue Apr 11 20:22:59 PDT 2023
Added Debate Club to standard banner

Tue Apr 11 20:22:59 PDT 2023
Added Debate Club to standard banner

Tue Apr 11 20:23:01 PDT 2023
Added Debate Club to weapon banner

Tue Apr 11 20:23:03 PDT 2023
Analyzed standard banner

Tue Apr 11 20:23:06 PDT 2023
Added Jean to standard banner

Tue Apr 11 20:23:06 PDT 2023
Analyzed standard banner

Tue Apr 11 20:23:10 PDT 2023
Removed latest wish from standard banner

Tue Apr 11 20:23:12 PDT 2023
Analyzed standard banner

## Phase 4: Task 3

A possible refactoring has to do with the fact that there are three very similar
classes in the UML diagram; namely, the three types of banners that extend Banner.
The program could have been refactored in a way such that Banner would have been
concrete, had an additional field to distinguish which type of banner it was, and 
WishHistory could have had three fields of type Banner. Another option would be
to have a single collection of Banner, with fixed size 3. In this way, it would have
been simpler to implement methods in WishHistory that had to use switch statements,
since it would have been possible to access the separate banner histories without
worrying about accessing the right field.


## Attributes

The code used for displaying the main menu UI was adapted from the 
TellerApp project example.

The code used for the JsonReader and JsonWriter as well as their respective test
classes was adapted from the JsonSerializationDemo project.