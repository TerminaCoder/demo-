import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class WordCountTopology {
    private static final String SENTENCE_SPOUT_ID = "sentence-spout";
    private static final String SPLIT_BOLT_ID = "split-bolt";
    private static final String COUNT_BOLT_ID = "count-bolt";
    private static final String REPORT_BOLT_ID = "report-bolt";
    private static final String TOPOLOGY_NAME = "topology-name";

    public static void main(String[] args) throws Exception {
        SentenceSpout spout = new SentenceSpout();
        SplitSentenceBolt splitBolt = new SplitSentenceBolt();
        WordCountBolt countBolt = new WordCountBolt();
        ReportBolt reportBolt = new ReportBolt();

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout(SENTENCE_SPOUT_ID, spout);

        //随机分配SplitSentenceBolt这个实例
        builder.setBolt(SPLIT_BOLT_ID, splitBolt).shuffleGrouping(SENTENCE_SPOUT_ID);

        //包含word关键字的被分配到WordCountBolt这个实例
        builder.setBolt(COUNT_BOLT_ID, countBolt).fieldsGrouping(SPLIT_BOLT_ID, new Fields("word"));

        //WordCountBolt的数据 全部发送到reportBolt这个bolt
        builder.setBolt(REPORT_BOLT_ID, reportBolt).globalGrouping(COUNT_BOLT_ID);

        Config config = new Config();

        //增加worker数量
        config.setNumWorkers(2);

        //线程数 + 任务数

        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());

        Thread.sleep(2000);

//        Robot r   =   new   Robot();
//        System.out.println( "延时前:"+new Date().toString()  );
//        r.delay(   2000   );
//        System.out.println(   "延时后:"+new Date().toString()   );

        cluster.killTopology(TOPOLOGY_NAME);
        cluster.shutdown();
    }
}
