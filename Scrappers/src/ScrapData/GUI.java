package ScrapData;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUI extends JFrame {

    private Timer timer;
    Node node = new Node();


    public GUI() throws Exception{
        super("Web Scrapper");
        setBounds(0, 0, 1350, 1050);
        setBackground(Color.black);
        setLayout(null);

        JLabel Unique = new JLabel("UNIQUE WORDS");
        Unique.setBounds(70,40,150,80);
        add(Unique);

        JTextArea UniqueWords = new JTextArea("");
        UniqueWords.setBounds(240,60,850,50);
        UniqueWords.setBackground(Color.lightGray);
        UniqueWords.setForeground(Color.BLACK);
        UniqueWords.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(UniqueWords);

        JLabel ten = new JLabel("TOP WORDS");
        ten.setBounds(70,150,150,80);
        add(ten);
        int count = 100;

        JTextArea Words = new JTextArea("");
        Words.setBounds(240,160,850,50);
        Words.setBackground(Color.lightGray);
        Words.setForeground(Color.BLACK);
       Words.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(Words);

        JLabel maxstory = new JLabel("MAXIMUM / LONGEST  STORY");
        maxstory.setBounds(200,220,200,50);
        add(maxstory);

        JTextArea story = new JTextArea("");
        story.setBounds(20,260,600,300);
        story.setFont(new Font("Lucida", Font.PLAIN, 28));
        story.setLineWrap(true);
        story.setWrapStyleWord(true);
        story.setBackground(Color.lightGray);
        story.setForeground(Color.BLACK);
        story.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(story);


        JLabel minstory = new JLabel("MINIMUM / SMALLEST  STORY");
        minstory.setBounds(900,220,200,50);
        add(minstory);

        JTextArea story1 = new JTextArea("");
        story1.setBounds(700,260,600,300);
        story1.setFont(new Font("Lucida", Font.PLAIN, 28));
        story1.setLineWrap(true);
        story1.setWrapStyleWord(true);
        story1.setBackground(Color.lightGray);
        story1.setForeground(Color.BLACK);
        story1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(story1);

        JLabel number = new JLabel("NUMBER OF STORIES SCRAPPED FROM EACH CATEGORY");
        number.setBounds(10,580,400,50);
        add(number);


        JTextArea numbers = new JTextArea("");
        numbers.setBounds(340,580,950,50);
        numbers.setBackground(Color.lightGray);
        numbers.setForeground(Color.BLACK);
        numbers.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(numbers);

        JButton btn = new JButton("START SCRAPPING");
        btn.setBounds(450,650,400,50);
        btn.setBackground(Color.lightGray);
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(btn);

        btn.addActionListener(e ->{

            Scrap test = new Scrap("https://www.bbc.com/urdu");
            timer = new Timer(1500,actionListener->{
                if(test.isAllThreadDead())
                {
                    String[][] top1 = node.countTopFrequency();
                    Words.setText(" " + top1[0][0] + "  ,  " + top1[0][1] + " " + "  " + top1[1][0] + "  ,  " + top1[1][1] + " " + "  " + top1[2][0] + "  ,  " + top1[2][1] + " " + "  " +  top1[3][0] + "  ,  " + top1[3][1] + " " + "  " + top1[4][0] + "  ,  " + top1[4][1]+ " " + "  " +  top1[5][0] + "  ,  " + top1[5][1]+ " " + "  " +  top1[6][0] + "  ,  " + top1[6][1]
                            + " " + "  " + top1[7][0] + "  ,  " + top1[7][1] + " " + "  " +  top1[8][0] + "  ,  " + top1[8][1] + " " +"  " +  top1[9][0] + "  ,  " + top1[9][1]);
                    int arr1[] = node.calculateStoriesInFile();
                    numbers.setText("PAKISTAN : " + count + "," + "AAS PASS : " + count + "," + "WORLD : " + count + "," + "KHEL : " + count + "," + "FUNKAR : " + count + "," + "SCIENCE : " + count);
                    String arr[] = node.maxAndMinStory();
                    int uWords = node.calculateUniqueWords();
                    UniqueWords.setText("     "+ uWords);
                    story1.setText(arr[0]);
                    story.setText(arr[1]);
                    timer.stop();
                }
            });
            timer.start();




        });



    }

    public static void main(String[] args) throws Exception{

        new GUI().setVisible(true);
    }
}
