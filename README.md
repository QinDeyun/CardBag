# CardBag — An efficient card management platform

## 💥Basic function

### ⭕The current version can recognize and manage three types of cards: campus card, bank card and ID card

  ![image](https://github.com/Cod1ngR1der/ActivityLifeCycle_205801/blob/master/app/src/main/res/drawable/github1.gif)

### ⭕By calling the camera to automatically identify the card information, avoid the shortcomings of manual input, such as error-prone and tedious

  ![image](https://github.com/Cod1ngR1der/ActivityLifeCycle_205801/blob/master/app/src/main/res/drawable/github3.gif)

### ⭕All the cards of the user are displayed on the home page for the user to choose

  ![image](https://github.com/Cod1ngR1der/ActivityLifeCycle_205801/blob/master/app/src/main/res/drawable/github5.gif)


### ⭕Click the card to display detailed card information and card pictures

  ![image](https://github.com/Cod1ngR1der/ActivityLifeCycle_205801/blob/master/app/src/main/res/drawable/github4.gif)


## 🤝Subsequent version optimization

### ⭕Improve campus card identification effect:

``` cpp {.line-numbers}
    Compared with the end - side recognition, the accuracy of cloud - side recognition is higher

    Improve the coupling effect of the two components
```

### ⭕The NFC module is introduced

  In the future, NFC read and simulation function (HCE) will be introduced to enhance the practicability of the platform
  
### ⭕Introduce encryption technology

  Encrypt personal information and card photos and store them in the database

## 💥Class functionality

![image](https://user-images.githubusercontent.com/79461685/231776227-034c66d7-c007-46bb-9e44-ac79dfbb05d1.png)

## 🤝Used dependencies
```
  com.loopeer.library:cardstack:1.0.2
  https://github.com/loopeer/CardStackView
```
```
  com.huawei.hms:ml-computer-card-icr-cn:3.7.0.303
  com.huawei.hms:ml-computer-card-bcr:3.7.0.302
  com.huawei.hms:ml-computer-vision-ocr:3.8.0.303
  https://github.com/HMS-MLKit/HUAWEI-HMS-MLKit-Sample
```
```
  com.github.imangazalievm:circlemenu:3.0.0
  https://github.com/ImangazalievM/CircleMenu
```
  

