# TrayUtils
## About
TrayUtils is a program, which aims to solve the problem of having too many little tray applications slowing down your computer. It is basically one application, which can load modules. These modules can do almost everything. The Application comes with a custom Gui, which can be evoked with an item in the system tray. The main method of interaction between the user and the modules, or the application, is by using native system keybindings. That means they are accessible no matter what the user is doing. Since the project is kind of open source, it is possible for users, to develop their own modules.
## Features
### Implemented
* Jar Module Loading
* Configuration Gui
* Native Keybindings
* Module System
* Tray Icon
* Multi Operating System Support
* Starting with OS
### Pending
* Gui Customization Options (like themes)
## Version
The current Version is **1.2.1**
## Download
To download and install this application, you should head into the release section of this repo. There should be a previously built jar, able to be executed on every system running Java. If you are on windows, you also have the possiblity to download the windows installer, which installs the program onto your computer with a bundled jre. This much easier than using the jar and is the recommended way of doing it.
## Modules
As of version 1.2, TrayUtils depends on loading external modules for it to have any functions / content. These modules can be downloaded and installed as shown below.
### Download
Modules for this software can be downloaded everywhere. Everyone could develop and publish a Module for this application. That also means, that there may be malware or other non-trustworthy modules available somewhere for download, so please make sure that you trust the souce you are downloading and installing modules from. If you are looking for a set of trustworthy and official modules, you could head over to the official [TrayModules Repository](https://github.com/VirtCode/TrayModules "TrayModules on Github").
### Installation
If you have now downloaded a module you trust, and you have installed the TrayUtils application, you are now ready to install that module. First you should open the app (Click on the icon in the system tray.) and go to *Menu | Settings*. There under advanced you should find (as of Version 1.2) and click a button named *"Open Modules Folder"*. This will take you to the a folder, where you should move your downloaded module to. If you later want to remove a module, just delete the file from the folder (Note that for this, TrayUtils must not be running). After you have added or removed Modules, you can restart TrayUtils by clicking on *Menu | Exit* and starting it again.
## Design Choices
For this program to be what it is, a few design choices have been made. The following few paragraphs try to explain why I made those decisions.
### Java
By looking at the selling points of this program, you might have asked yourself why I chose Java to develop it in. It is widely known, that Java isn't the most resource-efficient programming language out there. So why did I decide to use Java? <br> Firstly, it's the programming language I am most familiar with. But More importantly, it is just very nice to develop an application that can just be run on a number of different operating systems. It is also pretty easy to develop an application, loading external code (like addons), in Java. Programming an Application in Java also doesn't mean, it cannot be programmed with maximal resource-efficiency in mind.
### Module Architecture
One of the primary goals of this application was to shorten system resource usage by many different small tray applications. In order to achieve this, I made a little system to load different Modules from other Jar Files. As of its own, a Module loading System is not very resource efficient and may outcast the original goal to save system resources. But since there is no better way to unite small applications, this is the way I went, and it is still better than having multiple JREs running for multiple small applications.
### GUI
At the beginning of this Project it was intended to have no Gui but only a small system tray popup menu, for configuring the applicaitions / modules. This worked pretty well but it soon became limiting, since a few module ideas reqired a gui or something like that. So I decided to switch to a Gui. For the Gui I used the implemented Java Swing library, since it is already implemented in the *java.desktop* module and was one of the most resource efficient possibilities. I tried to stlye the Gui a bit, for it not to look like a program from the 1990's. This worked out pretty good, though it resulted in a few visual bugs of the user interface. Additionally, the Gui and its Objects are only created on opening the Gui. After closing it, those objects should be collected by the Java Garbage Collection. This is done to optimize Memory usage.
## Resource Usage
As explained by now, this Program aims to be pretty resource efficient. It though isn't noted anywhere how much resources the program uses in practice. And that does have a reason. Because TrayUtils mostly obtains functionality by loading modules, the resource usage really depends on what and how many modules you have loaded. On an average system though, it currently uses about 50 MB of Memory when the gui is closed, and with a few resource-efficient modules loaded. I know that this does not sound too great but it is currently the best I can do.
## Problems and Optimization
If you encounter any problem with the application, feel free to write an issue, or even contributing to the project by yourself. If you want to optimize or help with any other part of the program, you are very welcome to contribute.
## License
This Project is licensed under the Apache-2.0 License. Consider having a look at the LICENSE file for more information.
