# MarkingMate
MarkingMate is a cross-platform desktop application for marking and providing feedback on assessments. MarkingMate has two unique features:
* When you're marking an answer, it also displays the feedback and marks for other answers to the same question. 
* It supports sentence-level text completion (<kbd>Ctrl</kbd>+<kbd>Space</kbd>) to avoid re-typing feedback that applies to many answers. 

When you're done marking, you can export your marks into a CSV file and into individual text files with feedback for every student.

## Screenshots
![](https://i.imgur.com/YJhwNOV.png)
![](https://i.imgur.com/yzkFqy1.png)

## Installation
* Mac OSX: Download and run the DMG installer (MarkingMate.dmg) from the [Releases](https://github.com/kolovos/markingmate/releases) page.
* Windows/Linux: Download MarkingMate.zip from the [Releases](https://github.com/kolovos/markingmate/releases) page, extract its contents, and then double-click markingmate.jar

## File Format
MarkingMate doesn't provide a user interface for creating students and questions. As such you will have to set up your marking file using a text editor (give it any filename/extension you like) and then open it with MarkingMate to do your marking. The format is (almost) self-explanatory:

```xml
<?xml version="1.0" encoding="ASCII"?>
<Exam xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns="markingmate">
  <questions title="Question 1" marks="20"/>
  <questions title="Question 2" marks="15"/>
  <questions title="Question 3" marks="15"/>
  <questions title="Question 4" marks="25"/>
  <questions title="Question 5" marks="25"/>
  <students number="Y3851704"/>
  <students number="Y3851705"/>
  <students number="Y3851706"/>
  <students number="Y3851707"/>
  <students number="Y3851708"/>
</Exam>
```

## Custom marks and feedback generator
The `<Exam>` tag also accepts a `generator` attribute which can point to the absolute path of an [EGL](https://www.eclipse.org/epsilon/doc/articles/code-generation-tutorial-egl/) generator (.egx file) which MarkingMate should use instead of its built-in generator when it exports feedback. For inspiration, you can have a look at the [built-in generator](https://github.com/kolovos/markingmate/blob/master/io.dimitris.markingmate/resources/feedback.egx), which produces one CSV file with all the marks and one text file with feedback per student.
