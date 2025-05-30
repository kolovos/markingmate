# MarkingMate
MarkingMate is a cross-platform desktop application for marking and providing feedback on assessments. MarkingMate has several unique features:

* When you're marking an answer, it also displays the feedback and marks for other answers to the same question.
* When entering marks for an answer, you can use arithmetic expressions (e.g. `(3 - 0.5) + 2 - 1`) and MarkingMate will compute the result on the fly: you can see the result by selecting any other UI element. The literal expressions are saved, in case you want to revisit the detailed breakdown of a mark.
* It supports sentence-level text completion (<kbd>Ctrl</kbd>+<kbd>Space</kbd> or <kbd>Alt</kbd>+<kbd>Space</kbd>) to avoid re-typing feedback that applies to many answers. The default text completion is fuzzy (using [Lucene search queries](https://lucene.apache.org/core/10_2_1/queryparser/org/apache/lucene/queryparser/classic/package-summary.html#package.description)), but it can be switched to use exact substrings by using the "Tools - Suggestions - Substring" menu.
* It can use the [llm](https://llm.datasette.io/en/stable/) tool to help summarise the given feedback.

When you are done marking, you can export your marks into a CSV file and into individual text files with feedback for every student.

## Installation

The latest version of MarkingMate requires Java 21, due to Apache Lucene 10.x.

* Mac OSX: Download and run the DMG installer (MarkingMate.dmg) from the [Releases](https://github.com/kolovos/markingmate/releases) page.
* Windows/Linux: Download MarkingMate.zip from the [Releases](https://github.com/kolovos/markingmate/releases) page, extract its contents, and then double-click markingmate.jar

## Screenshots

![](https://i.imgur.com/YJhwNOV.png)
![](https://i.imgur.com/yzkFqy1.png)

## File Format

MarkingMate doesn't provide a user interface for creating questions. As such you will have to set up your marking file using a text editor (give it any filename/extension you like) and then open it with MarkingMate to do your marking. The format is (almost) self-explanatory:

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

Students can be created and deleted through the UI in recent versions, if preferred.

## Custom Marks and Feedback Generator

The `<Exam>` tag also accepts a `generator` attribute which can point to the absolute path of an [EGL](https://www.eclipse.org/epsilon/doc/articles/code-generation-tutorial-egl/) generator (.egx file) which MarkingMate should use instead of its built-in generator when it exports feedback. For inspiration, you can have a look at the [built-in generator](https://github.com/kolovos/markingmate/blob/master/io.dimitris.markingmate/resources/feedback.egx), which produces one CSV file with all the marks and one text file with feedback per student.

MarkingMate files are XMI documents conforming to an [Ecore metamodel](https://github.com/kolovos/markingmate/blob/master/io.dimitris.markingmate/markingmate.ecore), so you can use any EMF-compatible tool to query, analyse and transform them to different representations.

## LLM use

The program includes some template prompts to help refine the given feedback.
To use these prompts, ensure that:

* [llm](https://llm.datasette.io/en/stable/) is installed and configured with the appropriate keys and default model.
* `llm` is accessible from one of the folders in the `PATH` environment variable, or MarkingMate is launched with `-Dllm.path=/path/to/llm`.

Once these conditions are met, you can right-click on a feedback panel and select one of the options within the "LLM" submenu.
