# CardBag — An efficient card management platform

## Basic function

·The current version can recognize and manage three types of cards: campus card, bank card and ID card
·By calling the camera to automatically identify the card information, avoid the shortcomings of manual input, such as error-prone and tedious
·All the cards of the user are displayed on the home page for the user to choose

![image](https://github.com/Cod1ngR1der/ActivityLifeCycle_205801/blob/master/app/src/main/res/drawable/github1.gif)![image](https://github.com/Cod1ngR1der/ActivityLifeCycle_205801/blob/master/app/src/main/res/drawable/github3.gif)![image](https://github.com/Cod1ngR1der/ActivityLifeCycle_205801/blob/master/app/src/main/res/drawable/github5.gif)


Click the card to display detailed card information and card pictures

![image](https://github.com/Cod1ngR1der/ActivityLifeCycle_205801/blob/master/app/src/main/res/drawable/github4.gif)


## Subsequent version optimization
### Improve campus card identification effect:

  ·Compared with the end - side recognition, the accuracy of cloud - side recognition is higher

  ·Improve the coupling effect of the two components

### The NFC module is introduced

  In the future, NFC read and simulation function (HCE) will be introduced to enhance the practicability of the platform
  
### Introduce encryption technology

  Encrypt personal information and card photos and store them in the database
