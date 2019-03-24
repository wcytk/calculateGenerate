#1. Project Requirement Analysis
1. Input: An Integer
2. Output: The first line is my student id number, and n lines follows are equations and answers
3. Basic Format: Equations contains '+', '-', '*', '÷', but every equation must include more than 2, less than 5 symbols, at the mean time, the equation must include more than 2 different symbols
4. Requirements of calculation: Numbers in equations must be less than 100, decimal and negtive number is not allow to appear in either equations and the process of calculation, and the anwsers must be correct
5. Additional Function: To my opinion, there must be more than 1 pair of brackets when they appear. The requirements of fractions are quite simple, that all frations appears must be proper fraction.
#2. Design Ideas
My design ideas are quite direct.
First of all, generate the number of symbols, which also settles the quantity of numbers. 
Then generate all value of symbols and numbers. 
Next, calculate the equation by the symbols and numbers.
Finally, output the equation with the answer.
#3. Problems
Because of the direct idea I settled at the begining, all functions add afterwards are based on the original idea. The continuity may be acheived, however, it brings a series of problems to adding or editing functions. Meanly the confliction of calculation between basic principle and additonial principle, now I have to except some special conditions by using a number of judgment statements. This program haven't used any algorithm like Shunting Yard or RPN, but using quantities of judgment statements. So the logic is complicated, and the code is lengthy. Meanwhile, there are still some issues to be solved. Specific issues are as follows:

1. Exception handling, which is throwing excrptions when the input is not a number. And cyclic input till the input is a number.
	* The solution is quite simple, just using a while loop, and the try inside throws excption, then all you have to do is judge the condition of break statement.
2. Issues occurs when output '÷'
	* Sometimes it would garbled when output '÷'. By trying char and String, I found that String would not grabled but char would. So I convert all equation to String
3. How to convert char/int to String
	* Here, I used StringBuilder in Java to solve this problem, by checking it's source code, we can find `append` function toward every types of data(Inherit from AbstractStringBuilder), which can directly convert char/int/double, even boolean to StringBuilder, simply using `.toString()`. By the way, this function copied origin data and formed a new String, which won't affect the origin data.
4. How to output String to file
	* I used File, FileWriterand PrintWriter for file writing. Notice, you have to use exception handling on File, also you have to `flush` and `close` FileWriter and PringWriter. By flushing, data is written to file from buffer. By closing, the file stream is closed.
5. Other issues
	* Some issue are logically, like how to add brackets to a equation. But this kind of issues are complicated and hard to describe, also not very helpful.
#4. Versions
* Version 0.1 The basic functions of equations, didn't include brackets and fraction
* Version 0.2 Fraction is added
* Version 0.3 Part of brackets functions are added (Brackets won't appears after '*' and '÷'. Only one symbol is included. No Nesting brackets.)
* Version 0.4 (This version) Brackets can appear after '*' or '÷', but still only include one symbol, and no nesting brackets.
* Version 0.5 (Next version) Plan to get nesting brackets, but don't have a clue on muiltple symbols in brackets. 
#5. Run
	cd src/
	javac main.java
	java main
#6. Personal Software Process (PSP)
| PSP2.1        | Planed time   |  Actual time  |
| ------------- |:-------------:| -----:|
| Planning      | 10            |   8   |
| Estimate      | 10            |   8   |
| Development   | 180           |   628 |
| Analysis      | 25            |   18  |
| Design Spec   | 5             |   13  |
| Design Review | 5             |   12  |
| Coding Standard|5             |   8   |
| Design        | 20            |   36  |
| Coding        | 30            |   187 |
| Code Review   | 10            |   15  |
| est           | 80            |   339 |
| Reporting     | 10            |   16  |
| Test Report   | 2             |   3   |
| Size Measurement| 4           |   6   |
| Postmortem & Process Improvement Plan | 4 | 7 |
| Summary       | 200           |   652 |
#7. Summarize
After design and complete this program, I have realize the importance of Algorithm.
Writing this program simply using if-else is so freaking hard, especially after adding brackets, the situation became extremely complicated. And bugs are hiding deeply inside of codes, like NullPointerException, divide 0 or dead loop etc. So, it may not be a good idea just using if-else to write program, in next case, I would use some existing algorithm.


