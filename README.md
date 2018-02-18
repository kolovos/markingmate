# MarkingMate
MarkingMate is a Mac OSX tool for marking open assessments. The main novelty of MarkingMate is that when you're marking an answer, it also displays the feedback and marks you have given to other students for the same question, for consistency. It also supports sentence-level text completion (<kbd>Ctrl</kbd>+<kbd>Space</kbd>) to avoid re-typing comments. When you're done marking you can export marks as a CSV file as well as individual text files with feedback for every student.

## Installation
You can download a DMG installer from the [Releases](https://github.com/kolovos/markingmate/releases) page.

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

## Screenshot
![](https://github.com/kolovos/markingmate/wiki/screenshot.png)

## Status
![](https://travis-ci.org/kolovos/markingmate.svg?branch=master)
