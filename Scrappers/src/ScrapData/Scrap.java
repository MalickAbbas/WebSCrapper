package ScrapData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

public class Scrap{


    private final String allStoriesLink;
    private final String smallStoryDemoLink;
    private final String readMoreLink;
    private final String readMoreDivLink;
    private final String getReadMoreParagraphLink;
    private String[] array;
    private static CsvScrap csvDataSaving;
    private Thread thread1,thread2,thread3,thread4,thread5,thread6;
    private static final File file = new File("ScrapedData.csv");
    private static Scrap mainObject;


    public static Scrap getMainObject()
    {
        return mainObject;
    }
    public boolean isAllThreadDead()
    {
        return !(thread1.isAlive()||thread2.isAlive()||thread3.isAlive()||thread4.isAlive()||thread5.isAlive()||thread6.isAlive());
    }

    public void stopAllThreads()
    {
        if(thread1.isAlive())
            thread1.interrupt();
        if(thread2.isAlive())
            thread2.interrupt();
        if(thread3.isAlive())
            thread3.interrupt();
        if(thread4.isAlive())
            thread4.interrupt();
        if(thread5.isAlive())
            thread5.interrupt();
        if(thread6.isAlive())
            thread6.interrupt();

    }


    public Scrap(String link)
    {
        array = new String[6];


        extractLabels(link);


        allStoriesLink = "lx-stream-related-story";
        smallStoryDemoLink = "gel-5/8@l";
        readMoreLink = "qa-story-image-link";
        readMoreDivLink = "bbc-4wucq3 e57qer20";
        getReadMoreParagraphLink = "bbc-1sy09mr e1cc2ql70";

        try
        {

            csvDataSaving = new CsvScrap(file);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        Scrap.GiveMeLinks giveMeLinks1 = new GiveMeLinks(array[0],1);
        Scrap.GiveMeLinks giveMeLinks2 = new GiveMeLinks(array[1],2);
        Scrap.GiveMeLinks giveMeLinks3 = new GiveMeLinks(array[2],3);
        Scrap.GiveMeLinks giveMeLinks4 = new GiveMeLinks(array[3],4);
        Scrap.GiveMeLinks giveMeLinks5 = new GiveMeLinks(array[4],5);
        Scrap.GiveMeLinks giveMeLinks6 = new GiveMeLinks(array[5],6);


        thread1 = new Thread(giveMeLinks1);
        thread2 = new Thread(giveMeLinks2);
        thread3 = new Thread(giveMeLinks3);
        thread4 = new Thread(giveMeLinks4);
        thread5 = new Thread(giveMeLinks5);
        thread6 = new Thread(giveMeLinks6);


        threadProperties(thread1);
        threadProperties(thread2);
        threadProperties(thread3);
        threadProperties(thread4);
        threadProperties(thread5);
        threadProperties(thread6);
    }



    public void extractLabels(String link) {
        try {
            Document document = Jsoup.connect(link).get();
            Elements element = document.getElementsByClass("bbc-1v9wiw8 e1lim4kn2");
            int i=0;
            boolean status = true;
            for (Element rep : element) {
                if(status){
                    status=false;
                    continue;}
                if(i<6){
                    this.array[i] = "https://www.bbc.com" + rep.getElementsByClass("bbc-1x1owac e1lim4kn3").attr("href") ;
                }
                i++;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void categoryExplorer(String linkToCategory, int column,int row)
    {
        if(row==11) {return;}
        row = storyExtractor(linkToCategory,column,row);
        if(row==11){return;}
        categoryExplorer(getNextPageLink(linkToCategory), column, row);
    }


    public int storyExtractor(String linkToCategory,int column,int row)
    {
        try
        {
            Document document = Jsoup.connect(linkToCategory).get();
            Elements allStories = document.getElementsByClass(allStoriesLink);

            for(Element story:allStories)
            {
                if(row==11)
                {
                    return 11;
                }
                String data = story.getElementsByClass(smallStoryDemoLink).text();

                //Calling whole story extractor
                data+=extractWholeStory("https://www.bbc.com/"+story.getElementsByClass(readMoreLink).attr("href"));
                data = data.replaceAll("next",".");
                data = data.replaceAll(",","");
                data = data.replaceAll("\"","");
                csvDataSaving.set(row++,column,data).save(file);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return row;
    }


    public String extractWholeStory(String link)
    {
        StringBuilder data= new StringBuilder();
        try
        {
            Document document = Jsoup.connect(link).get();
            Elements storyParagraph = document.getElementsByClass(readMoreDivLink);
            for(Element paragraph:storyParagraph)
            {
                data.append(paragraph.getElementsByClass(getReadMoreParagraphLink).text());
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return data.toString();
    }


    public String getNextPageLink(String link)
    {
        try {
            Document document = Jsoup.connect(link).get();
            Elements pages = document.getElementsByClass("lx-pagination__controls lx-pagination__controls--right  qa-pagination-right");

            for(Element page:pages)
            {
                return "https://www.bbc.com"+page.getElementsByClass("lx-pagination__btn gs-u-mr+ qa-pagination-next-page lx-pagination__btn--rtl lx-pagination__btn--active").attr("href");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return link;
    }


    class GiveMeLinks implements Runnable
    {
        String link;
        int column;

        GiveMeLinks(String link, int column)
        {
            this.link = link;
            this.column = column;
        }


        public void run()
        {

            categoryExplorer(this.link,this.column,2);
        }
    }


    public void threadProperties(Thread thread)
    {
        thread.start();
        thread.setPriority(10);
    }
}
