import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;

import java.awt.*;
import java.util.Date;

public class test {

    private String[] sentences = {
            "my dog has fleas",
            "i like cold beverages",
            "the dog ate my homework",
            "don't have a cow man",
            "i don't think i like fleas"
    };

    public   static   void   main(String[]   args)   throws   Exception{
//        Robot r   =   new   Robot();
//        System.out.println( "延时前:"+new Date().toString()  );
//        r.delay(   2000   );
//        System.out.println(   "延时后:"+new Date().toString()   );
        System.out.println(new Fields("sentence", "test"));

    }


    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //declarer.declare(new Fields("sentence"));
    }
}
