import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class SentenceSpout extends BaseRichSpout {
    //发射器
    private SpoutOutputCollector collector;

    //定义输入的数据 这里可以是kafka的数据
    private String[] sentences = {
            "my dog has fleas",
            "i like cold beverages",
            "the dog ate my homework",
            "don't have a cow man",
            "i don't think i like fleas"
    };

    private int index = 0;

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //xiang
        declarer.declare(new Fields("sentence"));
    }

    public void open(Map config, TopologyContext content, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    public void nextTuple() {
        //输出到下一个Bolt
        this.collector.emit(new Values(sentences[index]));
        index++;
        if (index >= sentences.length) {
            index = 0;
        }

        //数据流只发送一次
//        if (index < sentences.length) {
//            this.collector.emit(new Values(sentences[index]));
//            index++;
//    }
        //Utils.waitForMillis(1);
    }
}
