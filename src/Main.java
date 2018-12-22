import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> lines = readArrayList();
        ArrayList<Question> questions = makeQuestions(lines);
        printTest(questions);
        System.out.println();
    }

    private static void printTest(ArrayList<Question> questions) {
        for(Question q: questions){
            System.out.println(q.printRawQuestion());
            System.out.println(q.printQuestion());
            System.out.println("######################################################################################");

        }
    }

    private static ArrayList<Question> makeQuestions(ArrayList<String> lines) {
        ArrayList<Question> questions = new ArrayList<>(100);
        Question q = null;
        int i = 0;
        while (i < lines.size()) {
            String s = lines.get(i);
            if (s.matches("^\\s*\\d+\\..*$")) { //New Line
                if (q != null) {
                    questions.add(q);
                }
                q = new Question();
                q.question = s;
                i++;

                //while (lines.get(i).indexOf("^") > -1) {
                while(i<lines.size() && !lines.get(i).matches("^\\s*\\d+\\..*$")){
                    q.answers.add(lines.get(i));
                    i++;
                }
               // i++;
            }
        }
        return questions;
    }

    public static ArrayList<String> readArrayList() {
        ArrayList<String> lines = new ArrayList<>(440);
        try {
            Scanner input = new Scanner(new File("originalQuestions.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                line.replace("^\\s+$", "");
                if (line.length() > 0) {
                    line.replace("\\s+", " ");
                    lines.add(line);
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }
}

class Question {
    public String question;
    public ArrayList<String> answers;

    public Question() {
        answers = new ArrayList<>();
    }

    public String printRawQuestion() {
        String s = "# " + question + "\n";
        for (String answer : answers) {
            s += "# " + answer + "\n";
        }
        return s;
    }

    public String printQuestion() {
        String answer = answers.get(0);
        answer = answer.replaceFirst("\\s*\\^\\s*","");
        String s = question + "\n";
        s += "*a. " + answer + "\n";
        s += "b.  \nc.  \nd.  \n\n@\n# Commentary: ";

        return s;
    }
}
